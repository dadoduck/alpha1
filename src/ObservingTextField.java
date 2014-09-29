
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

public class ObservingTextField extends JTextField implements Observer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void update(Observable o, Object arg) {
        Calendar calendar = (Calendar) arg;
        DatePicker dp = (DatePicker) o;
     //   System.out.println("picked=" + dp.formatDate(calendar));
        setText(dateToDatabase(dp.formatDate(calendar)));
    }
	
	/*
	 * convert date for insert into DB
	 */
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
