package com.gpmall.shopping.controller;

import com.gpmall.comment.ICommentService;
import com.gpmall.comment.dto.CommentListRequest;
import com.gpmall.comment.dto.CommentListResponse;
import com.gpmall.comment.dto.TotalCommentRequest;
import com.gpmall.comment.dto.TotalCommentResponse;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by oahnus on 2019/8/19
 * 21:29.
 */
@RestController
@RequestMapping("/shopping/comment")
@Api(tags = "ProductCommentController", description = "商品评价控制层")
public class ProductCommentController {
    @Reference(timeout = 3000)
    ICommentService commentService;

    @GetMapping("/all")
    @ApiOperation("获取所有评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query")
    })
    public ResponseData getCommentByItemId(
            @RequestParam("productId") String itemId,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "type", required = false) Integer type
    ) {
        CommentListRequest request = new CommentListRequest();
        request.setItemId(itemId);
        request.setPage(page);
        request.setSize(size);
        request.setType(type);
        CommentListResponse response = commentService.commentList(request);

        return new ResponseUtil<CommentListResponse>().setData(response);
    }

    /**
     * TODO type 创建枚举
     *
     * @param itemId
     * @param type
     * @return
     */
    @GetMapping("/count")
    @ApiOperation("获取指定商品的评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query", required = true)
    })
    public ResponseData getCommentCountByItemId(@RequestParam("productId") String itemId,
                                                @RequestParam(value = "type", required = false) Integer type) {
        TotalCommentRequest totalCommentRequest = new TotalCommentRequest();
        totalCommentRequest.setItemId(itemId);
        totalCommentRequest.setType(type);
        TotalCommentResponse response = commentService.totalComment(totalCommentRequest);
        long total = response.getTotal();
        return new ResponseUtil<Long>().setData(total);
    }
}
