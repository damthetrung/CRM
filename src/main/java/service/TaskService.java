package service;

import model.TaskDBModel;
import model.TaskModel;
import repository.TaskRepository;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepository=new TaskRepository();
    public List<TaskModel> getAllTask(){
        return taskRepository.getAllTask();
    }
    public boolean addTask(String jobname, String start_date, String end_date, int user_id, int job_id){
        return taskRepository.addTask(jobname, start_date, end_date, user_id, job_id);
    }
    public boolean editTask(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id){
        return taskRepository.editTask(id, name, start_date, end_date, user_id, job_id, status_id);
    }

}
