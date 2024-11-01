package org.sopt.diary.api.dto.diary.response;

public class DiaryDetailResponseWrapper {
    private final DiaryDetailResponse diary;

    public DiaryDetailResponseWrapper(DiaryDetailResponse diary) {
        this.diary = diary;
    }

    public DiaryDetailResponse getDiary() {
        return diary;
    }
}
