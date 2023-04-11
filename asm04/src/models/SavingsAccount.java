package models;

import dao.AccountDao;
import dao.TransactionDao;
import in.ITransfer;
import in.ReportService;
import in.Withdraw;
import java.io.IOException;
import java.io.Serializable;
import static models.Customer.listAccounts;
import static models.Customer.listTransactions;

public class SavingsAccount extends Account implements Withdraw, ReportService, ITransfer, Serializable {

    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;

    public SavingsAccount(String name, String customerld, String accountNumber, double balance) {
        super(name, customerld, accountNumber, balance);
    }

    // xuất biên lại giao dịch nếu rút tiền thành công
    @Override
    public void logWithdraw(double amount) {
        Utils utils = new Utils();
        System.out.println(utils.getDivider());
        System.out.printf("| %40s %17s \n", getTitle(), "|");
        System.out.printf("| NGAY G/D: %46s %1s \n", utils.getDateTime(), "|");
        System.out.printf("| ATM ID: %48s %1s \n", "DIGITAL-BANK-ATM 2022", "|");
        System.out.printf("| SO TK: %49s %1s \n", this.getAccountNumber(), "|");
        System.out.printf("| SO TIEN: %47s %1s \n", utils.formatBalance(amount), "|");
        System.out.printf("| SO DU: %49s %1s \n", utils.formatBalance(this.getBalance()), "|");
        System.out.printf("| PHI + VAT: %45s %1s \n", utils.formatBalance(this.getTransactionFee() * amount), "|");
        System.out.println(utils.getDivider());
    }

    // Xuất biên lai giao dịch nếu chuyển tiền thành công
    @Override
    public void logTranfer(String stkReceive, double amount) {
        Utils utils = new Utils();
        System.out.println(utils.getDivider());
        System.out.printf("| %40s %17s \n", getTitle(), "|");
        System.out.printf("| NGAY G/D: %46s %1s \n", utils.getDateTime(), "|");
        System.out.printf("| ATM ID: %48s %1s \n", "DIGITAL-BANK-ATM 2022", "|");
        System.out.printf("| SO TK: %49s %1s \n", this.getAccountNumber(), "|");
        System.out.printf("| SO TK NHẬN: %44s %1s \n", stkReceive, "|");
        System.out.printf("| SO TIEN CHUYỂN: %40s %1s \n", utils.formatBalance(amount), "|");
        System.out.printf("| SO DU: %49s %1s \n", utils.formatBalance(this.getBalance()), "|");
        System.out.printf("| PHI + VAT: %45s %1s \n", 0, "|");
        System.out.println(utils.getDivider());
    }

    // rút tiền trong tài khoản
    // set lại số dư tài khoản và trả về true nếu thành công
    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            this.setBalance(this.getBalance() - amount);
            try {
                AccountDao.save(listAccounts);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    // kiểm tra số dư tài khoản có hợp lệ hay không
    @Override
    public boolean isAccepted(double amount) {
        if (amount >= 50000 && amount % 10 == 0 && this.getBalance() - amount >= 50000) {
            if (isPremium(this.getBalance())) {
                return true;
            } else {
                return amount < SAVINGS_ACCOUNT_MAX_WITHDRAW;
            }
        }
        return false;
    }

    // chuyển tiền
    // set lại số dư cả 2 tài khoản nếu thành công
    @Override
    public boolean transfer(Account account, Account accountReceive, double amount) {
        if(isTransfer(amount)) {
            account.setBalance(this.getBalance() - amount);
            accountReceive.setBalance(accountReceive.getBalance() + amount);
            try {
                AccountDao.update(account);
                AccountDao.update(accountReceive);
                listTransactions.add(new Transaction(account.getCustomerld(), account.getAccountNumber(), -amount, new Utils().getDateTime(), true, "TRANSFER"));
                listTransactions.add(new Transaction(accountReceive.getCustomerld(), accountReceive.getAccountNumber(), amount, new Utils().getDateTime(), true, "TRANSFER"));
                TransactionDao.save(listTransactions);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        listTransactions.add(new Transaction(account.getCustomerld(), account.getAccountNumber(), -amount, new Utils().getDateTime(), false, "TRANSFER"));
        try {
            TransactionDao.save(listTransactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // kiểm tra rút tiền hợp lệ hay không
    @Override
    public boolean isTransfer(double amount) {
        return this.getBalance() - amount >= 50000;
    }



    // xuất phí giao dịch
    public double getTransactionFee() {
        return 0;
    }

    // xuất tiêu đề giao dịch trong biên lai
    public String getTitle() {
        return "BIÊN LAI GIAO DỊCH SAVINGS";
    }
}
