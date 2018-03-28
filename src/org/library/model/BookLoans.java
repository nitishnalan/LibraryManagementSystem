package org.library.model;

import java.util.Date;

public class BookLoans {

	private String id;
	private String book_id;
	private String borrower_id;
	private Date date_out;
	private Date date_in;
	
	private Date due_date;
	
	//For getting names of borrower
	private String borrower_name;
	//for getting difference in date
	private int Date_Difference;
	
	
	public int getDateDiff() {
		return Date_Difference;
	}

	public void setDateDiff(int Date_Difference) {
		this.Date_Difference = Date_Difference;
	}

	public String getBorrower_name() {
		return borrower_name;
	}

	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setDate_in(java.sql.Date date) {
		this.date_in = date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	
}
