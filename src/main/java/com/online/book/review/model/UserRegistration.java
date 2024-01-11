package com.online.book.review.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Този клас представлява регистрацията на потребители
@Entity
public class UserRegistration {
	@Id
	private String email; // полето email се анотира като основен ключ
	private String fullname; // поле за пълното име на потребителя
	private String password; // поле за паролата на потребителя
	private Date date; // поле за рожденната дата на потребителя
	private String gender; // поле за пола (мъж/жена) на потребителя
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}