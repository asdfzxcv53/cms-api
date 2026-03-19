package com.malgn.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContentResponse {

    private Long id;
    private String title;
    private String description;
    private Long viewCount;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;

    public ContentResponse() {}

    @Builder
    public ContentResponse(
            Long id,
            String title,
            String description,
            Long viewCount,
            LocalDateTime createdDate,
            String createdBy,
            LocalDateTime lastModifiedDate,
            String lastModifiedBy
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
    }
}
