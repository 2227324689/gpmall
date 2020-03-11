package com.gpmall.search.converter;

import com.gpmall.search.dal.entitys.Item;
import com.gpmall.search.dto.ProductDto;
import com.gpmall.search.entity.ItemDocument;
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
            @Mapping(source = "sell_point",target = "subTitle"),
            @Mapping(source = "image",target = "picUrl")
    })
    ProductDto item2Dto(ItemDocument item);

    List<ProductDto> items2Dto(List<ItemDocument> items);

    @Mappings({
            @Mapping(source = "sellPoint",target = "sell_point"),
            @Mapping(source = "limitNum",target = "limit_num")
    })
    ItemDocument item2Document(Item item);
}
