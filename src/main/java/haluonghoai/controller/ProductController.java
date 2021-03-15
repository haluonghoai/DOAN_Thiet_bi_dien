package haluonghoai.controller;

import com.google.gson.Gson;
import haluonghoai.dao.ProductDao;
import haluonghoai.dao_impl.ProductDao_impl;
import haluonghoai.modal.JsonResult;
import haluonghoai.modal.Product;
import haluonghoai.service.ProductService;
import haluonghoai.service_impl.ProductService_impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = "/api/v1/product/*")
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductService_impl();

    private ProductDao productDao = new ProductDao_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = "";
        try{
            Product product = new Gson().fromJson(request.getReader(),Product.class);
            Product productnew = productService.insert(product);
            rs = productnew != null ? jsonResult.jsonSuccess(productnew) : jsonResult.jsonSuccess("Thêm sản phẩm thất bại");
        }catch (Exception ex){
            ex.printStackTrace();
            rs = jsonResult.jsonFail("upload fail");
        }
        response.getWriter().write(rs);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = "";
        String pathInfo = request.getPathInfo();
        if(pathInfo.indexOf("/find-all")==0){
            try{
                List<Product> list = productService.findAll();
                rs = jsonResult.jsonSuccess(list);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-all-fail");
            }
            response.getWriter().write(rs);
        }else if(pathInfo.indexOf("/so-ban-ghi")==0){
            try{
                int sobanghi = productService.count();
                rs = jsonResult.jsonSuccess(sobanghi);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("so-ban-ghi fail");
            }
            response.getWriter().write(rs);
        }else if(pathInfo.indexOf("/search-san-pham")==0){
            try{
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                float price = Float.parseFloat(request.getParameter("price"));
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                List<Product> list = productService.search(id,name,price,status);
                rs = jsonResult.jsonSuccess(list);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("search-san-pham-fail");
            }
            response.getWriter().write(rs);
        }else if(pathInfo.indexOf("/find-by-category")==0) {
            try {
                int idCategory = Integer.parseInt(request.getParameter("idCategory"));
                List<Product> list = productService.findByCategory(idCategory);
                rs = jsonResult.jsonSuccess(list);
            } catch (Exception ex) {
                ex.printStackTrace();
                rs = jsonResult.jsonFail("find-by-category-fail");
            }
            response.getWriter().write(rs);
        }else if(pathInfo.indexOf("/search-by-name")==0){
            try{
                String name = request.getParameter("name");
                List<Product> list = productDao.searchProductByName(name);
                rs = jsonResult.jsonSuccess(list);
            }catch (Exception ex){
                ex.printStackTrace();
                rs = jsonResult.jsonFail("search-by-name-fail");
            }
            response.getWriter().write(rs);
        }
        else{
            response.sendError(404,"Url is not suported");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try {
            Product product = new Gson().fromJson(req.getReader(), Product.class);
            rs = jsonResult.jsonSuccess(productService.update(product));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update product fail");
        }
        resp.getWriter().write(rs);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            int id =Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(productService.delete(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            rs = jsonResult.jsonFail("delete category fail");
        }
        resp.getWriter().write(rs);
    }
}
