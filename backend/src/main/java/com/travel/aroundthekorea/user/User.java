package com.travel.aroundthekorea.user;

import org.hibernate.annotations.ColumnDefault;

import com.travel.aroundthekorea.common.context.entity.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AttributeOverride(name = "createdAt", column = @Column(name = "signupAt"))
@AttributeOverride(name = "updatedAt", column = @Column(name = "changedAt"))
@Table(name = "users")
@Entity
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;
	private String password;
	private String phoneNumber;

	protected User() {
	}

	public User(String username, String password, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
