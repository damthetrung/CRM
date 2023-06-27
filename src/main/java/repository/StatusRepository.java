package repository;

import config.MysqlConfig;
import model.JobModel;
import model.StatusModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository {
    public List<StatusModel> getAllStatus(){
        Connection connection = null;
        List<StatusModel> statusModelList = new ArrayList<>();


//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try {
            String sql = "select * from status";
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
                StatusModel statusModel = new StatusModel();
                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));


                statusModelList.add(statusModel);
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
        return statusModelList;
    }
}
