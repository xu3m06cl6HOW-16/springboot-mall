package com.yuanhao.springbootmall.dao;

import com.yuanhao.springbootmall.dto.UserRegisterRequest;
import com.yuanhao.springbootmall.model.User;

public interface UserDao {

    Integer createdUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
