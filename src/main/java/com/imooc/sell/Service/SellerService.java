package com.imooc.sell.Service;

import com.imooc.sell.entity.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenId(String openid);
}
