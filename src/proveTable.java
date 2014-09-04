import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class proveTable extends JPanel {
	
	Connection connection = null;
	private JTable table;
	String[] columnNames = {"Numero", "Data", "Indirizzo", "Tipologia", "Importo", "Acquisitore", "Provv A", "Venditore", "Provv V"};
	Object[][] data = {};
	
	/**
	 * Create the panel.
	 */  
	public proveTable() {

//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1300, 600);
		setLayout(null);
		
//		TABLE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		LoadTable();
		
		table.setRowHeight(25);
		
		scrollPane.setViewportView(table);
		
	}
	
	private void LoadTable() {
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);
			table = new JTable();
	        table.setModel(model);
			
			String query = "select * from Fattura";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			String strNumero="", strIndirizzo="", strTipologia="", strAcquisitore="", strVenditore="";
			double dblImporto = 0, dblProvvA = 0, dblProvvV = 0;
			String strDate="";
			int i=0;
			
			double totaleImporto = 0, totaleProvvAcq = 0, totaleProvvVen = 0;
			NumberFormat f = new DecimalFormat("#,###.00"); 
			
			while(rs.next()) {
				
				strNumero=rs.getString("NumeroFattura");
				strDate=rs.getString("DataFattura");
				strIndirizzo=rs.getString("IndirizzoImmobile");
				strTipologia=rs.getString("Tipologia");
				dblImporto=rs.getDouble("Importo");
				strAcquisitore=rs.getString("Acquisitore");
				dblProvvA=rs.getDouble("ProvvigioniAcquisitore");
				strVenditore=rs.getString("Venditore");
				dblProvvV=rs.getDouble("ProvvigioniVenditore");
								
				model.addRow(new Object[]{strNumero, strDate, strIndirizzo, strTipologia, f.format(dblImporto), strAcquisitore, f.format(dblProvvA), strVenditore, f.format(dblProvvV)});
				 
				totaleImporto+=dblImporto;
				totaleProvvAcq+=dblProvvA;
				totaleProvvVen+=dblProvvV;
				i++;
			}
			
			model.addRow(new Object[] {"Totale", "", "", "", f.format(totaleImporto), "", f.format(totaleProvvAcq), "", f.format(totaleProvvVen)});
			
			table.setDefaultRenderer(Object.class, new provaRenderer());
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class provaRenderer implements TableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		NumberFormat format = new DecimalFormat("#,###.00"); 
		format.setMaximumFractionDigits(2);  
		
        JLabel text = new JLabel();
        if(text!=null) {
        	text.setText(value.toString());
        	System.out.println(text.getFont());
        	System.out.println(text.getFont());
        	text.setFont(new Font("Dialog", Font.PLAIN, 12));
        	System.out.println(text.getFont());
        }
        if(row==(table.getRowCount()-1)) {
        	text.setFont(new Font("Dialog", Font.BOLD, 12));
        }
        if((column==4) || (column==6) || (column==8)) {
        	text.setHorizontalAlignment(SwingConstants.RIGHT);
        	System.out.println(value);
        }
        if((column==0) || (column==1)) {
        	text.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return text;
        
        
    }
}
