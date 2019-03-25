package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.respository.CurrentUserRepository;
import com.wsq.AssistantQ.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author CYann
 * @date 2018-03-29 21:22
 */
@Service
public class CurrentUserService {
    @Autowired
    private CurrentUserRepository currentUserRepository;
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserService userService;

    //增加用户
    @Transactional
    public void add(@RequestBody CurrentUserModel currentUserModel){
        Random random = new Random();
        int randomint = random.nextInt(9)+1;
        userService.updateContract(currentUserModel.getStuNumber(),currentUserModel.getMobilePhone(),currentUserModel.getLoginEmail());
        baseService.add(currentUserRepository,currentUserModel);
        switch (randomint){
            case 0:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
                break;
            case 1:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png");
                break;
            case 2:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/gaOngJwsRYRaVAuXXcmB.png");
                break;
            case 3:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png");
                break;
            case 4:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/WhxKECPNujWoWEFNdnJE.png");
                break;
            case 5:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
                break;
            case 6:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/psOgztMplJMGpVEqfcgF.png");
                break;
            case 7:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/ZpBqSxLxVEXfcUNoPKrz.png");
                break;
            case 8:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/laiEnJdGHVOhJrUShBaJ.png");
                break;
            case 9:
                currentUserModel.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/UrQsqscbKEpNuJcvBZBu.png");
                break;
        }
        currentUserModel.setUserType("user");
        currentUserModel.setActiveStatus(1);
        currentUserRepository.save(currentUserModel);
    }


    //查询所有用户
    public List<CurrentUserModel> findAllUser(){
        List<CurrentUserModel> list = currentUserRepository.findAllUser();
        return list;
    }


    //重置密码
    public void resetPwd(CurrentUserModel currentUserModel){
        CurrentUserModel userItem = currentUserRepository.findById(currentUserModel.getObjectId());
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            userItem.setLoginPsw("123456");
            currentUserRepository.save(userItem);
        }
    }
    //删除学生用户
    @Transactional
    public void delete(CurrentUserModel currentUserModel){
        CurrentUserModel currentuserItem = currentUserRepository.findById(currentUserModel.getObjectId());
        if(currentuserItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(currentUserRepository, currentuserItem);
        }
    }

    //用户登录
    public CurrentUserModel findByLoginEmailAndLoginPwd(String loginEmail , String loginPsw){
        return currentUserRepository.findByLoginEmailAndLoginPswAndDelTimeIsNull(loginEmail, loginPsw);
    }

    //根据 学号 目前单位 多条件动态查询
    public List<CurrentUserModel> findAllByAdvancedForm(CurrentUserModel currentUserModel) {
        return currentUserRepository.findAll(new Specification<CurrentUserModel>(){
            @Override
            public Predicate toPredicate(Root<CurrentUserModel> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> list = new ArrayList<Predicate>();
                // list.add(cb.isNull(root.get("delTime")));
                if(currentUserModel != null && !StringUtils.isEmpty(currentUserModel.getStuNumber()) ){
                    list.add(cb.like(root.get("stuNumber"), "%"+ currentUserModel.getStuNumber()+"%"));
                }
                if(currentUserModel != null && !StringUtils.isEmpty(currentUserModel.getStuName()) ){
                    list.add(cb.like(root.get("stuName"), "%"+currentUserModel.getStuName()+"%"));
                }
                if(currentUserModel != null && !StringUtils.isEmpty(currentUserModel.getMobilePhone()) ){
                    list.add(cb.like(root.get("mobilePhone"), "%"+ currentUserModel.getMobilePhone()+"%"));
                }
                if(currentUserModel != null && !StringUtils.isEmpty(currentUserModel.getLoginEmail()) ){
                    list.add(cb.like(root.get("loginEmail"), "%" + currentUserModel.getLoginEmail() + "%"));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }

    //用户更改密码
    public void updateMyPwd(CurrentUserModel currentUserModel,String newPwd, String objectId){
        CurrentUserModel userItem = currentUserRepository.findById(objectId);
        if(userItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            if (userItem.getLoginPsw().equals(currentUserModel.getLoginPsw()) == true){
                userItem.setLoginPsw(newPwd);
                userItem.setPassword("");
                currentUserRepository.save(userItem);
            }else {
                throw new MyException(ResultEnum.ERROR_105);
            }
        }
    }



    //发送邮箱激活码
    public void sendEamil(String loginEmail, String activeCode){
        CurrentUserModel currentuserItem = currentUserRepository.findByLoginEmail(loginEmail);
        if(currentuserItem != null){
            throw new MyException(ResultEnum.ERROR_107);
        } else {
            CurrentUserModel currentUserModel = new CurrentUserModel();
            currentUserModel.setLoginEmail(loginEmail);
            currentUserModel.setActiveCode(activeCode);
            currentUserModel.setActiveStatus(0);
            currentUserRepository.save(currentUserModel);
            // currentUserRepository.updateActivecode(currentuserItem.getActiveCode(), currentuserItem.getActiveStatus());
        }
    }

    //验证邮箱激活码是否正确
    public CurrentUserModel verifyActiveCode(String activeCode , String loginEmail){
        CurrentUserModel currentuserItem = currentUserRepository.findByActiveCode(activeCode, loginEmail);
        return currentuserItem;
    }



    //通过学号查询用户
    public List<CurrentUserModel> findByStuNumber(String stuNumber){
        List<CurrentUserModel> list = currentUserRepository.findByStuNumber(stuNumber);
        return list;
    }

    //通过学号查询用户
    public CurrentUserModel findByLoginEmail(String loginEmail){
        return currentUserRepository.findByLoginEmail(loginEmail);
    }

    //通过objectId查询用户
    public CurrentUserModel findByObjectId(String objectId){
        return currentUserRepository.findById(objectId);
    }


}
