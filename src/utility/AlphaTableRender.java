package utility;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
        if((column==4) || (column==6) || (column==8)) {
        	text.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        if((column==0) || (column==1)) {
        	text.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return text;
        
        
    }
}