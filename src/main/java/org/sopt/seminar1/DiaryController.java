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
