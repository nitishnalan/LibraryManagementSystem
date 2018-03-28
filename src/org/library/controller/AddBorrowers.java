package org.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.library.helpers.BorrowersDAO;
import org.library.model.Borrowers;

/**
 * Servlet implementation class AddBorrowers
 */
@WebServlet("/addborrowers")
public class AddBorrowers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBorrowers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		Borrowers borrower = new Borrowers();
		
		borrower.setSsn(request.getParameter("ssn"));
		borrower.setName(request.getParameter("name"));
		borrower.setAddress(request.getParameter("address"));
		borrower.setPhone(request.getParameter("phone"));
		String ssn = borrower.getSsn();
		System.out.println("SSN : " + ssn);
		BorrowersDAO borrowerDAO = new BorrowersDAO();
		boolean checkSSNExists = borrowerDAO.getSearchSsnExists(ssn);
		
		if(checkSSNExists) {
			//SHow alert
			//JOptionPane.showMessageDialog(null, "A User with this SSN already exists! Please check again...");
			String errMessage = "A User with this SSN already exists!";
			HttpSession session = request.getSession(false);
			session.setAttribute("Message1", errMessage);
			response.sendRedirect("message.jsp");
		}
		else {
			borrowerDAO.insertRecordInBorrowers(borrower);
			int cardNo = borrowerDAO.getCardNumbforNewBorrower(borrower);
			System.out.println("Record Inserted!");
			if (cardNo == 0) {
				String Message1 = "Something went wrong while creating this record!";
				HttpSession session = request.getSession(false);
				session.setAttribute("Message1", Message1);
				response.sendRedirect("message.jsp");
			}else {
				String suMessage = "The Borrower has been created Successfully! The Card No. for the borrower is : " + cardNo;
				HttpSession session = request.getSession(false);
				session.setAttribute("suMessage", suMessage);
				response.sendRedirect("success.jsp");
				//JOptionPane.showMessageDialog(null, "Success!");
			}

		}
			
	}

}
