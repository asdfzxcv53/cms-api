package com.malgn.content;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@Getter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    public Content() {}
    public Content(String title, String description, String createdBy) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.viewCount = 0L;
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.lastModifiedBy = this.createdBy;
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
    }
    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void update(String title, String description, String lastModifiedBy){
        this.title = title;
        this.description = description;
        this.lastModifiedBy = lastModifiedBy;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
