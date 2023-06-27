package repository;

import config.MysqlConfig;
import model.TaskDBModel;
import model.TaskModel;

import javax.swing.plaf.PanelUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskDBRepository {
    public TaskDBModel getTaskDBById(int id){
            Connection connection = null;
        TaskDBModel taskModel = new TaskDBModel();

//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
            try {
                String sql = "SELECT * FROM tasks WHERE id=?";
                connection = MysqlConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,id);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
                ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
                while (resultSet.next()) {
                    //Duyệt từng dòng dữ liệu

                    SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

                    //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                    taskModel.setId(resultSet.getInt("id"));
                    taskModel.setName((resultSet.getString("name")));
                    taskModel.setStart_date(resultSet.getDate("start_date"));
                    taskModel.setEnd_date(resultSet.getDate("end_date"));
                    taskModel.setUser_id(resultSet.getInt("user_id"));
                    taskModel.setJob_id(resultSet.getInt("job_id"));
                    taskModel.setStatus_id(resultSet.getInt("status_id"));

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
            return taskModel;
        }
        public boolean editTaskDB(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id){
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
        public boolean deleteTaskDBById(int id){
            Connection connection=null;
            boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
            try{
                String sql = "delete from tasks where id=?";
                connection = MysqlConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);

                isSuccess=statement.executeUpdate()>0;


            }catch (Exception e){
                System.out.println("Error deletetaskDBById" + e.getMessage());
            }finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        System.out.println("Lỗi đóng kết nối deletetaskDBById " + e.getMessage());
                    }
                }
            }
            return isSuccess;
        }
    public List<TaskDBModel> getTaskDBByUserId(int id){
        Connection connection = null;
        List<TaskDBModel> taskDBModelList=new ArrayList<>();

//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "SELECT * FROM tasks WHERE user_id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while (resultSet.next()) {
                //Duyệt từng dòng dữ liệu
                TaskDBModel taskModel = new TaskDBModel();
                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName((resultSet.getString("name")));
                taskModel.setStart_date(resultSet.getDate("start_date"));
                taskModel.setEnd_date(resultSet.getDate("end_date"));
                taskModel.setUser_id(resultSet.getInt("user_id"));
                taskModel.setJob_id(resultSet.getInt("job_id"));
                taskModel.setStatus_id(resultSet.getInt("status_id"));
                taskDBModelList.add(taskModel);
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
        return taskDBModelList;
    }
}
