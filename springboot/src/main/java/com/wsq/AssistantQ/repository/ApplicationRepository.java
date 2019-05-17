package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.ApplicationModel;
import com.wsq.AssistantQ.model.RecruitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:33
 */
@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel,String>, JpaSpecificationExecutor<ApplicationModel> {
    //查找所有报名信息
    @Query(value = "select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null")
    List<ApplicationModel> findAllApplication();

    //根据编号查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.objectId = ?1")
    ApplicationModel findByObjectId(@Param("objectId") String objectId);

    //根据招聘编号查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applRecruitId = ?1")
    List<ApplicationModel> findByApplRecruitId(@Param("applRecruitId") String applRecruitId);

    //根据提交者姓名查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applSubmitterName = ?1")
    List<ApplicationModel> findByApplSubmitterName(@Param("applSubmitterName") String applSubmitterName);

    //根据提交者ID查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applSubmitterId = ?1")
    List<ApplicationModel> findByApplSubmitterId(@Param("applSubmitterId") String applSubmitterId);

    //根据审核者ID查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applAuditorId = ?1")
    List<ApplicationModel> findByApplAuditorId(@Param("applAuditorId") String applAuditorId);

    //根据报名审核状态查找报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applStatus = ?1")
    List<ApplicationModel> findByApplStatus(@Param("applStatus") String applStatus);

    //根据招聘编号和提交者ID查询报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applRecruitId = ?1 and  applicationModel.applSubmitterId = ?2 ")
    ApplicationModel findByApplRecruitIdAndApplSubmitterId(@Param("applRecruitId") String applRecruitId,@Param("applSubmitterId") String applSubmitterId);

    //根据报名审核状态和提交者ID查询报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applStatus = ?1 and  applicationModel.applSubmitterId = ?2 ")
    List<ApplicationModel> findByApplStatusAndApplSubmitterId(@Param("applStatus") String applRecruitId,@Param("applSubmitterId") String applSubmitterId);

    //根据报名审核状态和审核者ID查询报名信息
    @Query("select applicationModel from ApplicationModel applicationModel where applicationModel.delTime is null and applicationModel.applStatus = ?1 and  applicationModel.applAuditorId = ?2 ")
    List<ApplicationModel> findByApplStatusAndApplAuditorId(@Param("applStatus") String applRecruitId,@Param("applAuditorId") String applAuditorId);
}
