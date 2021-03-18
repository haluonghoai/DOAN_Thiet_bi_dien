package haluonghoai.dao;

import haluonghoai.modal.Receipt;

import java.sql.SQLException;

public interface ReceiptDao extends BaseDao<Receipt> {
    boolean updateTinhTrang(boolean statusEnter, int id) throws SQLException;

}
