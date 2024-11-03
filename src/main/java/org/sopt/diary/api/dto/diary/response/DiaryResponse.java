package org.sopt.diary.api.dto.diary.response;

public record DiaryResponse(
        Long id,
        String username,
        String title,
        String createdAt
) {
}
