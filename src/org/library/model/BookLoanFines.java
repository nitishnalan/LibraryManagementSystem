package org.library.model;

import java.util.Date;

public class BookLoanFines {
	
	private String fine_id;
	private String book_loan_id;
	private String book_name;
	private String fine_amount;
	private boolean paid;
	
	private String book_id;
	private String borrower_id;
	private Date date_out;
	private Date date_in;	
	private Date due_date;
	private String borrower_name;
	
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	
	public String getBorrower_name() {
		return borrower_name;
	}
	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}
	public String getFine_id() {
		return fine_id;
	}
	public void setFine_id(String fine_id) {
		this.fine_id = fine_id;
	}
	public String getBook_loan_id() {
		return book_loan_id;
	}
	public void setBook_loan_id(String book_loan_id) {
		this.book_loan_id = book_loan_id;
	}
	public String getFine_amount() {
		return fine_amount;
	}
	public void setFine_amount(String fine_amount) {
		this.fine_amount = fine_amount;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBorrower_id() {
		return borrower_id;
	}
	public void setBorrower_id(String borrower_id) {
		this.borrower_id = borrower_id;
	}
	public Date getDate_out() {
		return date_out;
	}
	public void setDate_out(Date date_out) {
		this.date_out = date_out;
	}
	public Date getDate_in() {
		return date_in;
	}
	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	
	
	
}
