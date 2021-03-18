package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.dao.UserDao;
import haluonghoai.dao_impl.UserDao_impl;
import haluonghoai.modal.JsonResult;
import haluonghoai.modal.Supplier;
import haluonghoai.modal.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = "/api/v1/user/*")
public class UserController extends HttpServlet {

    private UserDao userDao = new UserDao_impl();

    JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs ="";
        try {
            User user = new Gson().fromJson(request.getReader(),User.class);
            User newUser = userDao.insert(user);
            rs = newUser != null ? jsonResult.jsonSuccess(user): jsonResult.jsonSuccess("thêm người dùng thất bại");
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload user fail !");
        }
        response.getWriter().write(rs);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String partInfo = request.getPathInfo();
        String rs = "";
        if(partInfo.indexOf("/find-all") == 0) {
            try {
                List<User> userList = userDao.findAll();
                rs = jsonResult.jsonSuccess(userList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find-all-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/so-ban-ghi") == 0){
            try{
                int count = userDao.count();
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
            User user = new Gson().fromJson(req.getReader(), User.class);
            rs = jsonResult.jsonSuccess(userDao.update(user));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update user fail");
        }
        resp.getWriter().write(rs);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            int id =Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(userDao.delete(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            rs = jsonResult.jsonFail("delete user fail");
        }
        resp.getWriter().write(rs);
    }
}
