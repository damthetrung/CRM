package service;


import model.TaskDBModel;
import repository.TaskDBRepository;

import java.util.List;

public class TaskDBService {
    private TaskDBRepository taskDBRepository=new TaskDBRepository();
    public TaskDBModel getTaskDBById(int id){
        return taskDBRepository.getTaskDBById(id);
    }
    public boolean editTaskDB(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id){
        return taskDBRepository.editTaskDB(id, name, start_date, end_date, user_id, job_id, status_id);
    }
    public boolean deleteTaskById(int id){
        return taskDBRepository.deleteTaskDBById(id);
    }
    public List<TaskDBModel> getTaskDBByUserId(int id){
        return taskDBRepository.getTaskDBByUserId(id);
    }
}
