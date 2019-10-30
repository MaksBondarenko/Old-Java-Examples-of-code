package zad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame{
	GUI(List<List<String>> data){
		DefaultTableModel tablemodel= new DefaultTableModel();
		
		tablemodel.addColumn("Nazwa");
		tablemodel.addColumn("Autor");
		tablemodel.addColumn("Rodzaj");
		tablemodel.addColumn("Rok napisania");
		tablemodel.addColumn("Rok wydania");
		tablemodel.addColumn("Wydawnictwo");
		
		DefaultTableModel tablemodel2= new DefaultTableModel();
		
		tablemodel2.addColumn("Nazwa");
		tablemodel2.addColumn("Autor");
		tablemodel2.addColumn("Rodzaj");
		tablemodel2.addColumn("Rok napisania");
		tablemodel2.addColumn("Rok wydania");
		tablemodel2.addColumn("Wydawnictwo");
		
		for(int i=0; i<data.size();i++) {
            String[] row= new String[data.get(i).size()];
            for(int x=0; x<data.get(i).size(); x++) {
                row[x]=data.get(i).get(x);
            }
            tablemodel.addRow(row);
        }

		JTable mainTable =new JTable(tablemodel);
		mainTable.setBackground(new Color(202, 230, 213));
		mainTable.getTableHeader().setBackground(new Color(251, 205, 190));
		mainTable.getTableHeader().setFont(new Font("TimesRoman", Font.PLAIN, 20));
		mainTable.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		mainTable.setRowHeight(30);
        mainTable.setDefaultEditor(Object.class, null);
        
        JScrollPane sp= new JScrollPane(mainTable);
        sp.setPreferredSize(new Dimension (1500,230));
        
        
        JPanel searchPanel= new JPanel(new GridLayout(2,2));
        
        JLabel searchLabel = new JLabel("Wpisz nazwę książki (lub część nazwy): ");
        searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        searchLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        JTextField searchField= new JTextField(30);
        searchField.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        
        JButton searchButton= new JButton("Szukaj");
        searchButton.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        searchButton.addActionListener(e -> {
        	int rows = tablemodel2.getRowCount();
        	for(int i = 0;i<rows; i++) {
        		tablemodel2.removeRow(0);
        	}
        	String partof = searchField.getText();
        	partof=partof.toLowerCase();
        	for(int i = 0;i<tablemodel.getRowCount(); i++) {
        		String nazwa = (String)tablemodel.getValueAt(i, 0);
        		nazwa=nazwa.toLowerCase();
        		if(nazwa.contains(partof)) {
        			 String[] row= new String[tablemodel.getColumnCount()];
        	            for(int x=0; x<tablemodel.getColumnCount(); x++) {
        	                row[x]=(String)tablemodel.getValueAt(i, x);
        	            }
        	         tablemodel2.addRow(row);
        		}
        	}
        	mainTable.setModel(tablemodel2);
        });
        
        JButton returnButton= new JButton("Pokaż wszystkie");
        returnButton.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        returnButton.addActionListener(e -> {
        	mainTable.setModel(tablemodel);
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(returnButton);
        searchPanel.add(searchButton);
		JPanel mainPanel= new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(sp, BorderLayout.PAGE_START);
		mainPanel.add(searchPanel,BorderLayout.AFTER_LAST_LINE);
		this.add(mainPanel);
        this.setTitle("Library");
        this.setPreferredSize(new Dimension(1500, 400));
        this.setLocation(250, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

	}
}
