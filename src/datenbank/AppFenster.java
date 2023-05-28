package datenbank;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;




public class AppFenster{

	  // Methode zum Hinzufügen eines einzelnen Teilnehmers zur Tabelle basierend auf der ID
    private static void addEinzelnerTeilnehmer(DefaultTableModel model, Connection connection,String anfangsjahr, String endjahr) throws SQLException {
    	String sql;
    	PreparedStatement statement;
    	if (anfangsjahr.equals("") || endjahr.equals("")) {
    		sql = "SELECT * FROM Teilnehmer;";
    		statement = connection.prepareStatement(sql);
    	} else {
    		sql = "SELECT * FROM Teilnehmer WHERE anfangsjahr >= ? AND endjahr <= ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, anfangsjahr);
            statement.setString(2, endjahr);
    	}
        ResultSet tableCursor = statement.executeQuery();
        while (tableCursor.next()) {
            // Füge Daten zur Tabelle hinzu
            Object[] row = new Object[6];
            row[0] = tableCursor.getString(1);
            row[1] = tableCursor.getString(2);
            row[2] = tableCursor.getString(3);
            row[3] = tableCursor.getString(4);
            row[4] = tableCursor.getString(5);
            row[5] = tableCursor.getString(6);

            model.addRow(row);
        }
    }
 // Methode zum Hinzufügen eines einzelnen Dozenten zur Tabelle basierend auf der ID
    private static void addEinzelnerDozent (DefaultTableModel model, Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM Dozent WHERE ID = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet tableCursor = statement.executeQuery();
        while (tableCursor.next()) {
            // Füge Daten zur Tabelle hinzu
            Object[] row = new Object[4];
            row[0] = tableCursor.getString(1);
            row[1] = tableCursor.getString(2);
            row[2] = tableCursor.getString(3);
            row[3] = tableCursor.getString(4);
            model.addRow(row);
        }
    }
   
    // Methode zum Hinzufügen eines einzelnen Mitarbeiters zur Tabelle basierend auf der ID
    private static void addEinzelnerMitarbeiter(DefaultTableModel model, Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM Mitarbeiter WHERE ID = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet tableCursor = statement.executeQuery();
        while (tableCursor.next()) {
            // Füge Daten zur Tabelle hinzu
            Object[] row = new Object[4];
            row[0] = tableCursor.getString(1);
            row[1] = tableCursor.getString(2);
            row[2] = tableCursor.getString(3);
            row[3] = tableCursor.getString(4);
            
            
            model.addRow(row);
        }
    }
    
	public static void main(String[] args) {
		// SQL Schnittstelle
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/Damago", "root", "КщщеКщще250288");
			
			
			// Testdaten erstellen
//			PreparedStatement statement = connection.prepareStatement
//					("INSERT INTO Damago.Teilnehmer VALUES (?,?,?,?,?)");
//			statement.setLong(1,0 );
//			statement.setString(2, "Alba");
//			statement.setString(3, "Martiney");
//			statement.setString(4, "female");
//			statement.setDate(5, Date.valueOf("1990-03-09"));
//			
//			statement.execute();
			
			// Testdaten auslesen
			String sql = "SELECT * FROM Teilnehmer;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet tableCursor = statement.executeQuery();
			
			while (tableCursor.next()) {
				
//				System.out.print(tableCursor.getString(1) + ", "); 
//				System.out.print(tableCursor.getString(2) + ", "); 
//				System.out.print(tableCursor.getString(3) + ", "); 
//				System.out.print(tableCursor.getString(4) + ", "); 
//				System.out.print(tableCursor.getString(5) + ", ");
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		
		// Erstelle das Hauptfenster
		
        JFrame fenster = new JFrame("Datenbankverwaltung");
        fenster.setSize(500, 400);
        fenster.setLocationRelativeTo(null);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setResizable(false);
//        fenster.setLayout(new FlowLayout());
        fenster.getContentPane().setBackground(Color.lightGray);
        
        // Erstelle die Tabelle 
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Füge Spalten zur Tabellenmodell hinzu
        model.addColumn("ID");
        model.addColumn("Vorname");
        model.addColumn("Nachname");
        model.addColumn("Geschlecht");
        model.addColumn("Beginn");
        model.addColumn("Abschluss");
        model.addColumn("Geburtsdatum");
        
        // Füge die Tabelle zum Fenster hinzu
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setPreferredSize(new Dimension(400, 200));

        // Erstelle den Button für Teilnehmer

	    JTextField anfangsjahrEingabe = new JTextField("", 5);
	    JTextField endjahrEingabe = new JTextField("", 5);

	    
        JButton buttonTeilnehmer = new JButton("Teilnehmer:in");
        buttonTeilnehmer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Damago", "root", "КщщеКщще250288");

                    // Hole die Anzahl der Zeilen in der Tabelle
                    int rowCount = model.getRowCount();

                    // loesche alte Eintraege
                    model.setRowCount(0);
                    
                    // Füge den nächsten Teilnehmer basierend auf der Anzahl der Zeilen hinzu
                    addEinzelnerTeilnehmer(model, connection, anfangsjahrEingabe.getText(),endjahrEingabe.getText());

                    // Schließe die Verbindung
                    connection.close();
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        });
        
        
        // Erstelle den Button für Dozenten
        
