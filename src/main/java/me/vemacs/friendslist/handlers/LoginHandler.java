package me.vemacs.friendslist.handlers;

import me.vemacs.friends.messaging.Action;
import me.vemacs.friends.messaging.ActionHandler;
import me.vemacs.friends.messaging.Message;
import me.vemacs.friendslist.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LoginHandler implements ActionHandler {
    @Override
    public Action getAction() {
        return Action.LOGIN;
    }

    @Override
    public void handle(Message payload) {
        Player p = Bukkit.getPlayer(payload.getTargetUser().getUuid());
        if (p == null || !p.isOnline()) return;
        p.sendMessage(UUIDUtils.fetchName(payload.getSourceUser().getUuid()) + " are logen!!1!!");
    }
}
