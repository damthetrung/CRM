package service;

import model.RoleModel;
import model.UserModel;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository=new UserRepository();
    private RoleRepository roleRepository=new RoleRepository();
    public List<UserModel> getAllUser(){
        return userRepository.getAllUser();
    }
    public List<RoleModel> getAllRole(){
        return roleRepository.getAllRole();
    }
    public boolean insertUser(String fullname, String email, String password, int role_id){
        return userRepository.insertUser(fullname, email, password, role_id);
    }
    public boolean deleteUserById(int id){
        return userRepository.deleteUserById(id);
    }
    public UserModel getUserDetail(int id){
        return userRepository.getUserDetail(id);
    }
    public boolean editUser(int id, String fullname, String email, String password, int role_id){
        return userRepository.editUser(id, fullname, email, password, role_id);
    }
}
