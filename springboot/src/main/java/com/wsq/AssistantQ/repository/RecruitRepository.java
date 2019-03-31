package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.CourseModel;
import com.wsq.AssistantQ.model.RecruitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:39
 */
@Repository
public interface RecruitRepository extends JpaRepository<RecruitModel,String>, JpaSpecificationExecutor<RecruitModel> {
    //查找所有招聘信息
    @Query(value = "select recruitModel from RecruitModel recruitModel where recruitModel.delTime is null")
    List<RecruitModel> findAllRecruit();

    //根据编号查找招聘信息
    @Query("select recruitModel from RecruitModel recruitModel where recruitModel.delTime is null and recruitModel.objectId = :objectId")
    RecruitModel findByObjectId(@Param("objectId") String objectId);

    //根据课程名称查找招聘信息
    @Query("select recruitModel from RecruitModel recruitModel where recruitModel.delTime is null and recruitModel.recrCourse = ?1")
    List<RecruitModel> findByRecrCourse(@Param("recrCourse") String recrCourse);

    //根据招聘信息标题查找招聘信息
    @Query("select recruitModel from RecruitModel recruitModel where recruitModel.delTime is null and recruitModel.recrTitle = ?1")
    List<RecruitModel> findByRecrTitle(@Param("recrTitle") String recrTitle);

    //根据提交者ID查找招聘信息
    @Query("select recruitModel from RecruitModel recruitModel where recruitModel.delTime is null and recruitModel.recrSubmitterId = ?1")
    List<RecruitModel> findByRecrSubmitterId(@Param("recrSubmitterId") String recrSubmitterId);

}
