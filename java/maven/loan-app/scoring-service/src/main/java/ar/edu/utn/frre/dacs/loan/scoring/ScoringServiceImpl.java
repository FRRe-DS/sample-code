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
package ar.edu.utn.frre.dacs.loan.scoring;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScoringServiceImpl implements ScoringService {
	
	// Dependencies -----------------------------------------------------------
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private SavingsAccountService savingsAccountService;

	// Overrides --------------------------------------------------------------
	
	@HystrixCommand(fallbackMethod = "defaultFicoRate")
	@Override
	public ScoringRate ficoRate(Long clientId) throws ClientNotFoundException {
		log.info("FICO Rating client with id: " + clientId);
		
		List<SavingsAccount> savings = savingsAccountService.findSavingsAccountByClientId(clientId);
		
		log.info("Savings Account: " + savings);
		
		Integer rate = rate(clientId);
		
		return ScoringRate.fromRate(rate);
	}

	@HystrixCommand(fallbackMethod = "defaultRate")
	@Override
	public Integer rate(Long clientId) throws ClientNotFoundException {
		log.info("Rating client with id: " + clientId);
		
		Client client = clientService.findOneClient(clientId);
		
		if(client == null) {
			log.warn("Client with id: " + clientId + " not found!" );
			throw new ClientNotFoundException(clientId);
		}
		
		log.info("Client Returned: " + client);
		
		Random rdn = new Random();
		return Integer.valueOf(300 + rdn.nextInt(550));
	}

	
	public ScoringRate defaultFicoRate(Long clientId) throws ClientNotFoundException {
		log.error("This is a fallback");
		return ScoringRate.VERY_POOR;
	}
	
	public Integer defaultRate(Long clientId) throws ClientNotFoundException {
		log.error("This is a fallback");
		return Integer.valueOf(300);
	}
}
