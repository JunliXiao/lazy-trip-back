package tour.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {
	public boolean isValidDate(String dateStr, String formatStr) {
	    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
	    sdf.setLenient(false);
	    try {
	      Date date = sdf.parse(dateStr);
	      return date != null;
	    } catch (Exception e) {
	      return false;
	    }
	}
}
