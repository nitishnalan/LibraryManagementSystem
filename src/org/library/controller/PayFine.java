package org.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BookLoansDAO;
import org.library.helpers.FinesDAO;

/**
 * Servlet implementation class PayFine
 */
@WebServlet("/payfine")
public class PayFine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayFine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		String bookLoanId = (String) request.getParameter("bookLoanId");
		BookLoansDAO checkForBook = new BookLoansDAO();
		Boolean checkIfBookRet = false;
	
		checkIfBookRet = checkForBook.checkIfBookReturned(bookLoanId);
		
		if(checkIfBookRet) {
			System.out.println("payment can be accepted");
			FinesDAO payFine = new FinesDAO();
			payFine.payTheFineForThisLoanId(bookLoanId);
			
			System.out.println("Payment for this Book_Loan has been accepted!");
			String suMessage = "Payment for this Book_Loan has been accepted!";
			
			session.setAttribute("Message1", suMessage);
			response.sendRedirect("message.jsp");
		}
		else {
			System.out.println("payment can not be accepted since the book has not been returned!");
			String suMessage = "Payment can not be accepted since the book has not been returned! "
					+ "The borrower  has to return the book first, and then pay the fine.";
			
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
