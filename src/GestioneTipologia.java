import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import utility.AlphaPanel;
import utility.AlphaTableRender;


public class GestioneTipologia extends JPanel {

	Connection connection = null;
	
	private JTable table;
	private String[] strNameColumns;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private AlphaPanel pnModificaTipologia;
	
	private JTextField tfGestioneTipologiaID;
	private JTextField tfGestioneTipologiaTipo;
	
	private JScrollPane scrollPane;
	private JButton btnModificaTipologia;
	private JButton btnInserisciTipologia;
	private JButton btnEliminaTipologia;
	private JButton btnInserisci;
	private JButton btnModifica;
	private JButton btnClear;
	private JButton btnElimina;
	
	
	public GestioneTipologia() {

//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
//		###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione delle Tipologie");
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		add(headerPanel, BorderLayout.PAGE_START);
		
//		###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		
		// ********** pannello Gestione tipologia
	
		pnModificaTipologia = new AlphaPanel("images/sfondo.jpg", "Gestione Tipologia", 30, 0, 300, 330);
		pnModificaTipologia.setBounds(30, 0, 300, 330);
		
		// button collapse Minus pnDatiFattura 
		ActionListener pnDatiFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaTipologia.setSize(300, 40);
				pnModificaTipologia.revalidate();
				pnModificaTipologia.repaint();
			}
		};
		pnModificaTipologia.setBtnCollapseMinus(pnDatiFatturaLisMinus);
		
		// button collapse Plus pnDatiFattura
		ActionListener pnDatiFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaTipologia.setSize(300, 330);
				pnModificaTipologia.revalidate();
				pnModificaTipologia.repaint();
			}
		};
		pnModificaTipologia.setBtnCollapsePlus(pnDatiFatturaLisPlus);
		
		// dati
		JLabel lblNumeroID = new JLabel("ID Tipologia");
		lblNumeroID.setForeground(new Color(255, 255, 255));
		lblNumeroID.setBounds(12, 70, 90, 15);
		pnModificaTipologia.add(lblNumeroID);
		
		tfGestioneTipologiaID = new JTextField();
		tfGestioneTipologiaID.setEditable(false);
		tfGestioneTipologiaID.setBackground(SystemColor.controlHighlight);
		tfGestioneTipologiaID.setBounds(142, 68, 146, 20);
		tfGestioneTipologiaID.setText(String.valueOf(getNextNumber()));
		pnModificaTipologia.add(tfGestioneTipologiaID);
		tfGestioneTipologiaID.setColumns(10);
		
		JLabel lblModificaTipo = new JLabel("Tipo");
		lblModificaTipo.setForeground(new Color(255, 255, 255));
		lblModificaTipo.setBounds(12, 110, 139, 15);
		pnModificaTipologia.add(lblModificaTipo);
		
		tfGestioneTipologiaTipo = new JTextField();
		tfGestioneTipologiaTipo.setBackground(new Color(224, 255, 255));
		tfGestioneTipologiaTipo.setBounds(142, 110, 146, 20);
		pnModificaTipologia.add(tfGestioneTipologiaTipo);
		tfGestioneTipologiaTipo.setColumns(10);
		
		leftPanel.add(pnModificaTipologia);
		
		btnInserisci = new JButton("Inserisci");
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlloDati()) {
					try {
						String query = "insert into Tipologia (Tipo) "
										+ "values (?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, tfGestioneTipologiaTipo.getText());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Nuova Tipologia inserita correttamente");
						
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
		btnInserisci.setBounds(12, 160, 276, 20);
		Image imgInserisci = new ImageIcon(this.getClass().getResource("/insertNewFattura.png")).getImage();
		btnInserisci.setIcon(new ImageIcon(imgInserisci));
		pnModificaTipologia.add(btnInserisci);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update Tipologia set " + 
									"Tipo='"+tfGestioneTipologiaTipo.getText() +
									"' where TipologiaID='"+tfGestioneTipologiaID.getText()+"'";
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
		btnModifica.setEnabled(false);
		btnModifica.setBounds(12, 200, 276, 20);
		Image imgModifica = new ImageIcon(this.getClass().getResource("/updateDatiFattura.png")).getImage();
		btnModifica.setIcon(new ImageIcon(imgModifica));
		pnModificaTipologia.add(btnModifica);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnModifica.setEnabled(false);
				btnElimina.setEnabled(false);
				btnInserisci.setEnabled(true);
				resetFields();
			}
		});
		btnClear.setBounds(12, 240, 276, 20);
		Image imgClear = new ImageIcon(this.getClass().getResource("/clearDatiFattura.png")).getImage();
		btnClear.setIcon(new ImageIcon(imgClear));
		pnModificaTipologia.add(btnClear);
		
		btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Confermi eliminazione?", "Attenzione!", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	try {
						String query = "delete from Tipologia where TipologiaID='"+tfGestioneTipologiaID.getText()+"'";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "eliminazione avvenuta con successo");
						pst.close();
						printTable();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
		        }
			}
		});
		btnElimina.setEnabled(false);
		btnElimina.setBounds(12, 280, 276, 20);
		Image imgElimina = new ImageIcon(this.getClass().getResource("/eliminaDatiFattura.png")).getImage();
		btnElimina.setIcon(new ImageIcon(imgElimina));
		pnModificaTipologia.add(btnElimina);
		
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
				
				row.add(rs.getString("TipologiaID"));
				row.add(rs.getString("Tipo"));
				
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
	 * creazione colonne tabella
	 */
	private void setColumnsName() {
		strNameColumns = new String[2];
		strNameColumns[0] = "TipologiaID";
		strNameColumns[1] = "Tipo";
	}
	
	/*
	 * crea la query
	 */
	private String getQuery() {
		String query = "select * from Tipologia";
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
					String tipologiaID = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Tipologia where TipologiaID='"+tipologiaID+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						tfGestioneTipologiaID.setText(rs.getString("TipologiaID").toString());
						tfGestioneTipologiaTipo.setText(rs.getString("Tipo").toString());
					}
					pst.close();
					btnInserisci.setEnabled(false);
					btnModifica.setEnabled(true);
					btnElimina.setEnabled(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
			}
		});
	}
	
	/*
	 * search in DB next number Personale
	 */
	private int getNextNumber() {
		int tipologiaID=0;
		try {
			String query = "select TipologiaID from Tipologia";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				tipologiaID++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} return tipologiaID+1;
	}
	
	/*
	 * reset campi inserimento Fattura
	 */
	private void resetFields() {
		tfGestioneTipologiaID.setText(String.valueOf(getNextNumber()));
		tfGestioneTipologiaTipo.setText("");
	}
	
	/*
	 * controllo dati per inserimento
	 * Fattura in DB
	 */
	private boolean controlloDati() {
		if(tfGestioneTipologiaTipo.getText().equals("") )
			return false;
		return true;
	}
}
