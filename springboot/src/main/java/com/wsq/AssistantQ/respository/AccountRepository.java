package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-02-28 21:19
 */
public interface AccountRepository extends JpaRepository<AccountModel,String>,JpaSpecificationExecutor<AccountModel> {

    //通过视图查看所有户口信息
    @Query(value = "select * from accountview where accountview.del_time is null",nativeQuery = true)
    List<Object[]> listAll();

    //通过视图查看所有户口信息
    @Query(value = "select * from accountview " +
            "where accountview.del_time is null " +
            "AND accountview.stu_name LIKE %?1% " +
            "And  accountview.stu_number LIKE %?2% ",nativeQuery = true)
    List<Object[]> searchAdmin(@Param("stuName") String stuName, @Param("stuNumber") String stuNumber);

    //查询所有户口信息
    @Query("select accountModel from AccountModel accountModel where accountModel.delTime is null")
    List<AccountModel> findALLAccount();

    //Id查询户口信息
    @Query("select accountModel from AccountModel accountModel where accountModel.objectId = :objectId and accountModel.delTime is null")
    AccountModel findById(@Param("objectId") String objectId);

    //学号查询户口信息
    @Query("select accountModel from AccountModel accountModel where accountModel.stuNumber = ?1 and accountModel.delTime is null order by accountModel.accountDate asc ")
    List<AccountModel> findByStuNumber(@Param("stuNumber") String stuNumber);


}
