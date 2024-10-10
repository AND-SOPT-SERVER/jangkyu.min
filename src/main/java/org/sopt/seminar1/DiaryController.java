package org.sopt.seminar1;

import java.io.IOException;
import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() throws IOException {
        diaryService.loadDiary();
        this.status = Status.RUNNING;
    }

    void finish() throws IOException {
        diaryService.saveDiary();
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        // 클라이언트로부터 넘겨 받은 값을 전달만 해주면 돼요잉
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        diaryService.deleteDiary(id);
    }

    final void patch(final String id, final String body) {
        diaryService.patchDiary(id, body);
    }

    final void restore(final String id) {
        diaryService.restoreDiary(id);
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}
