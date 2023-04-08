package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.R;
import org.example.common.ValidateCodeUtils;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author Christy Guo
 * @create 2023-03-29 8:47 PM
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param user 手机号封装
     * @Description: 发送短信
     */

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取用户的邮箱
        String phone = user.getPhone();

//        // 手机号为空
//        if (!StringUtils.isNotEmpty(phone)) {
//            return R.error("手机号为空！");
//        }


//        // 生成验证码
        String strCode = String.valueOf(1234);
//        String strCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
//        log.info("Code:" + strCode);
//
//************************************更新redis
//        // 将验证码放到Session中
//        session.setAttribute(phone, strCode);
//*********************************************
        redisTemplate.opsForValue().set(phone,strCode,5, TimeUnit.MINUTES);


        return R.success("发送成功");
    }

    /**
     * @Description: 用户登录
     * @param map 用户登录手机和验证码
     *
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map, HttpSession session){ //@RequestBody Map<String,String> map
        //获取用户信息
        String phone = map.get("phone");
        String code = map.get("code");

        //从session中获取讯息
        String attributeCode = (String) session.getAttribute(phone);

        // 从 Redis 中获取验证码
        String redisCode = (String) redisTemplate.opsForValue().get(phone);

        log.info(code);

        //从Session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);
        //从redis中获得缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //如果用户登录成功，删除redis中缓存的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登录失败");

    }

}
