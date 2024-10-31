package org.sopt.diary.api.dto.diary.request;

import org.sopt.diary.constant.Category;

public class DiaryCreateRequest {
    private final String title;
    private final String content;
    private final Boolean isPrivate;
    private final Category category;

    public DiaryCreateRequest(
            String title, String content, Boolean isPrivate, Category category
    ) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Category getCategory() {
        return category;
    }
}
