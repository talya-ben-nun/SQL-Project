package sqlP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Arrays;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class WordGroups {

	JList listW = new JList();
	JScrollPane jsp2 = new JScrollPane();
	static String[] array = new String[100000];
	String array2[];
	Border border = new LineBorder(Color.white, 1, true);
	JFrame f;
	Statement statement;
	PreparedStatement statementP;
	ResultSet rs;
	String[] groupsList;
	JList list1 = new JList();
	JScrollPane jsp = new JScrollPane();
	DefaultListModel listModel = new DefaultListModel();
	//String groupIns = "";
	
	
	DefaultTableModel mTableModel;
	DefaultTableModel tableExport;
	static JTable table2;

	public WordGroups() {
		groupBottuns();
	}

	@SuppressWarnings("unchecked")
	public void groupBottuns() {
		JPanel groups = new JPanel();
		groups.setBounds(70, 100, 750, 300);
		groups.setBorder(border);
		groups.setBackground(Color.black);
		JTabbedPaneFrame.panel3.add(groups);
		groups.setLayout(null);

		JLabel j1 = new JLabel("Define Word Groups");
		j1.setBounds(340, 10, 230, 80);
		Font font = j1.getFont();
		j1.setForeground(Color.white);
		j1.setFont(font.deriveFont(Font.PLAIN, 24f));
		JTabbedPaneFrame.panel3.add(j1);

		JLabel group = new JLabel("Insert name group:");
		font = group.getFont();
		group.setBounds(80, 55, 185, 25);
		group.setFont(font.deriveFont(Font.PLAIN, 18f));
		group.setForeground(Color.white);
		groups.add(group);

		JTextField G = new JTextField(20);
		G.setBounds(260, 55, 300, 25);
		G.setFont(font.deriveFont(Font.BOLD, 15f));
		groups.add(G);

		JButton insert = new JButton("Insert");
		insert.setBounds(580, 55, 90, 25);
		groups.add(insert);

		JLabel selecGroup = new JLabel("Group to select:");
		selecGroup.setBounds(80, 95, 185, 25);
		selecGroup.setForeground(Color.white);
		selecGroup.setFont(font.deriveFont(Font.PLAIN, 18f));
		groups.add(selecGroup);

		showListGroups();//check all groups to insert them to JComboBox groupsList
		JComboBox groupList = new JComboBox(groupsList);
		groupList.setSelectedIndex(0);
		groupList.setBounds(260, 95, 300, 25);
		groups.add(groupList);

		JLabel inserWord = new JLabel("Insert word to group:");
		inserWord.setBounds(80, 135, 185, 25);
		inserWord.setForeground(Color.white);
		inserWord.setFont(font.deriveFont(Font.PLAIN, 18f));
		groups.add(inserWord);

		JTextField W = new JTextField(20);
		W.setBounds(260, 135, 300, 25);
		W.setFont(font.deriveFont(Font.BOLD, 15f));
		groups.add(W);

		JButton add = new JButton("Add");
		add.setBounds(580, 95, 90, 65);
		groups.add(add);

		JLabel star = new JLabel("-  -  -");
		star.setBounds(380, 175, 90, 25);
		star.setForeground(Color.white);
		star.setFont(font.deriveFont(Font.PLAIN, 30f));
		groups.add(star);

		JLabel markWord = new JLabel("Mark word to group:");
		markWord.setBounds(80, 215, 185, 25);
		markWord.setForeground(Color.white);
		markWord.setFont(font.deriveFont(Font.PLAIN, 18f));
		groups.add(markWord);

		try {//to allow the user to select a group entry word from all the book words
			ViewWords.sumWords("all");
			Arrays.fill(array, " ");
			int index = 0;
			for (String name : ViewWords.hmap.keySet()) {
				String key = name.toString();
				String str = (key);
				array[index++] = str;
			}
			array2 = new String[index];
			for (int i = 0; i < index; i++) {
				array2[i] = array[i];
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JComboBox words = new JComboBox(array2);
		words.setSelectedIndex(0);
		words.setBounds(260, 215, 300, 25);
		groups.add(words);

		JButton enter = new JButton("Enter");
		enter.setBounds(580, 215, 90, 25);
		groups.add(enter);

		JLabel group2 = new JLabel("Group:");
		group2.setBounds(70, 430, 60, 30);
		group2.setFont(font.deriveFont(Font.PLAIN, 15f));
		group2.setForeground(Color.white);
		JTabbedPaneFrame.panel3.add(group2);

		showListGroups();
		JComboBox G2 = new JComboBox(groupsList);
		G2.setSelectedIndex(0);
		G2.setBounds(115, 435, 155, 20);
		JTabbedPaneFrame.panel3.add(G2);

		JButton enter2 = new JButton("Enter");
		enter2.setBounds(70, 463, 200, 25);
		JTabbedPaneFrame.panel3.add(enter2);

		list1.setForeground(Color.white);
		list1.setBackground(Color.black);
		jsp = new JScrollPane(list1);
		jsp.setBounds(70, 491, 200, 220);
		JTabbedPaneFrame.panel3.add(jsp);
		
		JButton print = new JButton("Print");
		print.setBounds(735, 720, 90, 25);
		JTabbedPaneFrame.panel3.add(print);
		
		Object column[] = { "Word", "Title","Paragraph", "Sentence", "Index In Sentence" };
		Object data[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		mTableModel = new DefaultTableModel(data, column);
		JTable table = new JTable(mTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(285, 435, 540, 275);
		JTabbedPaneFrame.panel3.add(scrollPane);
		mTableModel.removeRow(0);
		
		Object column2[] = { "Word", "Title","Paragraph", "Sentence", "Index In Sentence" };
		Object data2[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		tableExport = new DefaultTableModel(data2, column2);//the table to be printed
		table2 = new JTable(tableExport);
		TableColumnModel columnModel = table2.getColumnModel();
		columnModel.getColumn(1).setPreferredWidth(190);
		columnModel.getColumn(4).setPreferredWidth(100);
		tableExport.removeRow(0);
		
		//Enter a user-defined group name
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String group = G.getText();
				int flag = 0;
				if (group.equals(""))//If no data was entered
					JOptionPane.showMessageDialog(f, "Missing data.", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					try {
						statement = SqlCon.getConnection().createStatement();
						ResultSet rs = statement.executeQuery(SqlCon.SELECT_GROUP);
						while (rs.next()) {
							String groupName = rs.getString("groupName");
							if (groupName.equals(group)) {//If there is a name for such a group in the system
								JOptionPane.showMessageDialog(f, "This group is allready exist.", "Alert",
										JOptionPane.WARNING_MESSAGE);
								flag = 1;
							}
						}
						if (flag != 1) {//Introducing the new group into the system
							insertTable.insertgroupFunc(group);
							groupList.addItem(group);
							G2.addItem(group);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		//Enter a word for the group selected by the user
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showListGroups();
				String groupIns = "";
				int ind = groupList.getSelectedIndex();
				groupIns = groupsList[ind];
				String word = W.getText();
				if (word.equals(""))
					JOptionPane.showMessageDialog(f, "Missing data", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					for (int i = 1; i < Statistics.booksList.length; i++) {
						String title = Statistics.booksList[i];
						try {
							searchWordToGroup(title, word, groupIns);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});


		//Insert the selected word from the list into a textField
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wordIns = "";
				int ind = words.getSelectedIndex();
				wordIns = array2[ind];
				W.setText(wordIns);
			}
		});

		//Displays the words of the group selected by the user
		enter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showListGroups();
				int ind = G2.getSelectedIndex();
				String groupIns = groupsList[ind];
				try {
					listModel.clear();
					statementP = SqlCon.getConnection().prepareStatement(SqlCon.SELECT_GROUPWORD);
					statementP.setString(1, groupIns);
					rs = statementP.executeQuery();
					while (rs.next()) {
						listModel.addElement(rs.getString(1));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				list1.repaint();
				list1.setModel(listModel);
			}
		});
		
	
		//Sends the word table of the group that the user has selected for printing
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showListGroups();
					int ind = G2.getSelectedIndex();
					String groupIns = groupsList[ind];
					statementP = SqlCon.getConnection().prepareStatement(SqlCon.SELECT_WORDS);
					statementP.setString(1, groupIns);
					rs = statementP.executeQuery();
					Object[] rows2;
					while (rs.next()) {
						rows2 = new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5) };
						// add the temp row to the table
						tableExport.addRow(rows2);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				printToPrinter();
			}
		});

		//Option to display a placement index of a particular word selected from a group
		list1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				showListGroups();
				int ind = G2.getSelectedIndex();
				String groupIns = groupsList[ind];
				if (me.getClickCount() == 1) {
					JList target = (JList) me.getSource();
					int index = target.locationToIndex(me.getPoint());
					if (index >= 0) {
						Object item = target.getModel().getElementAt(index);
						String item2 = item.toString();
						createTable(groupIns,item2);
					}
				}
			}
		});

	}
	
	/*
	 * Creates a table of location index of a word selected by the user from a particular group
	 */
	public void createTable(String groupIns, String item2) {
		if (mTableModel.getRowCount() > 0) {  //clear the table
			for (int i = mTableModel.getRowCount() - 1; i > -1; i--) {
				mTableModel.removeRow(i);
			}
		}
		try {
			statementP = SqlCon.getConnection().prepareStatement(SqlCon.SELECT_WORDS_GROUP);
			statementP.setString(1, groupIns);
			statementP.setString(2, item2);
			rs = statementP.executeQuery();
			Object[] rows;
			// for each row returned
			while (rs.next()) {
				// add the values to the temporary row
				rows = new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5) };
				// add the temp row to the table
				mTableModel.addRow(rows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Updates the list of groups from the database
	 */
	public void showListGroups() {
		int i = 0;
		String[] array = new String[100];
		try {
			statement = SqlCon.getConnection().createStatement();
			rs = statement.executeQuery(SqlCon.SELECT_GROUP);
			while (rs.next()) {
				String group = rs.getString("groupName");
				array[i] = group;
				i++;
			}
			if(i==0) {//in our db we allowed the first group within the group table to be expressions 
				String name= "phrases";
				insertTable.insertgroupFunc(name);
				i=1;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		groupsList = new String[i];
		groupsList[0] = "None";
		for (int j = 1; j < i; j++) {
			groupsList[j] = array[j];
		}

	}

	/*
	 * Searches all the locations of a particular word to put the data into the database
	 */
	public void searchWordToGroup(String title, String word, String groupIns) throws IOException {
		String line;
		BufferedReader in;
		Connection conn = null;
		int flag = 0;
		int paragraphs = 1;
		int sentences = 1;
		try {
			System.out.println("func");
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
							String[] sentenceList2 = sentenceList[i].split(" ");
							for (int j = 0; j < sentenceList2.length; j++) {
								String newWord = sentenceList2[j];
								String newWord2 = newWord.replaceAll("[^\\p{IsDigit}\\p{IsAlphabetic}]", "");
								if (newWord2.toLowerCase().equals(word)) {// if the word is found
									int p = paragraphs;
									int s = sentences;
									int is = j + 1;
									insertTable.insertwordFunc(groupIns, word, title, p, s, is);//send to the function that insert to the database 
								}
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

	/*
	 * Prints the data table for a selected group
	 */
	private void printToPrinter()
	{
		    JTable table = table2;
		    JFrame frame = new JFrame();
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JPanel jPanel = new JPanel(new GridLayout(2, 0));
		    jPanel.setOpaque(true);
		    table.setPreferredScrollableViewportSize(new Dimension(550, 150));
		    jPanel.add(new JScrollPane(table));
		    /* Add the panel to the JFrame */
		    frame.add(jPanel);
		    /* Display the JFrame window */
		    frame.pack();
		    frame.setVisible(true);
		    try {
				table.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
	
	
	
		  
		


