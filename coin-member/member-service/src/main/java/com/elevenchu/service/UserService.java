package com.elevenchu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elevenchu.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserService extends IService<User>{


    Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status,Integer reviewStatus);

    Page<User> findDirectInvitePage(Page<User> page, Long userId);
}
