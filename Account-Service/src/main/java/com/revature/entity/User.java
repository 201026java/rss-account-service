package com.revature.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS_TABLE")
public class User implements Serializable {

	private static final long serialVersionUID = 8701888850828763090L;

	@Id
	@NotNull
	@Column(name = "U_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@NotNull
	@Column(name = "U_EMAIL", unique = true)
	private String email;

	@NotNull
	@Column(name = "U_PASSWORD")
	private String password;

	@Column(name = "U_PIC", nullable = true, columnDefinition = "BYTEA")
	private byte[] profilePic;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "IS_ADMIN")
	private boolean isAdmin = false;

	@Column(name = "user_discounted")
	private Boolean userDiscounted = false;

	@Column(name = "user_discount")
	private Integer userDiscount = 0;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Account> accId = new HashSet<>();

	public User() {
		super();
	}

	public User(@NotNull int userId, @NotNull String email, @NotNull String password, byte[] profilePic,
			String firstName, String lastName, boolean isAdmin) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
	}

	public User(@NotNull int userId, @NotNull String email, @NotNull String password, byte[] profilePic,
			String firstName, String lastName, boolean isAdmin, Boolean userDiscounted, Integer userDiscount,
			Set<Account> accId) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.userDiscounted = userDiscounted;
		this.userDiscount = userDiscount;
		this.accId = accId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getUserDiscounted() {
		return userDiscounted;
	}

	public void setUserDiscounted(Boolean userDiscounted) {
		this.userDiscounted = userDiscounted;
	}

	public Integer getUserDiscount() {
		return userDiscount;
	}

	public void setUserDiscount(Integer userDiscount) {
		this.userDiscount = userDiscount;
	}

	public Set<Account> getAccId() {
		return accId;
	}

	public void setAccId(Set<Account> accId) {
		this.accId = accId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(profilePic);
		result = prime * result
				+ Objects.hash(email, firstName, isAdmin, lastName, password, userDiscount, userDiscounted, userId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& isAdmin == other.isAdmin && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Arrays.equals(profilePic, other.profilePic)
				&& Objects.equals(userDiscount, other.userDiscount)
				&& Objects.equals(userDiscounted, other.userDiscounted) && userId == other.userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", profilePic="
				+ Arrays.toString(profilePic) + ", firstName=" + firstName + ", lastName=" + lastName + ", isAdmin="
				+ isAdmin + ", userDiscounted=" + userDiscounted + ", userDiscount=" + userDiscount + ", accId=" + accId
				+ "]";
	}

}
