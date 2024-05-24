package com.toggersapi.ToggersAPICore.Infrastructure.Entities;

import java.util.*;

public class Chat {

    private UUID chatId;

    private ChatType chatType;
    private List<User> users;

    private LinkedHashMap<UUID, Message> messages;

}
