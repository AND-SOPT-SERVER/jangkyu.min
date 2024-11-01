package org.sopt.diary.api.dto.diary.response;

public class DiaryResponse {
    private final Long id;
    private final String username;
    private final String title;
    private final String createdAt;

    public DiaryResponse(
            Long id, String username, String title, String createdAt
    ) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
