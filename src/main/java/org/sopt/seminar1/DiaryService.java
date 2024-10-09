package org.sopt.seminar1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    public Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.err.println("입력값을 Long으로 변환할 수 없습니다: " + input);
            return null;
        }
    }

    void writeDiary(final String body) {
        if(body.length() > 30) {
            throw new IllegalArgumentException();
        }

        // 다이어리 객체를 만들어줍니다.
        final Diary diary = new Diary(null, body);

        // 레포지토리에 넘겨서 DB에 저장합니다.
        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    final void patchDiary(String id, String input) {
        Long diaryId = convertStringToLong(id);

        if(diaryRepository.existById(diaryId)) {
            diaryRepository.patch(diaryId, input);
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }

    final void deleteDiary(String id) {
        Long diaryId = convertStringToLong(id);

        if(diaryRepository.existById(diaryId)) {
            diaryRepository.delete(diaryId);
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }
}