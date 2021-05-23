package sqlP;

import java.sql.Date;

public class Books {
	public int bookId;
	public String title;
	public String author;
	public String category;
	public String releaseDate;
	public int fileSize;
	public String filePath;
	public int numParagraph;
	public int numLines;
	public int numWords;
	public Books(int bookId, String title, String author, String category, String releaseDate, int fileSize,
			String filePath, int numParagraph, int numLines, int numWords) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.category = category;
		this.releaseDate = releaseDate;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.numParagraph = numParagraph;
		this.numLines = numLines;
		this.numWords = numWords;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getNumParagraph() {
		return numParagraph;
	}
	public void setNumParagraph(int numParagraph) {
		this.numParagraph = numParagraph;
	}
	public int getNumLines() {
		return numLines;
	}
	public void setNumLines(int numLines) {
		this.numLines = numLines;
	}
	public int getNumWords() {
		return numWords;
	}
	public void setNumWords(int numWords) {
		this.numWords = numWords;
	}
	
	
	

}
