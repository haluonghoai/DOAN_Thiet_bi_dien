package haluonghoai.dao_impl;

import haluonghoai.dao.OrderDao;
import haluonghoai.modal.MyConnection;
import haluonghoai.modal.Order;
import haluonghoai.modal.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao_impl implements OrderDao {

    MyConnection myConnection = new MyConnection();

    @Override
    public Order getObject(ResultSet resultSet) throws SQLException {
        Order order = null;
        order = new Order(resultSet.getInt("iMadonhang"),resultSet.getDate("dThoigiandat"),resultSet.getString("sGhichu"),resultSet.getInt("iManguoidung"),resultSet.getInt("iMakhachhang"),resultSet.getInt("iMahinhthucthanhtoan"),resultSet.getInt("iMatrangthai"),resultSet.getBoolean("bTrangthaithanhtoan"));
        return order;
    }

    @Override
    public List<Order> getList(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException, ClassNotFoundException {
        List<Order> list = new ArrayList<>();
        String sql = "select tblDonHang.iMadonhang as N'Mã đơn hàng', tblDonHang.dThoigiandat as N'Thời gian đặt', tblTrangThaiDonHang.sTentrangthai as N'Trạng thái đơn hàng', tblHinhThucThanhToan.sTenhinhthucthanhtoan as N'Hình thức thanh toán',\n" +
                "tblDonHang.bTrangthaithanhtoan as N'Trạng thái thanh toán' from ((tblDonHang inner join tblTrangThaiDonHang on tblDonHang.iMatrangthai = tblTrangThaiDonHang.iMatrangthai)\n" +
                "inner join tblHinhThucThanhToan on tblDonHang.iMahinhthucthanhtoan = tblHinhThucThanhToan.iMahinhthucthanhtoan)";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.first()){
            do{
                Order order = getObject(resultSet);
                if(order != null) list.add(order);
            }while(resultSet.next());
        }
        return list;
    }

    @Override
    public Order findById(int id) throws Exception {
        Order order = null;
        String sql = "select * from tblDonHang where iMadonhang = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet =  preparedStatement.executeQuery();
        if(resultSet.first()) {
            order = getObject(resultSet);
        }
        return order;
    }

    @Override
    public Order insert(Order order) throws Exception {
        Order newOrder = null;
        String sql = "insert tblDonHang values (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setDate(1, (Date) order.getTimecreate());
        preparedStatement.setString(2,order.getNote());
        preparedStatement.setString(3,order.get);
        preparedStatement.setString(4,user.getPhonenumber());
        preparedStatement.setDate(5, (Date) user.getDateOfBirth());
        preparedStatement.setString(6,user.getPass());
        preparedStatement.setInt(7,user.getIdQuyen());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.first()) {
                newUser = findById((int) resultSet.getLong(1));
            }
        }
        return newUser;
    }

    @Override
    public boolean update(Order object) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
