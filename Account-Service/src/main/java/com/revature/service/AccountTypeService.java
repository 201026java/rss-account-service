package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.AccountTypeDAO;
import com.revature.entity.AccountType;
import com.revature.exceptions.AccountIdException;
import com.revature.exceptions.AccountTypeValueEmptyException;

@Service
public class AccountTypeService {

	private AccountTypeDAO acctypedao;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountTypeService.class);
	
	@Autowired
	public AccountTypeService(AccountTypeDAO acctypedao) {
		this.acctypedao = acctypedao;
	}

	public List<AccountType> getAllAccTypes() {
		MDC.put("event", "select");
    	logger.info("Finding all AcccountTypes");
    	MDC.clear();
		return acctypedao.findAll();
	}
	
	public AccountType addAccountType(AccountType accType) {
		MDC.put("event", "create");
    	logger.info("Creating AccountType");
    	MDC.clear();
    	if(accType.getType()==null)
    		throw new AccountTypeValueEmptyException("The request body's type field is empty");
		return acctypedao.save(accType);
	}
	
	public AccountType findById(int accTypeId) {
		MDC.put("event", "select");
		MDC.put("AccountType id", Integer.toString(accTypeId));
    	logger.info("Finding AccountType by id");
    	MDC.clear();
        return this.acctypedao.findAccTypeByAccTypeId(accTypeId).orElseThrow(() -> new AccountIdException("Account Type with id:" + accTypeId + " dose not exist."));
    }
	
}
