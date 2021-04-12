package com.revature.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.AccountType;

@Repository
public interface AccountTypeDAO extends JpaRepository<AccountType, Integer> {
	public Optional<AccountType> findAccTypeByAccTypeId(int accTypeId);
}
