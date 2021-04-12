package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ACCOUNT_TABLE")
public class Account implements Serializable {

	private static final long serialVersionUID = 5247569089247802332L;

	@Id
	@Column(name = "ACC_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "U_ID")
	@JsonBackReference
	private User user;

	// @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	// @JoinColumn(name = "U_ID_FK", referencedColumnName = "U_ID")
//    @Column(name = "U_ID")
//    private int userId;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ACCTYPE_ID", referencedColumnName = "ACCTYPE_ID")
	private AccountType accountType;

	// @OneToOne(targetEntity = AccountType.class, cascade = CascadeType.ALL)
	// @JoinColumn(name = "ACCTYPE_ID_FK", referencedColumnName = "ACCTYPE_ID")
//    @Column(name = "ACCTYPE_ID")
//    private int accTypeId;

	@Column(name = "POINTS")
	private int points = 0;

	public Account() {
		super();
	}

	public Account(int accId, @NotNull User user, @NotNull AccountType accountType, int points) {
		super();
		this.accId = accId;
		this.user = user;
		this.accountType = accountType;
		this.points = points;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accId, accountType, points, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return accId == other.accId && Objects.equals(accountType, other.accountType) && points == other.points
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", user=" + user + ", accountType=" + accountType + ", points=" + points
				+ "]";
	}

}
