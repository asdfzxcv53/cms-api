package com.malgn.content;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ContentPageResponse {

    private int page;
    private int size;
    private long totalCount;
    private int totalPages;
    private List<ContentResponse> contents;
}
