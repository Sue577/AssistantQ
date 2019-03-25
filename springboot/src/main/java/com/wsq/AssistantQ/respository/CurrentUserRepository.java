package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.CurrentUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-03-29 21:23
 */
public interface CurrentUserRepository extends JpaRepository<CurrentUserModel,String>,JpaSpecificationExecutor<CurrentUserModel> {

    //查找所有用户
    @Query(value = "select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.userType= 'user'")
    List<CurrentUserModel> findAllUser();

    //通过数据库编号id查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.objectId = :objectId and currentUserModel.delTime is null")
    CurrentUserModel findById(@Param("objectId") String objectId);

    //通过查找学号查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.stuNumber = ?1 and currentUserModel.delTime is null")
    List<CurrentUserModel> findByStuNumber(@Param("stuNumber") String stuNumber);

    //通过查找姓名查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.stuName = ?1 and currentUserModel.delTime is null")
    List<CurrentUserModel> findByStuName(@Param("stuName") String stuName);

    //通过查找学号查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.stuNumber = ?1 and currentUserModel.delTime is null")
    CurrentUserModel findByStuNum(@Param("stuNumber") String stuNumber);

    //通过查找姓名查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.stuName = ?1 and currentUserModel.delTime is null")
    CurrentUserModel findByStuN(@Param("stuName") String stuName);

    //通过查找邮箱查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.loginEmail = ?1 and currentUserModel.delTime is null")
    CurrentUserModel findByLoginEmail(@Param("loginEmail") String loginEmail);

    //通过查找邮箱验证码查找用户
    @Query("select currentUserModel from CurrentUserModel currentUserModel where currentUserModel.activeCode = ?1 and currentUserModel.loginEmail = ?1 and currentUserModel.delTime is null")
    CurrentUserModel findByActiveCode(@Param("activeCode") String activeCode, @Param("loginEmail") String loginEmail);

    //登录
    CurrentUserModel findByLoginEmailAndLoginPswAndDelTimeIsNull(String loginEmail, String loginPsw);

    //更新邮箱激活码
    @Modifying
    @Query(value = "update CurrentUserModel currentUserModel  set currentUserModel.activeCode=?1, currentUserModel.activeStatus=?2 ")
    void updateActivecode(@Param("activeCode") String activeCode, @Param("activeStatus") int activeStatus);



}
