import javax.swing.JPanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.proteanit.sql.DbUtils;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VisualizzaPersonalePanel extends JPanel {
	
	Connection connection = null;
	private JTable table;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldTelefono;
	private JTextField textFieldDataNascita;
	private JTextField textFieldPersonaleID;

	/**
	 * Create the panel.
	 */
	public VisualizzaPersonalePanel() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1300, 600);
		setLayout(null);
		
//		TABLE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(499, 84, 789, 339);
		add(scrollPane);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String userIdSelected = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Personale where PersonaleID='"+userIdSelected+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						textFieldPersonaleID.setText(rs.getString("PersonaleID").toString());
						textFieldNome.setText(rs.getString("Nome"));
						textFieldCognome.setText(rs.getString("Cognome"));
						textFieldTelefono.setText(rs.getString("Telefono"));
						textFieldDataNascita.setText(rs.getString("DataNascita"));
					}
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		table.setRowHeight(20);
		
//		refresh tabella
		refreshTable();
		
		scrollPane.setViewportView(table);
		
		
//		Button & TextFields
		
		JLabel lblTitoloGestionePersonale = new JLabel("Gestisci Personale");
		lblTitoloGestionePersonale.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTitoloGestionePersonale.setBounds(75, 59, 373, 61);
		add(lblTitoloGestionePersonale);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(75, 186, 70, 15);
		add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(75, 238, 70, 15);
		add(lblCognome);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(75, 286, 70, 15);
		add(lblTelefono);
		
		JLabel lblDataDiNascita = new JLabel("Data di Nascita");
		lblDataDiNascita.setBounds(75, 331, 118, 15);
		add(lblDataDiNascita);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(247, 184, 201, 19);
		add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(247, 236, 201, 19);
		add(textFieldCognome);
		textFieldCognome.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(247, 284, 201, 19);
		add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		textFieldDataNascita = new JTextField();
		textFieldDataNascita.setBounds(247, 329, 201, 19);
		add(textFieldDataNascita);
		textFieldDataNascita.setColumns(10);
		
		JButton btnAggiorna = new JButton("Aggiorna");
		btnAggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update Personale set Nome='"+textFieldNome.getText()+"', Cognome='"+textFieldCognome.getText()+"', Telefono='"+textFieldTelefono.getText()+
								   "', DataNascita='"+textFieldDataNascita.getText()+"' where PersonaleID='"+textFieldPersonaleID.getText()+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Aggiornamento riuscito");
					pst.close();
					refreshTable();
					clearTextFields();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnAggiorna.setBounds(75, 391, 176, 29);
		add(btnAggiorna);
		
		JLabel lblPersonaleID = new JLabel("Personale ID");
		lblPersonaleID.setBounds(75, 132, 90, 15);
		add(lblPersonaleID);
		
		textFieldPersonaleID = new JTextField();
		textFieldPersonaleID.setEditable(false);
		textFieldPersonaleID.setBounds(247, 132, 201, 19);
		add(textFieldPersonaleID);
		textFieldPersonaleID.setColumns(10);
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Stai eliminando "+textFieldCognome.getText()+", continuare?","Warning",dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION){
							String query = "delete from Personale where PersonaleID='"+textFieldPersonaleID.getText()+"'";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.execute();
							JOptionPane.showMessageDialog(null, "eliminazione avvenuta con successo");
							pst.close();
							refreshTable();
							clearTextFields();
						} else {
							JOptionPane.showMessageDialog(null, "operazione annullata");
						}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnElimina.setBounds(275, 391, 176, 29);
		add(btnElimina);
		
	}
	
	/**
	 * Refresh Table
	 */
	private void refreshTable() {
		try {
			String query = "select * from Personale";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));			
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void clearTextFields() {
		textFieldPersonaleID.setText("");
		textFieldNome.setText("");
		textFieldCognome.setText("");
		textFieldTelefono.setText("");
		textFieldDataNascita.setText("");
	}
}
