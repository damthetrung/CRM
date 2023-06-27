package repository;

import config.MysqlConfig;
import model.JobModel;
import model.TaskModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    public List<TaskModel> getAllTask() {
        Connection connection = null;
        List<TaskModel> taskModelList = new ArrayList<>();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "SELECT t.id, t.name as taskname, j.name as jobname, u.fullname as username, t.start_date, t.end_date, s.name as statusname\n" +
                    "FROM tasks as t, jobs as j, users as u, status as s\n" +
                    "WHERE t.job_id = j.id\n" +
                    "AND   t.user_id =u.id \n" +
                    "AND   t.status_id =s.id ";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while (resultSet.next()) {
                //Duyệt từng dòng dữ liệu
                TaskModel taskModel = new TaskModel();
                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setTaskName((resultSet.getString("taskname")));
                taskModel.setJobName(resultSet.getString("jobname"));
                taskModel.setUserName(resultSet.getString("username"));
                taskModel.setStart_date(resultSet.getDate("start_date"));
                taskModel.setEnd_date(resultSet.getDate("end_date"));
                taskModel.setStatus(resultSet.getString("statusname"));
                taskModelList.add(taskModel);
            }

        } catch (Exception e) {
            System.out.println("Error getAllJob" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối getAllJob " + e.getMessage());
                }
            }
        }
        return taskModelList;
    }
    public Boolean addTask(String jobName, String start_date, String end_date, int user_id, int job_id){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "insert into tasks(name, start_date, end_date, user_id, job_id, status_id) value(?,?,?,?,?,?)";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, jobName);
            statement.setDate(2, java.sql.Date.valueOf(start_date));
            statement.setDate(3, java.sql.Date.valueOf(end_date));
            statement.setInt(4,user_id );
            statement.setInt(5, job_id);
            statement.setInt(6, 1);
            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error addTask" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối addTask" + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public boolean editTask(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id){
            Connection connection=null;
            boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
            try{
                String sql = "update tasks set name=?, start_date=?, end_date=?, user_id=?, job_id=?, status_id=? where id=?";
                connection = MysqlConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setDate(2, java.sql.Date.valueOf(start_date));
                statement.setDate(3, java.sql.Date.valueOf(end_date));
                statement.setInt(4,user_id );
                statement.setInt(5, job_id);
                statement.setInt(6, status_id);
                statement.setInt(7, id);
                isSuccess=statement.executeUpdate()>0;


            }catch (Exception e){
                System.out.println("Error editTask" + e.getMessage());
            }finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        System.out.println("Lỗi đóng kết nối editTask" + e.getMessage());
                    }
                }
            }
            return isSuccess;
    }

}
