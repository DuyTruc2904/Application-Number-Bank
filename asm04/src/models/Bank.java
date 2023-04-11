package models;

import Exception.CustomerIdNotValidException;
import dao.CustomerDao;
import java.io.Serializable;
import java.util.List;

import static models.Customer.listAccounts;

public class Bank implements Serializable {
    public List<Customer> listCustomers = CustomerDao.list();

    // tìm kiếm khách hàng có trong danh sách chưa
    // check khách hàng tồn tại cccd chưa
    public boolean isCustomerExisted(String customerld) {
        return listCustomers.stream().anyMatch(customer -> customer.getCustomerld().equals(customerld));
    }

    // tìm kiếm khách hàng và trả về Customer
    public Customer getCustomerById(String customerld) {
        for(Customer customer : listCustomers) {
            if(customer.getCustomerld().equals(customerld)) {
                return customer;
            }
        }
        throw new CustomerIdNotValidException("Không tồn tại khách hàng " + customerld + ". Tác vụ không thành công");
    }

    // Kiểm tra xem khách hàng có trùng số tài khoản hay không
    public boolean isAccountExisted(String stk) {
        return listAccounts.stream().anyMatch(account -> account.getAccountNumber().equals(stk));
    }

    // tìm số tài khoản tồn tại hay chưa. nếu chưa trả về null
    public Account getAcountByAccountNumber(String accountNumber) {
        return listAccounts.stream()
                .filter(account ->account.getAccountNumber().equals(accountNumber))
                .findAny()
                .orElse(null);
    }

    public Customer getCustomerByAccount(String accountNumber) {
        Account account = getAcountByAccountNumber(accountNumber);
        for(Customer customer: listCustomers) {
            if(account.getCustomerld().equals(customer.getCustomerld())) {
                return customer;
            }
        }
        return null;
    }
}
