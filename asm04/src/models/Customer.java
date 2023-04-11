package models;

import dao.AccountDao;
import dao.TransactionDao;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

public class Customer extends User implements Serializable {

    public static List<Account> listAccounts = AccountDao.list();

    public static List<Transaction> listTransactions = TransactionDao.list();

    // Hàm đọc dữ liệu từ file
    public Customer(List<String> values) {
        this(values.get(1), values.get(0));
    }

    public Customer(String name, String customerld) {
        super(name, customerld);
    }

    // kiểm tra khách hàng có phải là premium hay không
    // khách hàng là premium khi 1 account là premium
    public boolean isPremium() {
        for (Account account : listAccounts) {
            if(this.getCustomerld().equals(account.getCustomerld())) {
                if (account.isPremium(account.getBalance())) {
                    return true;
                }
            }

        }
        return false;
    }


    // Tính tổng số dư của khách hàng trong tài khoản
    public double getBalance() {
        double sodu = 0;
        for (Account account : listAccounts) {
            if(this.getCustomerld().equals(account.getCustomerld())) {
                sodu += account.getBalance();
            }
        }
        return sodu;
    }

    // Xuất thông tin tài khoản 1 khách hàng
    // kiểm tra khách hàng phải là premium hay không rồi xuất theo dạng
    //001215000001 |     Duy Truc |       Normal |         50,000đ
    //1     111111 |      SAVINGS |                        50,000đ
    //2     222222 |         LOAN |                             0đ
    public void displayInformation() {
        String is;
        boolean isPremiun = isPremium();
        if (isPremiun) {
            is = "Premium";
        } else {
            is = "Normal";
        }

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String balance = en.format(getBalance());

        System.out.printf(super.getCustomerld() + " |%-27s | %-10s | %17s \n", super.getName(), is, balance + "đ");
        int i = 1;
        for (Account account : listAccounts) {
            if(super.getCustomerld().equals(account.getCustomerld())) {
                System.out.print(i + "     ");
                String str = account.toString();
                System.out.println(str);
                i++;
            }
        }
    }

    public <T> List<T> getAccount(String customerld) {
        List<T> objects = new ArrayList<>();
        listAccounts.forEach(object -> {
            if(object.getCustomerld().equals(customerld)) {
                objects.add((T) object);
            }
        });
        return objects;
    }
}
