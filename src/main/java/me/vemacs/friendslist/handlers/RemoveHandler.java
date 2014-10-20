package me.vemacs.friendslist.handlers;

import me.vemacs.friends.data.User;
import me.vemacs.friends.messaging.Action;
import me.vemacs.friends.messaging.ActionHandler;
import me.vemacs.friendslist.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveHandler implements ActionHandler {
    @Override
    public Action getAction() {
        return Action.FRIEND_REMOVE;
    }

    @Override
    public void handle(User recipient, User subject) {
        Player p = Bukkit.getPlayer(recipient.getUuid());
        if (p == null || !p.isOnline()) return;
        p.sendMessage(UUIDUtils.fetchName(subject.getUuid()) + " are remove frum you're list!!1!!");
    }
}
