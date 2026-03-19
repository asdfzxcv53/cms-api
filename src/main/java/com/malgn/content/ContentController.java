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
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "200", description = "컨텐츠 생성 성공",
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


    @Operation(
            summary = "컨텐츠 변경",
            description = "컨텐츠를 변경합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "컨텐츠 변경 성공",
                content = @Content(
                        schema = @Schema(implementation = ContentResponse.class)
                )),
            @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class)
                ))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContentResponse> update(@PathVariable Long id, @Valid @RequestBody ContentUpdateRequest contentUpdateRequest) {
        ContentResponse contentResponse = contentService.update(id, contentUpdateRequest);

        return ResponseEntity.ok(contentResponse);
    }


    @Operation(
            summary = "컨텐츠 삭제",
            description = "컨텐츠를 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "컨텐츠 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class)
                ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
