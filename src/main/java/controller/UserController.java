package controller;

import model.*;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user-table", "/user-add", "/user-delete", "/user-details", "/user-edit", "/user-profile"})
public class UserController extends HttpServlet {
    private UserService userService=new UserService();
    private RoleService roleService=new RoleService();
    private TaskService taskService=new TaskService();
    private TaskDBService taskDBService=new TaskDBService();
    private JobService jobService= new JobService();
    private StatusService statusService=new StatusService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user-table":
                getAllUser(req, resp);
                break;
            case "/user-add":
                userAdd(req, resp);
                break;
            case "/user-details":
                userDetails(req, resp);
                break;
            case "/user-edit":
                userEdit(req, resp);
                break;
            case "/user-profile":
                userProfile(req, resp);
                break;
            case "/user-delete":
                userDelete(req, resp);
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user-add":
                userAdd(req, resp);
                break;
            case "/user-edit":
                userEdit(req, resp);
                break;
            default:

        }
    }



    private void getAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> list=userService.getAllUser();
        req.setAttribute("listUser", list);
        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
    }
    private void userAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getMethod();
        List<RoleModel> listRole=userService.getAllRole();
        if(method.toLowerCase().equals("post")){
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int role_id= Integer.parseInt(req.getParameter("role"));
            userService.insertUser(fullname, email, password, role_id);
        }
        req.setAttribute("listRole", listRole);
        req.getRequestDispatcher("user-add.jsp").forward(req, resp);
    }
    private void userDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        boolean isSuccess=userService.deleteUserById(id);
        resp.sendRedirect(req.getContextPath()+"/user-table");
    }
    private void userDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        UserModel userModel=userService.getUserDetail(id);
        List<TaskDBModel> tasklist=taskDBService.getTaskDBByUserId(id);
        List<JobModel> joblist=jobService.getAllJob();
        List<StatusModel> statuslist=statusService.getAllStatus();
        req.setAttribute("userdetails", userModel);
        req.setAttribute("tasklist", tasklist);
        req.setAttribute("joblist", joblist);
        req.setAttribute("statuslist", statuslist);
        req.getRequestDispatcher("user-details.jsp").forward(req, resp);
    }
    private void userEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String method = req.getMethod();
        if (method.toLowerCase().equals("post")) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int role_id = Integer.parseInt(req.getParameter("role"));
            userService.editUser(id, fullname, email, password, role_id);
        } else {
            UserModel userModel = userService.getUserDetail(id);
            List<RoleModel> listRole = roleService.getAllRole();
            req.setAttribute("userdetails", userModel);
            req.setAttribute("listRole", listRole);
            req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
        }
    }
    private void userProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        int user_id= (int) session.getAttribute("user_id");
        UserModel userModel=userService.getUserDetail(user_id);
        req.setAttribute("usermodel", userModel);
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}
