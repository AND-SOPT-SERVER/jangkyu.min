package org.sopt.diary.api.dto.diary.response;

public class DiaryDetailResponse {
    private final long id;
    private final String title;
    private final String content;
    private final String createdAt;
    private final String category;

    public DiaryDetailResponse(
            long id, String title, String content, String createdAt, String category
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCategory() {
        return category;
    }
}
