package org.sopt.diary.api.dto.diary.request;

import org.sopt.diary.constant.Category;

public class DiaryUpdateRequest {
    private final String content;
    private final Category category;

    public DiaryUpdateRequest(String content, Category category) {
        this.content = content;
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }
}
