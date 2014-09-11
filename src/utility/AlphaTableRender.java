package utility;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class AlphaTableRender implements TableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		NumberFormat format = new DecimalFormat("#,###.00"); 
		format.setMaximumFractionDigits(2);  
		
        JLabel text = new JLabel();
        if(text!=null) {
        	text.setText(value.toString());
        	text.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        if(row==(table.getRowCount()-1)) {
        	text.setFont(new Font("Dialog", Font.BOLD, 12));
        }
        if((table.getColumnName(column).equals("Importo")) || 
        		(table.getColumnName(column).equals("ProvvigioniAcquisitore")) || 
        		(table.getColumnName(column).equals("ProvvigioniVenditore"))) {
        	text.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        if((table.getColumnName(column).equals("NumeroFattura")) || (table.getColumnName(column).equals("DataFattura"))) {
        	text.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if((table.getColumnName(column).equals("IndirizzoImmobile")) ||
        		(table.getColumnName(column).equals("Tipologia")) ||
        		(table.getColumnName(column).equals("Acquisitore")) ||
        		(table.getColumnName(column).equals("Venditore"))) {
        	text.setBorder(new EmptyBorder(0, 7, 0, 0) );
        }
        if((table.getColumnName(column).equals("Importo")) ||
        		(table.getColumnName(column).equals("ProvvigioniAcquisitore")) ||
        		(table.getColumnName(column).equals("ProvvigioniVenditore"))) {
        	text.setBorder(new EmptyBorder(0, 0, 0, 7) );
        }
        	
        return text;
        
        
    }
}