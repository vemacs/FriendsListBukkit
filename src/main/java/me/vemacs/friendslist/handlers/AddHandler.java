package me.vemacs.friendslist.handlers;

import me.vemacs.friends.data.User;
import me.vemacs.friends.messaging.Action;
import me.vemacs.friends.messaging.ActionHandler;
import me.vemacs.friendslist.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddHandler implements ActionHandler {
    @Override
    public Action getAction() {
        return Action.FRIEND_ADD;
    }

    @Override
    public void handle(User recipient, User subject) {
        Player p = Bukkit.getPlayer(recipient.getUuid());
        if (p == null || !p.isOnline()) return;
        p.sendMessage(UUIDUtils.fetchName(subject.getUuid()) + " are add 2 you're list!!1!!");
    }
}
