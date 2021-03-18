package haluonghoai.service;

import haluonghoai.modal.Supplier;

import java.sql.SQLException;

public interface SupplierService {
    Supplier insert(Supplier supplier) throws SQLException;
}
