package sqlP;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCon {
	public static final String CONN_STRING = "jdbc:sqlserver://localhost;databaseName=Project;integratedSecurity=true;";
	public static final String SELECT_TITLE = "SELECT title FROM books";
	public static final String SELECT_PATH = "SELECT filePath FROM books";
	public static final String TITLE_RESULT = "SELECT title, author, category, releaseDate, fileSize FROM books WHERE title = ?";
	public static final String AUTHOR_RESULT = "SELECT title,author,category,releaseDate, fileSize FROM books WHERE author = ?";
	public static final String WORD_RESULT = "SELECT title,author,category,releaseDate, fileSize FROM books WHERE filePath = ?";
	public static final String COUNT_ROWS = "SELECT COUNT(*) FROM books" ;
	public static final String COUNT_GROUPS = "SELECT COUNT(*) FROM groups" ;
	public static final String SUM_SIZE = "SELECT SUM(fileSize) FROM books";
	public static final String SELECT_STATISTICS ="SELECT numParagraph, numLines, numWords, numSentences,numCharacters FROM books WHERE title =?";
	public static final String ALL_STATISTICS = "SELECT SUM(numParagraph),SUM(numLines),SUM(numWords),SUM(numSentences),SUM(numCharacters) FROM books";
	public static final String PATH_ACORDING_TITLE ="SELECT filePath FROM books WHERE title = ?";
	public static final String TITLE_ACORDING_PATH ="SELECT title FROM books WHERE filePath = ?";
	public static final String SELECT_TITLE_PATH = "SELECT title,path FROM books";
	public static final String NUMPARAG_ACORDING_TITLE ="SELECT numParagraph FROM books WHERE title = ?";
	public static final String SELECT_GROUP = "SELECT groupName FROM groups";
	public static final String SELECT_WORDID = "SELECT wordId FROM words WHERE word = ?";
	public static final String SELECT_GROUPID = "SELECT groupId FROM groups WHERE groupName = ?";
	public static final String SELECT_GROUPWORD = "SELECT DISTINCT word FROM words as w, groups as g, wordInGroup as wig WHERE wig.groupId = g.groupId AND w.wordId= wig.wordId AND g.groupName = ?";
	public static final String PHRASE_TO_CHECK = "SELECT DISTINCT word, MAX(w.wordId) FROM words as w, groups as g, wordInGroup as wig WHERE wig.groupId = g.groupId AND w.wordId= wig.wordId AND g.groupName = 'phrases' GROUP BY w.word ORDER BY MAX(w.wordId), w.word";
	public static final String SELECT_WORDS = "SELECT word, title, paragraph, sentence, indexSentence FROM words as w, groups as g, wordInGroup as wig WHERE wig.groupId = g.groupId AND w.wordId= wig.wordId AND g.groupName = ?";
	public static final String PHRASE_INDEX = "SELECT title ,paragraph, sentence FROM words WHERE word = ?";
	public static final String SELECT_BOOKID = "SELECT bookId FROM books WHERE title = ?";
	public static final String COUNT_WORDS_GROUP = "SELECT COUNT(DISTINCT word) FROM words as w, wordInGroup as wig, groups as g WHERE wig.groupId = g.groupId AND w.wordId= wig.wordId AND g.groupName <>'phrases' ";
	public static final String LAST_WORD_ID ="SELECT TOP 1 wordId FROM words ORDER BY wordId DESC"; 
	public static final String SELECT_WORDS_GROUP = "SELECT word, title, paragraph, sentence, indexSentence FROM words as w, groups as g, wordInGroup as wig WHERE wig.groupId = g.groupId AND w.wordId= wig.wordId AND g.groupName = ? AND w.word = ?";
	public static final String SELECT_BOOKS="SELECT * FROM books";
	public static final String CHECK_TITLE = "SELECT title FROM books WHERE title=?";
	public SqlCon() {
		
	}

	// connection to database
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(CONN_STRING);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_TITLE);
			while ( rs.next() ) {
                String title = rs.getString("title");
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) throws IOException {
		new JTabbedPaneFrame();
		 getConnection();
		 new insertTable();
		
	}
}
