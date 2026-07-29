package com.web.shs_demo.account.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.web.shs_demo.book.model.Book;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = -101885742532588984L;

	public static final String SESSION_KEY = "user_session_key";

	@Id
	@GeneratedValue
	private Long id;
	@Column(name="username",nullable=false,length=20)
	private String username;
	@Column(name="password",nullable=false,length=20)
	private String password;
	private String confirmPassword;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
	private Set<Book> books = new HashSet<Book>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Book getBook(Long bookId) {
		for (Book book : books) {
			if (bookId != null && bookId.equals(book.getId())) {
				return book;
			}
		}
		return null;
	}
}
