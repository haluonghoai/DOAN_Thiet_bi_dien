package haluonghoai.dao;


import haluonghoai.modal.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    List<Product> search(String id, String name, float price, boolean status) throws SQLException, ClassNotFoundException;
    List<Product> findByCategory(int idCategory) throws SQLException, ClassNotFoundException;
    List<Product> searchProductByName(String name) throws SQLException;
}
