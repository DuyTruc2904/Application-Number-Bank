import models.*;

import Exception.CustomerIdNotValidException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Asm04 {
    private static final DigitalBank activeBank = new DigitalBank();

    public static void main(String[] args) {
        int i;
        do {
            display();

            do {
                try {
                    Scanner sc = new Scanner(System.in);
                    i = sc.nextInt();
                    if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 0) {
                        break;
                    } else {
                        System.out.print("Nhập sai. Nhập lại: ");
                    }
                } catch (Exception e) {
                    System.out.print("Nhập sai. Nhập lại: ");
                }
            } while (true);
            Scanner sc = new Scanner(System.in);
            switch (i) {
                case 1:
                    activeBank.showCustomer();
                    break;

                case 2:
                    System.out.println("Nhập đường dẫn thư mục");
                    String fileName = sc.nextLine();
                    activeBank.addCustomers(fileName);
//                    activeBank.addCustomers("store//customers.txt");
                    break;

                case 3:
                    System.out.print("Nhập căn cước công dân khách hàng: ");
                    String customerld = sc.nextLine();
                    if (activeBank.isCustomerExisted(customerld)) {
                        String stk = inputATMAddAccount();
                        if (stk != null) {
                            double amount = inputAmount("Nhập số dư tài khoản >= 50000: ");
                            if (amount != -1) {
                                Account account = new Account("", customerld, stk, amount);
                                activeBank.addAccount(customerld, account);
                                System.out.println("Tạo tài khoản thành công");
                                break;
                            }
                        } else {
                            System.out.println("Bạn đã nhập sai quá nhiều lần. Tác vụ không thành công");
                        }
                    } else {
                        System.out.println("Không tìm thấy khách hàng " + customerld + ". Tác vụ không thành công");
                    }
                    break;

                case 4:
                    try {
                        Customer customer4 = inputCustomerId();
                        customer4.displayInformation();
                        String accountNumber = inputATMSend(customer4.getCustomerld(), "Nhập số tài khoản gửi: ");
                        String accountReceive = inputATMReceive(accountNumber);
                        if (accountReceive.equals("exit")) {
                            break;
                        } else if (accountReceive != null) {
                            Customer customer = activeBank.getCustomerByAccount(accountReceive);
                            System.out.println("Gửi tiền đến tài khoản: " + accountReceive + " | " + customer.getName());
                            double amount = inputAmount("Nhập số tiền cần chuyển: ");
                            if (activeBank.isAmount(accountNumber, amount)) {
                                Utils utils = new Utils();
                                System.out.print("Xác nhận thực hiện chuyển " + utils.formatBalance(amount) + " từ tài khoản [" + accountNumber + "] đến tài khoản [" + accountReceive + "] (Y/N): ");
                                String y = sc.nextLine();
                                if (y.equalsIgnoreCase("Y")) {
                                    activeBank.transfer(accountNumber, accountReceive, amount);
                                    System.out.println("Chuyển tiền thành công, biên lai giao dịch:");
                                    Account account = activeBank.getAcountByAccountNumber(accountNumber);
                                    SavingsAccount savingsAccount = new SavingsAccount(account.getName(), account.getCustomerld(), account.getAccountNumber(), account.getBalance());
                                    savingsAccount.logTranfer(accountReceive, amount);
                                } else {
                                    System.out.println("Lỗi xác nhận, chuyển tiền không thành công");
                                }

                            } else {
                                System.out.println("Chuyển tiền không thành công do số tiền bạn rút quá lớn");
                            }
                        }
                    } catch (CustomerIdNotValidException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        Customer customer5 = inputCustomerId();
                        customer5.displayInformation();
                        String stk = inputATMSend(customer5.getCustomerld(), "Nhập số tài khoản rút: ");
                        if (stk != null) {
                            double amount = inputAmount("Nhập số tiền cần rút: ");
                            if (amount != -1) {
                                if (!activeBank.withdraw(customer5, stk, amount)) {
                                    System.out.println("Rút tiền không thành công");
                                }
                                break;
                            }
                        }
                    } catch (CustomerIdNotValidException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        Customer customer = inputCustomerId();
                        activeBank.showTransaction(customer);
                    } catch (CustomerIdNotValidException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 0:
                    return;
            }
        } while (true);
    }

    private static void display() {
        final String name = "ngân hàng số";
        final String version = "FX19555@4.0.0";

        System.out.println();
        System.out.println("+--------------+----------------------------+--------------+");
        System.out.println("| " + name.toUpperCase() + " | " + version + "                             |");
        System.out.println("+--------------+----------------------------+--------------+");
        System.out.println("1. Xem danh sách khách hàng");
        System.out.println("2. Nhập danh sách khách hàng");
        System.out.println("3. Thêm tài khoản ATM");
        System.out.println("4. Chuyển tiền");
        System.out.println("5. Rút tiền");
        System.out.println("6. Tra cứu lịch sử giao dịch");
        System.out.println("0. Thoát");
        System.out.println("+-------------+-----------------------------+--------------+");
        System.out.print("Chức năng: ");
    }

    private static Customer inputCustomerId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập căn cước công dân khách hàng ");
        String customerld = sc.nextLine();
        try {
            return activeBank.getCustomerById(customerld);
        } catch (CustomerIdNotValidException e) {
            throw new CustomerIdNotValidException("Không tồn tại khách hàng " + customerld + ". Tác vụ không thành công");

        }
    }

    private static double inputAmount(String inputinfomation) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        double amount;

        System.out.print(inputinfomation);
        do {
            try {
                amount = sc.nextDouble();
                if (amount >= 50000) {
                    return amount;
                } else {
                    System.out.print(inputinfomation);
                    count++;
                }
            } catch (Exception e) {
                System.out.print(inputinfomation);
                count++;
            }
        } while (count != 5);

        return -1;
    }

    private static String inputATMAddAccount() {
        int count = 0;
        String accountNumber;
        Pattern pattern = Pattern.compile("\\d{6}");

        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
            accountNumber = sc.nextLine();
            if (pattern.matcher(accountNumber).matches()) {
                if (!activeBank.isAccountExisted(accountNumber)) {
                    return accountNumber;
                } else {
                    count++;
                }
            } else {
                count++;
            }
        }
        while (count != 5);
        return null;
    }

    private static String inputATMSend(String customerld, String infomation) {
        int count = 0;
        String accountNumber;
        Pattern pattern = Pattern.compile("\\d{6}");
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print(infomation);
            accountNumber = sc.nextLine();
            if (pattern.matcher(accountNumber).matches()) {
                Account account = activeBank.getAcountByAccountNumber(accountNumber);
                if (account != null && account.getCustomerld().equals(customerld)) {
                    return accountNumber;
                } else {
                    count++;
                }
            } else {
                count++;
            }
        } while (count != 5);
        return null;
    }

    private static String inputATMReceive(String accountNumberSend) {
        int count = 0;
        String accountNumber;
        Pattern pattern = Pattern.compile("\\d{6}");
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập số tài khoản nhận, 'exit' để thoát: ");
            accountNumber = sc.nextLine();
            if (pattern.matcher(accountNumber).matches()) {
                if (activeBank.isAccountExisted(accountNumber) && !accountNumber.equals(accountNumberSend)) {
                    return accountNumber;
                } else {
                    count++;
                }
            } else {
                if (accountNumber.equals("exits")) {
                    return accountNumber;
                }
                count++;
            }
        } while (count != 5);
        System.out.println("Bạn đã nhập sai quá nhiều lần. Tác vụ không thành công");
        return null;
    }
}
