import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import utility.AlphaPanel;
import utility.AlphaTableRender;


public class GestionePersonale extends JPanel {

	Connection connection = null;
	
	private JTable table;
	private String[] strNameColumns;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private AlphaPanel pnModificaPersonale;
	
	// dati pannello modifica personale
	private JTextField tfModificaID;
	private JTextField tfModificaData;
	private JTextField tfModificaNome;
	private JTextField tfModificaCognome;
	private JTextField tfModificaTelefono;
	
	
	private JScrollPane scrollPane;
	
	
	/**
	 * Create the panel.
	 */
	public GestionePersonale() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
//	###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione del Personale");
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		add(headerPanel, BorderLayout.PAGE_START);
		
		
//		###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		
		
		// ********** pannello Modifica personale
		
		pnModificaPersonale = new AlphaPanel("images/sfondo.jpg", "Modifica Personale", 30, 0, 300, 360);
		
		// button collapse Minus pnDatiFattura 
		ActionListener pnDatiFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaPersonale.setSize(300, 40);
				//pnModificaPersonale.setBounds(30, pnDatiFattura.getHeight() + 20, 300, pnInsertFattura.getHeight());
				pnModificaPersonale.revalidate();
				pnModificaPersonale.repaint();
			}
		};
		pnModificaPersonale.setBtnCollapseMinus(pnDatiFatturaLisMinus);
		
		// button collapse Plus pnDatiFattura
		ActionListener pnDatiFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaPersonale.setSize(300, 360);
				//pnModificaPersonale.setBounds(30, pnDatiFattura.getHeight() + 20, 300, 40);
				pnModificaPersonale.revalidate();
				pnModificaPersonale.repaint();
			}
		};
		pnModificaPersonale.setBtnCollapsePlus(pnDatiFatturaLisPlus);
		
			
		// dati
		JLabel lblNumeroID = new JLabel("ID Personale");
		lblNumeroID.setForeground(new Color(255, 255, 255));
		lblNumeroID.setBounds(12, 70, 90, 15);
		pnModificaPersonale.add(lblNumeroID);
		
		tfModificaID = new JTextField();
		tfModificaID.setEditable(false);
		tfModificaID.setBackground(SystemColor.controlHighlight);
		tfModificaID.setBounds(142, 68, 146, 20);
		pnModificaPersonale.add(tfModificaID);
		tfModificaID.setColumns(10);
		
		JLabel lblModificaNome = new JLabel("Nome");
		lblModificaNome.setForeground(new Color(255, 255, 255));
		lblModificaNome.setBounds(12, 110, 139, 15);
		pnModificaPersonale.add(lblModificaNome);
		
		tfModificaNome = new JTextField();
		tfModificaNome.setBackground(new Color(224, 255, 255));
		tfModificaNome.setBounds(142, 110, 146, 20);
		pnModificaPersonale.add(tfModificaNome);
		tfModificaNome.setColumns(10);
		
		JLabel lblModificaCognome = new JLabel("Cognome");
		lblModificaCognome.setForeground(new Color(255, 255, 255));
		lblModificaCognome.setBounds(12, 150, 139, 15);
		pnModificaPersonale.add(lblModificaCognome);
		
		tfModificaCognome = new JTextField();
		tfModificaCognome.setBackground(new Color(224, 255, 255));
		tfModificaCognome.setBounds(142, 150, 146, 20);
		pnModificaPersonale.add(tfModificaCognome);
		tfModificaCognome.setColumns(10);
		
		JButton btnDataMod = new JButton("Data");
		btnDataMod.setForeground(new Color(255, 255, 255));
		btnDataMod.setBackground(Color.darkGray);               	// MOMENTaneO
		btnDataMod.setBounds(12, 190, 80, 21);
		pnModificaPersonale.add(btnDataMod);
		
		tfModificaData = new ObservingTextField();
		tfModificaData.setEditable(false);
		tfModificaData.setBackground(new Color(224, 255, 255));
		tfModificaData.setBounds(142, 190, 146, 20);
		pnModificaPersonale.add(tfModificaData);
		tfModificaData.setColumns(10);
		
		JLabel lblModificaTelefono = new JLabel("Telefono");
		lblModificaTelefono.setForeground(new Color(255, 255, 255));
		lblModificaTelefono.setBounds(12, 230, 139, 15);
		pnModificaPersonale.add(lblModificaTelefono);
		
		tfModificaTelefono = new JTextField();
		tfModificaTelefono.setBackground(new Color(224, 255, 255));
		tfModificaTelefono.setBounds(142, 230, 146, 20);
		pnModificaPersonale.add(tfModificaTelefono);
		tfModificaTelefono.setColumns(10);
		
		// btn update e svuota campi
		JButton btnModificaPersonale = new JButton("Modifica");
		btnModificaPersonale.setBounds(12, 270, 128, 20);
		Image imgModificaPersonale = new ImageIcon(this.getClass().getResource("/updateDatiFattura.png")).getImage();
		btnModificaPersonale.setIcon(new ImageIcon(imgModificaPersonale));
		pnModificaPersonale.add(btnModificaPersonale);
		
		// btn svuota campi
		JButton btnClearPersonale = new JButton("Clear");
		btnClearPersonale.setBounds(160, 270, 128, 20);
		Image imgClearPersonale = new ImageIcon(this.getClass().getResource("/clearDatiFattura.png")).getImage();
		btnClearPersonale.setIcon(new ImageIcon(imgClearPersonale));
		pnModificaPersonale.add(btnClearPersonale);
		
		// btn svuota campi
		JButton btnEliminaPersonale = new JButton("Elimina");
		btnEliminaPersonale.setBounds(12, 310, 276, 20);
		Image imgEliminaPersonale = new ImageIcon(this.getClass().getResource("/eliminaDatiFattura.png")).getImage();
		btnEliminaPersonale.setIcon(new ImageIcon(imgEliminaPersonale));
		pnModificaPersonale.add(btnEliminaPersonale);
		
		
		leftPanel.add(pnModificaPersonale);
				
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		
		
