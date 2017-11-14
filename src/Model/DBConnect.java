package Model;

import java.sql.Connection;
import java.time.LocalDate;
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
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?" +
                    "user=root");
            stmt = con.createStatement();
            System.out.println("Successfully connected to advandb_mco1!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public DBConnect getInstance() {
        return this.db;
    }

    public ArrayList<BookLoan> query1(LocalDate date) {
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

    public ArrayList<Book> query2(String title) {
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

    public int query3(String author) {
        int count = 0;

        //query
        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NoBksWrTolkien FROM BOOK B, BOOK_AUTHORS BA WHERE B.BookID = BA.BookID AND BA.AuthorLastName = '" + author + "' GROUP BY BA.AuthorLastName;");
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

	public ArrayList<Book> query6(String title) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT Title, CONCAT(AuthorFirstName, ' ', AuthorLastName) AS Author, PublisherName, Address FROM book NATURAL JOIN book_authors NATURAL JOIN publisher WHERE Title LIKE '%"+ title + "%';");
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

	public ArrayList<Book> query7(String branchName) {
		ArrayList<Book> b = new ArrayList<Book>();
		Book bk;

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BorrowCountCol, Title, AuthorFirstName, AuthorLastName, PublisherName FROM ( SELECT BookID, COUNT(*) AS BorrowCountCol FROM book_loans NATURAL JOIN library_branch WHERE BranchName='" + branchName + "' GROUP BY BookID ) AS X NATURAL JOIN book NATURAL JOIN book_authors ORDER BY BorrowCountCol DESC;");
			while(rs.next()) {
				bk = new Book(0, rs.getString(2), rs.getString(5), rs.getInt(1), rs.getString(3), rs.getString(4), "");
				b.add(bk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public ArrayList<String> getAllBranchNames() {
		ArrayList<String> b = new ArrayList<String>();

		//query
		try {
			ResultSet rs = stmt.executeQuery("SELECT BranchName FROM advandb_mco1.library_branch;");
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

    public void disableProfiling() {
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
                System.out.println(rs.getInt("Query_ID"));
            }

            System.out.println(time / 15);
            return time / 15;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
