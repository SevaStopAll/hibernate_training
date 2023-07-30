package ru.sevastopall.listener;

import ru.sevastopall.entity.Chat;
import ru.sevastopall.entity.UserChat;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class UserChatListener {

    @PostPersist
    public void postPersist(UserChat userChat) {
        Chat chat = userChat.getChat();
        chat.setCount(chat.getCount() + 1);
    }

    @PostRemove
    public void postRemove(UserChat userChat) {
        Chat chat = userChat.getChat();
        chat.setCount(chat.getCount() - 1);
    }
}
