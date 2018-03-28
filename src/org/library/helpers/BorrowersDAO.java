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

import org.library.model.Books;
import org.library.model.Borrowers;

import com.mysql.jdbc.Statement;

public class BorrowersDAO {

	
	private static Connection conn;
	private ResultSet results;
	private static Connection conn2;
	private ResultSet results2;
	
	
	
	public BorrowersDAO() {
		
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
	
	public Boolean getSearchSsnExists(String SsnToCHeck) {
		String query = "select Count(*) from BORROWER where SSN = " + SsnToCHeck;
		int searchSize = 0;
		PreparedStatement prepstmt;
		try {
			
			System.out.println("Print 11");
			prepstmt =  conn.prepareStatement(query);
			//Statement prepstmt = (Statement) conn.createStatement();
			System.out.println("Print 212");
			this.results = prepstmt.executeQuery();
			results.next(); 
			searchSize = results.getInt(1);
			
			System.out.println(searchSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (searchSize >= 1){
			return true;
		}else {return false;}		
			
	}
	
	
	public  static List<Borrowers> getRecords(int start,int total){  
        List<Borrowers> list=new ArrayList<Borrowers>();  
        try{  
            //Connection con=getConnection();  
            PreparedStatement ps=conn.prepareStatement("select * from Borrower limit 5");
        
        	
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	Borrowers borrower =new Borrowers();  
            	borrower.setId(results.getString("id"));
            	borrower.setName(results.getString("name"));
            	borrower.setAddress(results.getString("address"));
            	borrower.setPhone(results.getString("phone"));
            					
                list.add(borrower);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;  
    }
	
	public  static List<Borrowers> getRecords(int start,int total,String searchField){
		List<Borrowers> list=new ArrayList<Borrowers>();
			try{  
            //Connection con=getConnection();  
            PreparedStatement ps=conn.prepareStatement("select * from Borrower WHERE (id like '%"+searchField+"%' OR name like '%"+searchField+"%') "
            		+ "limit 5");
        
        	
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
            	Borrowers borrower =new Borrowers();  
            	borrower.setId(results.getString("id"));
            	borrower.setName(results.getString("name"));
            	borrower.setAddress(results.getString("address"));
            	borrower.setPhone(results.getString("phone"));
            					
                list.add(borrower);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;  
    }
	
	public  static List<Borrowers> getTotalFineRecords(String searchField){
		List<Borrowers> list=new ArrayList<Borrowers>();
			try{  
            //Connection con=getConnection();  
				PreparedStatement ps;
	            ps=conn.prepareStatement("select BW.id, BW.name, SUM(FI.Fine_Amount) AS total_fine from "
	            		+ "BOOK_Loans BL inner join FINES FI on BL.id = FI.bookloan_id, Borrower BW "
	            		+ "WHERE (BL.BORROWER_ID = BW.ID AND FI.PAID = 0) GROUP BY BW.ID");
/*			if(searchField.toLowerCase().matches("all")) {
				System.out.println("inside all wala IF");

			}else
			{
				System.out.println("inside else wala IF");
	           ps=conn.prepareStatement("select BW.id, BW.name, SUM(FI.Fine_Amount) "
	            		+ "AS total_fine from BOOK_Loans BL inner join FINES FI on BL.id = FI.bookloan_id, "
	            		+ "Borrower BW WHERE (BL.BORROWER_ID = BW.ID AND BW.ID = '"+searchField+"') GROUP BY BW.ID;");
			}*/
        	
	            System.out.println("After select query");
        	
            ResultSet results = ps.executeQuery();  
            while(results.next()){  
            	Borrowers borrower =new Borrowers();  
            	borrower.setId(results.getString("id"));
            	borrower.setName(results.getString("name"));
            	borrower.setTotalFine(results.getString("total_Fine"));
            	System.out.println(results.getString("id") + results.getString("name") + results.getString("total_Fine"));				
                list.add(borrower);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;  
    }
	
	public  static List<Borrowers> getSpecificFineRecords(String searchField){
		List<Borrowers> list=new ArrayList<Borrowers>();
			try{  
            //Connection con=getConnection();  
				PreparedStatement ps;
	         ps=conn.prepareStatement("select BW.id, BW.name, SUM(FI.Fine_Amount) "
	            	+ "AS total_fine from BOOK_Loans BL inner join FINES FI on BL.id = FI.bookloan_id, "
	            	+ "Borrower BW WHERE (BL.BORROWER_ID = BW.ID AND "
	            	+ "BW.ID = '"+searchField+"') GROUP BY BW.ID");
 	
        	
            ResultSet results = ps.executeQuery();  
            while(results.next()){  
            	Borrowers borrower =new Borrowers();  
            	borrower.setId(results.getString("id"));
            	borrower.setName(results.getString("name"));
            	borrower.setTotalFine(results.getString("total_Fine"));
            	System.out.println(results.getString("id") + results.getString("name") + results.getString("total_Fine"));				
                list.add(borrower);  
            }  
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;  
    }

	public void insertRecordInBorrowers(Borrowers borrower) {
		// TODO Auto-generated method stub
		String insertQuery = "INSERT INTO BORROWER (SSN,NAME,ADDRESS, PHONE) VALUES (?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, borrower.getSsn());
			stmt.setString(2, borrower.getName());
			stmt.setString(3, borrower.getAddress());
			stmt.setString(4, borrower.getPhone());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}  
	
	public int getCardNumbforNewBorrower(Borrowers newBorrower) {
			PreparedStatement prepstmt;
			String tempSSN = newBorrower.getSsn();
			int cardNo = 0;
			try {
			prepstmt =  conn.prepareStatement("SELECT ID FROM Borrower WHERE SSN = '"+tempSSN+"'");
			//Statement prepstmt = (Statement) conn.createStatement();
			
			this.results = prepstmt.executeQuery();
			results.next(); 
			cardNo = results.getInt(1);
			
			System.out.println(cardNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return cardNo;
		}
	
	public static String getName(String  borrowerId) {
		PreparedStatement prepstmt;
		String name = null; 
		try {
		prepstmt =  conn.prepareStatement("SELECT name FROM Borrower WHERE id = '"+borrowerId+"'");
		//Statement prepstmt = (Statement) conn.createStatement();
		ResultSet results;
		results = prepstmt.executeQuery();
		results.next(); 
		name = results.getString("name");
		
		System.out.println(name);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return name;
	}

}
