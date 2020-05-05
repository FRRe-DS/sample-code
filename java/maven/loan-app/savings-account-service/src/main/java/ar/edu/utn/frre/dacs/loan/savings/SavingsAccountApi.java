/*
 * Copyright (C) 2015-2019 UTN-FRRe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.edu.utn.frre.dacs.loan.savings;

import java.math.BigDecimal;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ar.edu.utn.frre.dacs.loan.savings.dao.SavingsAccountRepository;
import ar.edu.utn.frre.dacs.loan.savings.dao.TransactionRepository;
import ar.edu.utn.frre.dacs.loan.savings.model.SavingsAccount;
import ar.edu.utn.frre.dacs.loan.savings.model.Transaction;

@RestController
public class SavingsAccountApi {

	protected Logger logger = LoggerFactory.getLogger(SavingsAccountApi.class.getName());

	// Dependencies -----------------------------------------------------------
	
	@Autowired
	private SavingsAccountRepository repository;
	
	@Autowired
	private TransactionRepository txRepository;
	
	// Methods ----------------------------------------------------------------

	@HystrixCommand(fallbackMethod = "defaultFindAll")
	@RequestMapping(value = "/savings", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> findAllSavingsAccounts() {
		logger.info("Returning all savings account");
		
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@HystrixCommand(fallbackMethod = "defaultFindOne")
	@RequestMapping(value = "/savings/{number}", method = RequestMethod.GET) 
	public ResponseEntity<?> findOneSavingAccount(
			@PathVariable("number") Long number) {
		logger.info("Returning saving account with number: " + number);
		
		if(number == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		SavingsAccount sa = repository.findOne(number);
		
		if(sa == null) {
			logger.info("Returning saving account with number: " + number + " not found!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sa, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/savings", method = RequestMethod.POST)
	public ResponseEntity<?> createSavingsAccount(@RequestBody SavingsAccount savingsAccount) {
		logger.info("Creating Savings Account: " + savingsAccount);
		
		SavingsAccount c = repository.save(savingsAccount);
		
		return new ResponseEntity<>(c, HttpStatus.CREATED);
	}
		
	@RequestMapping(value = "/savings", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSavingsAccount(@RequestBody SavingsAccount savingsAccount) {
		logger.info("Updating saving account with number: " + savingsAccount.getNumber());
		
		if(savingsAccount == null || savingsAccount.getNumber() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		if(!repository.exists(savingsAccount.getNumber())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		
		repository.save(savingsAccount);
		
		return new ResponseEntity<>(savingsAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/savings", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSavingsAccount(@RequestBody SavingsAccount savingsAccount) {
		logger.info("Deleting cliente: " + savingsAccount);

		if(savingsAccount == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		return deleteSavingsAccountById(savingsAccount.getNumber());		
	}	

	@RequestMapping(value = "/savings/{number}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSavingsAccountById(@PathVariable("number") Long number) {
		logger.info("Deleting Savins Account with number: " + number);

		if(number == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		

		if(!repository.exists(number)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		
		repository.delete(number);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/savings/client/{clientId}", method = RequestMethod.GET)
	public ResponseEntity<?> findSavingsAccountByClientId(@PathVariable("clientId") Long clientId) {
		logger.info("Returning all savins account by client id:" + clientId);
		
		if(clientId == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		return new ResponseEntity<>(repository.findByClientId(clientId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/savings/{number}/tx", method = RequestMethod.GET) 
	public ResponseEntity<?> transactionsBySavingsAccount(
			@PathVariable("number") Long number) {
		logger.info("Returning transaccionts of saving account with number: " + number);
	
		if(number == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		SavingsAccount sa = repository.findOne(number);
		
		if(sa == null) {
			logger.info("Returning saving account with number: " + number + " not found!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				txRepository.findBySavingsAccount(sa), 
				HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "/savings/{number}/deposit", method = RequestMethod.POST) 
	public ResponseEntity<?> depositFoundOnSavingsAccount(
			@PathVariable("number") Long number,
			@RequestBody BigDecimal amount) {
		
		logger.info("Depositing money on saving account with number: " + number);
		
		if(number == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		if(amount == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		SavingsAccount sa = repository.findOne(number);
		
		if(sa == null) {
			logger.info("Returning saving account with number: " + number + " not found!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Transaction tx = sa.createDeposit(amount);
		
		txRepository.save(tx);
		
		logger.info("Balance: " + sa.getBalance());		
		sa.applyTransaction(tx);
		logger.info("Balance: " + sa.getBalance());
		repository.saveAndFlush(sa);
				
		return new ResponseEntity<>(tx, HttpStatus.CREATED);
	}
			
	@RequestMapping(value = "/savings/{number}/withdraw", method = RequestMethod.POST) 
	public ResponseEntity<?> withdrawFoundOnSavingsAccount(
			@PathVariable("number") Long number,
			@RequestBody BigDecimal amount) {
		
		logger.info("Withdrawing money on saving account with number: " + number);

		if(number == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		if(amount == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		
		SavingsAccount sa = repository.findOne(number);
		
		if(sa == null) {
			logger.info("Returning saving account with number: " + number + " not found!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Transaction tx = sa.createWithdraw(amount);
		
		txRepository.save(tx);
		sa.applyTransaction(tx);
		repository.save(sa);
		
		return new ResponseEntity<>(tx, HttpStatus.CREATED);
	}
	
	
	// Fallback Methods -------------------------------------------------------
	
	public ResponseEntity<?> defaultFindAll() {
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> defaultFindOne() {
		return new ResponseEntity<>(SavingsAccount.DEFAULT, HttpStatus.OK);
	}
}
