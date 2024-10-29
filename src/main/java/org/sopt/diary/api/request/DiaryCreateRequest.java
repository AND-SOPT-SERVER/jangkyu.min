package org.sopt.diary.api.request;

public class DiaryCreateRequest {
    private final String title;
    private final String content;

    public DiaryCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
