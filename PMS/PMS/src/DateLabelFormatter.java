import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.DateFormatter;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            if (value instanceof Calendar) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            } else if (value instanceof Date) {
                return dateFormatter.format((Date) value);
            }
        }
        return "";
    }
}
