package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.modal.Category;
import haluonghoai.modal.JsonResult;
import haluonghoai.service.CategoryService;
import haluonghoai.service_impl.CategoryService_impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", urlPatterns = "/api/v1/category/*")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService = new CategoryService_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs ="";
        try {
            Category category = new Gson().fromJson(request.getReader(), Category.class);
            Category newCategory = categoryService.insert(category.getName());
            rs = newCategory != null ? jsonResult.jsonSuccess(category): jsonResult.jsonSuccess("thêm danh mục thất bại");
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload category fail !");
        }
        response.getWriter().write(rs);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String partInfo = request.getPathInfo();
        String rs = "";
        if(partInfo.indexOf("/find-all") == 0) {
            try {
                List<Category> categoryList = categoryService.findAll();
                rs = jsonResult.jsonSuccess(categoryList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find-all-error");
            }
            response.getWriter().write(rs);
        }else if(partInfo.indexOf("/so-ban-ghi") == 0){
            try{
                int count = categoryService.count();
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
            Category category = new Gson().fromJson(req.getReader(), Category.class);
            rs = jsonResult.jsonSuccess(categoryService.update(category));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update category fail");
        }
        resp.getWriter().write(rs);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            int id =Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(categoryService.delete(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            rs = jsonResult.jsonFail("delete category fail");
        }
        resp.getWriter().write(rs);
    }
}
