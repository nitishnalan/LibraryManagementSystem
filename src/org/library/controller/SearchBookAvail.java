package org.library.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BooksDAO;

/**
 * Servlet implementation class SearchBookAvail
 */
@WebServlet("/searchbookavailability")
public class SearchBookAvail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBookAvail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String searchFieldController = request.getParameter("searchfield");
		BooksDAO bookData = new BooksDAO();
		int queryResultSize = bookData.getSearchResultSize();
		int pagesize = 5;
		int numbOfPages = queryResultSize/pagesize;
		
        if (queryResultSize % pagesize != 0) {
        	numbOfPages = numbOfPages + 1;
        }
 
		
		request.setAttribute("pagesize", pagesize);
	
		
		HttpSession session = request.getSession(false);
		session.setAttribute("searchfieldDocument", searchFieldController);

		session.setAttribute("totalpagesneeded", numbOfPages);
		response.sendRedirect("checkAvailability.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
