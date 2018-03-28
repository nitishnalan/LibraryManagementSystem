package org.library.model;

public class Fines {
	
	private String id;
	private String book_loan_id;
	private String fine_amount;
	private boolean paid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

}
