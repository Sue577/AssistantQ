package com.wsq.AssistantQ.repository;

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
public interface TeacherRepository extends JpaRepository<TeacherModel, String>, JpaSpecificationExecutor<TeacherModel> {
    //查找所有教师
    @Query(value = "select teacherModel from TeacherModel teacherModel where teacherModel.delTime is null")
    List<TeacherModel> findAllTeacher();

    //根据编号查找教师
    @Query("select teacherModel from TeacherModel teacherModel where teacherModel.delTime is null and teacherModel.objectId = :objectId")
    TeacherModel findByObjectId(@Param("objectId") String objectId);

    //根据教师ID查找教师
    @Query("select teacherModel from TeacherModel teacherModel where teacherModel.delTime is null and teacherModel.teachId = ?1")
    TeacherModel findByTeachId(@Param("teachId") String teachId);

    //根据教师姓名查找教师
    @Query("select teacherModel from TeacherModel teacherModel where teacherModel.delTime is null and teacherModel.teachName = ?1")
    List<TeacherModel> findByTeachName(@Param("teachName") String teachName);
}
