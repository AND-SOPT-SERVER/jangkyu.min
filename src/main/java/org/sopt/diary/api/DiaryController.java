package org.sopt.diary.api;

import org.sopt.diary.api.dto.diary.request.DiaryCreateRequest;
import org.sopt.diary.api.dto.diary.request.DiaryUpdateRequest;
import org.sopt.diary.api.dto.diary.response.DiaryDetailResponse;
import org.sopt.diary.api.dto.diary.response.DiaryDetailResponseWrapper;
import org.sopt.diary.api.dto.diary.response.DiaryListResponse;
import org.sopt.diary.api.dto.diary.response.DiaryResponse;
import org.sopt.diary.constant.Category;
import org.sopt.diary.constant.SortConstant;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.validation.DiaryValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryValidator diaryValidator;

    public DiaryController(DiaryService diaryService, DiaryValidator diaryValidator) {
        this.diaryService = diaryService;
        this.diaryValidator = diaryValidator;
    }

    @PostMapping("/diary")
    public void postDiary(
            @RequestHeader Long userId,
            @RequestBody DiaryCreateRequest diaryCreateRequest
    ) {
        // DTO validation
        diaryValidator.validateDiaryLength(
                diaryCreateRequest.getContent()
        );

        diaryService.createDiary(
                userId,
                new Diary(
                        null,
                        diaryCreateRequest.getTitle(),
                        diaryCreateRequest.getContent(),
                        diaryCreateRequest.getPrivate(),
                        Category.of(diaryCreateRequest.getCategory()),
                        LocalDateTime.now(),
                        null
                )
        );
    }

    @GetMapping("/diaries")
    public ResponseEntity<DiaryListResponse> getDiaryList(
            @RequestHeader(required = false) Long userId,
            @RequestParam(defaultValue = "all") String categoryUrl,
            @RequestParam(defaultValue = "latest") String sortUrl
    ) {
        // category 와 sort 가 올바른지는 컨트롤러 수준에서 검증한다.
        Category category = Category.of(categoryUrl);
        SortConstant sortConstant = SortConstant.of(sortUrl);

        if(categoryUrl.equals("all")) {
            category = null;
        }

        List<Diary> diaryList = diaryService.getRecentDiaries(
                userId, category, sortConstant
        );

        return ResponseEntity.ok(buildDiaryListResponse(diaryList));
    }

    @GetMapping("/diaries/my")
    public ResponseEntity<DiaryListResponse> getMyDiaryList(
            @RequestHeader Long userId,
            @RequestParam(defaultValue = "all") String categoryUrl,
            @RequestParam(defaultValue = "latest") String sortUrl
    ) {
        // category 와 sort 가 올바른지는 컨트롤러 수준에서 검증한다.
        Category category = Category.of(categoryUrl);
        SortConstant sortConstant = SortConstant.of(sortUrl);

        if(categoryUrl.equals("all")) {
            category = null;
        }

        List<Diary> diaryList = diaryService.getMyRecentDiaries(
                userId, category, sortConstant
        );

        return ResponseEntity.ok(buildDiaryListResponse(diaryList));
    }

    @GetMapping("/diary/{id}")
    public ResponseEntity<DiaryDetailResponseWrapper> getDiaryDetail(@PathVariable Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Diary diary = diaryService.getDiaryById(id);

        DiaryDetailResponse diaryDetailResponse = new DiaryDetailResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getCreatedAt().format(formatter),
                diary.getCategory().getCategory()
        );
        DiaryDetailResponseWrapper detailResponseWrapper = new DiaryDetailResponseWrapper(
                diaryDetailResponse
        );

        return ResponseEntity.ok(detailResponseWrapper);
    }

    @PatchMapping("/diary/{id}")
    public void patchDiary(
            @RequestHeader Long userId,
            @PathVariable Long id,
            @RequestBody DiaryUpdateRequest diaryUpdateRequest) {
        // DTO validation
        diaryValidator.validateDiaryLength(
                diaryUpdateRequest.getContent()
        );

        diaryService.updateDiaryContent(
                userId, id, diaryUpdateRequest.getContent(), Category.of(diaryUpdateRequest.getCategory())
        );
    }

    @DeleteMapping("/diary/{id}")
    public void deleteDiary(
            @RequestHeader Long userId,
            @PathVariable Long id
    ) {
        diaryService.deleteDiary(userId, id);
    }

    // 중복되는 코드 하나의 private 함수로 묶어줌
    private DiaryListResponse buildDiaryListResponse(List<Diary> diaryList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<DiaryResponse> diaryResponseList = diaryList.stream()
                .map(diary -> new DiaryResponse(
                        diary.getId(),
                        diary.getUser().getNickname(),
                        diary.getTitle(),
                        diary.getCreatedAt().format(formatter)
                ))
                .toList();

        return new DiaryListResponse(diaryResponseList);
    }

}
