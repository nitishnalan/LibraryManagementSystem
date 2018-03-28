package org.library.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.BooksDAO;
import org.library.helpers.BorrowersDAO;

/**
 * Servlet implementation class BorrowerSearch
 */
@WebServlet("/borrowersearch")
public class BorrowerSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
    int offset;
    int length;
    List list;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String searchFieldController = request.getParameter("searchfield");
		BorrowersDAO borrower = new BorrowersDAO();
		HttpSession session = request.getSession(false);
		session.setAttribute("searchfieldBorrower", searchFieldController);
		int page = 1;
		session.setAttribute("page", page);
		//session.setAttribute("totalpagesneeded", numbOfPages);
		//response.sendRedirect("displaylistofborrowers.jsp");
		String url="/getlistofborrowers.jsp";
		//request.setAttribute("bookID",bookID);
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
		
//        int maxEntriesPerPage = 3;
//        int page = 1;
// 
//        String pageNumberValue = request.getParameter("pageNumber");
// 
//        if (pageNumberValue != null) {
//            try {
//                page = Integer.parseInt(pageNumberValue);
//                System.out.println("Page Number:" + page);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
        //int offset = maxEntriesPerPage * (page - 1);
/*        TestList(offset, maxEntriesPerPage);
 
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("pages", getPages());
        httpSession.setAttribute("studentDetails", getListByOffsetAndLength());
 
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("reportWithPagination.jsp");
        dispatcher.forward(request, response)*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
