package controller;

import model.JobModel;
import model.RoleModel;
import model.UserModel;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/groupwork", "/groupwork-details", "/groupwork-edit", "/groupwork-delete", "/groupwork-add"})
public class GroupworkController extends HttpServlet {
    private JobService jobService=new JobService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/groupwork":
                getAllJob(req, resp);
                break;
            case "/groupwork-details":
                getDetailsJob(req, resp);
                break;
            case "/groupwork-edit":
                editJob(req, resp);
                break;
            case  "/groupwork-add":
                try {
                    addJob(req, resp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/groupwork-edit":
                editJob(req, resp);
                break;
            case "/groupwork-delete":
                deleteJob(req, resp);
                break;
            case "/groupwork-add":
                try {
                    addJob(req, resp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public void getAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<JobModel> jobModelList=jobService.getAllJob();
    req.setAttribute("jobList", jobModelList);
    req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
    }
    public void getDetailsJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        JobModel jobModel=jobService.getDetailsJob(id);
        req.setAttribute("jobModel", jobModel);
        req.getRequestDispatcher("groupwork-details.jsp").forward(req, resp);
    }
    public void editJob(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        int id= Integer.parseInt(req.getParameter("id"));
        String method=req.getMethod();
        if(method.toLowerCase().equals("post")){
            String name = req.getParameter("name");
            String start_date = req.getParameter("start_date");
            String end_date = req.getParameter("end_date");
            jobService.editJob(id, name, start_date, end_date);
        }
        JobModel jobModel=jobService.getDetailsJob(id);
        req.setAttribute("jobdetails", jobModel);
        req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
    }
    public void deleteJob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        boolean isSuccess=jobService.deleteJob(id);
        String contextPath=req.getContextPath();
        resp.sendRedirect(contextPath+"/groupwork");
    }
    private void addJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getMethod();
        if(method.toLowerCase().equals("post")){
            String name = req.getParameter("name");
            String start_date=req.getParameter("start_date");
            String end_date=req.getParameter("end_date");
            jobService.addJob(name, start_date, end_date);
        }
        req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
    }
}
