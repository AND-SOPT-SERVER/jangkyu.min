package org.sopt.diary.api.dto.diary.request;

public record DiaryUpdateRequest(
        String content,
        String category
) {
}
