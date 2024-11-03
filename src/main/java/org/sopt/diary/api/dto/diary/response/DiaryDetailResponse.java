package org.sopt.diary.api.dto.diary.response;

public record DiaryDetailResponse(
        long id,
        String title,
        String content,
        String createdAt,
        String category
) {
}
