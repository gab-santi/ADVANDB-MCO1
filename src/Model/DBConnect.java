package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBConnect {
	private static DBConnect db = new DBConnect();
	private Statement stmt;

	public DBConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?",
					"root",
					"root");
			stmt = con.createStatement();
			System.out.println("Successfully connected to advandb_mco1!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public DBConnect getInstance() {
		return this.db;
	}

	public ArrayList<BookLoan> query1_1(LocalDate date) {
		ArrayList<BookLoan> b = new ArrayList<>();
		BookLoan bl;
		//perform query
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM book_loans WHERE DueDate > '" + date.toString() + "' AND DateReturned > DueDate;");
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

	public void preQuery1_2() {
		try {
			stmt.executeUpdate("CREATE INDEX a ON book_loans(DueDate);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery1_2() {
		try {
			stmt.executeUpdate("DROP INDEX a ON book_loans;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preQuery1_3() {
		try {
			stmt.executeUpdate("CREATE INDEX a ON book_loans(DueDate, DateReturned);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery1_3() {
		try {
			stmt.executeUpdate("DROP INDEX a ON book_loans;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query2_1(String title) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//perform query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName FROM book WHERE Title LIKE '%" + title + "%';");
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

	public void preQuery2_2() {
		try {
			stmt.executeUpdate("CREATE INDEX a ON book(Title);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery2_2() {
		try {
			stmt.executeUpdate("DROP INDEX a ON book;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int query3_1(String author) {
		int count = 0;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NoBksWrTolkien\n" +
					"FROM book NATURAL JOIN book_authors\n" +
					"WHERE CONCAT(AuthorFirstName, ' ', AuthorLastName) LIKE '%" + author + "%';");
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public int query3_2(String author) {
		int count = 0;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NoBksWrTolkien\n" +
					"FROM book B JOIN book_authors BA ON B.BookID = BA.BookID\n" +
					"WHERE CONCAT(BA.AuthorFirstName, ' ', BA.AuthorLastName) LIKE '%" + author + "%';");
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public void preQuery3_3() {
		try {
			stmt.executeUpdate("CREATE INDEX i ON book_authors(AuthorFirstName);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery3_3() {
		try {
			stmt.executeUpdate("DROP INDEX i ON book_authors;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preQuery3_4() {
		try {
			stmt.executeUpdate("CREATE INDEX i ON book_authors(AuthorFirstName, AuthorLastName);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery3_4() {
		try {
			stmt.executeUpdate("DROP INDEX i ON book_authors;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query4_1(String author) {
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

	public ArrayList<Book> query4_2(String author) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS AuthorName\n" +
					"FROM book JOIN book_authors\n" +
					"ON book.bookid = book_authors.BookID\n" +
					"WHERE book.BookID = ANY (\n" +
					"SELECT BookID\n" +
					"FROM book_authors\n" +
					"WHERE CONCAT(AuthorFirstName, ' ', AuthorLastName) LIKE '%" + author + "%'\n" +
					")\n" +
					"ORDER BY Title ASC;");
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

	public ArrayList<Book> query4_3() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS AuthorName\n" +
					"FROM book JOIN book_authors\n" +
					"ON book.bookid = book_authors.BookID\n" +
					"WHERE book.BookID = ANY (SELECT * FROM VAuthorBooks)\n" +
					"ORDER BY Title ASC;");
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

	public void preQuery4_3(String author) {
		try {
			stmt.executeUpdate("CREATE VIEW VAuthorBooks AS\n" +
					"SELECT BookID\n" +
					"FROM book_authors\n" +
					"WHERE CONCAT(AuthorFirstName, ' ', AuthorLastName) LIKE '%" + author + "%';\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery4_3() {
		try {
			stmt.executeUpdate("DROP VIEW VAuthorBooks;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query4_4() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, PublisherName, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS AuthorName\n" +
					"FROM book JOIN book_authors\n" +
					"ON book.bookid = book_authors.BookID\n" +
					"WHERE book.BookID = ANY (SELECT * FROM TTAuthorBooks)\n" +
					"ORDER BY Title ASC;");
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

	public void preQuery4_4(String author) {
		try {
			stmt.executeUpdate("CREATE TEMPORARY TABLE TTAuthorBooks AS\n" +
					"SELECT BookID\n" +
					"FROM book_authors\n" +
					"WHERE CONCAT(AuthorFirstName, ' ', AuthorLastName) LIKE '%" + author + "%';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery4_4() {
		try {
			stmt.executeUpdate("DROP TABLE TTAuthorBooks;\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preQuery4_5() {
		try {
			stmt.executeUpdate("CREATE INDEX i ON book_authors(AuthorFirstName);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery4_5() {
		try {
			stmt.executeUpdate("DROP INDEX i ON book_authors;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preQuery4_6() {
		try {
			stmt.executeUpdate("CREATE INDEX i ON book_authors(AuthorFirstName, AuthorLastName);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery4_6() {
		try {
			stmt.executeUpdate("DROP INDEX i ON book_authors;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query5_1(int branchID) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCount, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM (\n" +
					"SELECT BookID, COUNT(BookID) AS BorrowCount\n" +
					"FROM book_loans\n" +
					"WHERE BranchID = " + branchID + "\n" +
					"GROUP BY BookID\n" +
					") AS X NATURAL JOIN book NATURAL JOIN book_authors\n" +
					"ORDER BY BorrowCount DESC;");
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

	public ArrayList<Book> query5_2(int branchID) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCount, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM (\n" +
					"SELECT BookID, COUNT(BookID) AS BorrowCount\n" +
					"FROM book_loans\n" +
					"WHERE BranchID = " + branchID + "\n" +
					"GROUP BY BookID\n" +
					") AS X JOIN book B ON X.BookID = B.BookID\n" +
					"JOIN book_authors BA ON X.BookID = BA.BookID\n" +
					"ORDER BY BorrowCount DESC;");
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

	public ArrayList<Book> query5_3() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCount, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM VBorrowCount V JOIN book B ON V.BookID = B.BookID\n" +
					"JOIN book_authors BA ON V.BookID = BA.BookID\n" +
					"ORDER BY BorrowCount DESC;");
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

	public void preQuery5_3(int branchID) {
		try {
			stmt.executeUpdate("CREATE VIEW VBorrowCount AS\n" +
					"SELECT BookID, COUNT(BookID) AS BorrowCount\n" +
					"FROM book_loans\n" +
					"WHERE BranchID = " + branchID + "\n" +
					"GROUP BY BookID;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery5_3() {
		try {
			stmt.executeUpdate("DROP VIEW VBorrowCount;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query5_4() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;
		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCount, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM TTBorrowCount TT JOIN book B ON TT.BookID = B.BookID\n" +
					"JOIN book_authors BA ON TT.BookID = BA.BookID\n" +
					"ORDER BY BorrowCount DESC;");
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

	public void preQuery5_4(int branchID) {
		try {
			stmt.executeUpdate("CREATE TEMPORARY TABLE TTBorrowCount AS\n" +
					"SELECT BookID, COUNT(BookID) AS BorrowCount\n" +
					"FROM book_loans\n" +
					"WHERE BranchID = " + branchID + "\n" +
					"GROUP BY BookID;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery5_4() {
		try {
			stmt.executeUpdate("DROP TABLE TTBorrowCount;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query6_1(String title) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS Author, PublisherName, Address " +
					"FROM book NATURAL JOIN book_authors NATURAL JOIN publisher " +
					"WHERE Title LIKE '%" + title + "%';");
			while (rs.next()) {
				bk = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public ArrayList<Book> query6_2(String title) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS Author, P.PublisherName, Address\n" +
					"FROM book B JOIN book_authors BA \n" +
					"ON B.BookID = BA.BookID JOIN publisher P \n" +
					"ON B.PublisherName = P.PublisherName\n" +
					"WHERE Title LIKE '%" + title + "%';");
			while (rs.next()) {
				bk = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public ArrayList<Book> query7_1(String branchName) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCountCol, Title, AuthorFirstName, AuthorLastName, PublisherName " +
					"FROM ( " +
					"SELECT BookID, COUNT(*) AS BorrowCountCol " +
					"FROM book_loans NATURAL JOIN library_branch " +
					"WHERE BranchName='" + branchName + "' GROUP BY BookID ) AS X " +
					"NATURAL JOIN book NATURAL JOIN book_authors " +
					"ORDER BY BorrowCountCol DESC;");
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

	public ArrayList<Book> query7_2(String branchName) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCountCol, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM (\n" +
					"SELECT BookID, COUNT(*) AS BorrowCountCol\n" +
					"FROM book_loans BL JOIN library_branch LB ON BL.BranchID = LB.BranchID\n" +
					"WHERE BranchName='" + branchName + "'\n" +
					"GROUP BY BookID\n" +
					") AS X JOIN book B ON X.BookID = B.BookID JOIN book_authors BA ON X.BookID = BA.BookID\n" +
					"ORDER BY BorrowCountCol DESC;\n");
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

	public ArrayList<Book> query7_3() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCountCol, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM VBorrowCount NATURAL JOIN book NATURAL JOIN book_authors\n" +
					"ORDER BY BorrowCountCol DESC;");
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

	public void preQuery7_3(String branchName) {
		try {
			stmt.executeUpdate("CREATE VIEW VBorrowCount AS\n" +
					"SELECT BookID, COUNT(*) AS BorrowCountCol\n" +
					"FROM book_loans BL JOIN library_branch LB ON BL.BranchID = LB.BranchID\n" +
					"WHERE BranchName='" + branchName + "'\n" +
					"GROUP BY BookID;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery7_3() {
		try {
			stmt.executeUpdate("DROP VIEW VBorrowCount;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> query7_4() {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCountCol, Title, AuthorFirstName, AuthorLastName, PublisherName\n" +
					"FROM TTBorrowCount NATURAL JOIN book NATURAL JOIN book_authors\n" +
					"ORDER BY BorrowCountCol DESC;");
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

	public void preQuery7_4(String branchName) {
		try {
			stmt.executeUpdate("CREATE TEMPORARY TABLE TTBorrowCount AS\n" +
					"SELECT BookID, COUNT(*) AS BorrowCountCol\n" +
					"FROM book_loans BL JOIN library_branch LB ON BL.BranchID = LB.BranchID\n" +
					"WHERE BranchName='" + branchName + "'\n" +
					"GROUP BY BookID;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery7_4() {
		try {
			stmt.executeUpdate("DROP TABLE TTBorrowCount;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preQuery7_5() {
		try {
			stmt.executeUpdate("CREATE INDEX i ON library_branch(BranchName);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postQuery7_5() {
		try {
			stmt.executeUpdate("DROP INDEX i ON library_branch;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getAllBranchNames() {
		ArrayList<String> b = new ArrayList<String>();

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BranchName FROM library_branch;");
			while (rs.next()) {
				String s = rs.getString(1);
				b.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public void enableProfiling() {
		try {
			stmt.executeUpdate("SET PROFILING=1;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void disableProfiling() {
		try {
			stmt.executeUpdate("SET PROFILING=0;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double getTime() {
		try {
			ResultSet rs = stmt.executeQuery("SHOW PROFILES;");
			double time = 0;

			while (rs.next()) {
				time += rs.getDouble("Duration");
			}

			this.disableProfiling();
			return time / 15;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
