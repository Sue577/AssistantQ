package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.CourseModel;
import com.wsq.AssistantQ.model.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:37
 */
@Repository
public interface CourseRepository extends JpaRepository<CourseModel,String>, JpaSpecificationExecutor<CourseModel> {
    //查找所有课程
    @Query(value = "select courseModel from CourseModel courseModel where courseModel.delTime is null")
    List<CourseModel> findAllCourse();

    //根据编号查找课程
    @Query("select courseModel from CourseModel courseModel where courseModel.delTime is null and courseModel.objectId = ?1")
    CourseModel findByObjectId(@Param("objectId") String objectId);

    //根据课程名称查找课程
    @Query("select courseModel from CourseModel courseModel where courseModel.delTime is null and courseModel.courName = ?1")
    CourseModel findByCourName(@Param("courName") String courName);

    //根据提交者ID查找课程
    @Query("select courseModel from CourseModel courseModel where courseModel.delTime is null and courseModel.courSubmitterId = ?1")
    List<CourseModel> findByCourSubmitterId(@Param("courSubmitterId") String courSubmitterId);

    //根据教师姓名查找课程
    @Query("select courseModel from CourseModel courseModel where courseModel.delTime is null and courseModel.courTeacherName = ?1")
    List<CourseModel> findByCourTeacherName(@Param("courTeacherName") String courTeacherName);
}
