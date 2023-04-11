package models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils implements Serializable {
    public String getDivider() {
        return "+--------------+----------------------------+--------------+" ;
    }
    public String getDateTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(date);
    }

    public String formatBalance(double amount) {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        return en.format(amount);
    }
}
