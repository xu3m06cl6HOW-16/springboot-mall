package com.yuanhao.springbootmall.dao.impl;

import com.yuanhao.springbootmall.dao.UserDao;
import com.yuanhao.springbootmall.dto.UserRegisterRequest;
import com.yuanhao.springbootmall.model.User;
import com.yuanhao.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createdUser(UserRegisterRequest userRegisterRequest) {
        String sql="INSERT INTO user(email,password,created_date,last_modified_date) " +
                "VALUES(:email,:password,:created_date,:last_modified_date)";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());
        map.put("created_date", new Date());
        map.put("last_modified_date", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int userId=keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserById(Integer userId) {

        String sql="SELECT user_id,email,password,created_date,last_modified_date" +
                " FROM user WHERE user_id = :userId";

        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);

        List<User> userList=namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {

        String sql="SELECT user_id,email,password,created_date,last_modified_date" +
                " FROM user WHERE email = :email";

        Map<String,Object> map=new HashMap<>();
        map.put("email",email);

        List<User> userList=namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }

    }
}
