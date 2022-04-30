package sqlP;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class JTabbedPaneFrame extends JFrame {

	JFrame f;
	JTabbedPane tp;
	public static  JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;
	public static JPanel panel4;
	public static JPanel panel5;
	

	public JTabbedPaneFrame() throws IOException {
		//ImageIcon icon = new ImageIcon("java-swing-tutorial.JPG");

		f = new JFrame();
		tp = new JTabbedPane();
		tp.setBounds(30, 30, 900, 800);
		tp.setBackground(Color.WHITE);
		Border border = new LineBorder(Color.gray, 25, true);
		Border border2 = new LineBorder(Color.gray, 7, true);

		panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBackground(Color.pink);
		panel1.setBorder(border);
		
		
		panel2 = new JPanel();
		panel2.setBackground(Color.pink);
		panel2.setLayout(null);
		panel2.setBorder(border2);

		
		panel3 = new JPanel();
		panel3.setBackground(Color.pink);
		panel3.setLayout(null);
		panel3.setBorder(border);

		
		panel4 = new JPanel();
		panel4.setBackground(Color.pink);
		panel4.setLayout(null);
		panel4.setBorder(border);

		
		panel5 = new JPanel();
		panel5.setBackground(Color.pink);
		panel5.setLayout(null);
		panel5.setBorder(border);

		
		tp.add("Insert", panel1);
		tp.add("Search", panel2);
		tp.add("Groups", panel3);
		tp.add("Phrases", panel4);
		tp.add("Statistics", panel5);
		
		new InsertBook();
		new ViewWords();
		new WordGroups();
		new SearchPhrases();
		new Statistics();
		
		
		f.add(tp);
		f.setSize(1000, 1000);
		f.setLayout(null);
		f.setVisible(true);
		
		JButton export=new JButton("Export");
		JButton importt=new JButton("Import");
		
		export.setBounds(765, 20, 75, 25);
		f.add(export);
		
		importt.setBounds(855, 20, 75, 25);
		f.add(importt);
		
		XmlExportImport exportImport = new XmlExportImport();
		
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportImport.exportVehiclesToXML();
			}
		});
		
		importt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportImport.importVehiclesFromXML("projectXML/books.xml");
			}
		});
	}
}
