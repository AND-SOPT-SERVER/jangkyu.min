package org.sopt.seminar1;

import java.time.LocalDate;

public class Diary {
    private Long id = null;
    private final String body;
    private final LocalDate writeTime;

    public Diary(Long id, String body, LocalDate writeTime) {
        this.id = id;
        this.body = body;
        this.writeTime = writeTime;
    }

    public Diary(String body, LocalDate writeTime) {
        this.body = body;
        this.writeTime = writeTime;
    }

    public Long getId() {
        return this.id;
    }

    public String getBody() {
        return this.body;
    }

    public LocalDate getWriteTime() {
        return writeTime;
    }
}
