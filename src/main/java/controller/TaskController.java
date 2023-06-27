package controller;


import model.*;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/task", "/task-add", "/task-edit", "/task-delete"})
public class TaskController extends HttpServlet {
    private TaskService taskService=new TaskService();
    private JobService jobService=new JobService();
    private UserService userService=new UserService();
    private StatusService statusService=new StatusService();
    private TaskDBService taskDBService=new TaskDBService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/task":
                getAllTask(req, resp);
                 break;
            case "/task-add":
                addTask(req, resp);
                break;
            case "/task-edit":
                editTask(req, resp);
                break;
            case "/task-delete":
                deleteTask(req,resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        switch (path){
            case "/task-add":
                addTask(req, resp);
                break;
            case "/task-edit":
                editTask(req, resp);
                break;

        }
    }

    public void getAllTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> taskModelList=taskService.getAllTask();
        req.setAttribute("taskList", taskModelList);
        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }
    public void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getMethod();
        List<JobModel> jobModelList=jobService.getAllJob();
        List<UserModel> userModelList=userService.getAllUser();
        List<StatusModel> statusModelLis=statusService.getAllStatus();
        if(method.toLowerCase().equals("post")){
            String taskName=req.getParameter("taskName");
            String start_date=req.getParameter("start_date");
            String end_date=req.getParameter("end_date");
            int user_id= Integer.parseInt(req.getParameter("user_id"));
            int job_id= Integer.parseInt(req.getParameter("job_id"));
            taskService.addTask(taskName, start_date, end_date, user_id, job_id);
        }
        req.setAttribute("jobList", jobModelList);
        req.setAttribute("userList", userModelList);
        req.setAttribute("statusList", statusModelLis);
        req.getRequestDispatcher("task-add.jsp").forward(req, resp);
    }
    public void editTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id= Integer.parseInt(req.getParameter("id"));
        String method=req.getMethod();
        List<JobModel> jobModelList=jobService.getAllJob();
        List<UserModel> userModelList=userService.getAllUser();
        List<StatusModel> statusModelLis=statusService.getAllStatus();

        if(method.toLowerCase().equals("post")){
            String name = req.getParameter("taskName");
            String start_date = req.getParameter("start_date");
            String end_date= req.getParameter("end_date");
            int user_id= Integer.parseInt(req.getParameter("user_id"));
            int job_id= Integer.parseInt(req.getParameter("job_id"));
            int status_id= Integer.parseInt(req.getParameter("status_id"));
            taskDBService.editTaskDB(id, name, start_date,end_date, user_id, job_id, status_id);
        }
        TaskDBModel taskModel=taskDBService.getTaskDBById(id);
        req.setAttribute("jobList", jobModelList);
        req.setAttribute("userList", userModelList);
        req.setAttribute("statusList", statusModelLis);
        req.setAttribute("taskDB", taskModel);
        req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
    }
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id= Integer.parseInt(req.getParameter("id"));
        boolean isSuccess=taskDBService.deleteTaskById(id);
        String contextPath=req.getContextPath();
        resp.sendRedirect(contextPath+"/task");
    }
}
