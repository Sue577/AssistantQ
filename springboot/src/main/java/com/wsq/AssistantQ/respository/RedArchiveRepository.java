package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.RedArchiveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 22:52
 */
public interface RedArchiveRepository extends JpaRepository<RedArchiveModel,String> {

    //通过视图查看所有档案信息
    @Query(value = "select * from redarchiveview where redarchiveview.del_time is null",nativeQuery = true)
    List<Object[]> listAll();

    //通过视图查看所有档案信息
    @Query(value = "select * from redarchiveview " +
            "where redarchiveview.del_time is null " +
            "AND redarchiveview.stu_name LIKE %?1% " +
            "And  redarchiveview.stu_number LIKE %?2% ",nativeQuery = true)
    List<Object[]> searchAdmin(@Param("stuName") String stuName, @Param("stuNumber") String stuNumber);

    //查询所有红色档案信息
    @Query("select redArchiveModel from RedArchiveModel redArchiveModel where redArchiveModel.delTime is null")
    List<RedArchiveModel> findALLRedArchive();

    //Id查询红色档案信息
    @Query("select redArchiveModel from RedArchiveModel redArchiveModel where redArchiveModel.objectId = :objectId and redArchiveModel.delTime is null")
    RedArchiveModel findById(@Param("objectId") String objectId);

    //学号查询红色档案信息
    @Query("select redArchiveModel from RedArchiveModel redArchiveModel where redArchiveModel.stuNumber = ?1 and redArchiveModel.delTime is null")
    List<RedArchiveModel> findByStuNumber(@Param("stuNumber") String stuNumber);

}
