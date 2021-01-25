package me.geek.tom.chaticons.ducks;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface IconChatHud {
    void addLineWithIcon(Identifier icon, Text message);
}
