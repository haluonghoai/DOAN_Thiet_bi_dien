package haluonghoai.main;



import haluonghoai.dao.ProductDao;
import haluonghoai.dao.UserDao;
import haluonghoai.dao_impl.ProductDao_impl;
import haluonghoai.dao_impl.UserDao_impl;
import haluonghoai.modal.MyConnection;
import haluonghoai.modal.Product;
import haluonghoai.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MyConnection myConnection = new MyConnection();
        ProductDao productDao = new ProductDao_impl();
        List<Product> list = productDao.searchProductByName("Dây điện");
        for(Product p : list){
            System.out.println(p);
        }
        List<Product> list1 = productDao.findByCategory(6);
        for(Product p : list1){
            System.out.println(p);
        }

        UserDao userDao = new UserDao_impl();
        System.out.println();

    }
}
