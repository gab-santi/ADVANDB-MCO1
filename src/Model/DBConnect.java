package Model;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
	
public class DBConnect {
	private static DBConnect db = new DBConnect();
	private Statement stmt;
	
	public DBConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/advandb_mco1","root","1234"); 
			stmt = con.createStatement();
			System.out.println("Successfully connected to advandb_mco1!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public DBConnect getInstance() {
		return this.db;
	}
	
	public ArrayList<BookLoan> query1(Date d) {
		ArrayList<BookLoan> b = new ArrayList<BookLoan>();
		String date = d.toString();
		BookLoan bl;
		//perform query
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM book_loans WHERE DueDate > '" + date + "' AND DateReturned > DueDate;");
			while (rs.next()) {
				bl = new BookLoan(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5), rs.getDate(6));
				b.add(bl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	
	public ArrayList<Book> query2(String title) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		
		//perform query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName FROM book WHERE Title LIKE '%"+ title + "%';");
			while (rs.next()) {
				bk = new Book(0, rs.getString(1), rs.getString(2));
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	
	public int query3(String author) {
		int count = 0;
		
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NoBksWrTolkien FROM BOOK B, BOOK_AUTHORS BA WHERE B.BookID = BA.BookID AND BA.AuthorLastName = \"%" + author + "%\" GROUP BY BA.AuthorLastName;");
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	public ArrayList<Book> query4(String author) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS AuthorName FROM book NATURAL JOIN book_authors WHERE book.BookID = ANY ( SELECT BookID FROM book_authors WHERE CONCAT(AuthorFirstName, ' ', AuthorLastName) LIKE '%" + author + "%' ) ORDER BY Title ASC;");
			while (rs.next()) {
				bk = new Book(0, rs.getString(1), rs.getString(2), rs.getString(3));
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return b;
	}

	public ArrayList<Book> query5(int branchID) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCount, Title, AuthorFirstName, AuthorLastName, PublisherName FROM ( SELECT BookID, COUNT(BookID) AS BorrowCount FROM book_loans WHERE BranchID=" + branchID + " GROUP BY BookID ) AS X NATURAL JOIN book NATURAL JOIN book_authors ORDER BY BorrowCount DESC;");
			while (rs.next()) {
				bk = new Book(0, rs.getString(2), rs.getString(5), rs.getInt(1), rs.getString(3), rs.getString(4), "");
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
}
