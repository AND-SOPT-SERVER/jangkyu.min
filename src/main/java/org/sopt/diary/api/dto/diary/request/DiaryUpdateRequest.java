package org.sopt.diary.api.dto.diary.request;

import org.sopt.diary.constant.Category;

public class DiaryUpdateRequest {
    private final String content;
    private final String category;

    public DiaryUpdateRequest(String content, String category) {
        this.content = content;
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }
}
