package Model;

public class Book {
	private int bookID;
	private String title;
	private String publisherName;
	private int borrowCount;
	private String authorFirstName;
	private String authorLastName;
	private String authorFullName;
	private String publisherAddress;

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

	public Book(int bookID, String title, String publisherName, int borrowCount, String authorFirstName,
	            String authorLastName, String authorFullName) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.publisherName = publisherName;
		this.borrowCount = borrowCount;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.authorFullName = authorFullName;
	}

	public Book(String title, String authorFullName, String publisherName, String publisherAddress) {
		this.title = title;
		this.authorFullName = authorFullName;
		this.publisherName = publisherName;
		this.publisherAddress = publisherAddress;
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

	public int getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(int borrowCount) {
		this.borrowCount = borrowCount;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getPublisherAddress() {
		return publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}


}
