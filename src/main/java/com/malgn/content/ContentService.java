package com.malgn.content;

import com.malgn.exception.ContentCreatorMismatchException;
import com.malgn.exception.ContentNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public ContentResponse create(ContentCreateRequest contentCreateRequest) {
        Content content = new Content(
                contentCreateRequest.getTitle(),
                contentCreateRequest.getDescription(),
                getLoginUser()
        );

        Content savedContent = contentRepository.save(content);

        return ContentResponse.builder()
                .id(savedContent.getId())
                .title(savedContent.getTitle())
                .description(savedContent.getDescription())
                .createdDate(savedContent.getCreatedDate())
                .createdBy(savedContent.getCreatedBy())
                .lastModifiedDate(savedContent.getLastModifiedDate())
                .lastModifiedBy(savedContent.getLastModifiedBy())
                .build();
    }

    public ContentResponse update(Long id, ContentUpdateRequest contentUpdateRequest) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("컨텐츠가 없습니다."));

        String loginUsername = getLoginUser();

        // 현재 로그인 유저와 컨텐츠의 생성자와 다르거나 admin 이 아니면 exception
        if(!content.getCreatedBy().equals(loginUsername) && !isAdmin()) {
            throw new ContentCreatorMismatchException("컨텐츠를 수정하실 수 없습니다.");
        }

        content.update(
                contentUpdateRequest.getTitle(),
                contentUpdateRequest.getDescription(),
                getLoginUser()
        );

        return ContentResponse.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .createdDate(content.getCreatedDate())
                .createdBy(content.getCreatedBy())
                .lastModifiedDate(content.getLastModifiedDate())
                .lastModifiedBy(content.getLastModifiedBy())
                .build();
    }

    private String getLoginUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    // admin 이면 true
    private boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(
                        auth -> auth.getAuthority().equals("ROLE_ADMIN")
                );
    }
}
