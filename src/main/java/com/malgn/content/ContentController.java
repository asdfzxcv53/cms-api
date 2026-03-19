package com.malgn.content;

import com.malgn.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Operation(
            summary = "컨텐츠 생성",
            description = "새로운 컨텐츠를 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘텐츠 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = ContentResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping
    public ResponseEntity<ContentResponse> create(@Valid @RequestBody ContentCreateRequest contentCreateRequest) {
        ContentResponse contentResponse = contentService.create(contentCreateRequest);

        return ResponseEntity.ok(contentResponse);
    }
}
