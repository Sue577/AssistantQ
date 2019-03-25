package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CYann
 * @date 2018-02-26 20:38
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BaseService baseService;

    //增加学生用户
    @Transactional
    public void add(UserModel userModel){
        baseService.add(userRepository,userModel);
        userRepository.save(userModel);
    }

    //动态修改学生用户
    @Transactional
    public void update(UserModel userModel) {
        UserModel userItem = userRepository.findById(userModel.getObjectId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(userModel.getStuNumber() !=null && userItem.getStuNumber().equals(userModel.getStuNumber()) == false ){
                userItem.setStuNumber(userModel.getStuNumber());
            }
            if(userModel.getStuName() != null && userItem.getStuName().equals(userModel.getStuName()) == false ){
                userItem.setStuName(userModel.getStuName());
            }
            if(userModel.getStuClass() != null && userItem.getStuClass().equals(userModel.getStuClass()) == false ){
                userItem.setStuClass(userModel.getStuClass());
            }
            if(userModel.getStuMajor() != null && userItem.getStuMajor().equals(userModel.getStuMajor()) == false ){
                userItem.setStuMajor(userModel.getStuMajor());
            }
            if(userModel.getStuStartYear() != null && userItem.getStuStartYear().equals(userModel.getStuStartYear()) == false ){
                userItem.setStuStartYear(userModel.getStuStartYear());
            }
            if(userModel.getStuEndYear() != null && userItem.getStuEndYear().equals(userModel.getStuEndYear()) == false){
                userItem.setStuEndYear(userModel.getStuEndYear());
            }
            if(userItem.getRedParty() != userModel.getRedParty()){
                userItem.setRedParty(userModel.getRedParty());
            }
            if(userItem.getStuPower() != userModel.getStuPower()){
                userItem.setStuPower(userModel.getStuPower());
            }
            baseService.modify(userRepository,userItem);
            userRepository.save(userItem);
        }
    }

    //删除学生用户
    @Transactional
    public void delete(UserModel userModel){
        UserModel userItem = userRepository.findById(userModel.getObjectId());
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(userRepository, userItem);
        }
    }


    //用户修改基本信息-联系方式
    public void updateInfor(UserModel userModel){
        UserModel userItem = userRepository.findById(userModel.getObjectId());
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            if (userItem.getCurrentPhone() == null || userItem.getCurrentPhone().equals(userModel.getCurrentPhone()) == false) {
                userItem.setCurrentPhone(userModel.getCurrentPhone());
            }
            userRepository.save(userItem);
        }
    }

    //更改用户权限
    public void updatePower(UserModel userModel){
        UserModel userItem = userRepository.findById(userModel.getObjectId());
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            userItem.setStuPower(userModel.getStuPower());
            userRepository.save(userItem);
        }
    }

    //增加用户联系方式
    public void updateContract(String stuNumber, String mobilePhone, String loginEmail){
        UserModel userItem = userRepository.findByStuNumber(stuNumber);
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.modify(userRepository,userItem);
            userItem.setCurrentPhone(mobilePhone);
            userItem.setCurrentEmail(loginEmail);
            userRepository.save(userItem);
        }
    }




    //查询所有用户
    public List<UserModel> findAllUser(){
        List<UserModel> list = userRepository.findAllUser();
        return list;
    }

    //主key查询
    public UserModel findById(String id){
        return userRepository.findById(id);
    }



    //姓名查询
    public List<UserModel> findByStuName(String stuName){
        List<UserModel> list = userRepository.findByStuName(stuName);
        return list;
    }

    //学号查询
    public UserModel findByStuNumber(String stuNumber){
        return userRepository.findByStuNumber(stuNumber);
    }

    //学号、姓名查找
    public List<UserModel> findByStuNameAndStuNumber(String stuName, String stuNumber){
        List<UserModel> list = userRepository.findByStuNameAndStuNumber(stuName, stuNumber);
        return list;
    }

    //学号、是否党员查找
    public List<UserModel> findByStuNumberAndIfRed(String stuNumber, int redParty){
        List<UserModel> list = userRepository.findByStuNumberAndRedParty(stuNumber, redParty);
        return list;
    }

    //班级查询
    public List<UserModel> findByStuClass(String stuClass){
        List<UserModel> list = userRepository.findByStuClass(stuClass);
        return list;
    }

    //专业查询
    public List<UserModel> findByStuMajor(String stuMajor){
        List<UserModel> list = userRepository.findByStuClass(stuMajor);
        return list;
    }

    //根据 名字 学号 专业 毕业年份 入学年份 多条件动态查询课程
    public List<UserModel> findAllByAdvancedForm(UserModel userModel) {
        return userRepository.findAll(new Specification<UserModel>(){
            @Override
            public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.isNull(root.get("delTime")));
                if(userModel != null && !StringUtils.isEmpty(userModel.getStuName()) ){
                    list.add(cb.like(root.get("stuName"), "%"+ userModel.getStuName() + "%"));
                }
                if(userModel != null && !StringUtils.isEmpty(userModel.getStuNumber()) ){
                    list.add(cb.like(root.get("stuNumber"), "%" + userModel.getStuNumber() +"%" ));
                }
                if(userModel != null && !StringUtils.isEmpty(userModel.getStuMajor()) ){
                    list.add(cb.like(root.get("stuMajor"), "%"+ userModel.getStuMajor()+ "%"));
                }
                if(userModel != null && !StringUtils.isEmpty(userModel.getStuEndYear()) ){
                    list.add(cb.like(root.get("stuEndYear"),"%"+ userModel.getStuEndYear()+ "%"));
                }
                if(userModel != null && !StringUtils.isEmpty(userModel.getStuStartYear()) ){
                    list.add(cb.like(root.get("stuStartYear"), "%"+ userModel.getStuStartYear()+ "%"));
                }
                if(userModel != null && userModel.getRedParty() != 0 ){
                    list.add(cb.equal(root.get("redParty"), Integer.valueOf(userModel.getRedParty())));
                }
                if(userModel != null && userModel.getStuPower() != 0 ){
                    list.add(cb.equal(root.get("stuPower"), Integer.valueOf(userModel.getStuPower())));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }

}
