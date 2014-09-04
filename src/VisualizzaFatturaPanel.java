import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView.TableRow;

import utility.DateRenderer;
import utility.NumberRenderer;
import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VisualizzaFatturaPanel extends JPanel {

	Connection connection = null;
	private JTable table;
	private JTextField textFieldNumeroFattura;
	private ObservingTextField textFieldDataFattura;
	private JTextField textFieldIndirizzoImmobile;
	private JTextField textFieldImporto;
	private JTextField textFieldProvvigioniAcquisitore;
	private JTextField textFieldProvvigioniVenditore;
	private JComboBox<String> comboBoxTipologia;
	private JComboBox<String> comboBoxAcquisitore;
	private JComboBox<String> comboBoxVenditore;
	
	/**
	 * Create the panel.
	 */
	public VisualizzaFatturaPanel() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1300, 600);
		setLayout(null);
		
//		TABLE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(438, 84, 850, 432);
		add(scrollPane);
		table = new JTable();
		
//		set field by click row
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String numeroFattura = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Fattura where NumeroFattura='"+numeroFattura+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						textFieldNumeroFattura.setText(rs.getString("NumeroFattura").toString());
						textFieldDataFattura.setText(rs.getString("DataFattura"));
						textFieldIndirizzoImmobile.setText(rs.getString("IndirizzoImmobile"));
						
						comboBoxTipologia.setSelectedItem((String)rs.getString("Tipologia"));
						textFieldImporto.setText(rs.getString("Importo"));
						
						comboBoxAcquisitore.setSelectedItem((String)rs.getString("Acquisitore"));
						textFieldProvvigioniAcquisitore.setText(rs.getString("ProvvigioniAcquisitore"));
						
						comboBoxVenditore.setSelectedItem((String)rs.getString("Venditore"));
						textFieldProvvigioniVenditore.setText(rs.getString("ProvvigioniVenditore"));
					}
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
			}
		});
		
//		order table by click column
		table.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	int col = table.columnAtPoint(e.getPoint());
		    	String nameColumn = table.getColumnName(col);
		    	System.out.println(nameColumn);
		    	if(nameColumn.equals("DataFattura")) {
	    			nameColumn = "datetime(DataFattura)"; 
	    		}
		    	System.out.println(nameColumn);
		    	if (e.getClickCount() == 1) {
			    	orderTable(nameColumn, "ASC");
		    	} else if(e.getClickCount() == 2) {
		    		orderTable(nameColumn, "DESC");
		    	}
		    }
		});
		table.setRowHeight(20);
		
//		refresh tabella
		refreshTable();
		
		scrollPane.setViewportView(table);
		
//		Button & TextFields
		
		JLabel lblTitoloGestioneFatture = new JLabel("Visualizza e gestisci fatture");
		lblTitoloGestioneFatture.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTitoloGestioneFatture.setBounds(75, 59, 373, 61);
		add(lblTitoloGestioneFatture);
		
		JLabel lblNumeroFattura = new JLabel("Numero Fattura");
		lblNumeroFattura.setBounds(75, 134, 118, 15);
		add(lblNumeroFattura);
		
		textFieldNumeroFattura = new JTextField();
		textFieldNumeroFattura.setEditable(false);
		textFieldNumeroFattura.setBounds(243, 132, 114, 19);
		add(textFieldNumeroFattura);
		textFieldNumeroFattura.setColumns(10);
		
		textFieldDataFattura = new ObservingTextField();
		textFieldDataFattura.setBounds(243, 172, 114, 19);
		add(textFieldDataFattura);
		textFieldDataFattura.setColumns(10);
		
		JLabel lblIndirizzoImmobile = new JLabel("Indirizzo Immobile");
		lblIndirizzoImmobile.setBounds(75, 214, 133, 15);
		add(lblIndirizzoImmobile);
		
		textFieldIndirizzoImmobile = new JTextField();
		textFieldIndirizzoImmobile.setBounds(243, 212, 114, 19);
		add(textFieldIndirizzoImmobile);
		textFieldIndirizzoImmobile.setColumns(10);
		
		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setBounds(75, 254, 70, 15);
		add(lblTipologia);
		
		comboBoxTipologia = new JComboBox<String>();
		fillComboBox(comboBoxTipologia, "Tipo", "Tipologia");
		comboBoxTipologia.setBounds(243, 249, 114, 19);
		add(comboBoxTipologia);
		
		JLabel lblImporto = new JLabel("Importo");
		lblImporto.setBounds(75, 294, 70, 15);
		add(lblImporto);
		
		textFieldImporto = new JTextField();
		textFieldImporto.setBounds(243, 292, 114, 19);
		add(textFieldImporto);
		textFieldImporto.setColumns(10);
		
		JLabel lblAcquisitore = new JLabel("Acquisitore");
		lblAcquisitore.setBounds(75, 334, 91, 15);
		add(lblAcquisitore);
		
		comboBoxAcquisitore = new JComboBox<String>();
		fillComboBox(comboBoxAcquisitore, "Cognome", "Personale");
		comboBoxAcquisitore.setBounds(243, 329, 114, 19);
		add(comboBoxAcquisitore);
		
		JLabel lblProvvigAcquisitore = new JLabel("Provvig Acquisitore");
		lblProvvigAcquisitore.setBounds(75, 374, 148, 15);
		add(lblProvvigAcquisitore);
		
		textFieldProvvigioniAcquisitore = new JTextField();
		textFieldProvvigioniAcquisitore.setBounds(243, 372, 114, 19);
		add(textFieldProvvigioniAcquisitore);
		textFieldProvvigioniAcquisitore.setColumns(10);
		
		JLabel lblVenditore = new JLabel("Venditore");
		lblVenditore.setBounds(75, 414, 70, 15);
		add(lblVenditore);
		
		JLabel lblProvvigVenditore = new JLabel("Provvig Venditore");
		lblProvvigVenditore.setBounds(75, 454, 125, 15);
		add(lblProvvigVenditore);
		
		textFieldProvvigioniVenditore = new JTextField();
		textFieldProvvigioniVenditore.setBounds(243, 452, 114, 19);
		add(textFieldProvvigioniVenditore);
		textFieldProvvigioniVenditore.setColumns(10);
		
		comboBoxVenditore = new JComboBox<String>();
		fillComboBox(comboBoxVenditore, "Cognome", "Personale");
		comboBoxVenditore.setBounds(243, 409, 114, 20);
		add(comboBoxVenditore);
		
		JButton btnModificaFattura = new JButton("Modifica");
		btnModificaFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update Fattura set " + 
									"DataFattura='"+textFieldDataFattura.getText()+"', IndirizzoImmobile='"+textFieldIndirizzoImmobile.getText() + 
									"', Tipologia='"+(String)comboBoxTipologia.getSelectedItem() + "', Importo='"+textFieldImporto.getText() + 
									"', Acquisitore='"+(String)comboBoxAcquisitore.getSelectedItem() + "', ProvvigioniAcquisitore='"+textFieldProvvigioniAcquisitore.getText() +
									"', Venditore='"+(String)comboBoxVenditore.getSelectedItem() + "', ProvvigioniVenditore='"+textFieldProvvigioniVenditore.getText() +
									"' where NumeroFattura='"+textFieldNumeroFattura.getText()+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Fattura aggiornata correttamente");
					pst.close();
					refreshTable();
