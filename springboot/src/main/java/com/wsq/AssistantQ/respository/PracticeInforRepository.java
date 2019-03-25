package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.PracticeInforModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 23:15
 */
public interface PracticeInforRepository extends JpaRepository<PracticeInforModel,String> {
    //查询所有实习生信息
    @Query("select practiceInforModel from PracticeInforModel practiceInforModel where practiceInforModel.delTime is null")
    List<PracticeInforModel> findALLPracticeInfor();

    //Id查询实习生信息
    @Query("select practiceInforModel from PracticeInforModel practiceInforModel where practiceInforModel.objectId = :objectId and practiceInforModel.delTime is null")
    PracticeInforModel findById(@Param("objectId") String objectId);

    //学号查询实习生信息
    @Query("select practiceInforModel from PracticeInforModel practiceInforModel where practiceInforModel.stuNumber = ?1 and practiceInforModel.delTime is null")
    List<PracticeInforModel> findByStuNum(@Param("stuNumber") String stuNumber);

    //学校Id查询实习生信息
    @Query(value = "select * from practiceview where practiceview.del_time is null and practiceview.stu_number = ?1",nativeQuery = true)
    List<Object[]> findByStuNumber(@Param("stuNumber") String stuNumber);

    //通过视图查看所有实习生信息
    @Query(value = "select * from practiceview where practiceview.del_time is null",nativeQuery = true)
    List<Object[]> findAllPractice();

    //通过视图查看所有校友信息
    @Query(value = "select * from practiceview " +
            "where practiceview.del_time is null " +
            "AND practiceview.stu_name LIKE %?1% " +
            "OR  practiceview.stu_number LIKE %?2% ",nativeQuery = true)
    List<Object[]> searchAdmin(@Param("stuName") String stuName, @Param("stuNumber") String stuNumber);

    //通过视图查看所有实习生信息
    @Query(value = "select * from practiceview " +
            "where practiceview.del_time is null " +
            "AND practiceview.stu_name LIKE %?1% " +
            "OR  practiceview.stu_class LIKE %?2% ",nativeQuery = true)
    List<Object[]> search(@Param("stuName") String stuName, @Param("stuClass") String stuClass);

    //通过视图每个实习生最新信息
    @Query(value = "SELECT *,max(start_date) FROM (SELECT * FROM practiceview where practiceview.del_time is null ORDER BY start_date DESC) A GROUP BY stu_number",nativeQuery = true)
    List<Object[]> findMaxPractice();

    //通过视图查看所有实习生信息
    @Query(value = "SELECT *,max(start_date) FROM " +
            "(SELECT * FROM practiceview " +
            "where practiceview.del_time is null " +
            "AND practiceview.stu_name LIKE %?1% " +
            "OR  practiceview.stu_class LIKE %?2% " +
            "OR  practiceview.stu_major like %?3% " +
            "OR  practiceview.stu_number like %?4% " +
            "ORDER BY start_date DESC) A " +
            "GROUP BY stu_number ",nativeQuery = true)
    List<Object[]> searchMaxPractice(@Param("stuName") String stuName,
                                     @Param("stuClass") String stuClass,
                                     @Param("stuMajor") String stuMajor,
                                     @Param("stuNumber") String stuNumber);
}
