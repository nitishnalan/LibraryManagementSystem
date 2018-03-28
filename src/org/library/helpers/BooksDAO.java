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

public class BooksDAO {
	private static Connection conn;
	private ResultSet results;
	private static Connection conn2;
	private ResultSet results2;
	
	
	
	public BooksDAO() {
		
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
	
	
	public  static List<Books> getRecords(int start,int total, String searchField){  
        List<Books> list=new ArrayList<Books>();  
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from book limit "+(start-1)+","+total);
        	//PreparedStatement ps=conn.prepareStatement("select * from books where title like '%legend%' limit 5,10");
        	
/*        	PreparedStatement ps=conn.prepareStatement("select B.*, GROUP_CONCAT(DISTINCT A.name SEPARATOR ', ') as "
        			+ "author_name from Book B inner join Book_authors BA on B.isbn = BA.book_id "
        			+ "inner join Authors A on BA.author_id = A.id "
        			+ "WHERE ((B.isbn like '%"+searchField+"%' OR B.title like '%"+searchField+"%' OR A.name like '%"+searchField+"%') "
        			+ "AND B.isbn IN(1561382477,1569316961,1580291023))GROUP BY B.isbn");*/
        	
        	PreparedStatement ps=conn.prepareStatement("select B.*, GROUP_CONCAT(DISTINCT A.name SEPARATOR ', ') as "
        			+ "author_name from Book B inner join Book_authors BA on B.isbn = BA.book_id "
        			+ "inner join Authors A on BA.author_id = A.id "
        			+ "WHERE ((B.isbn like '%"+searchField+"%' OR B.title like '%"+searchField+"%' OR A.name like '%"+searchField+"%') "
        			+ ")GROUP BY B.isbn");
        			
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
            //list = updateStatusOfBooksInList(list);
           // conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;      }

	public static List<Books> updateStatusOfBooksInList(String bookSearchResult) {
		// TODO Auto-generated method stub
		String bookSearchResultId;
		List<Books> list=new ArrayList<Books>();
		try {
			
			PreparedStatement ps=conn.prepareStatement("SELECT TempView.*, if(Status IS NULL, 'Available',"
					+ " status) status from (select B.*, GROUP_CONCAT(DISTINCT A.name SEPARATOR ', ') as "
					+ "author_name from Book B inner join Book_authors BA on B.isbn = BA.book_id "
					+ "inner join Authors A on BA.author_id = A.id WHERE ((B.isbn "
					+ "like '%"+bookSearchResult+"%' OR B.title like '%"+bookSearchResult+"%' OR "
					+ "A.name like '%"+bookSearchResult+"%')) GROUP BY B.isbn) TempView "
					+ "LEFT OUTER JOIN (SELECT book_id, if((count(*)<1),'Available','Not Available') "
					+ "AS status FROM Book_loans WHERE (DATE_IN IS NULL)Group By book_id) Temp_Book_loans "
					+ "ON TempView.isbn = Temp_Book_loans.book_id");
			
			ResultSet results=ps.executeQuery();

            while(results.next()){  
            	Books book =new Books();
    			book.setIsbn(results.getString("isbn"));
    			book.setTitle(results.getString("title"));
    			book.setCover(results.getString("cover"));
    			book.setPublisher(results.getString("publisher"));
    			book.setPages(results.getInt("pages"));
    			book.setAuthors(results.getString("author_name"));  
    			book.setStatus(results.getString("status"));
                list.add(book);  
                
            }
	            
		}catch(Exception book){System.out.println(book);} 
		return list;
	}  
}
