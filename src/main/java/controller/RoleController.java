package controller;

import model.RoleModel;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(urlPatterns = {"/role-table", "/role-add", "/role-edit", "/role-delete"})
public class RoleController extends HttpServlet {
    private RoleService roleService=new RoleService();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/role-table":
                getAllRole(req, resp);
                break;
            case "/role-add":
                roleAdd(req, resp);
                break;
            case "/role-edit":
                roleEdit(req, resp);
                break;
            case "/role-delete":
                roleDelete(req, resp);
                break;

        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/role-add":
                roleAdd(req, resp);
                break;
            case "/role-edit":
                roleEdit(req, resp);
                break;


        }
    }

    public void getAllRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleModel> listRole=roleService.getAllRole();
        req.setAttribute("listRole", listRole);
        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
    }
    public void roleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getMethod();
        if(method.toLowerCase().equals("post")){
            String name = req.getParameter("rolename");
            String desc = req.getParameter("roledesc");
            roleService.addRole(name, desc);
            String contextPath=req.getContextPath();
            resp.sendRedirect(contextPath+"/role-table");
        }else {
            req.getRequestDispatcher("role-add.jsp").forward(req, resp);
        }


    }
    public void roleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getMethod();
        int id= Integer.parseInt(req.getParameter("id"));

        if(method.toLowerCase().equals("post")){
            String name=req.getParameter("name");
            String desc=req.getParameter("desc");
            roleService.editRole(id,name,desc);
        }
            RoleModel roleModel=roleService.getRole(id);
            req.setAttribute("role", roleModel);
            req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
    }
    public void roleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        boolean isSuccess=roleService.deleteRole(id);
        resp.sendRedirect(req.getContextPath()+"/role-table");
    }
}
