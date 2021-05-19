package sqlP;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Statistics {

	JPanel general;
	Border border = new LineBorder(Color.white, 1, true);
	static Statement statement;
	static ResultSet rs;
	int count = 0;
	String query = "";
	static String[] arr2 = new String[100];
	static String[] booksList;
	static int i;
	PreparedStatement statementP;

	Font font;
	int paragraphCount = 0;
	int linesCount = 0;
	int wordsCount = 0;
	int sentencesCount = 0;
	int charactersCount = 0;
	int wordsInParagraph = 0;
	int charactersInParagraph = 0;
	int wordsInline = 0;
	int charactersInLine = 0;
	int wordsInSentence = 0;
	int charactersInSentence = 0;
	int averageL= 0;
	JPanel spesific;

	JLabel totalWords2 =new JLabel(" ");
	JLabel letters2 =new JLabel(" ");
	JLabel averageLetters2=new JLabel(" ");
	
	JLabel totalP2 =new JLabel(" ");
	JLabel wordP2=new JLabel(" ");
	JLabel lettersP2=new JLabel(" ");
	
	JLabel totalL2 =new JLabel(" ");
	JLabel wordL2=new JLabel(" ");
	JLabel lettersL2=new JLabel(" ");
	
	JLabel totalS2 =new JLabel(" ");
	JLabel wordS2=new JLabel(" ");
	JLabel lettersS2=new JLabel(" ");
	
	public Statistics() {
		buttonStatics();
	}

	//all the buttons of statistics paper 
	public void buttonStatics() {
		JLabel j1 = new JLabel("General Statistics");
		j1.setBounds(260, 15, 200, 20);
		font = j1.getFont();
		j1.setForeground(Color.white);
		j1.setFont(font.deriveFont(Font.PLAIN, 18f));
		JTabbedPaneFrame.panel5.add(j1);

		general = new JPanel();
		general.setBounds(245, 40, 400, 200);
		general.setBorder(border);
		general.setBackground(Color.black);
		JTabbedPaneFrame.panel5.add(general);
		general.setLayout(null);

		JLabel total = new JLabel("Total Books:");
		total.setBounds(10, 20, 150, 30);
		total.setFont(font.deriveFont(Font.PLAIN, 16f));
		total.setForeground(Color.white);
		
		JLabel totalSize = new JLabel("Total Books Size:");
		totalSize.setBounds(10, 60, 150, 30);
		totalSize.setFont(font.deriveFont(Font.PLAIN, 16f));
		totalSize.setForeground(Color.white);
		
		JLabel groups = new JLabel("Total Groups:");
		groups.setBounds(10, 100, 150, 30);
		groups.setFont(font.deriveFont(Font.PLAIN, 16f));
		groups.setForeground(Color.white);
		
		JLabel average = new JLabel("Average Words In Group:");
		average.setBounds(10, 140, 200, 30);
		average.setFont(font.deriveFont(Font.PLAIN, 16f));
		average.setForeground(Color.white);
		
		JButton represh = new JButton("represh");//set the results in the "general" window 
		represh.setBounds(300, 170, 90, 20);
		general.add(represh);
		
		
		JLabel total2 = new JLabel();//the result to: Total Books
		total2.setBounds(220, 18, 30, 30);
		total2.setFont(font.deriveFont(Font.BOLD, 20f));
		total2.setForeground(Color.pink);
		general.add(total2);
		
		JLabel totalSize2 = new JLabel("");//the result to: Total Books Size
		totalSize2.setBounds(220, 58, 100, 30);
		totalSize2.setFont(font.deriveFont(Font.BOLD, 20f));
		totalSize2.setForeground(Color.pink);
		general.add(totalSize2);
		
		JLabel groups2 = new JLabel("");//the result to: Total Groups
		groups2.setBounds(220, 98, 30, 30);
		groups2.setFont(font.deriveFont(Font.BOLD, 20f));
		groups2.setForeground(Color.pink);
		general.add(groups2);
		
		JLabel average2 = new JLabel("");//the result to: Average Words In Group
		average2.setBounds(220, 138, 30, 30);
		average2.setFont(font.deriveFont(Font.BOLD, 20f));
		average2.setForeground(Color.pink);
		general.add(average2);
		
		represh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				countData(SqlCon.COUNT_ROWS);
				String st = String.valueOf(count);
				total2.setText(st);
				
				countData(SqlCon.SUM_SIZE);
				String st2 = String.valueOf(count);
				totalSize2.setText(st2);
				
				countData(SqlCon.COUNT_GROUPS);
				String st3 = String.valueOf(count-1);
				groups2.setText(st3);
				
				countData(SqlCon.COUNT_GROUPS);
				int groupsNumber = count-1;
				countData(SqlCon.COUNT_WORDS_GROUP);
				int wordsNumber = count;
				String st4;
				if(wordsNumber==0)
					st4 = String.valueOf(0);
				else
					st4 = String.valueOf(wordsNumber/groupsNumber);
				average2.setText(st4);
			}
		});
		
		general.add(total);
		general.add(totalSize);
		general.add(groups);
		general.add(average);


		JLabel j2 = new JLabel("Book Statistics");
		j2.setBounds(55, 270, 200, 20);
		font = j2.getFont();
		j2.setForeground(Color.white);
		j2.setFont(font.deriveFont(Font.PLAIN, 18f));
		JTabbedPaneFrame.panel5.add(j2);

		spesific = new JPanel();
		spesific.setBounds(30, 295, 825, 420);
		spesific.setBorder(border);
		spesific.setBackground(Color.black);
		JTabbedPaneFrame.panel5.add(spesific);
		spesific.setLayout(null);

		JLabel j3 = new JLabel("Select Book:");
		j3.setBounds(215, 10, 120, 80);
		j3.setForeground(Color.white);
		j3.setFont(font.deriveFont(Font.PLAIN, 18f));
		spesific.add(j3);

		showListTitel();

		JComboBox bookList = new JComboBox(booksList);
		bookList.setSelectedIndex(0);
		// petList.addActionListener();
		bookList.setFont(font.deriveFont(Font.PLAIN, 16f));
		bookList.setBounds(335, 40, 300, 25);
		spesific.add(bookList);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int ind = bookList.getSelectedIndex();
				String query;
				if (ind == 0)
					query = SqlCon.ALL_STATISTICS;
				else
					query = SqlCon.SELECT_STATISTICS;
				
				num(booksList[ind], query);
				
				String st = String.valueOf(wordsCount);
				totalWords2.setText(st);
				
				st = String.valueOf(charactersCount);
				letters2.setText(st);
				
				st = String.valueOf(averageL);
				averageLetters2.setText(st);
				
				
				totalWords2.setBounds(525, 90, 150, 30);
				totalWords2.setFont(font.deriveFont(Font.BOLD, 20f));
				totalWords2.setForeground(Color.pink);
				letters2.setBounds(525, 130, 150, 30);
				letters2.setFont(font.deriveFont(Font.BOLD, 20f));
				letters2.setForeground(Color.pink);
				averageLetters2.setBounds(525, 170, 150, 30);
				averageLetters2.setFont(font.deriveFont(Font.BOLD, 20f));
				averageLetters2.setForeground(Color.pink);
				
				spesific.add(totalWords2);
				spesific.add(letters2);
				spesific.add(averageLetters2);
				
				
				String st2;
				st2 = String.valueOf(paragraphCount);
				totalP2.setText(st2);
				
				st2 = String.valueOf(wordsInParagraph);
				wordP2.setText(st2);
				
				st2 = String.valueOf(charactersInParagraph);
				lettersP2.setText(st2);
				
				
				totalP2.setBounds(200, 296, 150, 30);
				totalP2.setFont(font.deriveFont(Font.PLAIN, 16f));
				totalP2.setForeground(Color.pink);
				wordP2.setBounds(200, 336, 150, 30);
				wordP2.setFont(font.deriveFont(Font.PLAIN, 16f));
				wordP2.setForeground(Color.pink);
				lettersP2.setBounds(200, 376, 150, 30);
				lettersP2.setFont(font.deriveFont(Font.PLAIN, 16f));
				lettersP2.setForeground(Color.pink);
				
				spesific.add(totalP2);
				spesific.add(wordP2);
				spesific.add(lettersP2);
				
				String st3;
				st3 = String.valueOf(linesCount);
				totalL2.setText(st3);
				
				st3 = String.valueOf(wordsInline);
				wordL2.setText(st3);
				
				st3 = String.valueOf(charactersInLine);
				lettersL2.setText(st3);
				
				
				totalL2.setBounds(455, 296, 60, 30);
				totalL2.setFont(font.deriveFont(Font.PLAIN, 16f));
				totalL2.setForeground(Color.pink);
				wordL2.setBounds(455, 336, 60, 30);
				wordL2.setFont(font.deriveFont(Font.PLAIN, 16f));
				wordL2.setForeground(Color.pink);
				lettersL2.setBounds(455, 376, 60, 30);
				lettersL2.setFont(font.deriveFont(Font.PLAIN, 16f));
				lettersL2.setForeground(Color.pink);
				
				spesific.add(totalL2);
				spesific.add(wordL2);
				spesific.add(lettersL2);
				
				
				String st4;
				st4 = String.valueOf(sentencesCount);
				totalS2.setText(st4);
				
				st4 = String.valueOf(wordsInSentence);
				wordS2.setText(st4);
				
				st4 = String.valueOf(charactersInSentence);
				lettersS2.setText(st4);
				
				totalS2.setBounds(760, 296, 60, 30);
				totalS2.setFont(font.deriveFont(Font.PLAIN, 16f));
				totalS2.setForeground(Color.pink);
				wordS2.setBounds(760, 336, 60, 30);
				wordS2.setFont(font.deriveFont(Font.PLAIN, 16f));
				wordS2.setForeground(Color.pink);
				lettersS2.setBounds(760, 376, 60, 30);
				lettersS2.setFont(font.deriveFont(Font.PLAIN, 16f));
				lettersS2.setForeground(Color.pink);
				
				spesific.add(totalS2);
				spesific.add(wordS2);
				spesific.add(lettersS2);
				
			}
		};
		bookList.addActionListener(actionListener);
		
		
		JLabel totalWords = new JLabel("Total Words:");
		JLabel letters = new JLabel("Total Letters:");
		JLabel averageLetters = new JLabel("Average Letters in Word:");
		
		totalWords.setBounds(285, 90, 150, 30);
		totalWords.setFont(font.deriveFont(Font.PLAIN, 18f));
		totalWords.setForeground(Color.white);
		
		letters.setBounds(285, 130, 150, 30);
		letters.setFont(font.deriveFont(Font.PLAIN, 18f));
		letters.setForeground(Color.white);
		
		averageLetters.setBounds(285, 170, 230, 30);
		averageLetters.setFont(font.deriveFont(Font.PLAIN, 18f));
		averageLetters.setForeground(Color.white);
		
		spesific.add(totalWords);
		spesific.add(letters);
		spesific.add(averageLetters);
		
		JLabel totalP = new JLabel("Total Paragraphs:");
		JLabel wordP = new JLabel("Average Words in Paragraph:");
		JLabel lettersP = new JLabel("Average Letters in Paragraph:");
		
		totalP.setBounds(5, 300, 150, 20);
		totalP.setFont(font.deriveFont(Font.PLAIN, 14f));
		totalP.setForeground(Color.white);
		
		wordP.setBounds(5, 340, 190, 20);
		wordP.setFont(font.deriveFont(Font.PLAIN, 14f));
		wordP.setForeground(Color.white);
		
		lettersP.setBounds(5, 380, 190,20);
		lettersP.setFont(font.deriveFont(Font.PLAIN, 14f));
		lettersP.setForeground(Color.white);
		
		spesific.add(totalP);
		spesific.add(wordP);
		spesific.add(lettersP);
		
		JLabel totalL = new JLabel("Total Lines:");
		JLabel wordL = new JLabel("Average Words in Line:");
		JLabel lettersL = new JLabel("Average Letters in Line:");
		
		totalL.setBounds(300, 300, 150, 20);
		totalL.setFont(font.deriveFont(Font.PLAIN, 14f));
		totalL.setForeground(Color.white);
		
		wordL.setBounds(300, 340, 150, 20);
		wordL.setFont(font.deriveFont(Font.PLAIN, 14f));
		wordL.setForeground(Color.white);
		
		lettersL.setBounds(300, 380, 150,20);
		lettersL.setFont(font.deriveFont(Font.PLAIN, 14f));
		lettersL.setForeground(Color.white);
		
		spesific.add(totalL);
		spesific.add(wordL);
		spesific.add(lettersL);
		
		
		JLabel totalS = new JLabel("Total Sentences:");
		JLabel wordS = new JLabel("Average Words in Sentence:");
		JLabel lettersS = new JLabel("Average Letters in Sentence:");
		
		totalS.setBounds(565, 300, 150, 20);
		totalS.setFont(font.deriveFont(Font.PLAIN, 14f));
		totalS.setForeground(Color.white);
		
		wordS.setBounds(565, 340, 190, 20);
		wordS.setFont(font.deriveFont(Font.PLAIN, 14f));
		wordS.setForeground(Color.white);
		
		lettersS.setBounds(565, 380, 190,20);
		lettersS.setFont(font.deriveFont(Font.PLAIN, 14f));
		lettersS.setForeground(Color.white);
		
		spesific.add(totalS);
		spesific.add(wordS);
		spesific.add(lettersS);
	}
 
	public void countData(String query) {
		try {
			statement = SqlCon.getConnection().createStatement();
			rs = statement.executeQuery(query);
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
/*
 * show the list of the books in comboBox.
 */
	public static void showListTitel() {
		try {
			statement = SqlCon.getConnection().createStatement();
			rs = statement.executeQuery(SqlCon.SELECT_TITLE);
			i = 1;
			arr2[0] = "All";
			while (rs.next()) {
				String title = rs.getString("title");
				arr2[i] = title;
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		booksList = new String[i];
		for (int j = 0; j < i; j++) {
			booksList[j] = arr2[j];
		}
	}

	/* count sum of paragraphs,lines,words,sentences,characters for each book
	 * 
	 */
	public void num(String title, String query) {
		spesific.repaint();
		try {
			statementP = SqlCon.getConnection().prepareStatement(query);
			if (query.equals(SqlCon.SELECT_STATISTICS))
				statementP.setString(1, title);
			rs = statementP.executeQuery();
			rs.next();
			
			paragraphCount = rs.getInt(1);
			linesCount = rs.getInt(2);
			wordsCount = rs.getInt(3);
			sentencesCount = rs.getInt(4);
			charactersCount = rs.getInt(5);

			wordsInParagraph = (wordsCount / paragraphCount);
			charactersInParagraph = (charactersCount / paragraphCount);

			wordsInline = (wordsCount / linesCount);
			charactersInLine = (charactersCount / linesCount);

			wordsInSentence = (wordsCount / sentencesCount);
			charactersInSentence = (charactersCount / sentencesCount);
			
			averageL = (charactersCount/wordsCount);
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
	}
}