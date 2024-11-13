package com.yuanhao.springbootmall.service;

import com.yuanhao.springbootmall.dto.UserRegisterRequest;
import com.yuanhao.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserRegisterRequest userRegisterRequest);
}
