package org.sopt.diary.api.dto.diary.response;

import java.util.List;

public record DiaryListResponse(
        List<DiaryResponse> diaries
) {
}
