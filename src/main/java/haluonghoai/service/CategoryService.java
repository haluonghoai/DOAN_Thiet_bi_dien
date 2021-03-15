package haluonghoai.service;

import haluonghoai.modal.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> findAll() throws SQLException, ClassNotFoundException;
    Category insert(String name) throws Exception;
    boolean update(Category category) throws SQLException;
    boolean delete(int id) throws SQLException;
    int count() throws SQLException, ClassNotFoundException;
}
