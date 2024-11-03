package org.sopt.diary.api.dto.diary.request;

public record DiaryCreateRequest(
        String title,
        String content,
        Boolean isPrivate,
        String category
) {
}
