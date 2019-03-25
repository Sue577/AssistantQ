package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.*;
import com.wsq.AssistantQ.service.*;
import com.wsq.AssistantQ.util.Result;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wsq.AssistantQ.enums.ResultEnum.ERROR_104;

/**
 * @author CYann
 * @date 2018-02-27 22:26
 */

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedArchiveService redArchiveService;
    @Autowired
    private AlumniInformationService alumniInformationService;
    @Autowired
    private PracticeInforService practiceInforService;
    @Autowired
    private ChatGroupService chatGroupService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private JavaMailSender javaMailSender;

    /*
     *用户操作 Start Here
     */
    //展示用户
    @GetMapping(value = "/listuser")
    public Result listUser(){
        List<CurrentUserModel> list = currentUserService.findAllUser();
        return Result.success(list);
    }

    //重置用户密码
    @PostMapping(value = "/resetuserpwd")
    public Result resetUserPwd(@RequestBody CurrentUserModel currentUserModel) throws Exception {
        currentUserService.resetPwd(currentUserModel);
        CurrentUserModel userItem = currentUserService.findByObjectId(currentUserModel.getObjectId());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //将承载的字符转换成字符串
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("Archive Book<xjx@zucc.edu.cn>");
        helper.setTo(userItem.getLoginEmail());
        helper.setSubject("Archive Book - Reset Operation");
        helper.setText("Your reset password:"+ "       123456      " + "Please change your login password as soon as possible when you sign in! " );
        javaMailSender.send(mimeMessage);
        List<CurrentUserModel> list = currentUserService.findAllUser();
        return Result.success(list);
    }

    //删除用户
    @PostMapping(value = "/deleteuser")
    public Result deleteUser(@RequestBody CurrentUserModel currentUserModel){
        currentUserService.delete(currentUserModel);
        List<CurrentUserModel> list = currentUserService.findAllUser();
        return Result.success(list);
    }

    //根据  多条件动态查询用户
    @PostMapping(value = "/searchuser")
    public Result searchUser(@RequestBody CurrentUserModel currentUserModel){
        List<CurrentUserModel> list = currentUserService.findAllByAdvancedForm(currentUserModel);
        return Result.success(list);
    }

    /*
     *用户操作 end Here
     */


    /*
     *学生用户操作 Start Here
    */

    //展示学生用户
    @GetMapping(value = "/liststu")
    public Result listStuUser(){
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //增加学生用户
    @PostMapping(value = "/addstu")
    public Result addStuUser(@RequestBody UserModel userModel){
        userService.add(userModel);
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //批量增加学生用户 stuName / stuNumber / stuMajor / stuEndYear / redParty / stuClass / stuPower / stuStartYear
    @PostMapping(value = "/addstubyfile")
    public Result addStuByfile(@RequestParam("file")MultipartFile file){
        if (file != null){
            System.out.println("File Not NULL");
            String fileName = file.getOriginalFilename();
            List<Map<String,String>> list = fileService.viewExcelFile("xlsx",file);
            // System.out.println(list);
            for (int i=0;i<list.size();i++){
                Map<String,String> tempMap = list.get(i);
                UserModel userModel = new UserModel();
                userModel.setStuName(tempMap.get("stuName"));
                userModel.setStuNumber(tempMap.get("stuNumber"));
                userModel.setStuMajor(tempMap.get("stuMajor"));
                userModel.setStuEndYear(tempMap.get("stuEndYear"));
                userModel.setRedParty(Integer.parseInt(tempMap.get("redParty")));
                userModel.setStuClass(tempMap.get("stuClass"));
                userModel.setStuPower(Integer.parseInt(tempMap.get("stuPower")));
                userModel.setStuStartYear(tempMap.get("stuStartYear"));
                userService.add(userModel);
            }
        } else {
            System.out.println("File is NULL");
        }
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //删除学生用户
    @PostMapping(value = "/deletestu")
    public Result deleteStuUser(@RequestBody UserModel userModel){
        userService.delete(userModel);
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //根据 名字 学号 专业 毕业年份 入学年份 多条件动态查询学生用户
    @PostMapping(value = "/searchstu")
    public Result searchStuUser(@RequestBody UserModel userModel){
        List<UserModel> list = userService.findAllByAdvancedForm(userModel);
        return Result.success(list);
    }

    //动态修改更新学生用户
    @PostMapping(value = "/modifystu")
    public Result modifyStuUser(@RequestBody UserModel userModel){
        userService.update(userModel);
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //修改用户权限信息
    @PostMapping(value = "/editstu")
    public Result editStuUser(UserModel userModel){
        userService.updatePower(userModel);
        return Result.success();
    }

    /*
     *学生用户操作 - End 待添加
     */


    /*
    * 档案信息操作 - Start
    *
        CREATE VIEW archiveview AS
    SELECT
        tb_archive.*,
        tb_user.stu_name
    FROM
        tb_archive,
        tb_user
    WHERE
        tb_archive.stu_number = tb_user.stu_number
    */
    //展示档案
    @GetMapping(value = "/listarchive")
    public Result listArchive(){
        List<Object[]> list = archiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //新增档案
    @PostMapping(value = "/addarchive")
    public Result addArchiver(@RequestBody ArchiveModel archiveModel){
        archiveService.add(archiveModel);
        List<Object[]> list = archiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //删除档案
    @PostMapping(value = "/deletearchive")
    public Result deleteArchive(@RequestBody ArchiveModel archiveModel){
        archiveService.delete(archiveModel);
        List<Object[]> list = archiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //动态修改更新档案
    @PostMapping(value = "/modifyarchive")
    public Result modifyArchive(@RequestBody ArchiveModel archiveModel){
        archiveService.update(archiveModel);
        List<Object[]> list = archiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //根据 学号 目前单位 多条件动态查询档案
    @PostMapping(value = "/searcharchive")
    public Result searchArchive(@RequestBody UserModel userModel){
        List<Object[]> list = archiveService.searchAdmin(userModel.getStuName(),userModel.getStuNumber());
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //批量增加档案  stuNumber / unit / unitAddress / flowDate
    @PostMapping(value = "/addarchviebyfile")
    public Result addArchiveByfile(@RequestParam("file")MultipartFile file){
        if (file != null){
            // System.out.println("File Not NULL");
            String fileName = file.getOriginalFilename();
            List<Map<String,String>> list = fileService.viewExcelFile("xlsx",file);
            for (int i=0;i<list.size();i++){
                Map<String,String> tempMap = list.get(i);
                ArchiveModel archiveModel = new ArchiveModel();
                archiveModel.setStuNumber(tempMap.get("stuNumber"));
                archiveModel.setUnit(tempMap.get("unit"));
                archiveModel.setUnitAddress(tempMap.get("unitAddress"));
                archiveModel.setFlowDate(tempMap.get("flowDate"));
                archiveService.add(archiveModel);
            }
        } else {
            throw new MyException(ResultEnum.ERROR_103);
            // System.out.println("File is NULL");
        }
        List<Object[]> list = archiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("flowDate",objects[4]);
            tempMap.put("stuNumber",objects[5]);
            tempMap.put("unit",objects[6]);
            tempMap.put("unitAddress",objects[7]);
            tempMap.put("stuName",objects[8]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }
    /*
     * 档案信息操作 - End
     */

    /*
     * 户口信息操作 - Start
     *         CREATE VIEW accountview AS
    SELECT
        tb_account.*,
        tb_user.stu_name
    FROM
        tb_account,
        tb_user
    WHERE
        tb_account.stu_number = tb_user.stu_number
     */
    //展示户口
    @GetMapping(value = "/listaccount")
    public Result listAccount(){
        List<Object[]> list = accountService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //新增户口
    @PostMapping(value = "/addaccount")
    public Result addAccount(@RequestBody AccountModel accountModel){
        accountService.add(accountModel);
        List<Object[]> list = accountService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //删除户口
    @PostMapping(value = "/deleteaccount")
    public Result deleteAccount(@RequestBody AccountModel accountModel){
        accountService.delete(accountModel);
        List<Object[]> list = accountService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //根据 学号 查询户口
    @PostMapping(value = "/searchaccount")
    public Result searchAccount(@RequestBody UserModel userModel){
        List<Object[]> list = accountService.searchAdmin(userModel.getStuName(),userModel.getStuNumber());
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }
    //动态修改更新户口
    @PostMapping(value = "/modifyaccount")
    public Result modifyAccount(@RequestBody AccountModel accountModel){
        accountService.update(accountModel);
        List<Object[]> list = accountService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //批量增加户口  stuNumber / accountAddress / accountDate
    @PostMapping(value = "/addaccountbyfile")
    public Result addAccountByfile(@RequestParam("file")MultipartFile file){
        if (file != null){
            // System.out.println("File Not NULL");
            String fileName = file.getOriginalFilename();
            List<Map<String,String>> list = fileService.viewExcelFile("xlsx",file);
            for (int i=0;i<list.size();i++){
                Map<String,String> tempMap = list.get(i);
                AccountModel accountModel = new AccountModel();
                accountModel.setStuNumber(tempMap.get("stuNumber"));
                accountModel.setAccountAddress(tempMap.get("accountAddress"));
                accountModel.setAccountDate(tempMap.get("accountDate"));
                accountService.add(accountModel);
            }
        } else {
            throw new MyException(ResultEnum.ERROR_103);
            //System.out.println("File is NULL");
        }
        List<Object[]> list = accountService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("accountAddress",objects[4]);
            tempMap.put("accountDate",objects[5]);
            tempMap.put("stuNumber",objects[6]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }
    /*
     * 户口信息操作 - End
     */

    /*
     * 红色档案信息操作 - Start
     *         CREATE VIEW redarchiveview AS
    SELECT
        tb_redarchive.*,
        tb_user.stu_name
    FROM
        tb_redarchive,
        tb_user
    WHERE
        tb_redarchive.stu_number = tb_user.stu_number
     */

    //展示红色档案
    @GetMapping(value = "/listredarchive")
    public Result listRedArchive(){
        List<Object[]> list = redArchiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //新增红色档案
    @PostMapping(value = "/addredarchive")
    public Result addRedArchive(@RequestBody RedArchiveModel redArchiveModel){
        redArchiveService.add(redArchiveModel);
        List<Object[]> list = redArchiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //删除红色档案
    @PostMapping(value = "/deleteredarchive")
    public Result deleteRedArchive(@RequestBody RedArchiveModel redArchiveModel){
        redArchiveService.delete(redArchiveModel);
        List<Object[]> list = redArchiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //查询红色档案
    @PostMapping(value = "/searchredarchive")
    public Result searchRedArchive(@RequestBody UserModel userModel){
        List<Object[]> list = redArchiveService.searchAdmin(userModel.getStuName(),userModel.getStuNumber());
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //动态修改更新红色档案
    @PostMapping(value = "/modifyredarchive")
    public Result modifyRedArchive(@RequestBody RedArchiveModel redArchiveModel){
        redArchiveService.update(redArchiveModel);
        List<Object[]> list = redArchiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //批量增加红色档案  stuNumber / becomeActivistDate / introducer / joinDate
    @PostMapping(value = "/addredarchivebyfile")
    public Result addRedArchiveByfile(@RequestParam("file")MultipartFile file){
        if (file != null){
            // System.out.println("File Not NULL");
            String fileName = file.getOriginalFilename();
            List<Map<String,String>> list = fileService.viewExcelFile("xlsx",file);
            for (int i=0;i<list.size();i++){
                Map<String,String> tempMap = list.get(i);
                RedArchiveModel redArchiveModel = new RedArchiveModel();
                redArchiveModel.setStuNumber(tempMap.get("stuNumber"));
                redArchiveModel.setActivistDate(tempMap.get("activistDate"));
                redArchiveModel.setIntroducer(tempMap.get("introducer"));
                redArchiveModel.setIntroducerB(tempMap.get("introducerB"));
                redArchiveModel.setJoinDate(tempMap.get("joinDate"));
                redArchiveService.add(redArchiveModel);
            }
        } else {
            throw new MyException(ResultEnum.ERROR_103);
            // System.out.println("File is NULL");
        }
        List<Object[]> list = redArchiveService.listAll();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("activistDate",objects[4]);
            tempMap.put("introducer",objects[5]);
            tempMap.put("joinDate",objects[6]);
            tempMap.put("stuNumber",objects[7]);
            tempMap.put("introducerB",objects[7]);
            tempMap.put("stuName",objects[7]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }
    /*
     * 红色档案信息操作 - End
     */

    /*
     * 校友信息操作 - Start
     *
     *校友信息视图展示
        CREATE VIEW alumniview AS
    SELECT
        tb_alumniinformation.*,
        tb_user.object_id AS userobject_id,
        tb_user.stu_name,
        tb_user.stu_major,
        tb_user.stu_class,
        tb_user.stu_start_year,
        tb_user.stu_end_year,
        tb_user.current_email,
        tb_user.current_phone,
        tb_currentuser.avatar
    FROM
        tb_alumniinformation,
        tb_user,
        tb_currentuser
    WHERE
        tb_alumniinformation.stu_number = tb_user.stu_number
            AND tb_alumniinformation.stu_number = tb_currentuser.stu_number
    */

    //查看所有校友资料
    @GetMapping(value = "/listalumniinfor")
    public Result viewAllAlumni(){
        List<Object[]> list = alumniInformationService.findAllAlumniInformation();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("company",objects[4]);
            tempMap.put("companyAddress",objects[5]);
            tempMap.put("industry",objects[6]);
            tempMap.put("occupation",objects[7]);
            tempMap.put("salary",objects[8]);
            tempMap.put("stuNumber",objects[9]);
            tempMap.put("endDate",objects[10]);
            tempMap.put("startDate",objects[11]);
            tempMap.put("userobjectId",objects[12]);
            tempMap.put("stuName",objects[13]);
            tempMap.put("stuMajor",objects[14]);
            tempMap.put("stuClass",objects[15]);
            tempMap.put("stuStartYear",objects[16]);
            tempMap.put("stuEndYear",objects[17]);
            tempMap.put("currentEmail",objects[18]);
            tempMap.put("currentPhone",objects[19]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //根据校友学号查找校友资料
    @PostMapping(value = "/searchalumniinfor")
    public Result searchAlumni(@RequestBody UserModel userModel){
        List<Object[]> list = alumniInformationService.searchAdmin(userModel.getStuNumber(),userModel.getStuName());
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("company",objects[4]);
            tempMap.put("companyAddress",objects[5]);
            tempMap.put("industry",objects[6]);
            tempMap.put("occupation",objects[7]);
            tempMap.put("salary",objects[8]);
            tempMap.put("stuNumber",objects[9]);
            tempMap.put("endDate",objects[10]);
            tempMap.put("startDate",objects[11]);
            tempMap.put("userobjectId",objects[12]);
            tempMap.put("stuName",objects[13]);
            tempMap.put("stuMajor",objects[14]);
            tempMap.put("stuClass",objects[15]);
            tempMap.put("stuStartYear",objects[16]);
            tempMap.put("stuEndYear",objects[17]);
            tempMap.put("currentEmail",objects[18]);
            tempMap.put("currentPhone",objects[19]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    /*
     * 校友信息操作 - End
     */

    /*
     * 实习生信息操作 - Start
     *
     *实习生信息视图展示
		CREATE VIEW practiceview AS
    SELECT
        tb_practiceinfor.*,
        tb_user.object_id AS userobject_id,
        tb_user.stu_name,
        tb_user.stu_major,
        tb_user.stu_class,
        tb_user.stu_start_year,
        tb_user.stu_end_year,
        tb_user.current_email,
        tb_user.current_phone,
        tb_currentuser.avatar
    FROM
        tb_practiceinfor,
        tb_user,
        tb_currentuser
    WHERE
        tb_practiceinfor.stu_number = tb_user.stu_number
            AND tb_practiceinfor.stu_number = tb_currentuser.stu_number
    */
    //查看所有实习生资料
    @GetMapping(value = "/listpractice")
    public Result viewAllPractice(){
        List<Object[]> list = practiceInforService.findAllPracticeInformation();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("company",objects[4]);
            tempMap.put("companyAddress",objects[5]);
            tempMap.put("industry",objects[6]);
            tempMap.put("occupation",objects[7]);
            tempMap.put("salary",objects[8]);
            tempMap.put("stuNumber",objects[9]);
            tempMap.put("endDate",objects[10]);
            tempMap.put("startDate",objects[11]);
            tempMap.put("userobjectId",objects[12]);
            tempMap.put("stuName",objects[13]);
            tempMap.put("stuMajor",objects[14]);
            tempMap.put("stuClass",objects[15]);
            tempMap.put("stuStartYear",objects[16]);
            tempMap.put("stuEndYear",objects[17]);
            tempMap.put("currentEmail",objects[18]);
            tempMap.put("currentPhone",objects[19]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }

    //根据实习生学号查找实习生资料
    @PostMapping(value = "/searchpractice")
    public Result searchPractice(@RequestBody UserModel userModel){
        List<Object[]> list = practiceInforService.searchAdmin(userModel.getStuNumber(),userModel.getStuName());
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("objectId",objects[0]);
            tempMap.put("createTime",objects[1]);
            tempMap.put("delTime",objects[2]);
            tempMap.put("updateTime",objects[3]);
            tempMap.put("company",objects[4]);
            tempMap.put("companyAddress",objects[5]);
            tempMap.put("industry",objects[6]);
            tempMap.put("occupation",objects[7]);
            tempMap.put("salary",objects[8]);
            tempMap.put("stuNumber",objects[9]);
            tempMap.put("endDate",objects[10]);
            tempMap.put("startDate",objects[11]);
            tempMap.put("userobjectId",objects[12]);
            tempMap.put("stuName",objects[13]);
            tempMap.put("stuMajor",objects[14]);
            tempMap.put("stuClass",objects[15]);
            tempMap.put("stuStartYear",objects[16]);
            tempMap.put("stuEndYear",objects[17]);
            tempMap.put("currentEmail",objects[18]);
            tempMap.put("currentPhone",objects[19]);
            returnList.add(tempMap);
        }
        return Result.success(returnList);
    }


    /*
     * 实习生信息操作 - End
     */

    /*
     * 交流群信息操作 - Start
     */
    //展示交流群
    @GetMapping(value = "/listchatgroup")
    public Result listChatGroup(){
        List<ChatGroupModel> list = chatGroupService.findAll();
        return Result.success(list);
    }


    //增加交流群
    @PostMapping(value = "/addchatgroup")
    public Result addChatGroup(@RequestBody ChatGroupModel chatGroupModel){
        chatGroupService.add(chatGroupModel);
        List<ChatGroupModel> list = chatGroupService.findAll();
        return Result.success(list);
    }

    //删除交流群
    @PostMapping(value = "/deletechatgroup")
    public Result deleteChatGroup(@RequestBody ChatGroupModel chatGroupModel){
        chatGroupService.delete(chatGroupModel);
        List<ChatGroupModel> list = chatGroupService.findAll();
        return Result.success(list);
    }

    //根据 专业和毕业时间 多条件动态查询交流群
    @PostMapping(value = "/searchchatgroup")
    public Result searchChatGroup(@RequestBody ChatGroupModel chatGroupModel){
        List<ChatGroupModel> list = chatGroupService.findAllByAdvancedForm(chatGroupModel);
        return Result.success(list);
    }

    //动态修改更新交流群
    @PostMapping(value = "/modifychatgroup")
    public Result modifyChatGroup(@RequestBody ChatGroupModel chatGroupModel){
        chatGroupService.update(chatGroupModel);
        List<ChatGroupModel> list = chatGroupService.findAll();
        return Result.success(list);
    }

    /*
     * 交流群信息操作 - End
     */

    /*
     * 通知信息操作 - Start
     */

    //展示所有通知消息
    @GetMapping(value = "/listnotices")
    public Result listNotice(){
        List<MsgModel> list = msgService.findAll();
        return Result.success(list);
    }

    //新增通知消息
    @PostMapping(value = "/addnotice")
    public Result addNotice(@RequestBody MsgModel msgModel){
        msgService.addBoard(msgModel);
        List<MsgModel> list = msgService.findAll();
        return Result.success(list);
    }
    //新增回复消息
    @PostMapping(value = "/replynotice")
    public Result replyNotice(@RequestBody MsgModel msgModel){
        msgService.addReply(msgModel);
        List<MsgModel> list = msgService.findAll();
        return Result.success(list);
    }

    //删除通知消息
    @PostMapping(value = "/deletenotice")
    public Result deleteNotice(@RequestBody MsgModel msgModel){
        msgService.delete(msgModel);
        List<MsgModel> list = msgService.findAll();
        return Result.success(list);
    }

    //动态修改更新所有通知消息
    @PostMapping(value = "/modifynotice")
    public Result modifyNotice(@RequestBody MsgModel msgModel){
        msgService.update(msgModel);
        List<MsgModel> list = msgService.findAll();
        return Result.success(list);
    }

    /*
     * 通知信息操作 - End
     */
}
