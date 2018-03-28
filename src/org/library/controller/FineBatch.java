package org.library.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.library.helpers.FinesDAO;
import org.library.model.BookLoans;

/**
 * Servlet implementation class FineBatch
 */
@WebServlet("/runFineBatch")
public class FineBatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FineBatch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Inside FINE Batch");
		FinesDAO fine = new FinesDAO();
		//fetch the records for update statement
		List<BookLoans> listUpdate=new ArrayList<BookLoans>();
		listUpdate = fine.getRecordsForUpdateInFineTable();
		
		//fetch the records for insert statement 
		fine.updateRecordInFines(listUpdate);
		
		List<BookLoans> listInsert=new ArrayList<BookLoans>();

		listInsert = fine.getRecordsForFine();
		
		fine.insertRecordInFines(listInsert);

		String errMessage = "The Batch Job to calculate the fines has been triggered!";
		HttpSession session = request.getSession(false);
		session.setAttribute("Message1", errMessage);
		response.sendRedirect("message.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
