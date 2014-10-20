package me.vemacs.friendslist;

import org.bukkit.Bukkit;

import java.util.UUID;

public class UUIDUtils {
    public static String fetchName(UUID uuid) {
        String name = Bukkit.getOfflinePlayer(uuid).getName();
        if (name == null)
            name = NameFetcher.nameHistoryFromUuid(uuid).get(0);
        return name;
    }

    public static UUID fetchUuid(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }
}
