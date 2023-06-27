package controller;

import config.MysqlConfig;
import model.UserModel;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "loginController",urlPatterns = {"/login", "/logout", "/blank", "/404"})
public class LoginController extends HttpServlet {
    private LoginService loginService=new LoginService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path=req.getServletPath();
    switch (path){
        case "/login":
            login(req,resp);
            break;
        case "/logout":
            logout(req, resp);
            break;
        case "/blank":
            blank(req, resp);
            break;
        case "/404":
            notFound(req, resp);
            break;

    }
        //Cookie
//        Cookie cookie = new Cookie("username","nguyenvana");
//        //yêu cầu client khởi tạo cookie
//        resp.addCookie(cookie);
//        Cookie[] cookies = req.getCookies();
////        [ Cookie("name","value"),Cookie("name","value"),Cookie("name","value")]
//        for (Cookie item : cookies) {
//            if(item.getName().equals("username")){
//                System.out.println("Kiem tra " + item.getValue());
//            }
//        }

//        HttpSession session = req.getSession();
//        session.setAttribute("password","123456");
//
//        System.out.println("Session : " + session.getAttribute("password"));

//        Cookie[] cookies = req.getCookies();
//        String userName = "";
//        String password = "";
//
//        for (Cookie item : cookies) {
//            if(item.getName().equals("username")){
//                userName = item.getValue();
//            }
//
//            if(item.getName().equals("password")){
//                password = item.getValue();
//            }
//        }
//
//        req.setAttribute("username",userName);
//        req.setAttribute("password",password);

    }

    /**
     * Tạo ra checkbox nhớ mật khẩu, khi người dùng click chọn
     * checkbox thì khi đăng nhập thành công thì sẽ lưu lại giá trị
     * đăng nhập là email, password
     *
     * Khi quay lại màn hình login thì tự động điền email và password
     * vào
     */

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/login":
                login(req, resp);
                break;


        }

    }
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getMethod();
        if(method.toLowerCase().equals("post")){
            String email = req.getParameter("username");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");
            UserModel userModel=loginService.checkLogin(email, password);
            if(userModel!=null){
                HttpSession session=req.getSession();
                session.setAttribute("fullname", userModel.getFullname());
                session.setAttribute("role_id", userModel.getRoleId());
                session.setAttribute("isSuccess", true);
                session.setAttribute("user_id", userModel.getId());
                String contextPath=req.getContextPath();
                resp.sendRedirect(contextPath+"/user-profile");
            }else {
                PrintWriter printWriter=resp.getWriter();
                printWriter.write("Login fail!");
                printWriter.close();
            }
        }else {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session=req.getSession();
        session.setAttribute("isSuccess", null);
        session.setAttribute("role_id", 0);
        String contextPath=req.getContextPath();
        resp.sendRedirect(contextPath+"/login");
        //req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
    public void blank(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("blank.jsp").forward(req, resp);
    }
    public void notFound(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("404.jsp").forward(req, resp);
    }
}
