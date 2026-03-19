package com.malgn.content;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public ContentResponse create(ContentCreateRequest contentCreateRequest) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Content content = new Content(
                contentCreateRequest.getTitle(),
                contentCreateRequest.getDescription(),
                username
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
}
