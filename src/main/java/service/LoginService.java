package service;

import model.UserModel;
import repository.UserRepository;

import java.util.List;

public class LoginService {
    private UserRepository userRepository=new UserRepository();
    public UserModel checkLogin(String email, String password){
        UserModel userModel=userRepository.findByEmailAndPassword(email, password);
        return userModel;
    }
}
