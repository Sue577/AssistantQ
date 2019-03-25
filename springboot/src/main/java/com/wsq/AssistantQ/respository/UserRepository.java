package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author CYann
 * @date 2018-02-26 20:43
 */

public interface UserRepository extends JpaRepository<UserModel,String>, JpaSpecificationExecutor<UserModel> {

    //查找所有用户
    @Query(value = "select userModel from UserModel userModel where userModel.delTime is null")
    List<UserModel> findAllUser();

    //通过数据库编号id查找用户
    @Query("select userModel from UserModel userModel where userModel.objectId = :objectId")
    UserModel findById(@Param("objectId") String objectId);

    //通过查找学号查找用户
    @Query("select userModel from UserModel userModel where userModel.stuNumber = ?1 and userModel.delTime is null")
    UserModel findByStuNumber(@Param("stuNumber") String stuNumber);

    //通过查找姓名查找用户
    @Query("select userModel from UserModel userModel where userModel.stuName = ?1 and userModel.delTime is null")
    List<UserModel> findByStuName(@Param("stuName") String stuName);

    //通过查找姓名和学号查找用户
    @Query("select userModel from UserModel userModel where userModel.stuName = ?1 and userModel.stuNumber = ?2 and userModel.delTime is null")
    List<UserModel> findByStuNameAndStuNumber(@Param("stuName") String stuName, @Param("stuNumber") String stuNumber);

    //通过查找学号和是否党员查找用户
    @Query("select userModel from UserModel userModel where userModel.stuNumber = ?1 and userModel.redParty = ?2 and userModel.delTime is null")
    List<UserModel> findByStuNumberAndRedParty(@Param("stuNumber") String stuNumber, @Param("redParty") int redParty);

    //通过查找班级查找用户
    @Query("select userModel from UserModel userModel where userModel.stuClass = ?1 and userModel.delTime is null")
    List<UserModel> findByStuClass(@Param("stuClass") String stuClass);

    //通过查找专业查找用户
    @Query("select userModel from UserModel userModel where userModel.stuMajor = ?1 and userModel.delTime is null")
    List<UserModel> findByStuMajor(@Param("stuMajor") String stuMajor);

    //通过查找入学年份查找用户
    @Query("select userModel from UserModel userModel where userModel.stuStartYear = ?1 and userModel.delTime is null")
    List<UserModel> findByStuStartYear(@Param("stuStartYear") String stuStartYear);

    //通过查找毕业年份查找用户
    @Query("select userModel from UserModel userModel where userModel.stuEndYear = ?1 and userModel.delTime is null")
    List<UserModel> findByStuEndYear(@Param("stuEndYear") String stuEndYear);

    //是否党员查找用户
    @Query("select userModel from UserModel userModel where userModel.redParty = ?1 and userModel.delTime is null")
    List<UserModel> findByRedParty(@Param("redParty") int redParty);

    //通过学生学号删除用户
    @Modifying
    @Query(value = "update UserModel userModel  set userModel.delTime=?2 where userModel.stuNumber = ?1")
    void deleteByStuNumber(String stuNumber, Timestamp delTime);

    @Modifying
    @Query(value = "update UserModel userModel  set userModel=?1 ")
    void modifyByForm(UserModel userModel);

}
