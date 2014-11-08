package me.vemacs.friendslist.handlers;

import me.vemacs.friends.messaging.Action;
import me.vemacs.friends.messaging.ActionHandler;
import me.vemacs.friends.messaging.Message;
import me.vemacs.friendslist.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddHandler implements ActionHandler {
    @Override
    public Action getAction() {
        return Action.FRIEND_ADD;
    }

    @Override
    public void handle(Message payload) {
        Player p = Bukkit.getPlayer(payload.getTargetUser().getUuid());
        if (p == null || !p.isOnline()) return;
        p.sendMessage(UUIDUtils.fetchName(payload.getSourceUser().getUuid()) + " are add 2 you're list!!1!");
    }
}
