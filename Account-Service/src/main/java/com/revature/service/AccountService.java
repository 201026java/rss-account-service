package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.UserNotFoundException;

@Service
public class AccountService {

	private AccountDAO accdao;
	private UserDAO userdao;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	public AccountService(AccountDAO accdao, UserDAO userdao) {
		this.accdao = accdao;
		this.userdao = userdao;
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
    	return this.accdao.findAccountByAccId(accId).orElseThrow(() -> new AccountNotFoundException());
    }
    
    public List<Account> findAccountById(int userId) {
    	MDC.put("event", "select");
    	MDC.put("User ID", Integer.toString(userId));
    	logger.info("Finding account by user id");
    	MDC.clear();
    	User u = userdao.findById(userId).orElseThrow(() -> new UserNotFoundException());
    	return this.accdao.findAccountByUser(u);
    }
    
}
