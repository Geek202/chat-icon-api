package me.geek.tom.chaticons.api;

import me.geek.tom.chaticons.ducks.IconChatHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChatIconsApi {
    /**
     * Adds a message to the given {@link ChatHud} that has the given icon.
     * @param chatHud The {@link ChatHud} to add the message to
     * @param message The message to add with the icon
     * @param icon The path to the texture of the icon.
     */
    public static void addChatMessageWithIcon(ChatHud chatHud, Text message, Identifier icon) {
        ((IconChatHud) chatHud).addLineWithIcon(icon, message);
    }

    /**
     * Convenience method to add a message to the {@link ChatHud} of the given {@link MinecraftClient}
     * @param client The {@link MinecraftClient} to get the {@link ChatHud from}
     * @param message The message to add with the icon.
     * @param icon The path to the texture of the icon.
     */
    public static void addChatMessageWithIcon(MinecraftClient client, Text message, Identifier icon) {
        ChatIconsApi.addChatMessageWithIcon(client.inGameHud.getChatHud(), message, icon);
    }
}
