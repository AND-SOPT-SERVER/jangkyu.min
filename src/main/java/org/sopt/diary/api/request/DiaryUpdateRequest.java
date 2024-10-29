package org.sopt.diary.api.request;

public class DiaryUpdateRequest {
    private final String content;

    public DiaryUpdateRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
