package com.wsq.AssistantQ.controllerguest;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.AlumniInformationModel;
import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.MsgModel;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.AlumniInformationService;
import com.wsq.AssistantQ.service.CurrentUserService;
import com.wsq.AssistantQ.service.MsgService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author CYann
 * @date 2018-04-28 10:36
 */
@RestController
@CrossOrigin
public class GuestController {
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private JavaMailSender javaMailSender;

    //登录
    @PostMapping(value = "/login")
    public Result login (@RequestBody CurrentUserModel currentUserModel, HttpSession session){
        CurrentUserModel loginUser = currentUserService.findByLoginEmailAndLoginPwd(currentUserModel.getLoginEmail(),currentUserModel.getLoginPsw());
        if(loginUser != null){
            session.setAttribute("ID", loginUser.getObjectId());
            session.setAttribute("TYPE", loginUser.getUserType());
            loginUser.setLoginPsw(null);
            Map result = new HashMap();
            result.put("currentAuthority", loginUser.getUserType());
            result.put("status", "ok");
            result.put("type", "account");
            result.put("SessionId", session.getId());
            return Result.success(result);
        } else {
            Map result = new HashMap();
            result.put("currentAuthority", "guest");
            result.put("status", "error");
            result.put("type", "account");
            // result.put("SessionId", session.getId());
            return Result.loginerror(result);
        }
    }

    //注册
    @PostMapping(value = "/register")
    public Result register(@RequestBody CurrentUserModel currentUserModel){
        CurrentUserModel item = currentUserService.findByLoginEmail(currentUserModel.getLoginEmail());

        CurrentUserModel item_activecode = currentUserService.findByLoginEmail(currentUserModel.getLoginEmail());

        List<UserModel> item_stuname = userService.findByStuName(currentUserModel.getStuName());

        UserModel item_stunumber = userService.findByStuNumber(currentUserModel.getStuNumber());

        // System.out.println(!item_stuname.isEmpty());
        if(item_activecode.getActiveCode().equals(currentUserModel.getActiveCode()) == false ){
            return Result.error(109,"该验证码无效");
        }
        else if(item_stuname.isEmpty()){
            return Result.error(108,"无该名字学生用户");
        }
        else if(item_stunumber == null && item_stunumber.getStuName().equals(currentUserModel.getStuName())){
            return Result.error(108,"无该名字学生用户");
        }
        else {
            // currentUserModel.setActiveStatus(1);
            item.setLoginPsw(currentUserModel.getLoginPsw());
            item.setStuName(currentUserModel.getStuName());
            item.setStuNumber(currentUserModel.getStuNumber());
            item.setMobilePhone(currentUserModel.getMobilePhone());
            currentUserService.add(item);
            Map result = new HashMap();
            result.put("status", "ok");
            result.put("type", "user");
            return Result.success(result);
        }
    }

    //忘记密码
    @PostMapping(value = "/forgetpsw")
    public Result forgetPsw(@RequestBody CurrentUserModel currentUserModel){
        CurrentUserModel item = currentUserService.findByLoginEmail(currentUserModel.getLoginEmail());
        if(item == null ){
            return Result.error(111,"用户不存在");
        }
        else {
            MsgModel msgModel = new MsgModel();
            msgModel.setSendUser(item.getLoginEmail());
            msgModel.setMsgContent("忘记密码");
            msgService.addFeedback(msgModel);
            return Result.success();
        }
    }

    //验证学生姓名
    @PostMapping(value = "/register/stuname")
    public Result registerStuName (@RequestBody CurrentUserModel currentUserModel){
        List<UserModel> item = userService.findByStuName(currentUserModel.getStuName());
        if (item != null){
            return Result.success();
        } else {
            return Result.error(108,"无该学生用户");
        }
    }

    //验证学生学号
    @PostMapping(value = "/register/stunumber")
    public Result registerStuNumber (@RequestBody CurrentUserModel currentUserModel){
        UserModel item = userService.findByStuNumber(currentUserModel.getStuNumber());
        if (item != null){
            return Result.success();
        } else {
            return Result.error(108,"无该学生用户");
        }
    }


    // 邮件验证发送验证码
    @PostMapping(value = "/register/sendemail")
    public Result sendEmail(@RequestBody CurrentUserModel currentUserModel) throws Exception{
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<6; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //将承载的字符转换成字符串
        String activecode = sb.toString();
        currentUserService.sendEamil(currentUserModel.getLoginEmail(),activecode);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("Archive Book<xjx@zucc.edu.cn>");
        helper.setTo(currentUserModel.getLoginEmail());
        helper.setSubject("Archive Book - Verification code of Email");
        helper.setText("Your verification code:" + activecode);
        javaMailSender.send(mimeMessage);
        return Result.success();
    }


}
