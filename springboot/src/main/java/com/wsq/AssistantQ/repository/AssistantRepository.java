package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.AssistantModel;
import com.wsq.AssistantQ.model.AssistantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:36
 */
@Repository
public interface AssistantRepository extends JpaRepository<AssistantModel,String>, JpaSpecificationExecutor<AssistantModel> {
    //查找所有助教信息
    @Query(value = "select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null")
    List<AssistantModel> findAllAssistant();

    //根据编号查找助教信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.objectId = :objectId")
    AssistantModel findByObjectId(@Param("objectId") String objectId);

    //根据助教学生ID查找助教信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.assiStudentId = ?1")
    List<AssistantModel> findByAssiStudentId(@Param("assiStudentId") String assiStudentId);

    //根据助教姓名查找助教信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.assiName = ?1")
    List<AssistantModel> findByAssiName(@Param("assiName") String assiName);

    //根据助教课程查找助教信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.assiCourse = ?1")
    List<AssistantModel> findByAssiCourse(@Param("assiCourse") String assiCourse);

    //根据助教教师ID查找助教信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.assiTeacherId = ?1")
    List<AssistantModel> findByAssiTeacherId(@Param("assiTeacherId") String assiTeacherId);

    //根据学生ID和课程名称查询报名信息
    @Query("select assistantModel from AssistantModel assistantModel where assistantModel.delTime is null and assistantModel.assiStudentId = ?1 and  assistantModel.assiCourse = ?2 ")
    AssistantModel findByAssiStudentIdAndAssiCourse(@Param("assiStudentId") String assiStudentId,@Param("assiCourse") String assiCourse);

}
