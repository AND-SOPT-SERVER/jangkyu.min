package org.sopt.diary.api;

public class DiaryDetailResponse {
    private long id;
    private String title;
    private String content;
    private String writeDate;

    public DiaryDetailResponse(long id, String title, String content, String writeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
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

    public String getWriteDate() {
        return writeDate;
    }
}
