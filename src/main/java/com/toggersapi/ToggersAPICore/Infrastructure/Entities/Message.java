package com.toggersapi.ToggersAPICore.Infrastructure.Entities;

import java.util.Date;
import java.util.UUID;

public class Message {

    private UUID messageId;

    private User source;
    private User target;

    private String content;
    private Date timestamp;
    private MessageStatus status;

}
