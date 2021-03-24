package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.dao.CustomerDao;
import haluonghoai.dao_impl.CustomerDao_impl;
import haluonghoai.modal.Category;
import haluonghoai.modal.Customer;
import haluonghoai.modal.JsonResult;
import haluonghoai.modal.MyConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerController", urlPatterns = "/api/v1/customer/*")
public class CustomerController extends HttpServlet {

    private  CustomerDao customerDao = new CustomerDao_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String partInfo = request.getPathInfo();
        String rs = "";
        if(partInfo.indexOf("/find-all") == 0) {//Hiển thị danh sách khách hàng
            try {
                List<Customer> customerList = customerDao.findAll();
                rs = jsonResult.jsonSuccess(customerList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find-all-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/so-ban-ghi") == 0){//Hiển thị số bản ghi
            try{
                int count = customerDao.count();
                rs = jsonResult.jsonSuccess(count);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("so-ban-ghi-error");
            }
            response.getWriter().write(rs);
        } else if (partInfo.indexOf("/search-customer-name") == 0){
            try {
                String username = request.getParameter("username");
                List<Customer> list = customerDao.searchByUsername(username);
                rs = jsonResult.jsonSuccess(list);

            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("seach-customer-name-fail");
            }
            response.getWriter().write(rs);
        } else if(partInfo.indexOf("/search-customer")==0){// tìm kiếm khách hàng trong trang admin
            try {
                String name = request.getParameter("name");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");
                String adress = request.getParameter("adress");
                List<Customer> list = customerDao.search(name,phoneNumber,email,adress);
                rs = jsonResult.jsonSuccess(list);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("seach-customer-fail");
            }
            response.getWriter().write(rs);
        }
        else {
            response.sendError(404, "URL is not supported");
        }
    }
}
