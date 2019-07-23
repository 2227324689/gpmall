package com.gpmall.shopping;

import com.gpmall.shopping.dto.CartListByIdRequest;
import com.gpmall.shopping.dto.CartListByIdResponse;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:57
 */
public interface ICartService {

    CartListByIdResponse getCartListById(CartListByIdRequest request);
}
