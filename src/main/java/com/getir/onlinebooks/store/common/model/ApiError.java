package com.getir.onlinebooks.store.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final Date timestamp;

    @JsonIgnore private HttpStatus httpStatus;
    private int status;
    private String error;
    private List<String> messages;
    private String path;

    public ApiError() {
        this.timestamp = Date.from(Instant.now());
    }

    public void addMessage(String message) {
        if (CollectionUtils.isEmpty(messages)) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }
}