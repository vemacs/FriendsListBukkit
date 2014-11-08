package me.vemacs.friendslist;

import me.vemacs.friends.data.FriendsDatabase;
import me.vemacs.friends.data.FriendsUser;
import me.vemacs.friends.data.User;
import me.vemacs.friends.messaging.ActionDispatcher;
import me.vemacs.friends.messaging.ActionHandler;
import me.vemacs.friendslist.handlers.AddHandler;
import me.vemacs.friendslist.handlers.LoginHandler;
import me.vemacs.friendslist.handlers.LogoutHandler;
import me.vemacs.friendslist.handlers.RemoveHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FriendsListBukkit extends JavaPlugin implements Listener {
    private Map<String, User> dataStore = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FriendsDatabase.getInstance().init(
                getConfig().getString("ip"), getConfig().getInt("port")
        );
        ActionDispatcher.getInstance().init();
        List<ActionHandler> handlers = Arrays.asList(
                new AddHandler(), new RemoveHandler(), new LoginHandler(), new LogoutHandler()
        );
        for (ActionHandler h : handlers)
            ActionDispatcher.getInstance().register(h);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = new FriendsUser(event.getPlayer().getUniqueId());
        user.login();
        dataStore.put(event.getPlayer().getName(), user);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        dataStore.get(event.getPlayer().getName()).logout();
        dataStore.remove(event.getPlayer().getName());
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length < 1) return false;
        switch (args[0].toLowerCase()) {
            case "add":
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        dataStore.get(sender.getName()).addFriend(
                                new FriendsUser(UUIDUtils.fetchUuid(args[1]))
                        );
                    }
                }.runTaskAsynchronously(this);
                break;
            case "remove":
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        dataStore.get(sender.getName()).removeFriend(
                                new FriendsUser(UUIDUtils.fetchUuid(args[1]))
                        );
                    }
                }.runTaskAsynchronously(this);
                break;
            case "list":
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        String base = "";
                        Map<User, Boolean> k = dataStore.get(sender.getName()).getFriends();
                        for (Map.Entry<User, Boolean> e : k.entrySet()) {
                            base += e.getValue() ? ChatColor.GREEN : ChatColor.RED +
                                    UUIDUtils.fetchName(e.getKey().getUuid()) + " ";
                        }
                        sender.sendMessage(base);
                    }
                }.runTaskAsynchronously(this);
                break;
            case "online":
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        String base = "";
                        Set<User> k = dataStore.get(sender.getName()).getOnlineFriends();
                        for (User u : k) {
                            base += UUIDUtils.fetchName(u.getUuid()) + " ";
                        }
                        sender.sendMessage(base);
                    }
                }.runTaskAsynchronously(this);
                break;
            default:
                break;
        }
        return true;
    }
}