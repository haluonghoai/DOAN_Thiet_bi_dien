package haluonghoai.dao;

import haluonghoai.modal.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends BaseDao<Order> {

    boolean updateStatusPayment(boolean tttt, int id) throws SQLException, ClassNotFoundException;

    boolean updateStatusOrder(int matrangthai, int id) throws SQLException, ClassNotFoundException;

    Order seeDetails(int id) throws SQLException, ClassNotFoundException;

    Order seeDetailsProduct(int id) throws SQLException, ClassNotFoundException;

    float totalMoney() throws SQLException, ClassNotFoundException;

    boolean updateProductInOrder(Order order) throws SQLException, ClassNotFoundException;

    List<Order> selectOrderByPayments(boolean payments) throws SQLException, ClassNotFoundException;

    List<Order> selectOrderByStatusOrder(int matrangthai) throws SQLException, ClassNotFoundException;

    List<Order> selectOrderByStatusPayments(boolean trangthaitt) throws SQLException, ClassNotFoundException;

    Order findById(int id) throws SQLException, ClassNotFoundException, Exception;
}
