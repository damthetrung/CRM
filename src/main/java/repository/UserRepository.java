package repository;

import config.MysqlConfig;
import model.UserModel;

import javax.servlet.http.Cookie;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public UserModel findByEmailAndPassword(String email, String password){
        Connection connection=null;
        UserModel userModel = new UserModel();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "select * from users u where u.email = ? and u.password = ?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//          Truyền tham số cho dấu chấm hỏi ( ? ) trong câu query
            statement.setString(1,email);
            statement.setString(2,password);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setRoleId(resultSet.getInt("role_id"));
            }

        }catch (Exception e){
            System.out.println("Error findByEmailAndPassword" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }
        return userModel;
    }
    public List<UserModel> getAllUser(){
        Connection connection=null;
        List<UserModel> userModelList=new ArrayList<>();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "select * from users";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                UserModel userModel = new UserModel();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setRoleId(resultSet.getInt("role_id"));

                userModelList.add(userModel);
            }

        }catch (Exception e){
            System.out.println("Error getAllUser" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối getAllUser " + e.getMessage());
                }
            }
        }
        return userModelList;
    }
    public Boolean insertUser(String fullname, String email, String password, int role_id){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "insert into users(email, password, fullname, role_id) value(?,?,?,?)";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setInt(4, role_id);
            isSuccess=statement.executeUpdate()>0;


    }catch (Exception e){
            System.out.println("Error insertUser" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối insertUser " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public boolean deleteUserById(int id){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "delete from users where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error deleteUserById" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối deleteUserById " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public UserModel getUserDetail(int id) {
        Connection connection=null;
        UserModel userModel=new UserModel();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "select * from users where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//          Truyền tham số cho dấu chấm hỏi ( ? ) trong câu query
            statement.setInt(1,id);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setRoleId(resultSet.getInt("role_id"));

            }

        }catch (Exception e){
            System.out.println("Error getUserDetail" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối getUserDetail" + e.getMessage());
                }
            }
        }
        return userModel;
    }
    public Boolean editUser(int id, String fullname, String email, String password, int role_id) {
        Connection connection = null;
        boolean isSuccess = false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "update users set fullname=?, email=?, password=?, role_id=? where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fullname);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, role_id);
            statement.setInt(5, id);
            isSuccess = statement.executeUpdate() > 0;


        } catch (Exception e) {
            System.out.println("Error edittUser" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối edittUser " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

}
