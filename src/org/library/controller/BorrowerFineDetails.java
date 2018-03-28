package org.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BooksDAO;
import org.library.helpers.BorrowersDAO;
import org.library.helpers.FinesDAO;

/**
 * Servlet implementation class BorrowerFineDetails
 */
@WebServlet("/borrowerfinedetails")
public class BorrowerFineDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerFineDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String borrowerId = request.getParameter("borrowerId");
		FinesDAO borrowerData = new FinesDAO();
	
		HttpSession session = request.getSession(false);
		session.setAttribute("borrowerId", borrowerId);
;
		response.sendRedirect("showFineSummary.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
