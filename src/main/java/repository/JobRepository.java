package repository;

import config.MysqlConfig;
import javax.script.*;
import model.JobModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobRepository {
    public List<JobModel> getAllJob() {
        Connection connection = null;
        List<JobModel> jobModelList = new ArrayList<>();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "select * from jobs";
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
                JobModel jobModel = new JobModel();
                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStart_date(resultSet.getDate("start_date"));
                jobModel.setEnd_date(resultSet.getDate("end_date"));

                jobModelList.add(jobModel);
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
        return jobModelList;
    }

    public JobModel getJobDetails(int id) {
        Connection connection = null;
        JobModel jobModel = new JobModel();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "select * from jobs where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//          Truyền tham số cho dấu chấm hỏi ( ? ) trong câu query
            statement.setInt(1, id);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while (resultSet.next()) {
                //Duyệt từng dòng dữ liệu
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStart_date((resultSet.getDate("start_date")));
                jobModel.setEnd_date(resultSet.getDate("end_date"));

            }

        } catch (Exception e) {
            System.out.println("Error getJibDetails" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối getJobDetails" + e.getMessage());
                }
            }
        }
        return jobModel;
    }
    public Boolean editJob(int id, String name, String start_date, String end_date) {
        Connection connection = null;
        boolean isSuccess = false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "update jobs set name=?, start_date=?, end_date=? where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//            SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
//            Date start_datesql=format.parse(start_date);
//            Date end_datesql=format.parse(end_date);
            statement.setString(1, name);
            statement.setDate(2, java.sql.Date.valueOf(start_date));
            statement.setDate(3, java.sql.Date.valueOf(end_date));
            statement.setInt(4, id);
            isSuccess = statement.executeUpdate() > 0;


        } catch (Exception e) {
            System.out.println("Error editJob" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối editJob " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public boolean deleteJob(int id){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "delete from jobs where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error deleteJob" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối deleteJob" + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public Boolean addJob(String name, String start_date, String end_date){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "insert into jobs(name, start_date, end_date) value(?,?,?)";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setDate(2, java.sql.Date.valueOf(start_date));
            statement.setDate(3, java.sql.Date.valueOf(end_date));
            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error addJob" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối addJob" + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
}
