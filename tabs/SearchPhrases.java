package sqlP;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;

public class SearchPhrases {
	Border border = new LineBorder(Color.white, 10, true);
	DefaultTableModel mTableModel;
	JTextArea area = new JTextArea();
	JScrollPane jsp = new JScrollPane();
	JFrame f;
	Statement statement;
	static ResultSet rs;
	static PreparedStatement statementP;
	int x;
	int y;
	Highlighter highlighter = area.getHighlighter();
	String[] arrPhrase;
	JComboBox<String> phraseList=new JComboBox();
	static int found = 0;
	static JComboBox bookList;

	public SearchPhrases() {
		buttonPhrases();
	}

	public void buttonPhrases() {
		
		JLabel j1 = new JLabel("Define Phrases");
		j1.setBounds(340, 10, 230, 80);
		Font font = j1.getFont();
		j1.setForeground(Color.gray);
		j1.setFont(font.deriveFont(Font.BOLD, 24f));
		JTabbedPaneFrame.panel4.add(j1);
		
		JLabel insPhrase = new JLabel("New phrase:");
		Font font2 = insPhrase.getFont();
		insPhrase.setBounds(70, 85, 150, 20);
		insPhrase.setForeground(Color.gray);
		insPhrase.setFont(font2.deriveFont(Font.PLAIN, 20f));
		JTabbedPaneFrame.panel4.add(insPhrase);

		JTextField P = new JTextField(20);
		P.setBounds(250, 85, 440, 25);
		P.setBackground(Color.gray);
		P.setFont(font.deriveFont(Font.BOLD, 15f));
		JTabbedPaneFrame.panel4.add(P);

		JButton insert = new JButton("Insert");
		insert.setBounds(710, 85, 90, 25);
		JTabbedPaneFrame.panel4.add(insert);

		JLabel searPhrase = new JLabel("Search phrase:");
		searPhrase.setBounds(70, 125, 185, 20);
		searPhrase.setForeground(Color.gray);
		searPhrase.setFont(font.deriveFont(Font.PLAIN, 20f));
		JTabbedPaneFrame.panel4.add(searPhrase);

		showListPhrases();
		phraseList = new JComboBox(arrPhrase);
		phraseList.setSelectedIndex(0);
		phraseList.setBounds(250, 125, 440, 25);
		JTabbedPaneFrame.panel4.add(phraseList);

		JButton select = new JButton("Select");
		select.setBounds(710, 125, 90, 25);
		JTabbedPaneFrame.panel4.add(select);

		Object column[] = { "Book","Paragraph", "Sentence"};
		Object data[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		mTableModel = new DefaultTableModel(data, column);
		JTable table = new JTable(mTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(70, 205, 735, 170);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200);
		JTabbedPaneFrame.panel4.add(scrollPane);
		mTableModel.removeRow(0);

		JLabel bookFilter = new JLabel("Book:");
		bookFilter.setBounds(250, 400, 50, 30);
		bookFilter.setFont(font.deriveFont(Font.PLAIN, 18f));
		bookFilter.setForeground(Color.gray);
		JTabbedPaneFrame.panel4.add(bookFilter);

		ViewWords.showListTitels();
		bookList = new JComboBox(ViewWords.booksList2);
		bookList.setSelectedIndex(0);
		bookList.setBounds(300, 405, 290, 20);
		JTabbedPaneFrame.panel4.add(bookList);

		JButton choose = new JButton("Choose");
		choose.setBounds(600, 405, 90, 20);
		JTabbedPaneFrame.panel4.add(choose);

		area.setBackground(Color.gray);
		area.setFont(font.deriveFont(Font.PLAIN, 15f));
		area.setForeground(Color.white);
		jsp = new JScrollPane(area);
		jsp.setBounds(70, 435, 735, 255);
		JTabbedPaneFrame.panel4.add(jsp);

		JButton search = new JButton("Search");
		search.setBounds(70, 690, 735, 25);
		JTabbedPaneFrame.panel4.add(search);

		JLabel showPhrase = new JLabel("");
		showPhrase.setBounds(250, 170, 400, 25);
		showPhrase.setForeground(Color.pink);
		showPhrase.setFont(font.deriveFont(Font.PLAIN, 22f));
		JTabbedPaneFrame.panel4.add(showPhrase);

		//Insert an expression into the database
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phrase = P.getText();
				int flag = 0;
				int added=0;
				if (phrase.equals(""))
					JOptionPane.showMessageDialog(f, "Missing data.", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					try {
						statement = SqlCon.getConnection().createStatement();
						ResultSet rs = statement.executeQuery(SqlCon.PHRASE_TO_CHECK);
						while (rs.next()) {
							String phraseName = rs.getString("word");
							if (phraseName.equals(phrase)) {
								JOptionPane.showMessageDialog(f, "This phrase is allready exist.", "Alert",
										JOptionPane.WARNING_MESSAGE);
								flag = 1;
								break;
							}
						}
						if(flag != 1) {
							System.out.println("jjj");
							String phrases = "";
							searchWordToGroup(phrase, phrases);
							if(found==1 && added ==0) {
								  phraseList.addItem(phrase);
								  added = 1;
								}
							if(added==0)
								JOptionPane.showMessageDialog(f, "There is no sentence that contains the phrase.","Alert", JOptionPane.WARNING_MESSAGE);	
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		//Select a phrase from the phrase list
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mTableModel.getRowCount() > 0) {
					for (int i = mTableModel.getRowCount() - 1; i > -1; i--) {
						mTableModel.removeRow(i);
					}
				}
				showListPhrases();
				showPhrase.setText("");
				String phraseIns = "";
				int ind = phraseList.getSelectedIndex();
				phraseIns = arrPhrase[ind];
				showPhrase.setText(phraseIns);
				createTablePhrase(phraseIns);
			}
		});

		//Displays the book entered by the user to search for an expression in it
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("");
				int ind = bookList.getSelectedIndex();
				String title = ViewWords.booksList2[ind];
				try {
					writeText(title);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int start;
				int end;
				try {
					start = area.getSelectionStart();
					end = area.getSelectionEnd();
					highlighter.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
					P.setText(area.getText().substring(start, end));
					highlighter.removeAllHighlights();
				} catch (BadLocationException ble) {
				}
			}
		});
	}

	/*
	 * for spesific pharase where is appears 
	 */
	public void createTablePhrase(String item2) {
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.PHRASE_INDEX);
			statementP.setString(1, item2);
			rs = statementP.executeQuery();
			Object[] rows;
			// for each row returned
			while (rs.next()) {
				// add the values to the temporary row
				rows = new Object[] { rs.getString(1), rs.getInt(2), rs.getInt(3)};
				// add the temp row to the table
				mTableModel.addRow(rows);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 *  show all the phrases in the database in comboBox to give the user option to choose one
	 */
	public void showListPhrases() {
		int i = 0;
		String[] array = new String[100];
		try {
			statement = SqlCon.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(SqlCon.PHRASE_TO_CHECK);
			while (rs.next()) {
				String phrase = rs.getString("word");
				array[i] = phrase;
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		i= i+1;
		arrPhrase = new String[i];
		arrPhrase[0] = "None";
		for (int j = 1; j < i; j++) {
			arrPhrase[j] = array[j-1];
		}
	}

	/*
	 * show to user the book which choose to give the user option to mark phrases 
	 */
	public void writeText(String title) throws BadLocationException {
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.TEXT_ACCORDING_TITLE);
			statementP.setString(1, title);
			rs = statementP.executeQuery();
			while (rs.next()) {
				area.append(rs.getString(1));
				area.append("\n");
                area.requestFocus();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String removeSign(String word) {
		word=word.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
		return word;
	}
	/*
	 * search all the phrases appearense and send them to the insertword function
	 */
	public static void searchWordToGroup(String word, String groupIns) throws IOException {
		
		String phraseSearch = "%" + word+"%";
		found = 0;
		try {
			System.out.println(phraseSearch);
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.SEARCH_PHRASES);
			statementP.setString(1, phraseSearch);
			rs = statementP.executeQuery();
			while (rs.next()) {
				System.out.println("rs");
				found=1;
				insertTable.insertwordFunc(groupIns, word, rs.getString(2), rs.getInt(3), rs.getInt(4),0);
			}
		}catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
}
