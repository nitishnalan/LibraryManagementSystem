package org.library.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.library.model.BookLoanFines;
import org.library.model.BookLoans;
import org.library.model.Books;

public class FinesDAO {
	private static Connection conn;
	private ResultSet results;
	private static Connection conn2;
	private ResultSet results2;
	
public FinesDAO() {
	
		Properties prop = new Properties();
		InputStream instream = getClass().getResourceAsStream("config.properties");
		try {
			prop.load(instream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			instream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String driver = prop.getProperty("driver.name");
		String url = prop.getProperty("server.name");
		String username = prop.getProperty("user.name");
		String password = prop.getProperty("user.password");

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url, username, password);
			
  
//		 conn=(Connection) Driver.getConnection(  
//			"jdbc:mysql://localhost:3306/mylibrarytesting","root","nn1233");  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSearchResultSize() {
		String query = "select * from book where title like '%legend%' limit 5,10";
		int searchSize = 0;
		PreparedStatement prepstmt;
		try {
			
			System.out.println("Print 11");
			prepstmt =  conn.prepareStatement(query);
			//Statement prepstmt = (Statement) conn.createStatement();
			System.out.println("Print 212");
			this.results = prepstmt.executeQuery();
			while(results.next()) {
			searchSize = searchSize + 1;
			}
			System.out.println(searchSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchSize;		
	}
	
	
	public  static List<BookLoans> getRecordsForFine(){  
        List<BookLoans> listbookForFineCalc = new ArrayList<BookLoans>();  
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
        	
        	PreparedStatement ps=conn.prepareStatement("SELECT *, DATEDIFF(curdate(),due_date) AS Date_Difference FROM book_loans\r\n" + 
        			" WHERE ((CURDATE() > due_date) AND DATE_IN IS NULL) AND NOT EXISTS (SELECT bookloan_id from "
        			+ "fines where book_loans.id = fines.bookloan_id AND Fines.paid = 0)");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	BookLoans bookForFineCalc =new BookLoans();  
            	bookForFineCalc.setId(results.getString("id"));
            	bookForFineCalc.setBook_id(results.getString("book_id"));
            	bookForFineCalc.setBorrower_id(results.getString("borrower_id"));
            	bookForFineCalc.setDateDiff(results.getInt("Date_Difference"));
//            	bookForFineCalc.setPages(results.getInt("pages"));
//            	bookForFineCalc.setAuthors(results.getString("author_name"));
            	listbookForFineCalc.add(bookForFineCalc);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return listbookForFineCalc;      
       }  
	
	
	public  static List<BookLoanFines> getRecords(int start,int total, String searchField){  
        List<BookLoanFines> list=new ArrayList<BookLoanFines>();  
        try{  
        	PreparedStatement ps;
        	if(searchField == null || searchField.isEmpty() || searchField.charAt(0) == '*') {
        	ps=conn.prepareStatement("select BW.id AS Card_No, BW.name, SUM(F.fine_amount) AS Total_Fine"
        				+ "from Book_Loans BL INNER JOIN BORROWER BW ON BL.BORROWER_ID = BW.ID LEFT OUTER JOIN "
        				+ "FINES F ON F.BOOKLOAN_ID = BL.ID WHERE F.PAID = 0 AND BL.DATE_IN IS NULL "
        				+ "Group By BW.id");
        	}else
        	{	
        	ps=conn.prepareStatement("select BW.id AS Card_No, BW.name, SUM(F.fine_amount) AS Total_Fine from "
        				+ "Book_Loans BL "
        				+ "INNER JOIN BORROWER BW ON BL.BORROWER_ID = BW.ID LEFT OUTER JOIN FINES F ON "
        				+ "F.BOOKLOAN_ID = BL.ID "
        				+ "WHERE ((BL.BOOK_ID = '"+searchField+"' OR BL.BORROWER_ID = '"+searchField+"' OR "
        						+ "BW.name like '%"+searchField+"%') "
        				+ "AND F.PAID = 0 AND BL.DATE_IN IS NULL) GROUP BY BW.id");
        	}
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	BookLoanFines bookLoans =new BookLoanFines();
            	bookLoans.setBorrower_id(results.getString("Card_No"));
            	bookLoans.setBorrower_name(results.getString("name")); 
            	bookLoans.setFine_amount(results.getString("Total_Fine"));
            	list.add(bookLoans);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list; 
     }
	
	
	public void insertRecordInFines(List<BookLoans> list) {
		// TODO Auto-generated method stub
		String insertQuery = "INSERT INTO FINES (BOOKLOAN_ID,FINE_AMOUNT, PAID) VALUES (?,?,0)";
		
		
		try {
			PreparedStatement stmt; 
			stmt = conn.prepareStatement(insertQuery);
			for(BookLoans eBookLoans : list) {
				double value = 0;
				stmt.setString(1, eBookLoans.getId());
				value = eBookLoans.getDateDiff();
				value = value * 0.25;
				stmt.setDouble(2,value);
				System.out.println("value in update : " + value);
				stmt.addBatch();
			}
			int[] result = stmt.executeBatch();
			System.out.println("The number of rows inserted : " + result.length);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

	public Boolean checkBookAvailability(Books bookBorrow) {
		// TODO Auto-generated method stub
		 List<Books> list=new ArrayList<Books>();
		 int count = -1;
	        try{  
	            //Connection con=getConnection();  
	            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
	        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
	        	String book = bookBorrow.getIsbn();
	        	PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) FROM BOOK_LOANS WHERE BOOK_ID ="+ book +" AND DATE_IN IS NULL");
	        			
	        	
	            ResultSet results=ps.executeQuery();
	            
	            	results.next();  
	            	count = results.getInt(1);
	            	//int count1 = results.getInt(0);
	            	//System.out.println("Count1 for the Book availability : " + count1);
	            	System.out.println("Count for the Book availability : " + count);
	              
	            
	           // conn.close();  
	        }catch(Exception book){System.out.println(book);}  
	        
	        if(count > 0) {
	        	return false;
	        }
	        else {return true;}
	
	}

	public boolean checkBorrowerPermitted(BookLoans borrowerPermitted) {
		// TODO Auto-generated method stub
		 int count = -1;
	        try{  
	            //Connection con=getConnection();  
	            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
	        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
	        	String borrower = borrowerPermitted.getBorrower_id();
	        	PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) FROM BOOK_LOANS "
	        			+ "WHERE BORROWER_ID ="+ borrower +" AND DATE_IN IS NULL");	        		
	        	
	            ResultSet results=ps.executeQuery();
	            
	            	results.next();  
	            	count = results.getInt(1);
	            	//int count1 = results.getInt(0);
	            	//System.out.println("Count1 for the Book availability : " + count1);
	            	System.out.println("Count for the Book availability : " + count);
	              
	            
	           // conn.close();  
	        }catch(Exception book){System.out.println(book);}  
	        
	        if(count >= 3) {
	        	return false;
	        }
	        else {return true;}
		
	}

	public void checkInTheBook(BookLoans checkINBook) {
		// TODO Auto-generated method stub
		String loanID = checkINBook.getId();
		String bookID = checkINBook.getBook_id();
		String borrowerID = checkINBook.getBorrower_id();
		
		String updateQuery = "UPDATE BOOK_LOANS SET DATE_IN = CURDATE() WHERE (ID = '"+loanID+"' "
				+ "AND BOOK_ID = '"+bookID+"' AND BORROWER_ID = '"+borrowerID+"')";
		PreparedStatement stmt; 
		try {
			stmt = conn.prepareStatement(updateQuery);
									
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public List<BookLoans> getRecordsForUpdateInFineTable() {
		// TODO Auto-generated method stub
		List<BookLoans> listbookForFineUpdate = new ArrayList<BookLoans>();  
        try{  
        	
        	PreparedStatement ps=conn.prepareStatement("SELECT *, DATEDIFF(curdate(),due_date) AS Date_Difference FROM book_loans\r\n" + 
        			" WHERE ((CURDATE() > due_date) AND DATE_IN IS NULL) AND EXISTS (SELECT bookloan_id from "
        			+ "fines where book_loans.id = fines.bookloan_id AND Fines.paid = 0)");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	BookLoans bookForFineUpdate =new BookLoans();  
            	bookForFineUpdate.setId(results.getString("id"));
            	bookForFineUpdate.setBook_id(results.getString("book_id"));
            	bookForFineUpdate.setBorrower_id(results.getString("borrower_id"));
            	bookForFineUpdate.setDateDiff(results.getInt("Date_Difference"));
            	listbookForFineUpdate.add(bookForFineUpdate);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return listbookForFineUpdate;      
	
	}
	
	public BookLoans getDetailsForUpdateInFineTableAfterCheckIn(String book_loan_id) {
		// TODO Auto-generated method stub
		BookLoans bookForFineUpdate = new BookLoans();  
        try{  
        	
        	PreparedStatement ps=conn.prepareStatement("SELECT *, DATEDIFF(curdate(),due_date) AS "
        			+ "Date_Difference FROM book_loans WHERE ((CURDATE() > due_date) "
        			+ "AND DATE_IN IS NULL) AND EXISTS "
        			+ "(SELECT bookloan_id from fines "
        			+ "where book_loans.id = '"+book_loan_id+"' AND Fines.paid = 0)");
        					        			
            ResultSet results=ps.executeQuery();  
            results.next();
            //while(results.next()){  
            	//BookLoans bookForFineUpdate =new BookLoans();  
            	bookForFineUpdate.setId(results.getString("id"));
            	bookForFineUpdate.setBook_id(results.getString("book_id"));
            	bookForFineUpdate.setBorrower_id(results.getString("borrower_id"));
            	bookForFineUpdate.setDateDiff(results.getInt("Date_Difference"));
            	  
           // }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return bookForFineUpdate;      
	
	}
	
	public void updateRecordInFinesDuringCheckIn(BookLoans bookLoanUpdate) {
		// TODO Auto-generated method stub
		String updateQuery = "UPDATE FINES SET FINE_AMOUNT=?, PAID=0 WHERE BOOKLOAN_ID = ?";	
		
		try {
			PreparedStatement stmt; 
			stmt = conn.prepareStatement(updateQuery);
				double value = 0;
				value = bookLoanUpdate.getDateDiff();
				value = value * 0.25;
				stmt.setDouble(1,value);
				System.out.println("value in update : " + value);
				stmt.setString(2, bookLoanUpdate.getId());


			stmt.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

	public void updateRecordInFines(List<BookLoans> listUpdate) {
		// TODO Auto-generated method stub
		String updateQuery = "UPDATE FINES SET FINE_AMOUNT=?, PAID=0 WHERE BOOKLOAN_ID = ?";	
		
		try {
			PreparedStatement stmt; 
			stmt = conn.prepareStatement(updateQuery);
			for(BookLoans eBookLoans : listUpdate) {
				double value = 0;
				value = eBookLoans.getDateDiff();
				value = value * 0.25;
				stmt.setDouble(1,value);
				System.out.println("value in update : " + value);
				stmt.setString(2, eBookLoans.getId());
				stmt.addBatch();
			}
			int[] result = stmt.executeBatch();
			System.out.println("The number of rows updated : " + result.length);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

	public boolean checkNeedToPayFine(String loanIdCheckIn, String bookIdCheckIn, String borrowerIdCheckIn) {
		// TODO Auto-generated method stub
		int count = -1;
        try{  

/*        	PreparedStatement ps=conn.prepareStatement("SELECT count(*) FROM FINES "
        			+ "WHERE BOOKLOAN_ID ="+ loanIdCheckIn +" AND PAID = 0");	 */
        	
        	PreparedStatement ps=conn.prepareStatement("SELECT count(*) FROM BOOK_LOANS "
        			+ "WHERE ID ="+ loanIdCheckIn +" AND (CURDATE() > due_date) AND DATE_IN IS NULL");
        	
            ResultSet results=ps.executeQuery();
            
            	results.next();  
            	count = results.getInt(1);
            	//int count1 = results.getInt(0);
            	//System.out.println("Count1 for the Book availability : " + count1);
            	System.out.println("Check IN for the FINE " + count);              
            
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        
        if(count >= 1) {
        	return true;
        }
        else {return false;}
	
	}
	
	public boolean ifEntryIsPresentInFines(String loanIdCheckIn) {
		// TODO Auto-generated method stub
		int count = -1;
        try{  

        	PreparedStatement ps=conn.prepareStatement("SELECT count(*) FROM FINES "
        			+ "WHERE BOOKLOAN_ID ="+ loanIdCheckIn +" AND PAID = 0");	 
        	
        	
            ResultSet results=ps.executeQuery();
            
            	results.next();  
            	count = results.getInt(1);
            	//int count1 = results.getInt(0);
            	//System.out.println("Count1 for the Book availability : " + count1);
            	System.out.println("Check IN for the FINE " + count);              
            
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        
        if(count >= 1) {
        	return true;
        }
        else {return false;}
	
	}

	public static List<BookLoanFines> getAmountduringCheckIn(String loanIdCheckIn,String bookIdCheckIn,String borrowerIdCheckIn) {
		List<BookLoanFines> list = new ArrayList<BookLoanFines>();  
        try{  
        	
        	PreparedStatement ps=conn.prepareStatement("select BL.id,B.isbn,B.title, BW.id AS borrower_id , BW.name, BL.date_out, BL.due_date, "
        			+ "BL.Date_In,"
        			+ "F.fine_amount FROM Book_Loans BL inner join Book B on B.isbn = BL.book_id inner join "
        			+ "Borrower BW on BL.borrower_id = BW.id INNER JOIN Fines F ON BL.id = F.bookloan_id "
        			+ "WHERE (BL.id = "+ loanIdCheckIn +" AND F.paid = 0)");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	BookLoanFines listBookLoanFines = new BookLoanFines();
            	listBookLoanFines.setBook_loan_id(results.getString("id"));  
            	listBookLoanFines.setBook_id(results.getString("isbn"));
            	listBookLoanFines.setBook_name(results.getString("title"));
            	listBookLoanFines.setBorrower_id(results.getString("borrower_id"));
            	listBookLoanFines.setBorrower_name(results.getString("name"));
            	listBookLoanFines.setDate_out(results.getDate("date_out"));
            	listBookLoanFines.setDue_date(results.getDate("due_date"));
            	listBookLoanFines.setFine_amount(results.getString("fine_amount"));
            	
            	list.add(listBookLoanFines);
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;    
	}
	
	public static List<BookLoanFines> getSummaryForBorrowerFineDetails(String borrowerId) {
		List<BookLoanFines> list = new ArrayList<BookLoanFines>();  
		
        try{  
        	
        	PreparedStatement ps=conn.prepareStatement("select BL.id AS book_loan_id,B.isbn as book_id,"
        			+ "B.title as book_title, BW.id as card_id, BW.name as borrower_name, BL.date_out, "
        			+ "BL.due_date, F.fine_amount FROM Book_Loans BL inner join Book B on B.isbn = BL.book_id "
        			+ "inner join Borrower BW on BL.borrower_id = BW.id INNER JOIN Fines F ON "
        			+ "BL.id = F.bookloan_id WHERE (BW.id = '"+borrowerId+"' AND F.paid = 0);");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	BookLoanFines listBookLoanFines = new BookLoanFines();
            	listBookLoanFines.setBook_loan_id(results.getString("book_loan_id"));  
            	listBookLoanFines.setBook_id(results.getString("book_id"));
            	listBookLoanFines.setBook_name(results.getString("book_title"));
            	listBookLoanFines.setBorrower_id(results.getString("card_id"));
            	listBookLoanFines.setBorrower_name(results.getString("borrower_name"));
            	listBookLoanFines.setDate_out(results.getDate("date_out"));
            	listBookLoanFines.setDue_date(results.getDate("due_date"));
            	listBookLoanFines.setFine_amount(results.getString("fine_amount"));
            	
            	list.add(listBookLoanFines);
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;    
	}

	public void payTheFineForThisLoanId(String loanIdCheckIn) {
		// TODO Auto-generated method stub
		System.out.println("set update for fine");
		String updateQuery = "UPDATE FINES SET PAID = 1 WHERE BOOKLOAN_ID = '"+loanIdCheckIn+"'";	
		PreparedStatement stmt;
		try {			 
			stmt = conn.prepareStatement(updateQuery);
		
			stmt.executeUpdate();
			System.out.println("executed update for fine");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	public static BookLoanFines getSummaryForSingleBookFineDetails(String bookLoanId) {
		BookLoanFines listBookLoanFines = new BookLoanFines();  
		
        try{  
        	
        	PreparedStatement ps=conn.prepareStatement("select BL.id AS book_loan_id,B.isbn as book_id,"
        			+ "B.title as book_title, BW.id as card_id, BW.name as borrower_name, BL.date_out, "
        			+ "BL.due_date, F.fine_amount FROM Book_Loans BL inner join Book B on B.isbn = BL.book_id "
        			+ "inner join Borrower BW on BL.borrower_id = BW.id INNER JOIN Fines F ON "
        			+ "BL.id = F.bookloan_id WHERE (BL.id = '"+bookLoanId+"' AND F.paid = 0);");
        			
            ResultSet results=ps.executeQuery();  
           results.next();  
            	
            	listBookLoanFines.setBook_loan_id(results.getString("book_loan_id"));  
            	listBookLoanFines.setBook_id(results.getString("book_id"));
            	listBookLoanFines.setBook_name(results.getString("book_title"));
            	listBookLoanFines.setBorrower_id(results.getString("card_id"));
            	listBookLoanFines.setBorrower_name(results.getString("borrower_name"));
            	listBookLoanFines.setDate_out(results.getDate("date_out"));
            	listBookLoanFines.setDue_date(results.getDate("due_date"));
            	listBookLoanFines.setFine_amount(results.getString("fine_amount"));
            	
            	
            
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return listBookLoanFines;    
	}
	
	public  static BookLoans getRecordsForFineForSingleBookCheckIn(String loanIdCheckIn){  
		BookLoans bookForFineCalc =new BookLoans();   
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
        	
        	PreparedStatement ps=conn.prepareStatement("SELECT *, DATEDIFF(curdate(),due_date) AS Date_Difference FROM book_loans\r\n" + 
        			" WHERE ((CURDATE() > due_date) AND DATE_IN IS NULL AND ID = '"+loanIdCheckIn+"') AND NOT EXISTS (SELECT bookloan_id from "
        			+ "fines where book_loans.id = fines.bookloan_id AND Fines.paid = 0)");
        			
            ResultSet results=ps.executeQuery();  
            results.next(); 
            	 
            	bookForFineCalc.setId(results.getString("id"));
            	bookForFineCalc.setBook_id(results.getString("book_id"));
            	bookForFineCalc.setBorrower_id(results.getString("borrower_id"));
            	bookForFineCalc.setDateDiff(results.getInt("Date_Difference"));
//            	bookForFineCalc.setPages(results.getInt("pages"));
//            	bookForFineCalc.setAuthors(results.getString("author_name"));
            	  
             
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return bookForFineCalc;      
       }  
	
	public void insertRecordInFinesForSingleBookCheckIn(BookLoans eBookLoans) {
		// TODO Auto-generated method stub
		String insertQuery = "INSERT INTO FINES (BOOKLOAN_ID,FINE_AMOUNT, PAID) VALUES (?,?,0)";
		
		
		try {
			PreparedStatement stmt; 
			stmt = conn.prepareStatement(insertQuery);
			
				double value = 0;
				stmt.setString(1, eBookLoans.getId());
				value = eBookLoans.getDateDiff();
				value = value * 0.25;
				stmt.setDouble(2,value);
				System.out.println("value in update : " + value);
				stmt.executeUpdate();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

}
