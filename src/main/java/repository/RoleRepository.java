package repository;

import config.MysqlConfig;
import model.RoleModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    public List<RoleModel> getAllRole(){
        Connection connection=null;
        List<RoleModel> listRole=new ArrayList<>();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "select * from roles";
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
                RoleModel roleModel = new RoleModel();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDesc(resultSet.getString("description"));

                listRole.add(roleModel);
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
        return listRole;
    }
    public boolean addRole(String name, String desc){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "insert into roles(name, description) value(?,?)";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, desc);
            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error addRole" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối addRole " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public RoleModel getRole(int id){
        Connection connection=null;
        RoleModel roleModel=new RoleModel();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "select * from roles where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();

//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDesc(resultSet.getString("description"));
            }

        }catch (Exception e){
            System.out.println("Error getRole" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối getRole " + e.getMessage());
                }
            }
        }
        return roleModel;
    }
    public boolean editRole(int id, String name, String desc){
        Connection connection = null;
        boolean isSuccess = false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "update roles set name=?, description=? where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, desc);
            statement.setInt(3, id);
            isSuccess = statement.executeUpdate() > 0;


        } catch (Exception e) {
            System.out.println("Error editRole" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối editRole " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
    public boolean deleteRole(int id){
        Connection connection=null;
        boolean isSuccess=false;


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            String sql = "delete from roles where id=?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            isSuccess=statement.executeUpdate()>0;


        }catch (Exception e){
            System.out.println("Error deleteRole" + e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Lỗi đóng kết nối deleteRole " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
}
