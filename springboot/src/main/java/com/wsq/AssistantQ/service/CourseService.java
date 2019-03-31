package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.CourseModel;
import com.wsq.AssistantQ.repository.CourseRepository;
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
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BaseService baseService;

    //新增课程信息
    public void add(CourseModel courseModel) {

        //验证课程名称是否已存在
        int flag = 1;
        List<CourseModel> list = findAllCourse();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCourName().equals(courseModel.getCourName())) {
                flag = 0;
            }
        }

        if (flag == 0) {//课程名称已存在，课程名称是唯一的（好处是可以唯一对应一个教师ID）
            throw new MyException(ResultEnum.ERROR_114);
        }
        else {
            //添加基础信息 编号和创建时间
            baseService.add(courseRepository, courseModel);

            //保存信息至数据库
            courseRepository.save(courseModel);
        }
    }

    //根据课程编号删除课程信息
    @Transactional
    public void deleteByObjectId(CourseModel courseModel) {
        CourseModel courseItem = courseRepository.findByObjectId(courseModel.getObjectId());
        if (courseItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(courseRepository, courseItem);
        }
    }

    //动态修改课程信息
    @Transactional
    public void modify(CourseModel courseModel) {
        CourseModel courseItem = courseRepository.findByObjectId(courseModel.getObjectId());
        if (courseItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(courseModel.getCourName() !=null && courseItem.getCourName().equals(courseModel.getCourName()) == false ){
                courseItem.setCourName(courseModel.getCourName());
            }
            if(courseModel.getCourDesc() !=null && courseItem.getCourDesc().equals(courseModel.getCourDesc()) == false ){
                courseItem.setCourDesc(courseModel.getCourDesc());
            }
            if(courseModel.getCourTeacherName() !=null && courseItem.getCourTeacherName().equals(courseModel.getCourTeacherName()) == false ){
                courseItem.setCourTeacherName(courseModel.getCourTeacherName());
            }
            if(courseModel.getCourSubmitterId() !=null && courseItem.getCourSubmitterId().equals(courseModel.getCourSubmitterId()) == false ){
                courseItem.setCourSubmitterId(courseModel.getCourSubmitterId());
            }
            baseService.modify(courseRepository,courseItem);
            courseRepository.save(courseItem);
        }
    }

    //查找所有课程信息
    public List<CourseModel> findAllCourse(){
        List<CourseModel> list = courseRepository.findAllCourse();
        return list;
    }

    //根据编号查找课程
    public CourseModel findByObjectId(String objectId){
        CourseModel courseModel = courseRepository.findByObjectId(objectId);
        return courseModel;
    }

    //根据课程名称查找课程
    public CourseModel findByCourName(String courName){
        CourseModel courseModel = courseRepository.findByCourName(courName);
        return courseModel;
    }

    //根据提交者ID查找课程
    public List<CourseModel> findByCourSubmitterId(String courSubmitterId){
        List<CourseModel> list = courseRepository.findByCourSubmitterId(courSubmitterId);
        return list;
    }

    //根据教师姓名查找课程
    public List<CourseModel> findByCourTeacherName(String courTeacherName){
        List<CourseModel> list = courseRepository.findByCourTeacherName(courTeacherName);
        return list;
    }
}
