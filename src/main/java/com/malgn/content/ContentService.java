package com.malgn.content;

import com.malgn.exception.ContentModifyNoPermissionException;
import com.malgn.exception.ContentNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        return toResponse(savedContent);
    }

    // 페이징 처리를 위한 page 와 size 를 받고 정렬기준 (시간, 조회수, id), 정렬방향을 받는다.
    public ContentPageResponse findAll(int page, int size, String sortBy, String direction){
        validatePageAndSize(page, size);

        int offset = (page - 1) * size;
        List<Content> contents = contentRepository.findAll(offset, size, sortBy, direction);
        long totalCount = contentRepository.count();

        List<ContentResponse> contentResponses = contents.stream()
                .map(this::toResponse)
                .toList();

        int totalPage = (int) Math.ceil(totalCount / (double) size);

        return ContentPageResponse.builder()
                .page(page)
                .size(size)
                .totalCount(totalCount)
                .totalPages(totalPage)
                .contents(contentResponses)
                .build();
    }

    public ContentResponse findById(long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("컨텐츠가 없습니다."));

        content.increaseViewCount();

        return toResponse(content);

    }

    public ContentResponse update(Long id, ContentUpdateRequest contentUpdateRequest) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("컨텐츠가 없습니다."));

        String loginUsername = getLoginUser();

        // 권한 체크
        if(!content.getCreatedBy().equals(loginUsername) && !isAdmin()) {
            throw new ContentModifyNoPermissionException("컴텐츠를 수정할 권한이 없습니다.");
        }

        content.update(
                contentUpdateRequest.getTitle(),
                contentUpdateRequest.getDescription(),
                getLoginUser()
        );

        return toResponse(content);
    }


    public void delete(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("컨텐츠가 없습니다."));

        String loginUsername = getLoginUser();

        // 권한 체크
        if(!content.getCreatedBy().equals(loginUsername) && !isAdmin()){
            throw new ContentModifyNoPermissionException("컨텐츠를 삭제할 권한이 없습니다.");
        }

        contentRepository.delete(content);
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

    private void validatePageAndSize(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page 는 1 이상이어야 합니다.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("size 는 1 이상이어야 합니다.");
        }
    }

    private ContentResponse toResponse(Content content) {
        return ContentResponse.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .viewCount(content.getViewCount())
                .createdDate(content.getCreatedDate())
                .createdBy(content.getCreatedBy())
                .lastModifiedDate(content.getLastModifiedDate())
                .lastModifiedBy(content.getLastModifiedBy())
                .build();
    }
}
