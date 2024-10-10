package org.sopt.seminar1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    // String 자료형을 Long 으로 바꿔주세요
    private Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.err.println("입력값을 Long으로 변환할 수 없습니다: " + input);
            return null;
        }
    }

    // 유저가 오늘 일기를 썼나요
    private boolean isUserWriteToday() {
        return diaryRepository.findDiaryByMaxId().getWriteTime().equals(LocalDate.now());
    }

    // 다이어리 수정 저장소를 확인하고 필요하다면 초기화 해주세요
    private boolean canPatchDiary() {
        long patchCount = diaryRepository.countPatchDiaries();

        // 일기 수정 기록이 존재하지만, 해당 기록이 오늘 작성된 것이 아닌 다른 날 작성된 것이라면 수정 저장소를 초기화 할 거에요
        if(patchCount == 1 && !diaryRepository.findFirstValueInPatch().getWriteTime().equals(LocalDate.now())) {
            diaryRepository.clearPatch();
        }

        return patchCount < 2;
    }

    void writeDiary(final String body) {
        if(body.length() > 30) {
            throw new IllegalArgumentException();
        }

        // 다이어리 객체를 만들어줍니다.
        final Diary diary = new Diary(body, LocalDate.now());

        if(diaryRepository.countDiaries() == 0) {
            diaryRepository.save(diary);
        } else {
            if(isUserWriteToday()) {
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
        // 일기 수정 가능 여부 검증
        if(!canPatchDiary()) {
            System.err.println("일기는 하루에 최대 두 번만 수정할 수 있어요.");
            return;
        }

        // 다이어리 검증
        Long diaryId = convertStringToLong(id);
        if(diaryId == null) {
            return;
        }

        if(diaryRepository.existsInStorage(diaryId)) {
            diaryRepository.patch(
                diaryId, new Diary(body, LocalDate.now())
            );
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }

    void deleteDiary(String id) {
        Long diaryId = convertStringToLong(id);
        if(diaryId == null) {
            return;
        }

        if(diaryRepository.existsInStorage(diaryId)) {
            diaryRepository.delete(diaryId);
        } else {
            System.err.println("존재하지 않는 다이어리 입니다.");
        }
    }

    void restoreDiary(String id) {
        Long diaryId = convertStringToLong(id);
        if(diaryId == null) {
            return;
        }

        if(diaryRepository.existsInTrash(diaryId)) {
            diaryRepository.restore(diaryId);
        } else {
            System.err.println("휴지통에 존재하지 않는 다이어리 입니다.");
        }
    }

    void saveDiary() throws IOException {
        diaryRepository.saveStorage();
        diaryRepository.saveTrashStorage();
        diaryRepository.savePatchStorage();
    }

    void loadDiary() throws IOException {
        diaryRepository.loadStorage();
        diaryRepository.loadTrashStorage();
        diaryRepository.loadPatchStorage();
    }
}