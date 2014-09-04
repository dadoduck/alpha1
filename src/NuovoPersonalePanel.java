import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NuovoPersonalePanel extends JPanel {
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldTelefono;
	private ObservingTextField textFieldDataNascita;
	Connection connection = null;
	
	/**
	 * Create the panel.
	 */
	public NuovoPersonalePanel() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1000, 600);
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(165, 146, 59, 27);
		add(lblNewLabel_1);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(165, 212, 83, 27);
		add(lblCognome);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(165, 267, 83, 27);
		add(lblTelefono);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(406, 150, 224, 19);
		add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setColumns(10);
		textFieldCognome.setBounds(406, 216, 224, 19);
		add(textFieldCognome);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(406, 271, 224, 19);
		add(textFieldTelefono);
		
		textFieldDataNascita = new ObservingTextField();
		textFieldDataNascita.setColumns(10);
		textFieldDataNascita.setBounds(406, 340, 224, 19);
		add(textFieldDataNascita);
		
		JButton btnDataNascita = new JButton("Data di Nascita");
		btnDataNascita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lang = null;
				final Locale locale = getLocale(lang);
				DatePicker dp = new DatePicker(textFieldDataNascita, locale);
				Date selectedDate = dp.parseDate(textFieldDataNascita.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(textFieldDataNascita);
			}
		});
		btnDataNascita.setBounds(165, 340, 147, 19);
		add(btnDataNascita);
		
		JButton btnAggiungiPersonale = new JButton("Aggiungi Personale");
		btnAggiungiPersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "insert into Personale (Nome, Cognome, Telefono, DataNascita) values (?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldNome.getText());
					pst.setString(2, textFieldCognome.getText());
					pst.setString(3, textFieldTelefono.getText());
					pst.setString(4, textFieldDataNascita.getText());
					pst.execute();
					JOptionPane.showMessageDialog(null, "Utente inserito correttamente");
					clearTextFields();
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnAggiungiPersonale.setBounds(232, 433, 353, 36);
		add(btnAggiungiPersonale);
		
		JLabel lblAggiungiPersonale = new JLabel("Aggiungi Personale");
		lblAggiungiPersonale.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAggiungiPersonale.setBounds(165, 59, 373, 61);
		add(lblAggiungiPersonale);
		
		
		
		

	}
	
	private Locale getLocale(String loc) {
		if(loc!=null && loc.length() > 0)
			return new Locale(loc);
		else
			return Locale.ITALY;
	}
	
	private void clearTextFields() {
		textFieldNome.setText("");
		textFieldCognome.setText("");
		textFieldTelefono.setText("");
		textFieldDataNascita.setText("");
	}
}
