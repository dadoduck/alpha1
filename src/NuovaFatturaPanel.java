import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NuovaFatturaPanel extends JPanel {

	Connection connection = null;
	private JTextField textFieldNumeroFattura;
	private ObservingTextField textFieldDataFattura;
	private JTextField textFieldIndirizzo;
	private JTextField textFieldImporto;
	private JTextField textFieldProvvigioniAcquisitore;
	private JTextField textFieldProvvigioniVenditore;
	private JComboBox<String> comboBoxTipologia;
	private JComboBox<String> comboBoxAcquisitore;
	private JComboBox<String> comboBoxVenditore;
	
	/**
	 * Create the panel.
	 */
	public NuovaFatturaPanel() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1000, 600);
		setLayout(null);
		
		JLabel lblAggiungiPersonale = new JLabel("Inserisci nuova fattura");
		lblAggiungiPersonale.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAggiungiPersonale.setBounds(165, 59, 373, 61);
		add(lblAggiungiPersonale);
		
		JLabel lblNumeroFattura = new JLabel("Numero");
		lblNumeroFattura.setBounds(573, 84, 66, 15);
		add(lblNumeroFattura);
		
		textFieldNumeroFattura = new JTextField();
		textFieldNumeroFattura.setEditable(false);
		textFieldNumeroFattura.setText(String.valueOf(getNextNumber()));
		textFieldNumeroFattura.setBounds(713, 82, 126, 19);
		add(textFieldNumeroFattura);
		textFieldNumeroFattura.setColumns(10);
		
		textFieldDataFattura = new ObservingTextField();
		textFieldDataFattura.setBounds(303, 153, 126, 19);
		add(textFieldDataFattura);
		textFieldDataFattura.setColumns(10);
		
		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setBounds(573, 155, 70, 15);
		add(lblIndirizzo);
		
		textFieldIndirizzo = new JTextField();
		textFieldIndirizzo.setBounds(713, 153, 126, 19);
		add(textFieldIndirizzo);
		textFieldIndirizzo.setColumns(10);
		
		JLabel lblImporto = new JLabel("Importo");
		lblImporto.setBounds(573, 210, 70, 15);
		add(lblImporto);
		
		textFieldImporto = new JTextField();
		textFieldImporto.setBounds(713, 208, 126, 19);
		add(textFieldImporto);
		textFieldImporto.setColumns(10);
		
		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setBounds(165, 210, 70, 15);
		add(lblTipologia);
		
		JLabel lblAcquisitore = new JLabel("Acquisitore");
		lblAcquisitore.setBounds(169, 289, 90, 15);
		add(lblAcquisitore);
		
		JLabel lblProvvigioniAcquisitore = new JLabel("Provvigioni Acquisitore");
		lblProvvigioniAcquisitore.setBounds(489, 289, 176, 15);
		add(lblProvvigioniAcquisitore);
		
		textFieldProvvigioniAcquisitore = new JTextField();
		textFieldProvvigioniAcquisitore.setBounds(713, 287, 126, 19);
		add(textFieldProvvigioniAcquisitore);
		textFieldProvvigioniAcquisitore.setColumns(10);
		
		JLabel lblVenditore = new JLabel("Venditore");
		lblVenditore.setBounds(165, 369, 70, 15);
		add(lblVenditore);
		
		JLabel lblProvvigioniVenditore = new JLabel("Provvigioni Venditore");
		lblProvvigioniVenditore.setBounds(489, 367, 151, 15);
		add(lblProvvigioniVenditore);
		
		textFieldProvvigioniVenditore = new JTextField();
		textFieldProvvigioniVenditore.setBounds(713, 367, 126, 19);
		add(textFieldProvvigioniVenditore);
		textFieldProvvigioniVenditore.setColumns(10);
		
		JButton btnInserisciFattura = new JButton("Inserisci");
		btnInserisciFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlloDati()) {
					String strDataFattura = dateToDatabase(textFieldDataFattura.getText());
					try {
						String query = "insert into Fattura (DataFattura, IndirizzoImmobile, Tipologia, Importo, Acquisitore, ProvvigioniAcquisitore, Venditore, ProvvigioniVenditore) "
										+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, strDataFattura);
						pst.setString(2, textFieldIndirizzo.getText());
						pst.setString(3, (String)comboBoxTipologia.getSelectedItem());
						pst.setString(4, textFieldImporto.getText());
						pst.setString(5, (String)comboBoxAcquisitore.getSelectedItem());
						pst.setString(6, textFieldProvvigioniAcquisitore.getText());
						pst.setString(7, (String)comboBoxVenditore.getSelectedItem());
						pst.setString(8, textFieldProvvigioniVenditore.getText());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Nuova Fattura inserita correttamente");
						
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
		btnInserisciFattura.setBounds(236, 446, 243, 25);
		add(btnInserisciFattura);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textFieldDataFattura.getText().equals("") || !textFieldImporto.getText().equals("") || !textFieldIndirizzo.getText().equals("") || 
						!textFieldProvvigioniAcquisitore.getText().equals("") || !textFieldProvvigioniVenditore.getText().equals("") || comboBoxTipologia.getSelectedIndex()!=0 ||
						comboBoxAcquisitore.getSelectedIndex()!=0 || comboBoxVenditore.getSelectedIndex()!=0 ) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Sicuro di voler resettare i campi?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						System.out.println(comboBoxTipologia.getSelectedIndex());
						System.out.println(comboBoxAcquisitore.getSelectedIndex());
						System.out.println(comboBoxVenditore.getSelectedIndex());
						resetFields();
					}
				}
			}
		});
		btnClear.setBounds(514, 446, 243, 25);
		add(btnClear);
		
		comboBoxTipologia = new JComboBox<String>();
		fillComboBox(comboBoxTipologia, "Tipo", "Tipologia");
		comboBoxTipologia.setBounds(303, 205, 126, 20);
		add(comboBoxTipologia);
		
		comboBoxAcquisitore = new JComboBox<String>();
		fillComboBox(comboBoxAcquisitore, "Cognome", "Personale");
		comboBoxAcquisitore.setBounds(303, 284, 126, 20);
		add(comboBoxAcquisitore);
		
		comboBoxVenditore = new JComboBox<String>();
		fillComboBox(comboBoxVenditore, "Cognome", "Personale");
		comboBoxVenditore.setBounds(303, 364, 126, 20);
		add(comboBoxVenditore);
		
		JButton btnDataFattura = new JButton("Data");
		btnDataFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lang = null;
				final Locale locale = getLocale(lang);
				DatePicker dp = new DatePicker(textFieldDataFattura, locale);
				Date selectedDate = dp.parseDate(textFieldDataFattura.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(textFieldDataFattura);
			}
		});
		btnDataFattura.setBounds(165, 153, 81, 19);
		add(btnDataFattura);
		

	}
	
	private void fillComboBox(JComboBox<String> comboBox, String parameterColumn, String nameTable) {
		comboBox.addItem("..:: Scegli ::..");
		try {
			String query = "select * from "+nameTable;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				comboBox.addItem(rs.getString(parameterColumn));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int getNextNumber() {
		int numeroFattura=0;
		try {
			String query = "select NumeroFattura from Fattura";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				numeroFattura++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} return numeroFattura+1;
	}
	
	private Locale getLocale(String loc) {
		if(loc!=null && loc.length() > 0)
			return new Locale(loc);
		else
			return Locale.ITALY;
	}
	
	private boolean controlloDati() {
		if(textFieldDataFattura.getText().equals("") || textFieldImporto.getText().equals("") || textFieldIndirizzo.getText().equals("") || 
				textFieldProvvigioniAcquisitore.getText().equals("") || textFieldProvvigioniVenditore.getText().equals("") )
			return false;
		return true;
	}
	
	private void resetFields() {
		textFieldNumeroFattura.setText(String.valueOf(getNextNumber()));
		textFieldDataFattura.setText("");
		textFieldIndirizzo.setText("");
		comboBoxTipologia.setSelectedIndex(0);
		textFieldImporto.setText("");;
		comboBoxAcquisitore.setSelectedIndex(0);
		textFieldProvvigioniAcquisitore.setText("");;
		comboBoxVenditore.setSelectedIndex(0);
		textFieldProvvigioniVenditore.setText("");
		
	}
	
	private String dateToDatabase(String  strDate) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat simpleFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strFormatted = simpleFormat.format(date);
		System.out.println(date);
		System.out.println(strFormatted);
		return strFormatted;
	}
	
	
	
	
	
}
