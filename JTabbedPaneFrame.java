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
		f = new JFrame();
		tp = new JTabbedPane();
		tp.setBounds(50, 50, 890, 890);
		panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBackground(Color.black);
		
		panel2 = new JPanel();
		panel2.setBackground(Color.black);
		panel2.setLayout(null);
		
		panel3 = new JPanel();
		panel3.setBackground(Color.black);
		panel3.setLayout(null);
		
		panel4 = new JPanel();
		panel4.setBackground(Color.black);
		panel4.setLayout(null);
		
		panel5 = new JPanel();
		panel5.setBackground(Color.black);
		panel5.setLayout(null);
		
		tp.add("Insert Book", panel1);
		tp.add("View Words", panel2);
		tp.add("Word Groups", panel3);
		tp.add("Search Phrases", panel4);
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
		
		export.setBounds(55, 15, 75, 25);
		f.add(export);
		
		importt.setBounds(137, 15, 75, 25);
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
