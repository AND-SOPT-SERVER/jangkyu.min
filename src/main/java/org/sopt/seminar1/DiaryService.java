package org.sopt.seminar1;

import java.time.LocalDate;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    // String 자료형을 Long 으로 바꿔주세요.
    private Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.err.println("입력값을 Long으로 변환할 수 없습니다: " + input);
            return null;
        }
    }

    // 유저가 오늘 일기를 썼나요
    private boolean isUserWrite() {
        return diaryRepository.findDiaryByMaxId().getWriteTime().equals(LocalDate.now());
    }

    void writeDiary(final String body) {
        if(body.length() > 30) {
            throw new IllegalArgumentException();
        }
        // 다이어리 객체를 만들어줍니다.
        final Diary diary = new Diary(body, LocalDate.now());

        if(diaryRepository.count() == 0) {
            diaryRepository.save(diary);
        } else {
            if(isUserWrite()) {
                System.err.println("일기는 하루에 하나만 작성할 수 있어요.");
            } else {
                diaryRepository.save(diary);
            }
        }
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    void patchDiary(String id, String body) {
        Long diaryId = convertStringToLong(id);

        if(diaryRepository.existById(diaryId)) {
            diaryRepository.patch(
                    diaryId, new Diary(body, LocalDate.now())
            );
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }

    void deleteDiary(String id) {
        Long diaryId = convertStringToLong(id);

        if(diaryRepository.existById(diaryId)) {
            diaryRepository.delete(diaryId);
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }
}