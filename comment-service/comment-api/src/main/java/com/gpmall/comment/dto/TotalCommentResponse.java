package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @author heps
 * @date 2019/8/17 23:16
 */
@Data
public class TotalCommentResponse extends AbstractResponse {

    private long total;
}
