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
package ar.edu.utn.frre.dacs.loan.savings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SavingsAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final SavingsAccount DEFAULT;
	
	// Constructos ------------------------------------------------------------

	static {
		DEFAULT = new SavingsAccount();
		DEFAULT.setNumber(0L);
		DEFAULT.setClientId(0L);
		DEFAULT.setName("Def Sav Acc");
	}	
	
	// Properties -------------------------------------------------------------
	
	@Id
	private Long number;
	
	private Long clientId;
	
	private String name;
	
	private BigDecimal balance = BigDecimal.ZERO;
	
	@JsonIgnore
	@OneToMany(mappedBy="savingsAccount", fetch = FetchType.LAZY)
	private Set<Transaction> transactions = new HashSet<Transaction>();

	// Getters/Setters --------------------------------------------------------

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction createDeposit(BigDecimal ammount) {
		if(ammount == null || ammount.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Invalid ammount: " + String.valueOf(ammount));
		return createTransaction(ammount, TransactionType.CREDIT);
	}

	public Transaction createWithdraw(BigDecimal ammount) {
		if(ammount == null || ammount.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Invalid ammount: " + String.valueOf(ammount));
		return createTransaction(ammount, TransactionType.DEBIT);
	}
	
	private Transaction createTransaction(BigDecimal ammount, TransactionType type) {
		Transaction tx = new Transaction();
		
		tx.setSavingsAccount(this);
		tx.setDate(new Date());
		tx.setType(type);
		tx.setAmmount(ammount);
		
		return tx;		
	}

	public void applyTransaction(Transaction tx) {
		if(tx == null)
			throw new IllegalArgumentException("Invalid tx");
		
		switch (tx.getType()) {
		case CREDIT:
			this.balance = this.balance.add(tx.getAmmount());		
			break;
		case DEBIT:
			this.balance = this.balance.subtract(tx.getAmmount());
		default:
			break;
		}
	}
}
