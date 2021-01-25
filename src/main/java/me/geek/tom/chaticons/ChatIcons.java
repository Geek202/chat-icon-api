package me.geek.tom.chaticons;

import me.geek.tom.chaticons.api.ChatIconsApi;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;

public class ChatIcons implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final ThreadLocal<Identifier> currentMessageBeingAdded = new ThreadLocal<>();

    public static final String MOD_ID = "chat-icon-api";
    public static final String MOD_NAME = "Chat icons API";

    @Override
    public void onInitialize() {
        LOGGER.info( "[" + MOD_NAME + "] Initializing");
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "chat/with_icon"), (client, handler, buf, responseSender) -> {
            Identifier icon = buf.readIdentifier();
            Text message = buf.readText();
            client.execute(() ->
                    ChatIconsApi.addChatMessageWithIcon(client, message, icon));
        });
    }
}
