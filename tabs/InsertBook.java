package sqlP;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class InsertBook {

	PreparedStatement statementP;
	Statement statement;
	ResultSet rs;
	static ArrayList<String> bookAccordingWord = new ArrayList<String>();
	DefaultTableModel mTableModel;
	JButton insertButton;
	Border border = new LineBorder(Color.white, 1, true);
	JFrame f;

	public InsertBook() {
		buttonsInsert();
	}

	public void buttonsInsert() {
		JLabel j1 = new JLabel("Insert New Book");
		j1.setBounds(340, 10, 200, 80);
		Font font = j1.getFont();
		j1.setForeground(Color.white);
		j1.setFont(font.deriveFont(Font.PLAIN, 24f));
		JTabbedPaneFrame.panel1.add(j1);

		JPanel insert = new JPanel();
		insert.setBounds(195, 100, 500, 230);
		insert.setBorder(border);
		insert.setBackground(Color.black);
		JTabbedPaneFrame.panel1.add(insert);
		insert.setLayout(null);

		JLabel path = new JLabel("Path:");
		JLabel title = new JLabel("Title:");
		JLabel author = new JLabel("Author:");
		JLabel category = new JLabel("Category:");
		JLabel date = new JLabel("Release Date:");
		JLabel fileSize = new JLabel("File Size In KB:");

		path.setBounds(10, 10, 50, 30);
		path.setFont(font.deriveFont(Font.BOLD, 15f));
		path.setForeground(Color.white);
		title.setBounds(10, 40, 50, 30);
		title.setFont(font.deriveFont(Font.BOLD, 15f));
		title.setForeground(Color.white);
		author.setBounds(10, 70, 70, 30);
		author.setFont(font.deriveFont(Font.BOLD, 15f));
		author.setForeground(Color.white);
		category.setBounds(10, 100, 70, 30);
		category.setFont(font.deriveFont(Font.BOLD, 15f));
		category.setForeground(Color.white);
		date.setBounds(10, 130, 200, 30);
		date.setFont(font.deriveFont(Font.BOLD, 15f));
		date.setForeground(Color.white);
		fileSize.setBounds(10, 160, 130, 30);
		fileSize.setFont(font.deriveFont(Font.BOLD, 15f));
		fileSize.setForeground(Color.white);

		JTextField P = new JTextField(150);
		JTextField T = new JTextField(100);
		JTextField A = new JTextField(100);
		JTextField C = new JTextField(100);
		JTextField D = new JTextField(100);
		JTextField F = new JTextField(100);

		D.setForeground(Color.gray);
		D.setText("yyyy-mm-dd");

		P.setBounds(140, 20, 250, 20);
		P.setFont(font.deriveFont(Font.BOLD, 15f));

		T.setBounds(140, 50, 250, 20);
		T.setFont(font.deriveFont(Font.BOLD, 15f));
		
		A.setBounds(140, 80, 250, 20);
		A.setFont(font.deriveFont(Font.BOLD, 15f));
		
		C.setBounds(140, 110, 250, 20);
		C.setFont(font.deriveFont(Font.BOLD, 15f));
		
		D.setBounds(140, 140, 250, 20);
		D.setFont(font.deriveFont(Font.BOLD, 15f));

		F.setBounds(140, 170, 250, 20);
		F.setFont(font.deriveFont(Font.BOLD, 15f));

		insert.add(P);
		insert.add(T);
		insert.add(A);
		insert.add(C);
		insert.add(F);
		insert.add(D);
		
		insertButton = new JButton("Insert");
		insertButton.setBounds(345, 200, 70, 25);

		JButton brouse = new JButton("Browse");
		brouse.setBounds(400, 18, 80, 25);

		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(420, 200, 70, 25);

		insert.add(insertButton);
		insert.add(clearButton);
		insert.add(brouse);

		insert.add(path);
		insert.add(title);
		insert.add(author);
		insert.add(category);
		insert.add(date);
		insert.add(fileSize);

		JLabel j2 = new JLabel("Inserted Books");
		j2.setBounds(350, 350, 200, 80);
		j2.setForeground(Color.white);
		j2.setFont(font.deriveFont(Font.PLAIN, 24f));
		JTabbedPaneFrame.panel1.add(j2);

		JPanel inserted = new JPanel();
		inserted.setBounds(30, 420, 825, 320);
		inserted.setBorder(border);
		inserted.setBackground(Color.black);
		JTabbedPaneFrame.panel1.add(inserted);
		inserted.setLayout(null);

		JLabel title2 = new JLabel("Title:");
		title2.setBounds(40, 30, 50, 30);
		title2.setFont(font.deriveFont(Font.BOLD, 15f));
		title2.setForeground(Color.white);
		inserted.add(title2);

		JTextField T2 = new JTextField(50);
		T2.setBounds(85, 40, 170, 20);
		T2.setFont(font.deriveFont(Font.BOLD, 15f));
		inserted.add(T2);

		JLabel author2 = new JLabel("Author:");
		author2.setBounds(265, 30, 70, 30);
		author2.setFont(font.deriveFont(Font.BOLD, 15f));
		author2.setForeground(Color.white);
		inserted.add(author2);

		JTextField A2 = new JTextField(50);
		A2.setBounds(325, 40, 170, 20);
		A2.setFont(font.deriveFont(Font.BOLD, 15f));
		inserted.add(A2);

		JLabel word = new JLabel("Word Appearance:");
		word.setBounds(505, 30, 130, 30);
		word.setFont(font.deriveFont(Font.BOLD, 15f));
		word.setForeground(Color.white);
		inserted.add(word);

		JTextField W = new JTextField(20);
		W.setBounds(640, 40, 150, 20);
		W.setFont(font.deriveFont(Font.BOLD, 15f));
		inserted.add(W);

		JButton search = new JButton("search");
		search.setBounds(730, 70, 75, 20);
		inserted.add(search);

		Object column[] = { "Title", "Author", "Category", "Date", "Size" };
		Object data[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" } };

		mTableModel = new DefaultTableModel(data, column);
		JTable table = new JTable(mTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(15, 100, 790, 170);
		inserted.add(scrollPane);
		mTableModel.removeRow(0);

		JButton open = new JButton("Open");
		open.setBounds(730, 280, 75, 20);
		inserted.add(open);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		
		brouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				P.setText(filename);
			}
		});


		//Inserting a book into the database according to the user's data
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titleIns = T.getText();
				String pathIns = P.getText();
				String authorIns = A.getText();
				String releseDateIns = D.getText();
				String categoryIns = C.getText();
				String fileSizeIns = F.getText();
				if (titleIns.equals("") || pathIns.equals("") || authorIns.equals("") || releseDateIns.equals("")
						|| categoryIns.equals("") || fileSizeIns.equals("")) {
					JOptionPane.showMessageDialog(f, "Missing data.", "Alert", JOptionPane.WARNING_MESSAGE);
				} else {
					int filesizeIns;
					filesizeIns = Integer.parseInt(fileSizeIns);

					int flag = 0;
					try {
						statement = SqlCon.getConnection().createStatement();
						ResultSet rs = statement.executeQuery(SqlCon.SELECT_TITLE);
						while (rs.next()) {
							String title = rs.getString("title");
							if (title.equals(titleIns)) {
								f = new JFrame();
								JOptionPane.showMessageDialog(f, "This book is allready exist.", "Alert",
										JOptionPane.WARNING_MESSAGE);
								flag = 1;
							}
						}
						if (flag != 1) {
							insertTable.insertTableFunc(titleIns, authorIns, categoryIns, releseDateIns, filesizeIns,
									pathIns);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		//clear the textFields
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				P.setText("");
				T.setText("");
				A.setText("");
				C.setText("");
				F.setText("");
				D.setText("");
			}
		});

		//search the element the user entered to be search
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String titleUser = T2.getText();
				String authorUser = A2.getText();
				String wordUser = W.getText();

				String chouseRes = null;
				String chouseUse = null;

				if (!(titleUser.equals(""))) {
					chouseRes = SqlCon.TITLE_RESULT;
					chouseUse = titleUser;
					func(chouseRes, chouseUse);

				}
				if (!(authorUser.equals(""))) {
					chouseRes = SqlCon.AUTHOR_RESULT;
					chouseUse = authorUser;
					func(chouseRes, chouseUse);
				}
				if (!(wordUser.equals(""))) {

					try {
						ViewWords.searchWord(wordUser, 0);// the mark is zero to know that insertBook send to the function
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					for (int i = 0; i < bookAccordingWord.size(); i++) {
						chouseRes = SqlCon.WORD_RESULT;
						chouseUse = bookAccordingWord.get(i);
						func(chouseRes, chouseUse);
					}
				}
			}
		});
		

		//Opens a book to read based on the data entered by the user
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, 0).toString();
				String path = "C:\\Users\\θμιδ\\project\\" + value + ".txt";
				Desktop desktop = Desktop.getDesktop();
				File file = new File(path);
				try {
					desktop.open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	/*
	 * fill the table with the founde
	 */
	public void func(String chouseRes, String chouseUse) {
		try {
			statementP = SqlCon.getConnection().prepareStatement(chouseRes);
			statementP.setString(1, chouseUse);
			rs = statementP.executeQuery();
			Object[] rows;
			// for each row returned
			while (rs.next()) {
				// add the values to the temporary row
				rows = new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5) };
				// add the temp row to the table
				mTableModel.addRow(rows);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	
}