//		###	CENTER			#############################################################################################################
		
//		TABLE
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		printTable();
	
		scrollPane.setViewportView(table);
		
		
		
//		###	RIGHT			#############################################################################################################
		
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setPreferredSize(new Dimension(20, 400));
		add(rightPanel, BorderLayout.LINE_END);
		
		
	}
	
	
	
	
//	********************************************	METODI DI SUPPORTO	 ******************************************
	
	private void printTable() {
		setColumnsName();
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(strNameColumns);
			table = new JTable();
	        table.setModel(model);
	        
	        String query = getQuery();
	        
	        PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				List<Object> row = new ArrayList<Object>();
				
				row.add(rs.getString("PersonaleID"));
				row.add(rs.getString("Nome"));
				row.add(rs.getString("Cognome"));
				row.add(rs.getString("Telefono"));
				row.add(rs.getString("DataNascita"));
				
				model.addRow(row.toArray());
			}
			
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 50));
			table.getTableHeader().setBackground(new Color(240, 235, 135));
			table.setRowHeight(25);
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}
	
	/*
	 * crea la query in base 
	 * agli elementi selezionati
	 */
	private String getQuery() {
		String query = "select * from Personale";
		return query;
	}
	
	/*
	 * creazione colonne tabella
	 */
	private void setColumnsName() {
		strNameColumns = new String[5];
		strNameColumns[0] = "PersonaleID";
		strNameColumns[1] = "Nome";
		strNameColumns[2] = "Cognome";
		strNameColumns[3] = "Telefono";
		strNameColumns[4] = "DataNascita";
	}
	
	/*
	 * posizione IT per la data
	 */
	private Locale getLocale(String loc) {
		if(loc!=null && loc.length() > 0)
			return new Locale(loc);
		else
			return Locale.ITALY;
	}

}
