package service;

import model.JobModel;
import repository.JobRepository;

import java.util.Date;
import java.util.List;

public class JobService {
    private JobRepository jobRepository=new JobRepository();
    public List<JobModel> getAllJob(){
        return jobRepository.getAllJob();
    }
    public JobModel getDetailsJob(int id){
        return jobRepository.getJobDetails(id);
    }
    public boolean editJob(int id, String name, String start_date, String end_date){
        return jobRepository.editJob(id, name, start_date, end_date);
    }
    public boolean deleteJob(int id){
        return jobRepository.deleteJob(id);
    }
    public boolean addJob(String name, String start_date, String end_date){
        return  jobRepository.addJob(name, start_date, end_date);
    }
}
