package me.geek.tom.chaticons.mixins;

import me.geek.tom.chaticons.ChatIcons;
import me.geek.tom.chaticons.ducks.IconChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHudLine.class)
public class MixinChatHudLine implements IconChatHudLine {
    private Identifier icon = null;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hook_init(int creationTick, Object text, int id, CallbackInfo ci) {
        this.icon = ChatIcons.currentMessageBeingAdded.get();
    }

    @Override
    public Identifier chatIconApi_getIcon() {
        return icon;
    }

//    @Override
//    public void chatIconApi_setIcon(Identifier icon) {
//        this.icon = icon;
//    }

    @Override
    public boolean hasIcon() {
        return this.icon != null;
    }
}
