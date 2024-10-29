package org.sopt.diary.api;

import org.sopt.diary.api.request.DiaryCreateRequest;
import org.sopt.diary.api.request.DiaryUpdateRequest;
import org.sopt.diary.api.response.DiaryDetailResponse;
import org.sopt.diary.api.response.DiaryListResponse;
import org.sopt.diary.api.response.DiaryResponse;
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
    void postDiary(@RequestBody DiaryCreateRequest diaryCreateRequest) {
        // DTO validation
        diaryValidator.validateDiaryLength(
                diaryCreateRequest.getContent()
        );

        diaryService.createDiary(
                new Diary(
                        null,
                        diaryCreateRequest.getTitle(),
                        diaryCreateRequest.getContent(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaryList() {
        // (1) Service 로 부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getRecentDiaries();

        // (2) Client 와 협의한 interface 로 변환
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for(Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getTitle()));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryDetailResponse> getDiaryDetail(@PathVariable Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Diary diary = diaryService.getDiaryById(id);

        DiaryDetailResponse diaryDetailResponse = new DiaryDetailResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getCreatedAt().format(formatter)
        );

        return ResponseEntity.ok(diaryDetailResponse);
    }

    @PatchMapping("/diary/{id}")
    void patchDiary(@PathVariable Long id, @RequestBody DiaryUpdateRequest diaryUpdateRequest) {
        // DTO validation
        diaryValidator.validateDiaryLength(
                diaryUpdateRequest.getContent()
        );

        diaryService.updateDiaryContent(id, diaryUpdateRequest.getContent());
    }

    @DeleteMapping("/diary/{id}")
    void deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
    }
}
