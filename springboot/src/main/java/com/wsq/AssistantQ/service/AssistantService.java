package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.AssistantModel;
import com.wsq.AssistantQ.repository.AssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:21
 */
@Service
public class AssistantService {
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private BaseService baseService;

    //新增课程信息
    public void add(AssistantModel assistantModel) {

        //添加基础信息 编号和创建时间
        baseService.add(assistantRepository, assistantModel);

        //保存信息至数据库
        assistantRepository.save(assistantModel);
    }

    //根据课程编号删除课程信息
    @Transactional
    public void deleteByObjectId(AssistantModel assistantModel) {
        AssistantModel assistantItem = assistantRepository.findByObjectId(assistantModel.getObjectId());
        if (assistantItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(assistantRepository, assistantItem);
        }
    }

    //动态修改课程信息
    @Transactional
    public void modify(AssistantModel assistantModel) {
        AssistantModel assistantItem = assistantRepository.findByObjectId(assistantModel.getObjectId());
        if (assistantItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(assistantModel.getAssiStudentId() !=null && assistantItem.getAssiStudentId().equals(assistantModel.getAssiStudentId()) == false ){
                assistantItem.setAssiStudentId(assistantModel.getAssiStudentId());
            }
            if(assistantModel.getAssiStudentName() !=null && assistantItem.getAssiStudentName().equals(assistantModel.getAssiStudentName()) == false ){
                assistantItem.setAssiStudentName(assistantModel.getAssiStudentName());
            }
            if(assistantModel.getAssiCourse() !=null && assistantItem.getAssiCourse().equals(assistantModel.getAssiCourse()) == false ){
                assistantItem.setAssiCourse(assistantModel.getAssiCourse());
            }
            if(assistantModel.getAssiTeacherId() !=null && assistantItem.getAssiTeacherId().equals(assistantModel.getAssiTeacherId()) == false ){
                assistantItem.setAssiTeacherId(assistantModel.getAssiTeacherId());
            }
            if(assistantModel.getAssiWork() !=null && assistantItem.getAssiWork().equals(assistantModel.getAssiWork()) == false ){
                assistantItem.setAssiWork(assistantModel.getAssiWork());
            }
            baseService.modify(assistantRepository,assistantItem);
            assistantRepository.save(assistantItem);
        }
    }

    //查找所有课程
    public List<AssistantModel> findAllAssistant(){
        List<AssistantModel> list = assistantRepository.findAllAssistant();
        return list;
    }

    //根据编号查找助教信息
    public AssistantModel findByObjectId(String objectId){
        AssistantModel assistantModel = assistantRepository.findByObjectId(objectId);
        return assistantModel;
    }

    //根据助教学生ID查找助教信息
    public List<AssistantModel> findByAssiStudentId(String assiStudentId){
        List<AssistantModel> list = assistantRepository.findByAssiStudentId(assiStudentId);
        return list;
    }

    //根据助教学生姓名查找助教信息
    public List<AssistantModel> findByAssiName(String assiStuName){
        List<AssistantModel> list = assistantRepository.findByAssiName(assiStuName);
        return list;
    }

    //根据助教课程查找助教信息
    public List<AssistantModel> findByAssiCourse(String assiCourse){
        List<AssistantModel> list = assistantRepository.findByAssiCourse(assiCourse);
        return list;
    }

    //根据助教教师ID查找助教信息
    public List<AssistantModel> findByAssiTeacherId(String assiTeacherId){
        List<AssistantModel> list = assistantRepository.findByAssiTeacherId(assiTeacherId);
        return list;
    }

    //根据学生ID和课程名称查询报名信息
    public AssistantModel findByAssiStudentIdAndAssiCourse(String assiStudentId,String assiCourse){
        AssistantModel assistantModel = assistantRepository.findByAssiStudentIdAndAssiCourse(assiStudentId,assiCourse);
        return assistantModel;
    }
}
