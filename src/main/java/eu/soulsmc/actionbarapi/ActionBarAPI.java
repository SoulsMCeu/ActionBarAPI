package eu.soulsmc.actionbarapi;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Joshua Samenfink
 * https://www.arrow-systems.de
 * Created on 22.10.2020 at 23:18:04
 * Coded with IntelliJ on Windows
 */

public class ActionBarAPI extends JavaPlugin implements Listener {

    private static Plugin plugin;
    private static String nmsver;
    private static boolean useOldMethods = false;
    public static String prefix = "§8» §9SoulsMC§8.§9eu §8┃ §7";
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        plugin = this;

        nmsver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (nmsver.startsWith("v1_7_") || nmsver.startsWith("v1_8_")) {
            useOldMethods = true;
        }

        console.sendMessage(prefix + "Plugin §6" + getDescription().getName() +
                " §7Version §a" + getDescription().getVersion() + " §7successfully §a§nactivated§7!");
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }
        ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
        if (actionBarMessageEvent.isCancelled()) {
            return;
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".network.protocol.game.PacketPlayOutChat");
            Class<?> packetClass = packetPlayOutChatClass.getSuperclass();
            if (useOldMethods) {
                Class<?> chatSerializerClass = Class.forName("net.minecraft.util.ChatSerializer");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.network.chat.IChatBaseComponent");
                Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                Object cbc = m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}");
                packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, byte.class).newInstance(cbc, (byte) 2);
            } else {
                Class<?> chatComponentTextClass = Class.forName("net.minecraft.network.chat.ChatComponentText");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.network.chat.IChatBaseComponent");
                Object chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(message);
                packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, byte.class).newInstance(chatCompontentText, (byte) 2);
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("b");
            playerConnectionField.setAccessible(true);
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(Player player, String message, int duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, duration + 1);
        }

        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, duration);
        }
    }

    public static void sendActionBarToAllPlayers(String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    public static void sendActionBarToAllPlayers(String message, int duration) {
        Bukkit.getOnlinePlayers().forEach(player -> sendActionBar(player, message, duration));
    }

    @Override
    public void onDisable() {
        console.sendMessage(prefix + "Plugin §6" + getDescription().getName() +
                " §7Version §a" + getDescription().getVersion() + " §7successfully §c§ndeactivated§7!");
    }
}