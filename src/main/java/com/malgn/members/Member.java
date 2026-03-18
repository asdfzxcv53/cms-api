package com.malgn.members;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Builder
    public Member(String name, String username, String password, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
    }

    // 정보 수정시 마지막 변경시간도 같이 수정
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.lastModifiedDate = modifiedDate;
    }

    public Member() {}
}
