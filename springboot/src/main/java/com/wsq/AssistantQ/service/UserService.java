package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

/**
 * @author WSQ
 * @date 2019/3/28 10:20
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BaseService baseService;

    //用户登录
    public UserModel findByUserIdAndUserPwdAndDelTimeIsNull(String userId, String userPwd) {
        return userRepository.findByUserIdAndUserPwdAndDelTimeIsNull(userId, userPwd);
    }

    //新增用户
    public void add(UserModel userModel) {

        //验证用户ID是否已存在
        int flag=1;
        List<UserModel> list=findAllUser();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUserId().equals(userModel.getUserId())){
                flag=0;
            }
        }

        if(flag==0){//用户ID已存在，用户ID是唯一的，说明该用户已存在
            throw new MyException(ResultEnum.ERROR_107);
        }
        else {//用户ID不存在

            //添加基础信息 编号和创建时间
            baseService.add(userRepository, userModel);

            //随机添加头像
            Random random = new Random();
            int randomint = random.nextInt(9) + 1;
            switch (randomint) {
                case 0:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
                    break;
                case 1:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png");
                    break;
                case 2:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/gaOngJwsRYRaVAuXXcmB.png");
                    break;
                case 3:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png");
                    break;
                case 4:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/WhxKECPNujWoWEFNdnJE.png");
                    break;
                case 5:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
                    break;
                case 6:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/psOgztMplJMGpVEqfcgF.png");
                    break;
                case 7:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/ZpBqSxLxVEXfcUNoPKrz.png");
                    break;
                case 8:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/laiEnJdGHVOhJrUShBaJ.png");
                    break;
                case 9:
                    userModel.setUserImage("https://gw.alipayobjects.com/zos/rmsportal/UrQsqscbKEpNuJcvBZBu.png");
                    break;
            }
            //保存信息至数据库
            userRepository.save(userModel);
        }
    }

    //根据用户编号删除用户
    @Transactional
    public void deleteByObjectId(UserModel userModel) {
        UserModel userItem = userRepository.findByObjectId(userModel.getObjectId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(userRepository, userItem);
        }
    }

    //根据用户ID删除用户
    @Transactional
    public void deleteByUserId(UserModel userModel) {
        UserModel userItem = userRepository.findByUserId(userModel.getUserId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(userRepository, userItem);
        }
    }

    //根据用户编号重置用户密码为111111
    public void resetPwd(UserModel userModel) {
        UserModel userItem = userRepository.findByObjectId(userModel.getObjectId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            userItem.setUserPwd("111111");
            userRepository.save(userItem);
        }
    }

    //根据用户ID重置用户密码为111111
    public void resetPwdByUserId(UserModel userModel) {
        UserModel userItem = userRepository.findByUserId(userModel.getUserId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            userItem.setUserPwd("111111");
            userRepository.save(userItem);
        }
    }

    //自己更改自己的用户密码
    public void modifyMyPwd(UserModel userModel,String objectId) {
        UserModel userItem = userRepository.findByObjectId(objectId);
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if (userItem.getUserPwd().equals(userModel.getUserPwd()) == true) {
                userItem.setUserPwd(userModel.getUserNewPwd());
                userRepository.save(userItem);
            } else {
                throw new MyException(ResultEnum.ERROR_105);
            }
        }
    }

    //自己动态修改自己的用户信息
    @Transactional
    public void modifyMy(UserModel userModel,String objectId) {
        UserModel userItem = userRepository.findByObjectId(objectId);
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(userModel.getUserId() !=null && userItem.getUserId().equals(userModel.getUserId()) == false ){
                userItem.setUserId(userModel.getUserId());
            }
            if(userModel.getUserPwd() !=null && userItem.getUserPwd().equals(userModel.getUserPwd()) == false ){
                userItem.setUserPwd(userModel.getUserPwd());
            }
            if(userModel.getUserImage() !=null && userItem.getUserImage().equals(userModel.getUserImage()) == false ){
                userItem.setUserImage(userModel.getUserImage());
            }
            if(userModel.getUserName() !=null && userItem.getUserName().equals(userModel.getUserName()) == false ){
                userItem.setUserName(userModel.getUserName());
            }
            if(userModel.getUserEmail() !=null && userItem.getUserEmail().equals(userModel.getUserEmail()) == false ){
                userItem.setUserEmail(userModel.getUserEmail());
            }
            if(userModel.getUserPhone() !=null && userItem.getUserPhone().equals(userModel.getUserPhone()) == false ){
                userItem.setUserPhone(userModel.getUserPhone());
            }
            if(userModel.getUserType() !=null && userItem.getUserType().equals(userModel.getUserType()) == false ){
                userItem.setUserType(userModel.getUserType());
            }
            baseService.modify(userRepository,userItem);
            userRepository.save(userItem);
        }
    }

    //动态修改用户信息
    @Transactional
    public void modify(UserModel userModel) {
        UserModel userItem = userRepository.findByObjectId(userModel.getObjectId());
        if (userItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(userModel.getUserId() !=null && userItem.getUserId().equals(userModel.getUserId()) == false ){
                userItem.setUserId(userModel.getUserId());
            }
            if(userModel.getUserPwd() !=null && userItem.getUserPwd().equals(userModel.getUserPwd()) == false ){
                userItem.setUserPwd(userModel.getUserPwd());
            }
            if(userModel.getUserImage() !=null && userItem.getUserImage().equals(userModel.getUserImage()) == false ){
                userItem.setUserImage(userModel.getUserImage());
            }
            if(userModel.getUserName() !=null && userItem.getUserName().equals(userModel.getUserName()) == false ){
                userItem.setUserName(userModel.getUserName());
            }
            if(userModel.getUserEmail() !=null && userItem.getUserEmail().equals(userModel.getUserEmail()) == false ){
                userItem.setUserEmail(userModel.getUserEmail());
            }
            if(userModel.getUserPhone() !=null && userItem.getUserPhone().equals(userModel.getUserPhone()) == false ){
                userItem.setUserPhone(userModel.getUserPhone());
            }
            if(userModel.getUserType() !=null && userItem.getUserType().equals(userModel.getUserType()) == false ){
                userItem.setUserType(userModel.getUserType());
            }
            baseService.modify(userRepository,userItem);
            userRepository.save(userItem);
        }
    }

    //查找所有用户
    public List<UserModel> findAllUser() {
        List<UserModel> list = userRepository.findAllUser();
        return list;
    }

    //根据用户类型查找用户
    public List<UserModel> findByUserType(String userType) {
        List<UserModel> list = userRepository.findByUserType(userType);
        return list;
    }

    //根据用户姓名查找用户
    public List<UserModel> findByUserName(String userName) {
        List<UserModel> list = userRepository.findByUserName(userName);
        return list;
    }

    //根据用户ID查找用户
    public UserModel findByUserId(String userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        return userModel;
    }

    //根据编号查找用户
    public UserModel findByObjectId(String objectId) {
        UserModel userModel = userRepository.findByObjectId(objectId);
        return userModel;
    }

}
