package org.library.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BookLoansDAO;
import org.library.model.BookLoans;
import org.library.model.Books;
import org.library.model.Borrowers;

/**
 * Servlet implementation class Borrow
 */
@WebServlet("/borrow")
public class Borrow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Borrow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String bookID = request.getParameter("bookId");
		System.out.println("Book ID is :" + bookID);
		
		//check if the book is present or not
		Books bookBorrow = new Books();
		bookBorrow.setIsbn(bookID);
		BookLoansDAO bookLoanBorrow = new BookLoansDAO();
		Boolean flag = false;
		
		flag= bookLoanBorrow.checkBookAvailability(bookBorrow);
		
		if(flag) {//if yes then
			String url="/getlistofborrowers.jsp";
			HttpSession session = request.getSession(false);
			request.setAttribute("bookID",bookID);
			session.setAttribute("bookID", bookID);
			session.setAttribute("searchfieldBorrower", null);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		else
		{
			System.out.println("The Book is currently not available!");
			String Message = "The Book is currently not available!";
			HttpSession session = request.getSession(false);
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
