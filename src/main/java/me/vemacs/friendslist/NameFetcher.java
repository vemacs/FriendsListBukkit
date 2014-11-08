package me.vemacs.friendslist;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class NameFetcher {
    private static JSONParser parser = new JSONParser();
    private static Map<UUID, List<String>> cache = new HashMap<>();

    public static List<String> nameHistoryFromUuid(UUID uuid) {
        if (cache.containsKey(uuid)) return cache.get(uuid);
        URLConnection connection;
        try {
            connection = new URL("https://api.mojang.com/user/profiles/"
                    + uuid.toString().replace("-", "").toLowerCase() + "/names"
            ).openConnection();
            String text = new Scanner(connection.getInputStream()).useDelimiter("\\Z").next();
            List<String> list = new ArrayList<String>((JSONArray) parser.parse(text));
            cache.put(uuid, list);
            return list;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
