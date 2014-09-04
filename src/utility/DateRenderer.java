package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	private Date dateValue;
	private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd-MM-yyyy");
	private String valueToString = "";
	
	@Override
	public void setValue(Object value) {
	    if ((value != null)) {
	        String stringFormat = value.toString();
	        try {
	            dateValue = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY).parse(stringFormat);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        valueToString = sdfNewValue.format(dateValue);
	        value = valueToString;
	    }
	    super.setValue(value);
	}
}