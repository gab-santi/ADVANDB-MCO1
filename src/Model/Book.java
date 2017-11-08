package Model;

public class Book {
	private int bookID;
	private String title;
	private String publisherName;
	private String authorFullName;
	
	public Book(int bookID, String title, String publisherName) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.publisherName = publisherName;
	}
	
	public Book(int bookID, String title, String publisherName, String authorFullName) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.publisherName = publisherName;
		this.authorFullName = authorFullName;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getAuthorFullName() {
		return authorFullName;
	}

	public void setAuthorFullName(String authorFullName) {
		this.authorFullName = authorFullName;
	}
	
	
	
}
