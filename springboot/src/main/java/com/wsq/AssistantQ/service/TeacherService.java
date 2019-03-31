package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.TeacherModel;
import com.wsq.AssistantQ.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:24
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private BaseService baseService;

    //新增教师信息
    public void add(TeacherModel teacherModel) {

        //验证教师ID是否已存在
        int flag = 1;
        List<TeacherModel> list = findAllTeacher();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTeachId().equals(teacherModel.getTeachId())) {
                flag = 0;
            }
        }

        if (flag == 0) {//教师ID已存在，教师ID是唯一的，说明教师已存在
            throw new MyException(ResultEnum.ERROR_113);
        } else {

            //添加基础信息 编号和创建时间
            baseService.add(teacherRepository, teacherModel);

            //保存信息至数据库
            teacherRepository.save(teacherModel);
        }

    }

    //根据教师编号删除教师信息
    @Transactional
    public void deleteByObjectId(TeacherModel teacherModel) {
        TeacherModel teacherItem = teacherRepository.findByObjectId(teacherModel.getObjectId());
        if (teacherItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(teacherRepository, teacherItem);
        }
    }

    //根据教师ID删除教师信息
    @Transactional
    public void deleteByStuId(TeacherModel teacherModel) {
        TeacherModel teacherItem = teacherRepository.findByTeachId(teacherModel.getTeachId());
        if (teacherItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(teacherRepository, teacherItem);
        }
    }

    //根据教师编号动态修改教师信息
    @Transactional
    public void modify(TeacherModel teacherModel) {
        TeacherModel teacherItem = teacherRepository.findByObjectId(teacherModel.getObjectId());
        if (teacherItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if (teacherModel.getTeachId() != null && teacherItem.getTeachId().equals(teacherModel.getTeachId()) == false) {
                teacherItem.setTeachId(teacherModel.getTeachId());
            }
            if (teacherModel.getTeachName() != null && teacherItem.getTeachName().equals(teacherModel.getTeachName()) == false) {
                teacherItem.setTeachName(teacherModel.getTeachName());
            }
            if (teacherModel.getTeachLevel() != null && teacherItem.getTeachLevel().equals(teacherModel.getTeachLevel()) == false) {
                teacherItem.setTeachLevel(teacherModel.getTeachLevel());
            }
            if (teacherModel.getTeachBranch() != null && teacherItem.getTeachBranch().equals(teacherModel.getTeachBranch()) == false) {
                teacherItem.setTeachBranch(teacherModel.getTeachBranch());
            }
            if (teacherModel.getTeachSchool() != null && teacherItem.getTeachSchool().equals(teacherModel.getTeachSchool()) == false) {
                teacherItem.setTeachSchool(teacherModel.getTeachSchool());
            }
            baseService.modify(teacherRepository, teacherItem);
            teacherRepository.save(teacherItem);
        }
    }

    //自己根据教师ID动态修改自己的教师信息
    @Transactional
    public void modifyMy(TeacherModel teacherModel, String teachId) {
        TeacherModel teacherItem = teacherRepository.findByTeachId(teachId);
        if (teacherItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if (teacherModel.getTeachId() != null && teacherItem.getTeachId().equals(teacherModel.getTeachId()) == false) {
                teacherItem.setTeachId(teacherModel.getTeachId());
            }
            if (teacherModel.getTeachName() != null && teacherItem.getTeachName().equals(teacherModel.getTeachName()) == false) {
                teacherItem.setTeachName(teacherModel.getTeachName());
            }
            if (teacherModel.getTeachLevel() != null && teacherItem.getTeachLevel().equals(teacherModel.getTeachLevel()) == false) {
                teacherItem.setTeachLevel(teacherModel.getTeachLevel());
            }
            if (teacherModel.getTeachBranch() != null && teacherItem.getTeachBranch().equals(teacherModel.getTeachBranch()) == false) {
                teacherItem.setTeachBranch(teacherModel.getTeachBranch());
            }
            if (teacherModel.getTeachSchool() != null && teacherItem.getTeachSchool().equals(teacherModel.getTeachSchool()) == false) {
                teacherItem.setTeachSchool(teacherModel.getTeachSchool());
            }
            baseService.modify(teacherRepository, teacherItem);
            teacherRepository.save(teacherItem);
        }
    }

    //查找所有教师
    public List<TeacherModel> findAllTeacher() {
        List<TeacherModel> list = teacherRepository.findAllTeacher();
        return list;
    }

    //根据编号查找教师
    public TeacherModel findByObjectId(String objectId) {
        TeacherModel teacherModel = teacherRepository.findByObjectId(objectId);
        return teacherModel;
    }

    //根据教师ID查找教师
    public TeacherModel findByTeachId(String teachId) {
        TeacherModel teacherModel = teacherRepository.findByTeachId(teachId);
        return teacherModel;
    }

    //根据教师姓名查找教师
    public List<TeacherModel> findByTeachName(String teachName) {
        List<TeacherModel> list = teacherRepository.findByTeachName(teachName);
        return list;
    }
}
