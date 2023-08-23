package me.lidan.infinityerror.supercommands;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class WebhookMessage {
    private String content;

    public WebhookMessage(String content) {
        this.content = content;
    }
}
