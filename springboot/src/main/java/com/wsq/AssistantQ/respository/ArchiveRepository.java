package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.ArchiveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 22:52
 */
public interface ArchiveRepository extends JpaRepository<ArchiveModel,String>, JpaSpecificationExecutor<ArchiveModel> {

    //查询所有档案信息
    @Query("select archiveModel from ArchiveModel archiveModel where archiveModel.delTime is null")
    List<ArchiveModel> findALLArchive();

    //通过视图查看所有档案信息
    @Query(value = "select * from archiveview where archiveview.del_time is null",nativeQuery = true)
    List<Object[]> listAll();

    //通过视图查看所有档案信息
    @Query(value = "select * from archiveview " +
            "where archiveview.del_time is null " +
            "AND archiveview.stu_name LIKE %?1% " +
            "And  archiveview.stu_number LIKE %?2% ",nativeQuery = true)
    List<Object[]> searchAdmin(@Param("stuName") String stuName, @Param("stuNumber") String stuNumber);

    //Id查询档案信息
    @Query("select archiveModel from ArchiveModel archiveModel where archiveModel.objectId = :objectId and archiveModel.delTime is null")
    ArchiveModel findById(@Param("objectId") String objectId);

    //学号查询档案信息
    @Query("select archiveModel from ArchiveModel archiveModel where archiveModel.stuNumber = ?1 and archiveModel.delTime is null order by archiveModel.flowDate asc")
    List<ArchiveModel> findByStuNumber(@Param("stuNumber") String stuNumber);

    //学号和目前单位查询档案信息
    @Query("select archiveModel from ArchiveModel archiveModel where archiveModel.stuNumber = ?1 and archiveModel.unit = ?2 and archiveModel.delTime is null")
    ArchiveModel findByStuNumberAndUnit(@Param("stuNumber") String stuNumber, @Param("unit") String unit);

}
