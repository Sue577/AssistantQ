package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.AlumniInformationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 23:13
 */
public interface AlumniInformationRepository extends JpaRepository<AlumniInformationModel,String>{
    //查询所有校友信息
    @Query("select alumniInformationModel from AlumniInformationModel alumniInformationModel where alumniInformationModel.delTime is null")
    List<AlumniInformationModel> findALLAlumniInformation();

    //Id查询校友信息
    @Query("select alumniInformationModel from AlumniInformationModel alumniInformationModel where alumniInformationModel.objectId = :objectId and alumniInformationModel.delTime is null")
    AlumniInformationModel findById(@Param("objectId") String objectId);

    //学校查询校友信息
    @Query("select alumniInformationModel from AlumniInformationModel alumniInformationModel where alumniInformationModel.stuNumber = ?1 and alumniInformationModel.delTime is null")
    List<AlumniInformationModel> findByStuNum(@Param("stuNumber") String stuNumber);

    //学校Id查询校友信息
    @Query(value = "select * from alumniview where alumniview.del_time is null and alumniview.stu_number = ?1",nativeQuery = true)
    List<Object[]> findByStuNumber(@Param("stuNumber") String stuNumber);

    //通过视图查看所有校友信息
    @Query(value = "select * from alumniview where alumniview.del_time is null",nativeQuery = true)
    List<Object[]> findAllAlumni();

    //通过 学生姓名 学生学号 视图查询
    @Query(value = "select * from alumniview " +
            "where alumniview.del_time is null " +
            "AND alumniview.stu_name LIKE %?1%  " +
            "OR  alumniview.stu_number like %?2%  ",nativeQuery = true)
    List<Object[]> searchAdmin(@Param("stuName") String stuName,@Param("stuNumber") String stuNumber);

    //通过 学生姓名 学生班级 等视图查询
    @Query(value = "select * from alumniview " +
            "where alumniview.del_time is null " +
            "AND alumniview.stu_name LIKE %?1%  " +
            "OR  alumniview.stu_class like %?2%  " +
            "OR  alumniview.stu_major like %?3%  " +
            "OR  alumniview.stu_number like %?4% ",nativeQuery = true)
    List<Object[]> search(@Param("stuName") String stuName,
                          @Param("stuClass") String stuClass,
                          @Param("stuMajor") String stuMajor,
                          @Param("stuNumber") String stuNumber);


    //通过视图每个校友最新信息
    @Query(value = "SELECT *,max(start_date) FROM (SELECT * FROM alumniview where alumniview.del_time is null ORDER BY start_date DESC) A GROUP BY stu_number",nativeQuery = true)
    List<Object[]> findMaxAlumni();

    //通过视图查看所有校友信息
    @Query(value = "SELECT *,max(start_date) FROM " +
            "(SELECT * FROM alumniview " +
            "where alumniview.del_time is null " +
            "AND alumniview.stu_name LIKE %?1% " +
            "OR  alumniview.stu_class LIKE %?2% " +
            "OR  alumniview.stu_major like %?3% " +
            "OR  alumniview.stu_number like %?4% " +
            "ORDER BY start_date DESC) A " +
            "GROUP BY stu_number ",nativeQuery = true)
    List<Object[]> searchMaxAlumni(@Param("stuName") String stuName,
                                   @Param("stuClass") String stuClass,
                                   @Param("stuMajor") String stuMajor,
                                   @Param("stuNumber") String stuNumber);

}
