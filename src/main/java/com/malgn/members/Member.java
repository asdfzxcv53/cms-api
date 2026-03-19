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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String username, String password, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.role = role;
    }

    // 정보 수정시 마지막 변경시간도 같이 수정
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.lastModifiedDate = modifiedDate;
    }

    public Member() {}
}
