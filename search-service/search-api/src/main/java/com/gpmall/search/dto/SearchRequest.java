package com.gpmall.search.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.search.SearchException;
import com.gpmall.search.consts.SearchEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用请求类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public class SearchRequest extends AbstractRequest {

    private String keyword;
    private Integer currentPage;
    private Integer pageSize;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "keyword='" + keyword + '\'' +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }

    @Override
    public void requestCheck() {
        if (StringUtils.isAllBlank(keyword)) {
            throw SearchException.create(SearchEnum.STRING_EMPTY.getCodeString(),
                    SearchEnum.STRING_EMPTY.param(keyword));
        }
    }
}
