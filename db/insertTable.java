package sqlP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class insertTable {

	static ResultSet rs;
	static PreparedStatement statementP;
	static Statement statement;
	

	public insertTable() throws IOException {
		ArrayList<Books> booksArr = new ArrayList<Books>();
		//booksArr.add(new Books(1, "Alices Adventures in Wonderland", "Lewis Carroll", "Children", "2008-06-27", 150,
				//"q", 1, 1, 1));
		booksArr.add(new Books(2, "The Little Gingerbread Man", "Carol Moore", "Children", "1995-10-01", 9, "q", 1, 1, 1));
		
		booksArr.add(new Books(6, "Jack and the Beanstalk", "Benjamin Tabart's", "Children", "1994-03-01", 9, "q", 1,
				1, 1));
		
        //enter the books to the data base at the first time
		boolean flag = false;
		for (int i = 0; i < booksArr.size(); i++) {
			String bookname = booksArr.get(i).getTitle();
			try {
				PreparedStatement stmt = SqlCon.getConnection().prepareStatement(SqlCon.CHECK_TITLE);
				stmt.setString(1, bookname);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					flag = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (flag == false) {
				String path = "C:\\Library\\" + bookname + ".txt";
				insertTableFunc(booksArr.get(i).getTitle(), booksArr.get(i).getAuthor(), booksArr.get(i).getCategory(),
						booksArr.get(i).getReleaseDate(), booksArr.get(i).getFileSize(), path);
			}
		}
		
	}

	/*
	 * insert books to the books table
	 */
	static void insertTableFunc(String title2, String author2, String category2, String releaseDate2, int fileSize2,
			String path2) throws IOException {
		String line;
		BufferedReader in;
		int paragraphs = 1;
		int lines = 0;
		int countWord = 0;
		int sentenceCount = 0;
		int characterCount = 0;

		in = new BufferedReader(new FileReader(path2));
		line = in.readLine();
		ArrayList<String> sentencesList = new ArrayList<String>();
		while (line != null) {
			System.out.println(lines);
			if (line.equals("")) {
				paragraphs++;
				sentencesList.add(" ");
			} else {
				sentencesList.add(line);
				characterCount += line.length();
				
				
//				String[] wordList = line.split("\\s+");
//				countWord += wordList.length;

//				String[] sentenceList = line.split("[!?.:]+");
//				sentenceCount += sentenceList.length;
			}
			lines++;
			line = in.readLine();
		}
		
		int countS=0;
		int countP=1;
		int numberSen=1;
		String[] SENTENCE;
		String[] sentenceArray = sentencesList.toArray(new String[sentencesList.size()]);
		for (int r=0;r<sentenceArray.length;r++)
	       {
//				if(sentenceArray[r]=="")
//					p++;
				SENTENCE = sentenceArray[r].split("(?<=[.!?])\\s*");
	            for(int i=0;i<SENTENCE.length;i++) {
	        	   String strS = SENTENCE[i];
	        	   if(strS==" ") {
	        		   countP++;
	        		   numberSen=0;
	        		   sentenceCount--;
	        	   }
	        	   countS++;
	        	   
	        	   String insertWords = "insert into sentences(word, title, paragraph, sentence, senInPar) values (?,?,?,?,?);; ";
	        	   try {
			   			PreparedStatement stmtWords = SqlCon.getConnection().prepareStatement(insertWords);
			   			stmtWords.setString(1, strS);
			   			stmtWords.setString(2, title2);
			   			if(strS==" ")
			   				stmtWords.setInt(3, 0);
			   			else
			   				stmtWords.setInt(3, countP);
			   			stmtWords.setInt(4, countS);
			   			stmtWords.setInt(5, numberSen);
			   			stmtWords.executeUpdate();
				   		} catch (SQLException e) {
				   			
				   		}
		           sentenceCount++;
		           numberSen++;
		        	   countWord += SENTENCE[i].length(); 
	           }
	           
	       }

		String str = "insert into books(title, author, category, releaseDate, fileSize, filePath, numParagraph, numLines, numWords, numSentences,numCharacters) values (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);; ";
				//+ "where not exists ( SELECT * FROM books where title = title2 and author = author2 and category = category2 and releaseDate=releaseDate2 and fileSize=fileSize2 and filePath=filePath2 and numParagraph =paragraphs and numLines=lines and numWords=countWord and numSentences =sentenceCount and numCharacters =characterCount);;";
		try {
			PreparedStatement stmt = SqlCon.getConnection().prepareStatement(str);

			stmt.setString(1, title2);
			stmt.setString(2, author2);
			stmt.setString(3, category2);
			stmt.setString(4, releaseDate2);
			stmt.setInt(5, fileSize2);
			stmt.setString(6, path2);
			stmt.setInt(7, paragraphs);
			stmt.setInt(8, lines);
			stmt.setInt(9, countWord);
			stmt.setInt(10, sentenceCount);
			stmt.setInt(11, characterCount);

			stmt.executeUpdate();
		} catch (SQLException e) {
			
		}
		//ViewWords.showListTitels();
	}

	/*
	 * insert names of groups to the groups table
	 */
	public static void insertgroupFunc(String name) {
		String str = "insert into groups(groupName) values (?);";
		try {
			PreparedStatement stmt = SqlCon.getConnection().prepareStatement(str);
			stmt.setString(1, name);
			stmt.executeUpdate();
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}

	}

	/*
	 *  insert words to the words table, wordInGroup table according the group name,
	 *   wordInBook table according the book name
	 */
	public static void insertwordFunc(String g, String w, String t, int p, int s, int is) {
		int bid = 0;
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.SELECT_BOOKID);
			statementP.setString(1, t);
			rs = statementP.executeQuery();
			rs.next();
			bid = rs.getInt("bookId");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String str = "insert into words(word,title, paragraph, sentence,indexSentence) values (?,?,?,?,?);";
		String str2 = "insert into wordInGroup(groupId,wordId) values (?,?);";
		String str3 = "insert into wordInBook(bookId,wordId2) values(?,?);";

		int wid = 0;
		int gid = 0;
		try {
			PreparedStatement stmt = SqlCon.getConnection().prepareStatement(str);
			stmt.setString(1, w);
			stmt.setString(2, t);
			stmt.setInt(3, p);
			stmt.setInt(4, s);
			stmt.setInt(5, is);
			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			statement = SqlCon.getConnection().createStatement();
			rs = statement.executeQuery(SqlCon.LAST_WORD_ID);
			rs.next();
			wid = rs.getInt("wordId");
			System.out.println("wid " + wid);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.SELECT_GROUPID);
			if (g.equals(""))
				statementP.setString(1, "phrases");
			else
				statementP.setString(1, g);
			rs = statementP.executeQuery();
			rs.next();
			gid = rs.getInt("groupId");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			PreparedStatement stmt = SqlCon.getConnection().prepareStatement(str2);
			stmt.setInt(1, gid);
			stmt.setInt(2, wid);
			stmt.executeUpdate();

			PreparedStatement stmt2 = SqlCon.getConnection().prepareStatement(str3);
			stmt2.setInt(1, bid);
			stmt2.setInt(2, wid);
			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
