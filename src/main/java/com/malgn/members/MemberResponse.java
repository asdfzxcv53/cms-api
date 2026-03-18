package com.malgn.members;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String name;
    private String username;

    public MemberResponse(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }
}
