package org.sopt.diary.api.response;

public class DiaryResponse {
    private final long id;
    private final String title;

    public DiaryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
