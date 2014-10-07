import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	
	private Image imgContainer;
	
	private JTable table;
	private String[] strNameColumns;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private AlphaPanel pnModificaPersonale;
	
	// dati pannello modifica personale
	private JTextField tfGestionePersonaleID;
	private ObservingTextField tfGestionePersonaleData;
	private JTextField tfGestionePersonaleNome;
	private JTextField tfGestionePersonaleCognome;
	private JTextField tfGestionePersonaleTelefono;
	
	
	private JScrollPane scrollPane;
	private JButton btnModificaPersonale;
	private JButton btnInserisciPersona;
	private JButton btnEliminaPersonale;
	
	
	/**
	 * Create the panel.
	 */
	public GestionePersonale() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
		this.imgContainer = new ImageIcon("images/sfondoChiaro.jpg").getImage();
		
//	###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione del Personale");
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		headerPanel.setOpaque(false);
		add(headerPanel, BorderLayout.PAGE_START);
		
		
//		###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setOpaque(false);
		
		
		// ********** pannello Gestione personale
		
		pnModificaPersonale = new AlphaPanel("images/sfondo.jpg", "Gestione Personale", 30, 0, 300, 390);
		pnModificaPersonale.setBounds(30, 0, 300, 390);
		
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
				pnModificaPersonale.setSize(300, 390);
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
		
		tfGestionePersonaleID = new JTextField();
		tfGestionePersonaleID.setEditable(false);
		tfGestionePersonaleID.setBackground(SystemColor.controlHighlight);
		tfGestionePersonaleID.setBounds(142, 68, 146, 20);
		tfGestionePersonaleID.setText(String.valueOf(getNextNumber()));
		pnModificaPersonale.add(tfGestionePersonaleID);
		tfGestionePersonaleID.setColumns(10);
		
		JLabel lblModificaNome = new JLabel("Nome");
		lblModificaNome.setForeground(new Color(255, 255, 255));
		lblModificaNome.setBounds(12, 110, 139, 15);
		pnModificaPersonale.add(lblModificaNome);
		
		tfGestionePersonaleNome = new JTextField();
		tfGestionePersonaleNome.setBackground(new Color(224, 255, 255));
		tfGestionePersonaleNome.setBounds(142, 110, 146, 20);
		pnModificaPersonale.add(tfGestionePersonaleNome);
		tfGestionePersonaleNome.setColumns(10);
		
		JLabel lblModificaCognome = new JLabel("Cognome");
		lblModificaCognome.setForeground(new Color(255, 255, 255));
		lblModificaCognome.setBounds(12, 150, 139, 15);
		pnModificaPersonale.add(lblModificaCognome);
		
		tfGestionePersonaleCognome = new JTextField();
		tfGestionePersonaleCognome.setBackground(new Color(224, 255, 255));
		tfGestionePersonaleCognome.setBounds(142, 150, 146, 20);
		pnModificaPersonale.add(tfGestionePersonaleCognome);
		tfGestionePersonaleCognome.setColumns(10);
		
		JButton btnDataMod = new JButton("Data");
		btnDataMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lang = null;
				final Locale locale = getLocale(lang);
				DatePicker dp = new DatePicker(tfGestionePersonaleData, locale);
				Date selectedDate = dp.parseDate(tfGestionePersonaleData.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(tfGestionePersonaleData);
			}
		});
		btnDataMod.setForeground(new Color(255, 255, 255));
		btnDataMod.setBackground(Color.darkGray);               	// MOMENTaneO
		btnDataMod.setBounds(12, 190, 80, 21);
		pnModificaPersonale.add(btnDataMod);
		
		tfGestionePersonaleData = new ObservingTextField();
		tfGestionePersonaleData.setBackground(new Color(224, 255, 255));
		tfGestionePersonaleData.setBounds(142, 190, 146, 20);
		pnModificaPersonale.add(tfGestionePersonaleData);
		tfGestionePersonaleData.setColumns(10);
		
		JLabel lblModificaTelefono = new JLabel("Telefono");
		lblModificaTelefono.setForeground(new Color(255, 255, 255));
		lblModificaTelefono.setBounds(12, 230, 139, 15);
		pnModificaPersonale.add(lblModificaTelefono);
		
		tfGestionePersonaleTelefono = new JTextField();
		tfGestionePersonaleTelefono.setBackground(new Color(224, 255, 255));
		tfGestionePersonaleTelefono.setBounds(142, 230, 146, 20);
		pnModificaPersonale.add(tfGestionePersonaleTelefono);
		tfGestionePersonaleTelefono.setColumns(10);
		
		// btn update e svuota campi
		btnModificaPersonale = new JButton("Modifica");
		btnModificaPersonale.setEnabled(false);
		btnModificaPersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "Update Personale set " + 
									"Nome='"+tfGestionePersonaleNome.getText()+"', Cognome='"+tfGestionePersonaleCognome.getText() + 
									"', Telefono='"+tfGestionePersonaleTelefono.getText() + 
									"', DataNascita='"+tfGestionePersonaleData.getText() +
									"' where PersonaleID='"+tfGestionePersonaleID.getText()+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Aggiornamento riuscito!");
					pst.close();
					printTable();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnModificaPersonale.setBounds(12, 270, 128, 20);
		Image imgModificaPersonale = new ImageIcon(this.getClass().getResource("/updateDatiFattura.png")).getImage();
		btnModificaPersonale.setIcon(new ImageIcon(imgModificaPersonale));
		pnModificaPersonale.add(btnModificaPersonale);
		
		// btn svuota campi
		JButton btnClearPersonale = new JButton("Clear");
		btnClearPersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnModificaPersonale.setEnabled(false);
				btnEliminaPersonale.setEnabled(false);
				btnInserisciPersona.setEnabled(true);
				resetFields();
			}
		});
		btnClearPersonale.setBounds(160, 270, 128, 20);
		Image imgClearPersonale = new ImageIcon(this.getClass().getResource("/clearDatiFattura.png")).getImage();
		btnClearPersonale.setIcon(new ImageIcon(imgClearPersonale));
		pnModificaPersonale.add(btnClearPersonale);
		
		// btn inserisci		
		btnInserisciPersona = new JButton("Inserisci");
		btnInserisciPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlloDati()) {
					try {
						String query = "insert into Personale (Nome, Cognome, Telefono, DataNascita) "
										+ "values (?, ?, ?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, tfGestionePersonaleNome.getText());
						pst.setString(2, tfGestionePersonaleCognome.getText());
						pst.setString(3, tfGestionePersonaleTelefono.getText());
						pst.setString(4, tfGestionePersonaleData.getText());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Nuovo Personale inserito correttamente");
						
						printTable();
						resetFields();

						pst.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
				else
					JOptionPane.showMessageDialog(null, "i dati sono tutti obbligatori");
			}
		});
		Image imgInserisciPersonale = new ImageIcon(this.getClass().getResource("/insertNewFattura.png")).getImage();
		btnInserisciPersona.setIcon(new ImageIcon(imgInserisciPersonale));
		btnInserisciPersona.setBounds(12, 310, 276, 20);
		pnModificaPersonale.add(btnInserisciPersona);
		
		// btn elimina
		btnEliminaPersonale = new JButton("Elimina");
		btnEliminaPersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Confermi eliminazione?", "Attenzione!", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	try {
						String query = "delete from Personale where PersonaleID='"+tfGestionePersonaleID.getText()+"'";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "eliminazione avvenuta con successo");
						pst.close();
						printTable();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			}
		});
		btnEliminaPersonale.setEnabled(false);
		btnEliminaPersonale.setBounds(12, 350, 276, 20);
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
		rightPanel.setOpaque(false);
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
			
			table.setDefaultRenderer(Object.class, new AlphaTableRender());
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 50));
			table.getTableHeader().setBackground(new Color(240, 235, 135));
			table.setRowHeight(25);
			
			pst.close();
			rs.close();
			
			tableClickRow();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}
	
	/*
	 * crea la query 
	 */
	private String getQuery() {
		String query = "select * from Personale";
		return query;
	}
	
	/*
	 * select row in table
	 */
	public void tableClickRow() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String personaleID = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Personale where PersonaleID='"+personaleID+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						tfGestionePersonaleID.setText(rs.getString("PersonaleID").toString());
						tfGestionePersonaleNome.setText(rs.getString("Nome").toString());
						tfGestionePersonaleCognome.setText(rs.getString("Cognome").toString());
						tfGestionePersonaleTelefono.setText(rs.getString("Telefono").toString());
						tfGestionePersonaleData.setText(rs.getString("DataNascita").toString());
					}
					pst.close();
					btnInserisciPersona.setEnabled(false);
					btnModificaPersonale.setEnabled(true);
					btnEliminaPersonale.setEnabled(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
			}
		});
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
	 * search in DB next number Personale
	 */
	private int getNextNumber() {
		int personaleID=0;
		try {
			String query = "select PersonaleID from Personale";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				personaleID++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} return personaleID+1;
	}
	
	/*
	 * reset campi inserimento Fattura
	 */
	private void resetFields() {
		tfGestionePersonaleID.setText(String.valueOf(getNextNumber()));
		tfGestionePersonaleNome.setText("");
		tfGestionePersonaleCognome.setText("");
		tfGestionePersonaleTelefono.setText("");
		tfGestionePersonaleData.setText("");
	}
	
	/*
	 * controllo dati per inserimento
	 * Fattura in DB
	 */
	private boolean controlloDati() {
		if(tfGestionePersonaleNome.getText().equals("") || tfGestionePersonaleCognome.getText().equals("") || 
				tfGestionePersonaleTelefono.getText().equals("") || tfGestionePersonaleData.getText().equals("") )
			return false;
		return true;
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
	
	public void paintComponent(Graphics g) {
		g.drawImage(imgContainer, 0, 0, null);
	}
}
