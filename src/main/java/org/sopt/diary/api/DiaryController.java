package org.sopt.diary.api;

import org.sopt.diary.api.dto.diary.request.DiaryCreateRequest;
import org.sopt.diary.api.dto.diary.request.DiaryUpdateRequest;
import org.sopt.diary.api.dto.diary.response.DiaryDetailResponse;
import org.sopt.diary.api.dto.diary.response.DiaryDetailResponseWrapper;
import org.sopt.diary.api.dto.diary.response.DiaryListResponse;
import org.sopt.diary.api.dto.diary.response.DiaryResponse;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.validation.DiaryValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                        diaryCreateRequest.getCategory(),
                        LocalDateTime.now(),
                        null
                )
        );
    }

    @GetMapping("/diaries")
    public ResponseEntity<DiaryListResponse> getDiaryList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // (1) Service 로 부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getRecentDiaries();

        // (2) Client 와 협의한 interface 로 변환
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for(Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(
                    diary.getId(),
                    "",
                    diary.getTitle(),
                    diary.getCreatedAt().format(formatter)
            ));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
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
                userId, id, diaryUpdateRequest.getContent(), diaryUpdateRequest.getCategory()
        );
    }

    @DeleteMapping("/diary/{id}")
    public void deleteDiary(
            @RequestHeader Long userId,
            @PathVariable Long id
    ) {
        diaryService.deleteDiary(userId, id);
    }
}

