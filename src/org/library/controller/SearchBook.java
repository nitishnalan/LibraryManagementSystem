package org.library.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BooksDAO;
import org.library.helpers.ReadQuery;

/**
 * Servlet implementation class SearchBook
 */
@WebServlet("/searchbook")
public class SearchBook extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    int offset;
    int length;
    List list;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBook() {
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
		
		//to give reference to first page
		int page = 1;
		request.setAttribute("page", page);
		
		String url="/diplaysearchresults.jsp?page="+page;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		HttpSession session = request.getSession(false);
		session.setAttribute("searchfieldDocument", searchFieldController);
		session.setAttribute("page", page);
		session.setAttribute("totalpagesneeded", numbOfPages);
		response.sendRedirect("diplaysearchresults.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
/*		ReadQuery rq = new ReadQuery();
		
		rq.read();
		
		String table = rq.getHTMLTable();
		System.out.println(table);
		request.setAttribute("table", table);
		String url="/diplaysearchresults.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		
		dispatcher.forward(request, response);*/
		
		BooksDAO bookData = new BooksDAO();
		int pagesize = bookData.getSearchResultSize();
		request.setAttribute("pagesize", pagesize);
		
		//to give reference to first page
		int page = 1;
		request.setAttribute("page", page);
		
		String url="/diplaysearchresults.jsp?page="+page;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
    public List getPages() {
        List pageNumbers = new ArrayList();
        int pages = list.size() / this.length;
        if (list.size() % this.length != 0) {
            pages = pages + 1;
        }
 
        for (int i = 1; i <= pages; i++) {
            pageNumbers.add(new Integer(i));
        }
        return pageNumbers;
    }

}
