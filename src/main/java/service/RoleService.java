package service;

import model.RoleModel;
import repository.RoleRepository;

import java.util.List;

public class RoleService {
    private RoleRepository roleRepository=new RoleRepository();
    public List<RoleModel> getAllRole(){
        return roleRepository.getAllRole();
    }
    public boolean addRole(String name, String desc){
        return roleRepository.addRole(name, desc);
    }
    public RoleModel getRole(int id){
        return roleRepository.getRole(id);
    }
    public boolean editRole(int id, String name, String desc){
        return roleRepository.editRole(id, name, desc);
    }
    public boolean deleteRole(int id){
        return roleRepository.deleteRole(id);
    }
 }
