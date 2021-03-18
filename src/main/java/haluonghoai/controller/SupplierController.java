package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.dao.SupplierDao;
import haluonghoai.dao_impl.SupplierDap_impl;
import haluonghoai.modal.Category;
import haluonghoai.modal.JsonResult;
import haluonghoai.modal.Product;
import haluonghoai.modal.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SupplierController", urlPatterns = "/api/v1/supplier/*")
public class SupplierController extends HttpServlet {

    private SupplierDao supplierDao = new SupplierDap_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs ="";
        try {
            Supplier supplier = new Gson().fromJson(request.getReader(),Supplier.class);
            Supplier newSupplier = supplierDao.insert(supplier);
            rs = newSupplier != null ? jsonResult.jsonSuccess(supplier): jsonResult.jsonSuccess("thêm nhà cung cấp thất bại");
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload supplier fail !");
        }
        response.getWriter().write(rs);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String partInfo = request.getPathInfo();
        String rs = "";
        if(partInfo.indexOf("/find-all") == 0) {
            try {
                List<Supplier> supplierList = supplierDao.findAll();
                rs = jsonResult.jsonSuccess(supplierList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find-all-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/so-ban-ghi") == 0){
            try{
                int count = supplierDao.count();
                rs = jsonResult.jsonSuccess(count);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("so-ban-ghi-error");
            }
            response.getWriter().write(rs);
        }
        else {
            response.sendError(404, "URL is not supported");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try {
            Supplier supplier = new Gson().fromJson(req.getReader(), Supplier.class);
            rs = jsonResult.jsonSuccess(supplierDao.update(supplier));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update supplier fail");
        }
        resp.getWriter().write(rs);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            int id =Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(supplierDao.delete(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            rs = jsonResult.jsonFail("delete supplier fail");
        }
        resp.getWriter().write(rs);
    }
}
