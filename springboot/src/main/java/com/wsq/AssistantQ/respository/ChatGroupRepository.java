package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.ChatGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-04-01 17:32
 */
public interface ChatGroupRepository extends JpaRepository<ChatGroupModel,String>, JpaSpecificationExecutor<ChatGroupModel> {
    //查询所有交流群信息
    @Query("select chatGroupModel from ChatGroupModel chatGroupModel where chatGroupModel.delTime is null")
    List<ChatGroupModel> findALL();

    //查询所有交流群信息
    @Query("select chatGroupModel from ChatGroupModel chatGroupModel where chatGroupModel.objectId = :objectId and chatGroupModel.delTime is null")
    ChatGroupModel findById(@Param("objectId") String objectId);

    //通过专业和毕业时间 查询所有交流群信息
    @Query("select chatGroupModel from ChatGroupModel chatGroupModel where chatGroupModel.stuMajor = ?1 and chatGroupModel.stuEndYear = ?2 and chatGroupModel.delTime is null")
    ChatGroupModel findByStuMajorAndAndStuEndYear(@Param("stuMajor") String stuMajor,@Param("stuEndYear") String stuEndYear );

}
