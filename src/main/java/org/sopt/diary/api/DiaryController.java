package org.sopt.diary.api;

import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diary")
    void postDiary(@RequestBody DiaryCreateRequest diaryCreateRequest) {
        // DTO validation
        if(diaryCreateRequest.getContent().length() > 30) {
            System.err.println("30자 초과 !");
            return;
        }

        diaryService.createDiary(
                new Diary(
                        null,
                        diaryCreateRequest.getTitle(),
                        diaryCreateRequest.getContent(),
                        LocalDate.now()
                )
        );
    }

    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaryList() {
        // (1) Service 로 부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.readDiaryList();

        // (2) Client 와 협의한 interface 로 변환
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for(Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getTitle()));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryDetailResponse> getDiaryDetail(@PathVariable Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Diary diary = diaryService.readDiaryDetail(id);

        DiaryDetailResponse diaryDetailResponse = new DiaryDetailResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getWriteDate().format(formatter)
        );

        return ResponseEntity.ok(diaryDetailResponse);
    }

    @PatchMapping("/diary/{id}")
    void patchDiary(@PathVariable Long id, @RequestBody DiaryUpdateRequest diaryUpdateRequest) {
        // DTO validation
        if(diaryUpdateRequest.getContent().length() > 30) {
            System.err.println("30자 초과 !");
            return;
        }

        diaryService.updateDiary(id, diaryUpdateRequest.getContent());
    }

    @DeleteMapping("/diary/{id}")
    void deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
    }
}