//					clearTextFields();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnModificaFattura.setBounds(75, 491, 133, 25);
		add(btnModificaFattura);
		
		JButton btnDataFattura = new JButton("Data Fattura");
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
		btnDataFattura.setBounds(75, 171, 125, 19);
		add(btnDataFattura);
		
		JButton btnClearFields = new JButton("Clear");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumeroFattura.setText("");
				textFieldDataFattura.setText("");
				textFieldIndirizzoImmobile.setText("");
				comboBoxTipologia.setSelectedIndex(0);
				textFieldImporto.setText("");
				comboBoxAcquisitore.setSelectedIndex(0);
				textFieldProvvigioniAcquisitore.setText("");
				comboBoxVenditore.setSelectedIndex(0);
				textFieldProvvigioniVenditore.setText("");
			}
		});
		btnClearFields.setBounds(224, 491, 133, 25);
		add(btnClearFields);
		

	}
	
	private void refreshTable() {
		try {
			String query = "select * from Fattura";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
//			set cells format
			TableColumnModel tcm = table.getColumnModel();  
			NumberFormat format = new DecimalFormat("#,###.00"); 
			format.setMaximumFractionDigits(2);  
			tcm.getColumn(4).setCellRenderer( new NumberRenderer( format ) ); 
			tcm.getColumn(6).setCellRenderer( new NumberRenderer( format ) ); 
			tcm.getColumn(8).setCellRenderer( new NumberRenderer( format ) );
			
			table.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer());
	
//			set TOTAL last row
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.addRow(new Object[] {"TOTALE", null, "", "", getSumColumn(4), "", getSumColumn(6), "", getSumColumn(8)});
			
			//table.setDefaultRenderer(Object.class, new MyRenderer());

			
			
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void orderTable(String nameColumn, String typeOrder) {
		try {
			String query = "select * from Fattura ORDER BY " + nameColumn + " " + typeOrder;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
//			set cells format
			TableColumnModel tcm = table.getColumnModel();  
			NumberFormat format = new DecimalFormat("#,###.00"); 
			format.setMaximumFractionDigits(2);  
			tcm.getColumn(4).setCellRenderer( new NumberRenderer( format ) ); 
			tcm.getColumn(6).setCellRenderer( new NumberRenderer( format ) ); 
			tcm.getColumn(8).setCellRenderer( new NumberRenderer( format ) ); 
			
			table.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer());
			
//			set TOTAL last row
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.addRow(new Object[] {"TOTALE", null, "", "", getSumColumn(4), "", getSumColumn(6), "", getSumColumn(8)});
			
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private Locale getLocale(String loc) {
		if(loc!=null && loc.length() > 0)
			return new Locale(loc);
		else
			return Locale.ITALY;
	}
	
	private double getSumColumn(int column) {
		double sum = 0;
		int rows = table.getRowCount();
		for(int i=0; i<rows; i++) {
			sum += (double)table.getModel().getValueAt(i, column);
		} return sum;
	}
}

class MyRenderer implements TableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		NumberFormat format = new DecimalFormat("#,###.00"); 
		format.setMaximumFractionDigits(2);  
		
        JLabel text = new JLabel();
        if(text!=null) {
        	text.setText(value.toString());
        	text.setFont(new Font("Serif", Font.PLAIN, 14));
        }
        if(row==(table.getRowCount()-1)) {
        	text.setForeground(Color.blue);
        }
        if(column==4) {
        	System.out.println(value);
        }
        return text;
        
        
    }
}



	