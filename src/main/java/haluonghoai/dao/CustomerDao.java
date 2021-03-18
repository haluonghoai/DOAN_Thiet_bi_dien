package haluonghoai.dao;

import haluonghoai.modal.Customer;
import haluonghoai.modal.Product;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends BaseDao<Customer> {

    List<Customer> search(String name, String phoneNumber, String email, String Adress) throws SQLException, ClassNotFoundException;
}
