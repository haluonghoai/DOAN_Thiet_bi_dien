package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.dao.OrderDao;
import haluonghoai.dao_impl.OrderDao_impl;
import haluonghoai.modal.Category;
import haluonghoai.modal.JsonResult;
import haluonghoai.modal.Order;
import haluonghoai.modal.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderController", urlPatterns = "/api/v1/order/*")
public class OrderController extends HttpServlet {

    private OrderDao orderDao = new OrderDao_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String partInfo = request.getPathInfo();
        String rs = "";
        if(partInfo.indexOf("/find-all") == 0) {
            try {
                List<Order> orderList = orderDao.findAll();
                rs = jsonResult.jsonSuccess(orderList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find-all-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/so-ban-ghi") == 0){
            try{
                int count = orderDao.count();
                rs = jsonResult.jsonSuccess(count);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("so-ban-ghi-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/find-by-hinh-thuc-thanh-toan")==0){//Hiển thị danh sách đơn hàng theo hình thức thanh toán trong trang admin, phần select hình thức thanh toán
            try{
                boolean payments = Boolean.parseBoolean(request.getParameter("payments"));
                List<Order> orderList = orderDao.selectOrderByPayments(payments);
                rs = jsonResult.jsonSuccess(orderList);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-by-hinh-thuc-thanh-toan-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/find-by-trang-thai-thanh-toan")==0){//Hiển thị sanh sách đơn hàng theo trạng thái thanh toán
            try{
                boolean statusPayments = Boolean.parseBoolean(request.getParameter("statusPayments"));
                List<Order> orderList = orderDao.selectOrderByStatusPayments(statusPayments);
                rs = jsonResult.jsonSuccess(orderList);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-by-trang-thai-thanh-toan-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/find-by-trang-thai-don-hang")==0){//Hiển thị danh sách đơn hàng theo trạng thái đơn hàng
            try{
                int idStatusOrder = Integer.parseInt(request.getParameter("id"));
                List<Order> orderList = orderDao.selectOrderByStatusOrder(idStatusOrder);
                rs = jsonResult.jsonSuccess(orderList);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-by-trang-thai-don-hang-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/find-by-id")==0){//Tìm kiếm đơn hàng
            try{
                int id = Integer.parseInt(request.getParameter("id"));
                Order order = orderDao.findById(id);
                rs = jsonResult.jsonSuccess(order);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-by-find-by-id-error");
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
        String partInfo = req.getPathInfo();
        if(partInfo.indexOf("/update-by-status-payments")==0){//Cập nhật trạng thái thanh toán phần select chọn đã thanh toán hoặc chưa thanh toán nhé
            try {
                Order order = new Gson().fromJson(req.getReader(), Order.class);
                rs = jsonResult.jsonSuccess(orderDao.updateStatusPayment(order.isStatusPaments(),order.getId()));
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("update order fail");
            }
            resp.getWriter().write(rs);
        }else if(partInfo.indexOf("/update-by-status-order")==0){//Cập nhật trạng thái đơn hàng
            try {
                Order order = new Gson().fromJson(req.getReader(), Order.class);
                rs = jsonResult.jsonSuccess(orderDao.updateStatusOrder(order.getIdOrderstatus(),order.getId()));
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("update order fail");
            }
            resp.getWriter().write(rs);
        }else{
            resp.sendError(404, "URL is not supported");
        }
    }

}
