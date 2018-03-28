package org.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BookLoansDAO;
import org.library.model.BookLoans;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		String bookIdCheckout = (String) session.getAttribute("bookID");
		String borrowerIdCheckout = request.getParameter("borrowerId");
		System.out.println("bookIdCheckout" + bookIdCheckout);
		BookLoans bookCheckout = new BookLoans();
		bookCheckout.setBook_id(bookIdCheckout);
		bookCheckout.setBorrower_id(borrowerIdCheckout);
		boolean flagBorrowerPermitted = false;
		BookLoansDAO borrowerPermitted = new BookLoansDAO();
		flagBorrowerPermitted = borrowerPermitted.checkBorrowerPermitted(bookCheckout);
				
		if(flagBorrowerPermitted) {	//check if the borrower is permitted
			BookLoansDAO bookLoanCheckout = new BookLoansDAO();
			bookLoanCheckout.insertRecordInBookLoans(bookCheckout);
			
			System.out.println("Record Inserted!");
			String suMessage = "The book has been checked out successfully!";
			
			session.setAttribute("Message1", suMessage);
			response.sendRedirect("message.jsp");
		}
		else
		{
			System.out.println("The Borrower has 3 books with him already! We can not assign more than 3 books.");
			String Message = "The Borrower has 3 books with him already! We can not assign more than 3 books.";
		
			session.setAttribute("Message1", Message);
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
