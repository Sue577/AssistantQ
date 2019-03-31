package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:40
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel,String>, JpaSpecificationExecutor<UserModel> {

    //查找所有用户
    @Query(value = "select userModel from UserModel userModel where userModel.delTime is null")
    List<UserModel> findAllUser();

    //根据编号查找用户
    @Query("select userModel from UserModel userModel where userModel.delTime is null and userModel.objectId = :objectId")
    UserModel findByObjectId(@Param("objectId") String objectId);

    //根据用户ID查找用户
    @Query("select userModel from UserModel userModel where userModel.delTime is null and userModel.userId = ?1")
    UserModel findByUserId(@Param("userId") String userId);

    //根据用户姓名查找用户
    @Query("select userModel from UserModel userModel where userModel.delTime is null and userModel.userName = ?1")
    List<UserModel> findByUserName(@Param("userName") String userName);

    //根据用户类型查找用户
    @Query("select userModel from UserModel userModel where userModel.delTime is null and userModel.userType = ?1")
    List<UserModel> findByUserType(@Param("userType") String userType);

    //用户登录
    UserModel findByUserIdAndUserPwdAndDelTimeIsNull(String userId, String userPwd);

}
