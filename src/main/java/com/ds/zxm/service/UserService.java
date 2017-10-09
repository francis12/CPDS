package com.ds.zxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.zxm.mapper.UserMapper;
import com.ds.zxm.model.User;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(){
        User user=userMapper.findUserInfo();
        //User user=null;
        return user;
    }

}
