package haluonghoai.service_impl;

import haluonghoai.dao.CategoryDao;
import haluonghoai.dao_impl.CategoryDao_impl;
import haluonghoai.modal.Category;
import haluonghoai.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryService_impl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDao_impl();

    @Override
    public List<Category> findAll() throws SQLException, ClassNotFoundException {
        return categoryDao.findAll();
    }

    @Override
    public Category insert(String name) throws Exception {
        Category category = new Category();
        category.setName(name);
        category.setStatus(false);
        return categoryDao.insert(category);
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return category.getId() > 0 ? categoryDao.update(category) : false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id>0 ? categoryDao.delete(id) : false;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        return categoryDao.count();
    }
}
