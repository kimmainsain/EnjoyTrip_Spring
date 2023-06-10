package com.ssafy.paging;

import com.github.pagehelper.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PagingResult<E>{
    private List<E> data;
    private int pageNum;
    private int numOfRows;
    private int pages;

    public PagingResult(Page<E> page){
        data = page.getResult();
        pageNum = page.getPageNum();
        numOfRows = page.getPageSize();
        pages = page.getPages();
    }

    public PagingResult(List<E> data, int pageNum, int numOfRows, int pages) {
        this.data = data;
        this.pageNum = pageNum;
        this.numOfRows = numOfRows;
        this.pages = pages;
    }
}
