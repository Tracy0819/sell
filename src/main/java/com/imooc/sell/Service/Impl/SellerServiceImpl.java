package com.imooc.sell.Service.Impl;

import com.imooc.sell.Dao.SellerInfoDao;
import com.imooc.sell.Service.SellerService;
import com.imooc.sell.entity.SellerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
   @Autowired
   private SellerInfoDao repository;

    @Override
    public SellerInfo findSellerInfoByOpenId(String openid) {
        return repository.findByOpenid(openid);
    }
}
