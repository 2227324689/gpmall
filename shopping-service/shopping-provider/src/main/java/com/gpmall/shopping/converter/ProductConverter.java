package com.gpmall.shopping.converter;

import com.gpmall.shopping.dal.entitys.Item;
import com.gpmall.shopping.dto.ProductDetailDto;
import com.gpmall.shopping.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-19:15
 */
@Mapper(componentModel = "spring")
public interface ProductConverter {

    @Mappings({
            @Mapping(source = "id",target = "productId"),
            @Mapping(source = "title",target = "productName"),
            @Mapping(source = "price",target = "salePrice"),
            @Mapping(source = "sellPoint",target = "subTitle"),
            @Mapping(source = "imageBig",target = "picUrl")
    })
    ProductDto item2Dto(Item item);

    List<ProductDto> items2Dto(List<Item> items);
}
