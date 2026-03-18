package com.malgn.members;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckUsernameResponse {
    private boolean available; // 중복이 아니면 true, 중복이면 false

    public CheckUsernameResponse(boolean available) {
        this.available = available;
    }
}
