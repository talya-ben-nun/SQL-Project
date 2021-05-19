package sqlP;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	Border border = new LineBorder(Color.white, 1, true);
	DefaultTableModel mTableModel;
	JTextArea area = new JTextArea();
	JScrollPane jsp = new JScrollPane();
	JFrame f;
	Statement statement;
	ResultSet rs;
	PreparedStatement statementP;
	int x;
	int y;
	Highlighter highlighter = area.getHighlighter();
	String[] arrPhrase;
	int found = 0;

	public SearchPhrases() {
		buttonPhrases();
	}

	public void buttonPhrases() {
		JLabel insPhrase = new JLabel("Insert a phrase:");
		Font font = insPhrase.getFont();
		insPhrase.setBounds(70, 85, 150, 20);
		insPhrase.setForeground(Color.white);
		insPhrase.setFont(font.deriveFont(Font.PLAIN, 20f));
		JTabbedPaneFrame.panel4.add(insPhrase);

		JTextField P = new JTextField(20);
		P.setBounds(250, 85, 440, 25);
		P.setFont(font.deriveFont(Font.BOLD, 15f));
		JTabbedPaneFrame.panel4.add(P);

		JButton insert = new JButton("Insert");
		insert.setBounds(710, 85, 90, 25);
		JTabbedPaneFrame.panel4.add(insert);

		JLabel searPhrase = new JLabel("Phrase to search:");
		searPhrase.setBounds(70, 125, 185, 20);
		searPhrase.setForeground(Color.white);
		searPhrase.setFont(font.deriveFont(Font.PLAIN, 20f));
		JTabbedPaneFrame.panel4.add(searPhrase);

		showListPhrases();
		JComboBox phraseList = new JComboBox(arrPhrase);
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
		bookFilter.setForeground(Color.white);
		JTabbedPaneFrame.panel4.add(bookFilter);

		ViewWords.showListTitels();
		JComboBox bookList = new JComboBox(ViewWords.booksList2);
		bookList.setSelectedIndex(0);
		bookList.setBounds(300, 405, 290, 20);
		JTabbedPaneFrame.panel4.add(bookList);

		JButton choose = new JButton("Choose");
		choose.setBounds(600, 405, 90, 20);
		JTabbedPaneFrame.panel4.add(choose);

		area.setBackground(Color.black);
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
				if (phrase.equals(""))
					JOptionPane.showMessageDialog(f, "Missing data.", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					try {
						statement = SqlCon.getConnection().createStatement();
						ResultSet rs = statement.executeQuery(SqlCon.PHRASE_TO_CHECK);
						while (rs.next()) {
							String phraseName = rs.getString("word");
							if (phraseName.equals(phrase)) {
								// f = new JFrame();
								JOptionPane.showMessageDialog(f, "This phrase is allready exist.", "Alert",
										JOptionPane.WARNING_MESSAGE);
								flag = 1;
							}
						}
						if (flag != 1) {
							for (int i = 1; i < Statistics.booksList.length; i++) {
								String title = Statistics.booksList[i];
								try {
									String phrases = "";
									searchWordToGroup(title, phrase, phrases);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							if (found == 1) {
								phraseList.addItem(phrase);
							} else
								JOptionPane.showMessageDialog(f, "There is no sentence that contains the phrase.",
										"Alert", JOptionPane.WARNING_MESSAGE);
						}
					} catch (SQLException e1) {
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
		for(int k= 0; k<arrPhrase.length;k++)
			System.out.println(arrPhrase[k]);
	}

	/*
	 * show to user the book which choose to give the user option to mark phrases 
	 */
	public void writeText(String title) throws BadLocationException {
		String line2;
		BufferedReader in2;
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
			statementP.setString(1, title);
			rs = statementP.executeQuery();
			while (rs.next()) {
				String path3 = rs.getString("filePath");
				in2 = new BufferedReader(new FileReader(path3));
				line2 = in2.readLine();
				while (line2 != null) {
					area.append(line2 + "\n");
					line2 = in2.readLine();
					if (line2 != null && line2.equals("")) {
						area.append("\n");
						line2 = in2.readLine();
					}
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * search all the phrases appearense and send them to the insertword function
	 */
	public void searchWordToGroup(String title, String word, String groupIns) throws IOException {
		String line;
		BufferedReader in;
		int paragraphs = 1;
		int sentences = 1;
		found = 0;
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
			statementP.setString(1, title);
			rs = statementP.executeQuery();
			while (rs.next()) {
				String path = rs.getString("filePath");
				in = new BufferedReader(new FileReader(path));
				line = in.readLine();
				while (line != null) {
					if (line.equals("")) {
						paragraphs++;
						sentences = 1;
					} else {
						String[] sentenceList = line.split("[!?.:]+");
						for (int i = 0; i < sentenceList.length; i++) {
							if (sentenceList[i].contains(word)) {
								int p = paragraphs;
								int s = sentences;
								int is =0;
								insertTable.insertwordFunc(groupIns, word, title, p, s, is);
								found = 1;
							}
						}
					}
					sentences++;
					line = in.readLine();
				}
				paragraphs = 1;
				sentences = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
