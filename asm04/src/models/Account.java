package models;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import static models.Customer.listTransactions;

public class Account extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String accountNumber;
    private double balance;

    public Account(String name, String customerld, String accountNumber, double balance) {
        super(name, customerld);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Kiểm tra tài khoản có phải là premium hay không
    // Trên 10.000.000 trả về true,
    public boolean isPremium(double balance) {
        return balance >= 10000000;
    }

    // xuất tài khoản khách hàng
    // xuất thông tin theo dạng: 1     111111 |      SAVINGS |                        50,000đ
    public String toString() {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String balance = en.format(getBalance());
        return String.format(accountNumber + " | %-26s | %30s", "Savings", balance + "đ");
    }

    public void getTransaction() {
        listTransactions.forEach(transaction -> {
            if (transaction.getAccountNumber().equals(this.getAccountNumber())) {
                displayTransaction(transaction);
            }
        });
    }

    public void displayTransaction(Transaction transaction) {
        System.out.println(transaction);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
