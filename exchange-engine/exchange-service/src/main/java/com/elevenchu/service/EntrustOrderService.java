package com.elevenchu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elevenchu.domain.EntrustOrder;
import com.baomidou.mybatisplus.extension.service.IService;
public interface EntrustOrderService extends IService<EntrustOrder>{


    Page<EntrustOrder> findByPage(Page<EntrustOrder> page, Long userId, String symbol, Integer type);
}
