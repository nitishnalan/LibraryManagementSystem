package org.library.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.library.model.BookLoans;
import org.library.model.Books;
import org.library.model.Borrowers;

public class BookLoansDAO {
	private static Connection conn;
	private ResultSet results;
	private static Connection conn2;
	private ResultSet results2;
	
	
	
	public BookLoansDAO() {
		
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
	
	
	public  static List<Books> getRecords(int start,int total){  
        List<Books> list=new ArrayList<Books>();  
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
        	
        	PreparedStatement ps=conn.prepareStatement("select B.*, GROUP_CONCAT(DISTINCT A.name SEPARATOR ', ') as "
        			+ "author_name from Book B inner join Book_authors BA on B.isbn = BA.book_id "
        			+ "inner join Authors A on BA.author_id = A.id "
        			+ "WHERE ((B.isbn like '%legend%' OR B.title like '%legend%' OR A.name like '%legend%') "
        			+ "AND B.isbn IN(1561382477,1569316961,1580291023))GROUP BY B.isbn");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
                Books book =new Books();  
				book.setIsbn(results.getString("isbn"));
				book.setTitle(results.getString("title"));
				book.setCover(results.getString("cover"));
				book.setPublisher(results.getString("publisher"));
				book.setPages(results.getInt("pages"));
				book.setAuthors(results.getString("author_name"));
                list.add(book);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;      }  
	
	
	public  static List<BookLoans> getRecords(int start,int total, String searchField){  
        List<BookLoans> list=new ArrayList<BookLoans>();  
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
        	
/*        	PreparedStatement ps=conn.prepareStatement("select B.*, GROUP_CONCAT(DISTINCT A.name SEPARATOR ', ') as "
        			+ "author_name from Book B inner join Book_authors BA on B.isbn = BA.book_id "
        			+ "inner join Authors A on BA.author_id = A.id "
        			+ "WHERE ((B.isbn like '%"+searchField+"%' OR B.title like '%"+searchField+"%' OR A.name like '%"+searchField+"%') "
        			+ "AND B.isbn IN(1561382477,1569316961,1580291023))GROUP BY B.isbn");*/
        	
        	PreparedStatement ps=conn.prepareStatement("select BL.*,BW.name from Book_Loans BL INNER JOIN BORROWER BW ON BL.BORROWER_ID = BW.ID "
        			+ "WHERE ((BL.BOOK_ID = '"+searchField+"' OR "
        			+ "BL.BORROWER_ID = '"+searchField+"' OR BW.name like '%"+searchField+"%') AND BL.DATE_IN IS NULL)");
        			
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
                BookLoans bookLoans =new BookLoans();
                bookLoans.setId(results.getString("id"));
                bookLoans.setBook_id(results.getString("book_id"));
                bookLoans.setBorrower_id(results.getString("borrower_id"));
                bookLoans.setBorrower_name(results.getString("name"));
                bookLoans.setDate_out(results.getDate("date_out"));
                bookLoans.setDue_date(results.getDate("due_date"));
                bookLoans.setDate_in(results.getDate("date_in"));
                list.add(bookLoans);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list; 
     }
	
	public void insertRecordInBookLoans(BookLoans bookBorrow) {
		// TODO Auto-generated method stub
		String insertQuery = "INSERT INTO BOOK_LOANS (BOOK_ID,BORROWER_ID, DATE_OUT,DUE_DATE) VALUES (?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 14 DAY))";
		PreparedStatement stmt; 
		try {
			stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, bookBorrow.getBook_id());
			stmt.setString(2, bookBorrow.getBorrower_id());
						
			stmt.executeUpdate();
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
	        	PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) FROM BOOK_LOANS WHERE BORROWER_ID ="+ borrower +" AND DATE_IN IS NULL");
	        			
	        	
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

	public Boolean checkIfBookReturned(String bookLoanId) {
		// TODO Auto-generated method stub
		
		 List<Books> list=new ArrayList<Books>();
		 int count = -1;
	        try{  
	            //Connection con=getConnection();  
	            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
	        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
	        	
	        	PreparedStatement ps=conn.prepareStatement("SELECT count(*) FROM BOOK_LOANS WHERE ID ='"+ bookLoanId +"' AND Date_IN IS NULL");
	        			
	        	
	            ResultSet results=ps.executeQuery();
	            
	            	results.next();  
	            	count = results.getInt(1);
	            	//int count1 = results.getInt(0);
	            	//System.out.println("Count1 for the Book availability : " + count1);
	            	System.out.println("Count if Book has been returned : " + count);
	              
	            
	           // conn.close();  
	        }catch(Exception book){System.out.println(book);}  
	        
	        if(count > 0) {
	        	return false;
	        }
	        else {return true;}
		
		} 
	
	
}
