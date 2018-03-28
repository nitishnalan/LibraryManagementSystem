package org.library.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BookLoansDAO;
import org.library.helpers.FinesDAO;
import org.library.model.BookLoans;

/**
 * Servlet implementation class checkinpay
 */
@WebServlet("/checkinpay")
public class checkinpay extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkinpay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		String loanIdCheckIn = (String) request.getParameter("loanId");
		String bookIdCheckIn = (String) request.getParameter("bookId");
		String borrowerIdCheckIn = request.getParameter("borrowerId");
		String paymentCheckIn = request.getParameter("payment");
		
		System.out.println("bookIdCheckout" + bookIdCheckIn);
		BookLoans bookCheckIn = new BookLoans();
		session.setAttribute("searchBookLoansController", null);
		bookCheckIn.setId(loanIdCheckIn);
		bookCheckIn.setBook_id(bookIdCheckIn);
		bookCheckIn.setBorrower_id(borrowerIdCheckIn);

		session.setAttribute("loanIdCheckIn", loanIdCheckIn);
		session.setAttribute("bookIdCheckIn", bookIdCheckIn);
		session.setAttribute("borrowerIdCheckIn", borrowerIdCheckIn);
		System.out.println("loanIdCheckIn"+ loanIdCheckIn);
		System.out.println("bookIdCheckIn"+ bookIdCheckIn);
		System.out.println("borrowerIdCheckIn"+ borrowerIdCheckIn);
		System.out.println("paymentCheckIn"+ paymentCheckIn);
		FinesDAO checkFine = new FinesDAO();
		boolean fine = checkFine.checkNeedToPayFine(loanIdCheckIn,bookIdCheckIn,borrowerIdCheckIn);
		boolean ifEntryInFinesTableExists = checkFine.ifEntryIsPresentInFines(loanIdCheckIn);
		//check if there is a fine against this particular record
		//if yes then deny check in and ask the user to pay the fine first
		System.out.println("flag " + fine);
		if(fine)
		{	
			if(paymentCheckIn.matches("1")) {
			System.out.println("Inside IF to update fine");
			System.out.println(" paymentCheckIn where it matches 1" + paymentCheckIn);
			
			checkFine.payTheFineForThisLoanId(loanIdCheckIn);

			
			BookLoansDAO checkINBook = new BookLoansDAO();
			checkINBook.checkInTheBook(bookCheckIn);
			
			System.out.println("Record Updated 2!");
			String suMessage = "The CheckIn is Successful!";
			
			session.setAttribute("Message1", suMessage);
			response.sendRedirect("message.jsp");
		}
			if(paymentCheckIn.matches("0"))
			{
				System.out.println("Inside IF @ to update fine");
				System.out.println(" paymentCheckIn where it matches 0" + paymentCheckIn);
//				String url="/payfineduringcheckin.jsp";
//				//request.setAttribute("bookID",bookID);
//				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//				dispatcher.forward(request, response);
				//checkInBook set date_in as todays date
				
				BookLoans bookToCheckIn = new BookLoans();
				FinesDAO updateFine = new FinesDAO();
				System.out.println(loanIdCheckIn);
				if(ifEntryInFinesTableExists) {
					System.out.println("If entry is in Fines Table");
					bookToCheckIn = updateFine.getDetailsForUpdateInFineTableAfterCheckIn(loanIdCheckIn);
					System.out.println(bookToCheckIn.getBook_id() + " " + bookToCheckIn.getDateDiff());
					updateFine.updateRecordInFinesDuringCheckIn(bookToCheckIn);
				}
				else
				{
					System.out.println("If entry is NOT in Fines Table");
					bookToCheckIn = updateFine.getRecordsForFineForSingleBookCheckIn(loanIdCheckIn);
					System.out.println(bookToCheckIn.getBook_id() + " " + bookToCheckIn.getDateDiff());
					updateFine.insertRecordInFinesForSingleBookCheckIn(bookToCheckIn);
				}			
				
				
				BookLoansDAO checkINBook = new BookLoansDAO();
				checkINBook.checkInTheBook(bookCheckIn);
				//response.sendRedirect("payfineduringcheckin.jsp");
				
				response.sendRedirect("payfineaftercheckin.jsp");			

			}

		}
		else
		{
			BookLoansDAO checkINBook = new BookLoansDAO();
			checkINBook.checkInTheBook(bookCheckIn);
			
			System.out.println("Record Updated!");
			String suMessage = "The CheckIn is Successful!";
			
			session.setAttribute("Message1", suMessage);
			response.sendRedirect("message.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
