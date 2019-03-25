package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.PracticeInforModel;
import com.wsq.AssistantQ.respository.PracticeInforRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 23:15
 */
@Service
public class PracticeInforService extends BaseService {
    @Autowired
    private PracticeInforRepository practiceInforRepository;
    @Autowired
    private BaseService baseService;

    //增
    public void add(PracticeInforModel traineeInformationModel){
        if(traineeInformationModel.getStartDate().compareTo(traineeInformationModel.getEndDate()) < 0){
            baseService.add(practiceInforRepository,traineeInformationModel);
            practiceInforRepository.save(traineeInformationModel);
        }
        else {
            throw new MyException(ResultEnum.ERROR_112);
        }

    }

    //删
    @Transactional
    public void delete(PracticeInforModel traineeInformationModel){
        PracticeInforModel traineeInformationItem = practiceInforRepository.findById(traineeInformationModel.getObjectId());
        if (traineeInformationItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(practiceInforRepository, traineeInformationItem);
        }

    }

    //改
    public void update(PracticeInforModel traineeInformationModel){
        PracticeInforModel traineeInformationItem = practiceInforRepository.findById(traineeInformationModel.getObjectId());
        if (traineeInformationItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            practiceInforRepository.save(traineeInformationModel);
        }
    }

    //查询所有实习生信息
    public List<PracticeInforModel> findALLPracticeInfor(){
        List<PracticeInforModel> list = practiceInforRepository.findALLPracticeInfor();
        return list;
    }

    //学号查询所有实习生信息
    public List<PracticeInforModel> findByStuNum(String stuNumber){
        List<PracticeInforModel> list = practiceInforRepository.findByStuNum(stuNumber);
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> search(String stuName,String stuClass){
        List<Object[]> list = practiceInforRepository.search(stuName,stuClass);
        return list;
    }

    //视图--学号查询实习生信息
    public List<Object[]> findByStuNumber(String stuNumber){
        List<Object[]> list = practiceInforRepository.findByStuNumber(stuNumber);
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> searchAdmin(String stuName,String stuClass){
        List<Object[]> list = practiceInforRepository.searchAdmin(stuName,stuClass);
        return list;
    }

    //视图--查询所有实习生信息
    public List<Object[]> findAllPracticeInformation(){
        List<Object[]> list = practiceInforRepository.findAllPractice();
        return list;
    }

    //视图--查询所有实习生信息
    public List<Object[]> findMaxPractice(){
        List<Object[]> list = practiceInforRepository.findMaxPractice();
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> searchMaxPractice(String stuName,String stuClass,String stuMajor,String stuNumber){
        List<Object[]> list = practiceInforRepository.searchMaxPractice(stuName,stuClass,stuMajor,stuNumber);
        return list;
    }


}
