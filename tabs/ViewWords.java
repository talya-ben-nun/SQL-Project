package sqlP;

import java.awt.Color;

import java.awt.Font;
import java.awt.List;
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
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import java.util.stream.*;  
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
	static JComboBox B;
	static JComboBox bookList;

	public ViewWords() {
		buttonsView();
	}

	public void buttonsView() {
		j1 = new JLabel("Search By Index");
		j1.setBounds(400, 10, 200, 30);
		Font font = j1.getFont();
		j1.setForeground(Color.gray);
		j1.setFont(font.deriveFont(Font.BOLD, 18f));
		JTabbedPaneFrame.panel2.add(j1);

		JPanel words = new JPanel();
		words.setBounds(10, 35, 870, 130);
		words.setBorder(border);
		words.setBackground(Color.gray);
		JTabbedPaneFrame.panel2.add(words);
		words.setLayout(null);

		JLabel bookFilter = new JLabel("Book:");
		bookFilter.setBounds(10, 20, 50, 30);
		bookFilter.setFont(font.deriveFont(Font.PLAIN, 15f));
		bookFilter.setForeground(Color.white);
		words.add(bookFilter);

		
		showListTitels();
		bookList = new JComboBox(booksList2);
		bookList.setSelectedIndex(1);
		bookList.setBounds(50, 25, 190, 20);
		words.add(bookList);

		JLabel parag = new JLabel("Paragraph:");
		parag.setBounds(255, 20, 80, 30);
		parag.setFont(font.deriveFont(Font.PLAIN, 15f));
		parag.setForeground(Color.white);
		words.add(parag);

		JTextField P = new JTextField(20);
		P.setBounds(330, 25, 50, 20);
		P.setFont(font.deriveFont(Font.BOLD, 15f));
		words.add(P);

		JLabel line = new JLabel("Sentence In Paragraph:");
		line.setBounds(397, 20, 170, 30);
		line.setFont(font.deriveFont(Font.PLAIN, 15f));
		line.setForeground(Color.white);
		words.add(line);

		JTextField L = new JTextField(20);
		L.setBounds(567, 25, 50, 20);
		L.setFont(font.deriveFont(Font.BOLD, 15f));
		words.add(L);

		JLabel lineInd = new JLabel("Index In Sentence:");
		lineInd.setBounds(627, 20, 120, 30);
		lineInd.setFont(font.deriveFont(Font.PLAIN, 15f));
		lineInd.setForeground(Color.white);
		words.add(lineInd);

		JTextField LI = new JTextField(20);
		LI.setBounds(762, 25, 50, 20);
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

		JButton enter = new JButton("Find");
		enter.setBounds(715, 100, 70, 25);
		words.add(enter);

		JButton clear = new JButton("Clear");
		clear.setBounds(790, 100, 70, 25);
		words.add(clear);

		JLabel book = new JLabel("Book:");
		book.setBounds(15, 215, 60, 30);
		book.setFont(font.deriveFont(Font.BOLD, 15f));
		book.setForeground(Color.gray);
		JTabbedPaneFrame.panel2.add(book);

		Statistics.showListTitel();
		B = new JComboBox(Statistics.booksList);
		B.setSelectedIndex(0);
		B.setBounds(60, 220, 155, 20);
		JTabbedPaneFrame.panel2.add(B);

		JButton enter2 = new JButton("Show Words");
		enter2.setBounds(15, 248, 200, 25);
		JTabbedPaneFrame.panel2.add(enter2);

		list1.setForeground(Color.white);
		list1.setBackground(Color.gray);
		jsp = new JScrollPane(list1);
		jsp.setBounds(15, 280, 200, 440);
		JTabbedPaneFrame.panel2.add(jsp);
		
		JLabel indexByWord = new JLabel("Search By Word");
		indexByWord.setBounds(400, 185, 200, 30);
		indexByWord.setFont(font.deriveFont(Font.BOLD, 18f));
		indexByWord.setForeground(Color.gray);
		JTabbedPaneFrame.panel2.add(indexByWord);


		Object column[] = { "Book", "Paragraph", "Sentence", "Index In Sentence" };
		Object data[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		mTableModel = new DefaultTableModel(data, column);
		JTable table = new JTable(mTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(225, 220, 650, 115);
		JTabbedPaneFrame.panel2.add(scrollPane);
		mTableModel.removeRow(0);

		JButton clearTable = new JButton("Clear");
		clearTable.setBounds(225, 335, 650, 25);
		JTabbedPaneFrame.panel2.add(clearTable);

		area.setBackground(Color.gray);
		area.setFont(font.deriveFont(Font.PLAIN, 15f));
		area.setForeground(Color.white);
		jsp2 = new JScrollPane(area);
		jsp2.setBounds(225, 375, 650, 320);
		JTabbedPaneFrame.panel2.add(jsp2);
		
		JButton clear2 = new JButton("Clear");
		clear2.setBounds(225, 695, 650, 25);
		JTabbedPaneFrame.panel2.add(clear2);

		
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
					TreeMap<String, Integer> sorted = new TreeMap<>();
					sorted.putAll(hmap);
					for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
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
		int i=-1;
		int countS=1;
		int ind1 = 0;
	    int ind2 = 0;
	    int sum=0;
		try {
			while(i< 2) {
				if(value==1)
					i++;
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.TEXT_ACCORDING_PARAGRAPH);
				statementP.setString(1, bookName);
				statementP.setInt(2, value +i);
				rs = statementP.executeQuery();
				while (rs.next()) {
					System.out.println(indexL);
					area.append(rs.getString(1));
					area.append("\n");
	                area.requestFocus();
					if(i==0)//in corrent paragraph
					{
						if(numLine==countS)
						{
							String[] wordLine = rs.getString(1).split(" ");
							for (int j = 0; j < indexL-1; j++) {
								System.out.println(wordLine[j]);
								sum += wordLine[j].length();
							}
							ind1= indexL-1 +sum;
							ind2 = ind1 + wordLine[indexL-1].length();
						}
						countS++;
					}
					sum+=rs.getString(1).length()+1;
				h.addHighlight(ind1, ind2, new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));// highlight the spesific word that the user look for
				
				}
				
				i++;
			}
		} catch (SQLException | BadLocationException e) {
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
	 * @throws IOException pass all the books in the db and
	 *                     search the word that accepted.
	 */
	public static void searchWord(String word, int mark) throws IOException {
		int flag = 0;
		InsertBook.bookAccordingWord.clear();
		
		try {
			String wordSearch = "%" + removeSign(word)+" "+"%";
			if (mark == 0) {
				System.out.println("dd");
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.ALL_WORDS);
				statementP.setString(1, wordSearch);
				rs = statementP.executeQuery();
			} else {
				
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.SEARCH_WORD);
				statementP.setString(1, title2);
				statementP.setString(2, wordSearch);
				rs = statementP.executeQuery();
			}
			
			while (rs.next()) {
				int si= 0;
				String [] arr = rs.getString("word").split(" ");
				for(int i=0; i< arr.length; i++) {
					
					if(arr[i].equals(word)) {
						if (mark == 0) {
							InsertBook.bookAccordingWord.add(rs.getString(1));
							flag = 1;
						} else {
						System.out.println(arr[i]);
						si=i+1;
						Object[] rows = new Object[] { title2, rs.getInt(2), rs.getInt(3), si };	
						mTableModel.addRow(rows);
					}
				}
				
			}
			}
			flag = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * return word according place that enter from the user, if the location not found the system will print warning massage
	 */
	public static String wordIndex(String title, int p, int s, int si) throws IOException {
//		String line;
//		BufferedReader in;
//		int flag = 0;
//		int countP = 1;
//		int countL = 1;
		int worning = 1;
		String found = " ";
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.WORD_INDEX);
			statementP.setString(1, title);
			statementP.setInt(2, p);
			statementP.setInt(3, s);
			rs = statementP.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("word"));
				String [] arr = rs.getString("word").split(" ");
				if(si<=arr.length)
				{
					found= arr[si-1];
					worning = 0;
				}
				
//				String path = rs.getString("filePath");
//				in = new BufferedReader(new FileReader(path));
//				line = in.readLine();
//				while (line != null && flag != 1) {
//					if (countP == p) {
//						while ((l > countL) && (!(line.equals("")))) {
//							line = in.readLine();
//							countL++;
//						}
//						if (l == countL && (!(line.equals("")))) {
//							String[] str = line.split(" ");
//							if (li > str.length) {
//								System.out.println("wor1");
//								worning = 1;
//								flag = 1;
//							} else {
//								found = str[li - 1];
//								found = found.replaceAll("[^\\p{IsAlphabetic}]", "");
//								flag = 1;
//							}
//						} else {
//							worning = 1;
//							flag = 1;
//						}
//
//					} else if (line.equals("")) {
//						countP++;
//						line = in.readLine();
//					} else
//						line = in.readLine();
//				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (worning == 1) {
			f = new JFrame();
			JOptionPane.showMessageDialog(f, "Not Found", "Alert", JOptionPane.WARNING_MESSAGE);
		}
		found=removeSign(found);
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
				rs = statement.executeQuery(SqlCon.SHOW_TEXT);
			} else {
				statementP = SqlCon.getConnection().prepareStatement(SqlCon.TEXT_ACCORDING_TITLE);
				statementP.setString(1, title);
				rs = statementP.executeQuery();
			}

            // for each row returned
			while (rs.next()) {
				String sent = rs.getString(1);
					if (!(sent.equals(""))) {
						String[] wordList = sent.split("\\s+");
						for (int i = 0; i < wordList.length; i++) {
							String newWord = wordList[i];
							newWord = newWord.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
							if (!(newWord.equals(""))) {
								newWord = newWord.toLowerCase();
								count = hmap.containsKey(newWord) ? hmap.get(newWord) : 0;
								hmap.put(newWord, count + 1);
								}
							}
					}
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static String removeSign(String word) {
		word=word.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
		return word;
	}
}
