package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.AccountDAO;
import com.revature.entity.Account;

@Service
public class AccountService {

	private AccountDAO accdao;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	public AccountService(AccountDAO accdao) {
		this.accdao = accdao;
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
        return this.accdao.save(acc);
    }

    
    public Account findById(int accId) {
    	MDC.put("event", "select");
    	MDC.put("Account ID", Integer.toString(accId));
    	logger.info("FInding account by id");
    	MDC.clear();
    	return this.accdao.findAccountByAccId(accId);
    }
    
    public List<Account> findAccountById(int userId) {
    	MDC.put("event", "select");
    	MDC.put("User ID", Integer.toString(userId));
    	logger.info("Finding account by user id");
    	MDC.clear();
    	return this.accdao.findAccountByUserId(userId);
    }
    
}
