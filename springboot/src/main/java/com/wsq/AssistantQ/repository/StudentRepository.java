package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.StudentModel;
import com.wsq.AssistantQ.model.TeacherModel;
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
public interface StudentRepository extends JpaRepository<StudentModel,String>, JpaSpecificationExecutor<StudentModel> {
    //查找所有学生
    @Query(value = "select studentModel from StudentModel studentModel where studentModel.delTime is null")
    List<StudentModel> findAllStudent();

    //根据编号查找学生
    @Query("select studentModel from StudentModel studentModel where studentModel.delTime is null and studentModel.objectId = ?1")
    StudentModel findByObjectId(@Param("objectId") String objectId);

    //根据学生ID查找学生
    @Query("select studentModel from StudentModel studentModel where studentModel.delTime is null and studentModel.stuId = ?1")
    StudentModel findByStuId(@Param("stuId") String stuId);

    //根据学生姓名查找学生
    @Query("select studentModel from StudentModel studentModel where studentModel.delTime is null and studentModel.stuName = ?1")
    List<StudentModel> findByStuName(@Param("stuName") String stuName);

    //根据学生班级查找学生
    @Query("select studentModel from StudentModel studentModel where studentModel.delTime is null and studentModel.stuClass = ?1")
    List<StudentModel> findByStuClass(@Param("stuClass") String stuClass);

    //根据是否为助教查找学生
    @Query("select studentModel from StudentModel studentModel where studentModel.delTime is null and studentModel.stuIsAssistant = ?1")
    List<StudentModel> findByStuIsAssistant(@Param("stuIsAssistant") String stuIsAssistant);
}
