package com.revature.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.Account;
import com.revature.entity.User;

@Repository
public interface AccountDAO extends JpaRepository<Account, Integer> {
	
	public Optional<List<Account>> findAccountByUser(User user);
	
	public Optional<Account> findAccountByAccId(int accId);
}