        JButton buttonDozent = new JButton("Dozent:in");
        buttonDozent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Damago", "root", "КщщеКщще250288");

                    // Hole die Anzahl der Zeilen in der Tabelle
                    int rowCount = model.getRowCount();

                    // Füge den nächsten Dozenten basierend auf der Anzahl der Zeilen hinzu
                    addEinzelnerDozent(model, connection, rowCount + 1);

                    // Schließe die Verbindung
                    connection.close();
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        });

        // Erstelle den Button für Mitarbeiter
        
        JButton buttonMitarbeiter = new JButton("Mitarbeiter:in");
        buttonMitarbeiter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Damago", "root", "КщщеКщще250288");

                    // Hole die Anzahl der Zeilen in der Tabelle
                    int rowCount = model.getRowCount();

                    // Füge den nächsten Mitarbeiter basierend auf der Anzahl der Zeilen hinzu
                    addEinzelnerMitarbeiter(model, connection, rowCount + 1);

                    // Schließe die Verbindung
                    connection.close();
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        });

     // Erstelle die Buttons für anfangsjahr und endjahr
        JButton anfangsjahrButton = new JButton("Anfangsjahr");
        anfangsjahrButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					 Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Damago", "root", "КщщеКщще250288");
					 String sql = "SELECT * FROM Teilnehmer WHERE anfangsjahr >= ? AND endjahr <= ?;";
					PreparedStatement statement = connection.prepareStatement(sql);

			        statement.setString(1, anfangsjahrEingabe.getText());
			        statement.setString(2, endjahrEingabe.getText());
			        ResultSet tableCursor = statement.executeQuery();
			        while (tableCursor.next()) {
			            // Füge Daten zur Tabelle hinzu
			            Object[] row = new Object[6];
			            row[0] = tableCursor.getString(1);
			            row[1] = tableCursor.getString(2);
			            row[2] = tableCursor.getString(3);
			            row[3] = tableCursor.getString(4);
			            row[4] = tableCursor.getString(5);
			            row[5] = tableCursor.getString(6);

			            model.addRow(row);
			        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
//        JButton endjahrButton = new JButton("Endjahr");

        // Füge die Buttons zum Fenster hinzu
        JPanel panel = new JPanel();

        JPanel panelButton = new JPanel(new FlowLayout());
        panelButton.add(buttonTeilnehmer);
        panelButton.add(buttonDozent);
        panelButton.add(buttonMitarbeiter);
        
        JPanel panelJahr = new JPanel(new GridLayout(2, 1));
        JLabel anfangsjahrLabel = new JLabel("Anfangsjahr");
        JLabel endjahrLabel = new JLabel("Endjahr");
        panelJahr.add(anfangsjahrLabel);
        panelJahr.add(anfangsjahrEingabe);
        panelJahr.add(endjahrLabel);
        panelJahr.add(endjahrEingabe);
   
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));
        
        panel.add(panelButton);
        panel.add(panelJahr);
        panel.add(scrollPane);


//	    fenster.add(panel);
//        fenster.pack();
		fenster.setVisible(true);	
	}
}
