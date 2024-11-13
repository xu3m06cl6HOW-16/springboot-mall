package com.yuanhao.springbootmall.service.impl;

import com.yuanhao.springbootmall.dao.UserDao;
import com.yuanhao.springbootmall.dto.UserRegisterRequest;
import com.yuanhao.springbootmall.model.User;
import com.yuanhao.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //檢查註冊的Email
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null){
            log.warn("This email {} has already been registered.",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        //創建帳號
        return userDao.createdUser(userRegisterRequest);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


    @Override
    public User login(UserRegisterRequest userRegisterRequest) {
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());

        //檢查User是否存在
        if(user ==null){
            log.warn("This email {} has not been registered.",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用 MD5 生成密碼的湊值(彩虹表破解，可以用加鹽(Salt)方式強化)
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());

        //比較密碼
        if(hashedPassword.equals(user.getPassWord())){
            return user;
        }else{
            log.warn("The password is incorrect.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
