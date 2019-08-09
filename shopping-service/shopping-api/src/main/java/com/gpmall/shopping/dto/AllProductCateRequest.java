package com.gpmall.shopping.dto;


import com.gpmall.commons.result.AbstractRequest;
import lombok.Data;

/**
 * Created by oahnus on 2019/8/8
 * 21:46.
 */
@Data
public class AllProductCateRequest extends AbstractRequest {
    private String sort;

    @Override
    public void requestCheck() {

    }
}
