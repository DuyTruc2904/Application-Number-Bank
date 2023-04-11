package models;

import dao.AccountDao;
import dao.CustomerDao;
import dao.TransactionDao;
import service.TextFileService;
import java.io.*;

import static models.Customer.listAccounts;
import static models.Customer.listTransactions;

public class DigitalBank extends Bank implements Serializable {

    // Hiển thị danh sách khách hàng từ file customer.dat
    public void showCustomer() {
        for (Customer customer : listCustomers) {
            customer.displayInformation();
        }
    }

    // đọc dữ liệu khách hàng từ file .txt
    public void addCustomers(String fileName) {
        try {
            TextFileService.textFileService(fileName).forEach(u -> {
                if (u.size() <= 1) {
                    System.out.println("Thêm khách hàng " + u.get(0) + " không hợp lệ");
                } else {
                    Customer customer = new Customer(u);
                    if (customer.isCustomerldExisted(customer.getCustomerld())) {
                        addCustomerInList(new Customer(customer.getName(), customer.getCustomerld()));
                    } else {
                        System.out.println("số căn cước công dân " + customer.getCustomerld() + " không hợp lệ");
                    }
                }
            });
            CustomerDao.save(listCustomers);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // thêm 1 account từ digitalBank
    public void addAccount(String customerld, Account account) {
        for (Customer customer : listCustomers) {
            if (customer.getCustomerld().equals(customerld)) {
                try {
                    AccountDao.update(account);
                    listTransactions.add(new Transaction(customerld, account.getAccountNumber(), account.getBalance(), new Utils().getDateTime(), true, "DEPOSIT"));
                    TransactionDao.save(listTransactions);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
    }


    // rút tiền
    // Tìm tài khoản cần rút
    // kiểm tra số tiền rút có hợp lệ hay không
    // lưu lại vào file accounts.dat
    public boolean withdraw(Customer customer, String accountNumber, double amount) {
        Account account = getAcountByAccountNumber(accountNumber);
        SavingsAccount savingsAccount = new SavingsAccount(account.getName(), account.getCustomerld(), account.getAccountNumber(), account.getBalance());
        if (savingsAccount.withdraw(amount)) {
            account.setBalance(savingsAccount.getBalance());
            listTransactions.add(new Transaction(customer.getCustomerld(), accountNumber, -amount, new Utils().getDateTime(), true, "WITHDRAW"));
            try {
                AccountDao.update(account);
                TransactionDao.save(listTransactions);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Rút tiền thành công, biên lai giao dịch:");
            savingsAccount.logWithdraw(amount);
            return true;
        } else {
            listTransactions.add(new Transaction(customer.getCustomerld(), accountNumber, -amount, new Utils().getDateTime(), false, "WITHDRAW"));
        }
        try {
            TransactionDao.save(listTransactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Chuyển tiền
    // Tìm hai tài khoản gửi và chuyển tiền
    // Kiểm tra số tiền chuyển hợp lệ hay không
    // lưu lại số tiền và lưu và lưu vào file
    public void transfer(String stk, String stkReceive, double amount) {
        Account account = getAcountByAccountNumber(stk);
        Account accountReceive = getAcountByAccountNumber(stkReceive);
        SavingsAccount savingsAccount = new SavingsAccount(account.getName(), account.getCustomerld(), account.getAccountNumber(), account.getBalance());
        savingsAccount.transfer(account, accountReceive, amount);
    }

    public boolean isAmount(String stk, double amount) {
        Account account = getAcountByAccountNumber(stk);
        SavingsAccount savingsAccount = new SavingsAccount(account.getName(), account.getCustomerld(), account.getAccountNumber(), account.getBalance());
        return savingsAccount.isTransfer(amount);
    }


    //     Hiển thị thông tin lịch sử giao dịch
//     Xuất list giao dịch của 1 khách hàng
    public void showTransaction(Customer customer) {
        customer.displayInformation();
        for(Account account: listAccounts) {
            if(account.getCustomerld().equals(customer.getCustomerld())) {
                account.getTransaction();
            }
        }
    }


    // Xuất ra các thông tin về khách hàng đã tồn tại hoặc thêm mới
    private void addCustomerInList(Customer customer) {
        if (listCustomers.size() == 0) {
            listCustomers.add(customer);
            System.out.println("Đã thêm khách hàng " + customer.getCustomerld() + " vào danh sách khách hàng");
        } else {
            if (isCustomerExisted(customer.getCustomerld())) {
                System.out.println("Khách hàng " + customer.getCustomerld() + " đã tồn tại, thêm khách hàng không thành công");

            } else {
                listCustomers.add(customer);
                System.out.println("Đã thêm khách hàng " + customer.getCustomerld() + " vào danh sách khách hàng");
            }
        }
    }
}

