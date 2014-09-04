import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GestisciTipologiaPanel extends JPanel {
	
	Connection connection = null;
	private JTextField textFieldTipologiaID;
	private JTextField textFieldTipo;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public GestisciTipologiaPanel() {
		
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
					String tipologiaIdSelected = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Tipologia where TipologiaID='"+tipologiaIdSelected+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						textFieldTipologiaID.setText(rs.getString("TipologiaID").toString());
						textFieldTipo.setText(rs.getString("Tipo"));
					}
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		table.setRowHeight(20);
		refreshTable();
		scrollPane.setViewportView(table);
		
//		Button & TextFields
		JLabel lblTipologiaId = new JLabel("Tipologia ID");
		lblTipologiaId.setBounds(75, 148, 96, 15);
		add(lblTipologiaId);
		
		JLabel lblNewLabel = new JLabel("Gestisci Tipologia Fattura");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel.setBounds(75, 59, 373, 61);
		add(lblNewLabel);
		
		textFieldTipologiaID = new JTextField();
		textFieldTipologiaID.setEditable(false);
		textFieldTipologiaID.setBounds(197, 146, 177, 19);
		add(textFieldTipologiaID);
		textFieldTipologiaID.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(75, 217, 70, 15);
		add(lblTipo);
		
		textFieldTipo = new JTextField();
		textFieldTipo.setBounds(197, 215, 177, 19);
		add(textFieldTipo);
		textFieldTipo.setColumns(10);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "insert into Tipologia (Tipo) values (?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldTipo.getText());
					pst.execute();
					System.out.println("Tipologia inserito correttamente");
					pst.close();
					JOptionPane.showMessageDialog(null, "Nuova tipologia aggiunta correttamente");
					refreshTable();
					clearTextFields();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnAggiungi.setBounds(75, 285, 299, 25);
		add(btnAggiungi);
		
		JButton btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update Tipologia set Tipo='"+textFieldTipo.getText()+"' where TipologiaID='"+textFieldTipologiaID.getText()+"'";
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
		btnModifica.setBounds(75, 343, 299, 25);
		add(btnModifica);
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Stai eliminando "+textFieldTipo.getText()+", continuare?","Warning",dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION){
							String query = "delete from Tipologia where TipologiaID='"+textFieldTipologiaID.getText()+"'";
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
		btnElimina.setBounds(75, 398, 299, 25);
		add(btnElimina);
		
		

	}
	
	/**
	 * Refresh Table
	 */
	private void refreshTable() {
		try {
			String query = "select * from Tipologia";
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
		textFieldTipologiaID.setText("");
		textFieldTipo.setText("");
	}
}
