package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountTypeDAO;
import com.revature.dao.UserDAO;
import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InvalidNewAccountException;
import com.revature.exceptions.UserNotFoundException;

@Service
public class AccountService {

	private AccountDAO accdao;
	private UserDAO userdao;
	private AccountTypeDAO acctypedao;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	public AccountService(AccountDAO accdao, UserDAO userdao, AccountTypeDAO acctypedao) {
		this.accdao = accdao;
		this.userdao = userdao;
		this.acctypedao = acctypedao;
	}

    public List<Account> getAllUsers() {
    	MDC.put("event", "select");
    	logger.info("Finding all accounts");
    	MDC.clear();
        return this.accdao.findAll();
    }

    public Account addAccount(Account acc) {
    	MDC.put("event", "create");
    	logger.info("Creating account");
    	MDC.clear();
    	if(acc.getUser()==null
    			|| acc.getAccountType()==null)
    		throw new InvalidNewAccountException("Required request body is incorrect");
    	
    	if(!userdao.existsById(acc.getUser().getUserId()))
    		throw new InvalidNewAccountException("Unable to find User with id:"+ acc.getUser().getUserId() +"; can't attach new account");
    	if(!acctypedao.existsById(acc.getAccountType().getAccTypeId()))
    		throw new InvalidNewAccountException("Unable to find account type with id:" + acc.getAccountType().getAccTypeId() + "; can't attach new account");
    	
        return this.accdao.save(acc);
    }

    
    public Account findById(int accId) {
    	MDC.put("event", "select");
    	MDC.put("Account ID", Integer.toString(accId));
    	logger.info("FInding account by id");
    	MDC.clear();
    	return this.accdao.findAccountByAccId(accId).orElseThrow(() -> new AccountNotFoundException("Account with id:" + accId +" not found"));
    }
    
    public List<Account> findAccountById(int userId) {
    	MDC.put("event", "select");
    	MDC.put("User ID", Integer.toString(userId));
    	logger.info("Finding account by user id");
    	MDC.clear();
    	User u = userdao.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id"+ userId+" not found"));
    	return this.accdao.findAccountByUser(u).orElseThrow(() -> new AccountNotFoundException("User has no accounts"));
    }
    
}
