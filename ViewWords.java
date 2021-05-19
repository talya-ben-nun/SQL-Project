package sqlP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.TextAction;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ViewWords {

	JLabel j1;
	Border border = new LineBorder(Color.white, 1, true);
	static PreparedStatement statementP;
	static Statement statement;
	static ResultSet rs;
	static String line;
	static BufferedReader in;
	static HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	static DefaultTableModel mTableModel;
	JTextArea area = new JTextArea();
	JScrollPane jsp2 = new JScrollPane();
	DefaultListModel listModel = new DefaultListModel();

	static String[] arr = new String[100000];

	JList list1 = new JList();
	JScrollPane jsp = new JScrollPane();

	static String title;
	static int paragraphs = 1;
	static int lines = 1;
	static String title2;
	static JFrame f;

	static String[] booksList2;
	int check;

	public ViewWords() {
		buttonsView();
	}

	public void buttonsView() {
		j1 = new JLabel("Word By Index");
		j1.setBounds(400, 5, 200, 20);
		Font font = j1.getFont();
		j1.setForeground(Color.white);
		j1.setFont(font.deriveFont(Font.BOLD, 14f));
		JTabbedPaneFrame.panel2.add(j1);

		JPanel words = new JPanel();
		words.setBounds(10, 25, 870, 130);
		words.setBorder(border);
		words.setBackground(Color.black);
		JTabbedPaneFrame.panel2.add(words);
		words.setLayout(null);

		JLabel bookFilter = new JLabel("Book:");
		bookFilter.setBounds(10, 20, 50, 30);
		bookFilter.setFont(font.deriveFont(Font.PLAIN, 15f));
		bookFilter.setForeground(Color.white);
		words.add(bookFilter);

		showListTitels();
		JComboBox bookList = new JComboBox(booksList2);
		bookList.setSelectedIndex(1);
		bookList.setBounds(50, 25, 190, 20);
		words.add(bookList);

		JLabel parag = new JLabel("Paragraph:");
		parag.setBounds(255, 20, 80, 30);
		parag.setFont(font.deriveFont(Font.PLAIN, 15f));
		parag.setForeground(Color.white);
		words.add(parag);

		JTextField P = new JTextField(20);
		P.setBounds(330, 25, 100, 20);
		P.setFont(font.deriveFont(Font.BOLD, 15f));
		words.add(P);

		JLabel line = new JLabel("Line In Paragraph:");
		line.setBounds(447, 20, 120, 30);
		line.setFont(font.deriveFont(Font.PLAIN, 15f));
		line.setForeground(Color.white);
		words.add(line);

		JTextField L = new JTextField(20);
		L.setBounds(567, 25, 100, 20);
		L.setFont(font.deriveFont(Font.BOLD, 15f));
		words.add(L);

		JLabel lineInd = new JLabel("Index In Line:");
		lineInd.setBounds(677, 20, 92, 30);
		lineInd.setFont(font.deriveFont(Font.PLAIN, 15f));
		lineInd.setForeground(Color.white);
		words.add(lineInd);

		JTextField LI = new JTextField(20);
		LI.setBounds(762, 25, 100, 20);
		LI.setFont(font.deriveFont(Font.BOLD, 15f));
		words.add(LI);

		JLabel word = new JLabel("Word:");
		word.setBounds(330, 60, 60, 30);
		word.setFont(font.deriveFont(Font.BOLD, 15f));
		word.setForeground(Color.white);
		words.add(word);

		JLabel W = new JLabel("");
		W.setBounds(380, 60, 150, 30);
		W.setFont(font.deriveFont(Font.BOLD, 15f));
		W.setForeground(Color.pink);
		words.add(W);

		JButton enter = new JButton("Enter");
		enter.setBounds(715, 100, 70, 25);
		words.add(enter);

		JButton clear = new JButton("Clear");
		clear.setBounds(790, 100, 70, 25);
		words.add(clear);

		JButton clear2 = new JButton("Clear");
		clear2.setBounds(225, 675, 650, 25);
		JTabbedPaneFrame.panel2.add(clear2);

		

		JLabel book = new JLabel("Book:");
		book.setBounds(15, 195, 60, 30);
		book.setFont(font.deriveFont(Font.PLAIN, 15f));
		book.setForeground(Color.white);
		JTabbedPaneFrame.panel2.add(book);

		Statistics.showListTitel();
		JComboBox B = new JComboBox(Statistics.booksList);
		B.setSelectedIndex(0);
		B.setBounds(60, 200, 155, 20);
		JTabbedPaneFrame.panel2.add(B);

		JButton enter2 = new JButton("Enter");
		enter2.setBounds(15, 228, 200, 25);
		JTabbedPaneFrame.panel2.add(enter2);

		list1.setForeground(Color.white);
		list1.setBackground(Color.black);
		jsp = new JScrollPane(list1);
		jsp.setBounds(15, 260, 200, 440);
		JTabbedPaneFrame.panel2.add(jsp);
		
		JLabel indexByWord = new JLabel("Index By Word");
		indexByWord.setBounds(400, 170, 200, 20);
		indexByWord.setFont(font.deriveFont(Font.BOLD, 15f));
		indexByWord.setForeground(Color.white);
		JTabbedPaneFrame.panel2.add(indexByWord);


		Object column[] = { "Book", "Paragraph", "Line", "Index In Line" };
		Object data[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		mTableModel = new DefaultTableModel(data, column);
		JTable table = new JTable(mTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(225, 200, 650, 115);
		JTabbedPaneFrame.panel2.add(scrollPane);
		mTableModel.removeRow(0);

		JButton clearTable = new JButton("Clear Results");
		clearTable.setBounds(225, 315, 650, 25);
		JTabbedPaneFrame.panel2.add(clearTable);

		area.setBackground(Color.black);
		area.setFont(font.deriveFont(Font.PLAIN, 15f));
		area.setForeground(Color.white);
		jsp2 = new JScrollPane(area);
		jsp2.setBounds(225, 355, 650, 310);
		JTabbedPaneFrame.panel2.add(jsp2);
		
		//Displays a word by index
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = "";
				int ind = bookList.getSelectedIndex();
				title = booksList2[ind];
				try {
					String paragIns = P.getText();
					String lineIns = L.getText();
					String indexIns = LI.getText();
					if (paragIns.equals("") || lineIns.equals("") || indexIns.equals(""))
						JOptionPane.showMessageDialog(f, "Missing data", "Alert", JOptionPane.WARNING_MESSAGE);
					else {
						float a = Float.parseFloat(paragIns);
						float b = Float.parseFloat(lineIns);
						float c = Float.parseFloat(indexIns);
						int a1 = Math.round(a);
						int b1 = Math.round(b);
						int c1 = Math.round(c);
						statementP = SqlCon.getConnection().prepareStatement(SqlCon.NUMPARAG_ACORDING_TITLE);
						statementP.setString(1, title);
						rs = statementP.executeQuery();
						rs.next();
						check = rs.getInt(1);
						System.out.println(check);
						if ((a1 - a > 0) || (b1 - b > 0) || (c1 - c > 0) || (int) a > check)
							JOptionPane.showMessageDialog(f, "Incorrect Data", "Alert", JOptionPane.WARNING_MESSAGE);
						else {

							String wordFound = wordIndex(title, (int) a, (int) b, (int) c);
							if (!(wordFound.equals(" "))) {
								W.setText(wordFound);
								System.out.println(wordFound);
							}
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		//clear the search of 'word by index'
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				P.setText("");
				L.setText("");
				LI.setText("");
				W.setText("");
			}
		});
		
		//Displays all the words that appear in the selected book or in all the books
		enter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = "";
				int ind = B.getSelectedIndex();
				if (ind == 0) {
					title = "all";
				} else {
					title = Statistics.booksList[ind];
				}
				try {
					sumWords(title);
					Arrays.fill(arr, " ");
					int index = 0;
					for (String name : hmap.keySet()) {
						String key = name.toString();
						String value = hmap.get(name).toString();
						String str = ("  " + key + "  " + "(" + value + ")");
						arr[index++] = str;
					}

					DefaultListModel listModel = new DefaultListModel();
					for (int i = 0; i < arr.length; i++) {
						if (!(arr[i].equals(" "))) {
							listModel.addElement(arr[i]);
						}
					}
					list1.repaint();
					list1.setModel(listModel);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Displays the occurrences of a selected word
		list1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 1) {
					JList target = (JList) me.getSource();
					int index = target.locationToIndex(me.getPoint());
					if (index >= 0) {
						Object item = target.getModel().getElementAt(index);
						String item2 = item.toString();
						String wordSplit[] = item2.split("\\s+");
						String word = wordSplit[1];

						try {
							if (title.equals("all")) {
								for (int i = 1; i < Statistics.booksList.length; i++) {
									title2 = Statistics.booksList[i];
									searchWord(word, 1);
								}
							} else {
								title2 = title;
								searchWord(word, 1);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		//Shows the context of a word by a selected line
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 1) {
					int row = table.getSelectedRow();
					int value = (int) table.getModel().getValueAt(row, 1);
					String bookName = (String) table.getModel().getValueAt(row, 0).toString();
					int numLine = (int) table.getModel().getValueAt(row, 2);
					int indexL = (int) table.getModel().getValueAt(row, 3);
					textRaw(row, value, bookName, numLine, indexL);//Send to a function that will see the section of the context of the word
				}
			}
		});
		
		//clear the results in the table
		clearTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mTableModel.getRowCount() > 0) {
					for (int i = mTableModel.getRowCount() - 1; i > -1; i--) {
						mTableModel.removeRow(i);
					}
				}

			}
		});

		//clear the textArea
		clear2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("");

			}
		});
	}

	/*
	 *  show the text that contains the word from the book
	 */
	public void textRaw(int row, int value, String bookName, int numLine, int indexL) {
		int lines2 = 0;
		String line2;
		BufferedReader in2;
		int paragraphs2 = 0;
		Highlighter h = area.getHighlighter();
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
			statementP.setString(1, bookName);
			rs = statementP.executeQuery();
			while (rs.next()) {
				String path3 = rs.getString("filePath");
				in2 = new BufferedReader(new FileReader(path3));
				line2 = in2.readLine();
				int sum = 0;
				int flag2 = 0;
				int ind1 = 0;
				int ind2 = 0;
				if (value != 1) {
					sum++;
				}
				while (line2 != null && flag2 != 1) {
					if ((value != 1 && paragraphs2 == value - 2) || paragraphs2 == value - 1
							|| (value != check && paragraphs2 == value)) {// show sum paragraphs around the word
						while (line2 != null && !(line2.equals(""))) {
							if (lines2 == numLine - 1) {
								String[] wordLine = line2.split(" ");
								for (int i = 0; i < indexL - 1; i++) {
									sum += wordLine[i].length();
								}
								ind1 = sum + indexL - 1;
								ind2 = ind1 + wordLine[indexL - 1].length();
							}
							sum += line2.length() + 1;
							area.append(line2 + "\n");
							line2 = in2.readLine();
							lines2++;
						}
						area.append("\n");
					}
					if (line2 != null && line2.equals("")) {
						paragraphs2++;
					}
					if (line2 != null && flag2 != 1) {

						line2 = in2.readLine();
						lines2++;
					}
				}
				h.addHighlight(ind1, ind2, DefaultHighlighter.DefaultPainter);// highlight the spesific word that the user look for
			}
		} catch (IOException | SQLException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	/*
	 *  the names of books that in the database
	 */
	public static void showListTitels() {
		int i = 0;
		String[] array = new String[100];
		try {
			statement = SqlCon.getConnection().createStatement();
			rs = statement.executeQuery(SqlCon.SELECT_TITLE);
			while (rs.next()) {
				String title = rs.getString("title");
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		booksList2 = new String[i];
		for (int j = 0; j < i; j++) {
			booksList2[j] = array[j];
		}
	}

	/*
	 * mark to know if ViewWords or InsertBook send to the function
	 *             insertBook is zero, ViewWords is one.
	 * @throws IOException pass all the books in the db according the filePath and
	 *                     search the word that accepted.
	 */
	public static void searchWord(String word, int mark) throws IOException {
		String line;
		BufferedReader in;
		Connection conn = null;
		int flag = 0;
		InsertBook.bookAccordingWord.clear();
		try {
			if (mark == 0) {
				statement = SqlCon.getConnection().createStatement();
				rs = statement.executeQuery(SqlCon.SELECT_PATH);

			} else {
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
				statementP.setString(1, title2);
				rs = statementP.executeQuery();
			}
			while (rs.next()) {
				String path = rs.getString("filePath");
				in = new BufferedReader(new FileReader(path));
				line = in.readLine();
				while (line != null && flag != 1) {
					if (line.equals("")) {
						paragraphs++;
					} else {
						String[] wordList = line.split("\\s+");
						for (int i = 0; i < wordList.length && flag != 1; i++) {
							String newWord = wordList[i];
							newWord = newWord.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
							// System.out.println(newWord);
							if (newWord.toLowerCase().equals(word)) {

								if (mark == 0) {
									InsertBook.bookAccordingWord.add(path);
									flag = 1;
								} else {
										Object[] rows;
										int p = paragraphs;
										int l = lines;
										int il = i + 1;
										rows = new Object[] { title2, p, l, il };
										// add the temp row to the table
										mTableModel.addRow(rows);
								}
							}
						}
					}
					lines++;
					line = in.readLine();
				}
				flag = 0;
				paragraphs = 1;
				lines = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * return word according place that enter from the user, if the location not found the system will print warning massage
	 */
	public static String wordIndex(String title, int p, int l, int li) throws IOException {
		String line;
		BufferedReader in;
		int flag = 0;
		int countP = 1;
		int countL = 1;
		int worning = 0;
		String found = " ";
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
			statementP.setString(1, title);
			rs = statementP.executeQuery();
			while (rs.next()) {
				String path = rs.getString("filePath");
				in = new BufferedReader(new FileReader(path));
				line = in.readLine();
				while (line != null && flag != 1) {
					if (countP == p) {
						while ((l > countL) && (!(line.equals("")))) {
							line = in.readLine();
							countL++;
						}
						if (l == countL && (!(line.equals("")))) {
							System.out.println("iff l=cont");
							String[] str = line.split(" ");
							if (li > str.length) {
								System.out.println("wor1");
								worning = 1;
								flag = 1;
							} else {
								found = str[li - 1];
								found = found.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
								flag = 1;
							}
						} else {
							worning = 1;
							flag = 1;
						}

					} else if (line.equals("")) {
						countP++;
						line = in.readLine();
					} else
						line = in.readLine();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (worning == 1) {
			f = new JFrame();
			JOptionPane.showMessageDialog(f, "Not Found", "Alert", JOptionPane.WARNING_MESSAGE);
		}
		return found;
	}

	/*
	 *  count how many words appears from every word
	 */
	public static void sumWords(String title) throws IOException {
		int lines = 0;
		hmap.clear();
		int count;
		try {
			if (title.equals("all")) {
				statement = SqlCon.getConnection().createStatement();
				rs = statement.executeQuery(SqlCon.SELECT_PATH);
			} else {
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.PATH_ACORDING_TITLE);
				statementP.setString(1, title);
				rs = statementP.executeQuery();
			}

            // for each row returned
			while (rs.next()) {
				String path = rs.getString("filePath");
				in = new BufferedReader(new FileReader(path));
				line = in.readLine();
				while (line != null) {
					if (!(line.equals(""))) {
						String[] wordList;
						String sentenceList[] = line.split("[   \n'/'(){}#&@*$%^~!?,'.:;_﻿-]+");
						for (int i = 0; i < sentenceList.length; i++) {
							wordList = sentenceList[i].split("\\s+");
							for (int j = 0; j < wordList.length; j++) {
								wordList[j] = wordList[j].replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
								if (!(wordList[j].equals(""))) {
									wordList[j] = wordList[j].toLowerCase();
									count = hmap.containsKey(wordList[j]) ? hmap.get(wordList[j]) : 0;
									hmap.put(wordList[j], count + 1);
								}
							}
						}
					}
					lines++;
					line = in.readLine();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}