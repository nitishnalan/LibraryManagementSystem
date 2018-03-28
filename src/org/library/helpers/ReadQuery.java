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

public class ReadQuery {
	
	private static Connection conn;
	private ResultSet results;
	
	public ReadQuery() {
		
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
	
	public void read() {
		String query = "Select * from books limit 5";
		
		PreparedStatement prepstmt;
		try {
			
			System.out.println("Print 1");
			prepstmt =  conn.prepareStatement(query);
			//Statement prepstmt = (Statement) conn.createStatement();
			System.out.println("Print 22");
			this.results = prepstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public String getHTMLTable() {
		
		String table = "";
		table += "<table border=1>";
		
		try {
			while(this.results.next()) {
				Books book = new Books();
				book.setIsbn(this.results.getString("isbn"));
				book.setTitle(this.results.getString("title"));
				book.setCover(this.results.getString("cover"));
				book.setPublisher(this.results.getString("publisher"));
				book.setPages(this.results.getInt("pages"));
				
				System.out.println(book.getIsbn() + book.getPublisher());
				
				table+="<tr>";
				table+="<td>";
				table+=book.getIsbn();
				table+="</td>";
				
				table+="<td>";
				table+=book.getTitle();
				table+="</td>";

				table+="<td>";
				table+=book.getCover();
				table+="</td>";
				
				table+="<td>";
				table+=book.getPublisher();
				table+="</td>";

				table+="<td>";
				table+=book.getPages();
				table+="</td>";

						
				table+="</tr>";
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table += "</table>";
		return table;
		
	}
	
	
	public  List<Books> getRecords(int start,int total){  
        List<Books> list=new ArrayList<Books>();  
        try{  
            //Connection con=getConnection();  
            //PreparedStatement ps=conn.prepareStatement("select * from emp limit "+(start-1)+","+total);
        	PreparedStatement ps=conn.prepareStatement("select * from emp limit 5");
            ResultSet results=ps.executeQuery();  
            while(results.next()){  
                Books book =new Books();  
				book.setIsbn(this.results.getString("isbn"));
				book.setTitle(this.results.getString("title"));
				book.setCover(this.results.getString("cover"));
				book.setPublisher(this.results.getString("publisher"));
				book.setPages(this.results.getInt("pages"));
				
                list.add(book);  
            }  
            conn.close();  
        }catch(Exception book){System.out.println(book);}  
        return list;  
    }  
}
