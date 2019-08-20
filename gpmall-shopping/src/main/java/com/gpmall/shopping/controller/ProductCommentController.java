package com.gpmall.shopping.controller;

import com.gpmall.comment.ICommentService;
import com.gpmall.comment.dto.CommentListRequest;
import com.gpmall.comment.dto.CommentListResponse;
import com.gpmall.comment.dto.TotalCommentRequest;
import com.gpmall.comment.dto.TotalCommentResponse;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
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
public class ProductCommentController {
    @Reference(timeout = 3000)
    ICommentService commentService;

    @GetMapping("/all")
    public ResponseData getCommentByItemId(
            @RequestParam("productId") String itemId,
            @RequestParam(value = "page", defaultValue = "0", required = false)Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false)Integer size,
            @RequestParam(value = "type", required = false)Integer type
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
     * @param itemId
     * @param type
     * @return
     */
    @GetMapping("/count")
    public ResponseData getCommentCountByItemId(@RequestParam("productId")String itemId,
                                                @RequestParam(value = "type", required = false)Integer type) {
        TotalCommentRequest totalCommentRequest = new TotalCommentRequest();
        totalCommentRequest.setItemId(itemId);
        totalCommentRequest.setType(type);
        TotalCommentResponse response = commentService.totalComment(totalCommentRequest);
        long total = response.getTotal();
        return new ResponseUtil<Long>().setData(total);
    }
}
