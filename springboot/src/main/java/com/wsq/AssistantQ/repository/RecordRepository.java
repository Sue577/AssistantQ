package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.RecordModel;
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
public interface RecordRepository extends JpaRepository<RecordModel,String>, JpaSpecificationExecutor<RecordModel> {
    //查找所有工作记录
    @Query(value = "select recordModel from RecordModel recordModel where recordModel.delTime is null")
    List<RecordModel> findAllRecord();

    //根据编号查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.objectId = :objectId")
    RecordModel findByObjectId(@Param("objectId") String objectId);

    //根据工作日期查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoDate = ?1")
    List<RecordModel> findByRecoDate(@Param("recoDate") String recoDate);

    //根据相关课程查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoCourse = ?1")
    List<RecordModel> findByRecoCourse(@Param("recoCourse") String recoCourse);

    //根据提交者姓名查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoSubmitterName = ?1")
    List<RecordModel> findByRecoSubmitterName(@Param("recoSubmitterName") String recoSubmitterName);

    //根据提交者ID查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoSubmitterId = ?1")
    List<RecordModel> findByRecoSubmitterId(@Param("recoSubmitterId") String recoSubmitterId);

    //根据审核者ID查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoAuditorId = ?1")
    List<RecordModel> findByRecoAuditorId(@Param("recoAuditorId") String recoAuditorId);

    //根据记录审核状态查找工作记录
    @Query("select recordModel from RecordModel recordModel where recordModel.delTime is null and recordModel.recoStatus = ?1")
    List<RecordModel> findByRecoStatus(@Param("recoStatus") String recoStatus);
}
