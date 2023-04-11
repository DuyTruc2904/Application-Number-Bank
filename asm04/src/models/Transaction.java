package models;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Transaction implements Serializable  {
    private static final long serialVersionUID = 1L;
    private final String customerld;
    private final String accountNumber;
    private final double amount;
    private final String time;
    private final boolean status;

    private final String type;

    public Transaction(String customerld, String accountNumber, double amount, String time, boolean status, String type) {
        this.customerld = customerld;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.status = status;
        this.type = type;
    }

    // xuất thông tin liịch sử giao dịch
    public String toString() {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String amount = en.format(getAmount());

        if(isStatus()) {
            return String.format("%5s %5s | %8s | %15s | %30s", "[GD]", getAccountNumber(), type, amount + " đ", getTime());
        } else {
            return String.format("%5s %5s | %15s | %30s", "[GD]", getAccountNumber(), "Lỗi xác định", getTime());
        }
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public boolean isStatus() {
        return status;
    }

}
