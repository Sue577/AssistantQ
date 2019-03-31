package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.StudentModel;
import com.wsq.AssistantQ.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:23
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BaseService baseService;

    //新增学生信息
    public void add(StudentModel studentModel) {

        //验证学生ID是否已存在
        int flag = 1;
        List<StudentModel> list = findAllStudent();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStuId().equals(studentModel.getStuId())) {
                flag = 0;
            }
        }

        if (flag == 0) {//学生ID已存在，学生ID是唯一的，说明学生已存在
            throw new MyException(ResultEnum.ERROR_112);
        } else {
            //添加基础信息 编号和创建时间
            baseService.add(studentRepository, studentModel);

            //保存信息至数据库
            studentRepository.save(studentModel);
        }

    }

    //根据学生编号删除学生信息
    @Transactional
    public void deleteByObjectId(StudentModel studentModel) {
        StudentModel studentItem = studentRepository.findByObjectId(studentModel.getObjectId());
        if (studentItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(studentRepository, studentItem);
        }
    }

    //根据学生ID删除学生信息
    @Transactional
    public void deleteByStuId(StudentModel studentModel) {
        StudentModel studentItem = studentRepository.findByStuId(studentModel.getStuId());
        if (studentItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(studentRepository, studentItem);
        }
    }

    //根据学生编号动态修改学生信息
    @Transactional
    public void modify(StudentModel studentModel) {
        StudentModel studentItem = studentRepository.findByObjectId(studentModel.getObjectId());
        if (studentItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if (studentModel.getStuId() != null && studentItem.getStuId().equals(studentModel.getStuId()) == false) {
                studentItem.setStuId(studentModel.getStuId());
            }
            if (studentModel.getStuName() != null && studentItem.getStuName().equals(studentModel.getStuName()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuClass() != null && studentItem.getStuClass().equals(studentModel.getStuClass()) == false) {
                studentItem.setStuClass(studentModel.getStuClass());
            }
            if (studentModel.getStuName() != null && studentItem.getStuName().equals(studentModel.getStuName()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuBranch() != null && studentItem.getStuBranch().equals(studentModel.getStuBranch()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuSchool() != null && studentItem.getStuSchool().equals(studentModel.getStuSchool()) == false) {
                studentItem.setStuSchool(studentModel.getStuSchool());
            }
            if (studentModel.getStuIsAssistant() != null && studentItem.getStuIsAssistant().equals(studentModel.getStuIsAssistant()) == false) {
                studentItem.setStuIsAssistant(studentModel.getStuIsAssistant());
            }
            baseService.modify(studentRepository, studentItem);
            studentRepository.save(studentItem);
        }
    }

    //自己根据学生ID动态修改自己的学生信息
    @Transactional
    public void modifyMy(StudentModel studentModel, String stuId) {
        StudentModel studentItem = studentRepository.findByStuId(stuId);
        if (studentItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if (studentModel.getStuId() != null && studentItem.getStuId().equals(studentModel.getStuId()) == false) {
                studentItem.setStuId(studentModel.getStuId());
            }
            if (studentModel.getStuName() != null && studentItem.getStuName().equals(studentModel.getStuName()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuClass() != null && studentItem.getStuClass().equals(studentModel.getStuClass()) == false) {
                studentItem.setStuClass(studentModel.getStuClass());
            }
            if (studentModel.getStuName() != null && studentItem.getStuName().equals(studentModel.getStuName()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuBranch() != null && studentItem.getStuBranch().equals(studentModel.getStuBranch()) == false) {
                studentItem.setStuName(studentModel.getStuName());
            }
            if (studentModel.getStuSchool() != null && studentItem.getStuSchool().equals(studentModel.getStuSchool()) == false) {
                studentItem.setStuSchool(studentModel.getStuSchool());
            }
            if (studentModel.getStuIsAssistant() != null && studentItem.getStuIsAssistant().equals(studentModel.getStuIsAssistant()) == false) {
                studentItem.setStuSchool(studentModel.getStuIsAssistant());
            }
            baseService.modify(studentRepository, studentItem);
            studentRepository.save(studentItem);
        }
    }

    //查找所有学生
    public List<StudentModel> findAllStudent() {
        List<StudentModel> list = studentRepository.findAllStudent();
        return list;
    }

    //根据编号查找学生
    public StudentModel findByObjectId(String objectId) {
        StudentModel studentModel = studentRepository.findByObjectId(objectId);
        return studentModel;
    }

    //根据学生ID查找学生
    public StudentModel findByStuId(String stuId) {
        StudentModel studentModel = studentRepository.findByStuId(stuId);
        return studentModel;
    }

    //根据学生姓名查找学生
    public List<StudentModel> findByStuName(String stuName) {
        List<StudentModel> list = studentRepository.findByStuName(stuName);
        return list;
    }

    //根据学生班级查找学生
    public List<StudentModel> findByStuClass(String stuClass) {
        List<StudentModel> list = studentRepository.findByStuClass(stuClass);
        return list;
    }

    //根据是否为助教查找学生
    public List<StudentModel> findByStuIsAssistant(String stuIsAssistant) {
        List<StudentModel> list = studentRepository.findByStuIsAssistant(stuIsAssistant);
        return list;
    }

}
