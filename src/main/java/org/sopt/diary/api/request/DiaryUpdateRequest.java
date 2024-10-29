package org.sopt.diary.api.request;

public class DiaryUpdateRequest {
    private final String content;

    // 필드가 하나인 DTO 클래스는 기본생성자가 있어야 한다.
    public DiaryUpdateRequest() {
        content = null;
    }

    public DiaryUpdateRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
