package me.geek.tom.chaticons.mixins;

import me.geek.tom.chaticons.ChatIcons;
import me.geek.tom.chaticons.ducks.IconChatHud;
import me.geek.tom.chaticons.ducks.IconChatHudLine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class MixinChatHud extends DrawableHelper implements IconChatHud {
    @Shadow protected abstract void removeMessage(int messageId);

    @Shadow protected abstract void addMessage(Text message, int messageId);

    @Shadow @Final private MinecraftClient client;
//    private final ThreadLocal<Identifier> currentIconBeingAdded = new ThreadLocal<>();

    @Override
    public void addLineWithIcon(Identifier icon, Text message) {
        ChatIcons.currentMessageBeingAdded.set(icon);
        this.addMessage(message, 0);
        ChatIcons.currentMessageBeingAdded.set(null);
    }

//    @Redirect(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V", ordinal = 1))
//    private void redirect_addChatHudLine(List<ChatHudLine<?>> list, int index, Object element) {
//        if (ChatIcons.currentMessageBeingAdded.get() != null) {
//            ((IconChatHudLine) element).chatIconApi_setIcon(ChatIcons.currentMessageBeingAdded.get());
//        }
//    }

    @Inject(method = "render", locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"))
    private void hook_preRender(MatrixStack matrices, int tickDelta, CallbackInfo ci, int i, int j, boolean bl, double d, int k, double e, double f, double g, double h, int l, int m, ChatHudLine<Text> chatHudLine, double o, int p, int q, int r, double s) {
        if (((IconChatHudLine) chatHudLine).hasIcon()) {
            matrices.push();
            int size = this.client.textRenderer.fontHeight;
            // Move the text to the right to make space for the icon.
            matrices.translate(size + 2, 0, 0);
        }
    }

    @Inject(method = "render", locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I", shift = At.Shift.AFTER))
    private void hook_postRender(MatrixStack matrices, int tickDelta, CallbackInfo ci, int i, int j, boolean bl, double d, int k, double e, double f, double g, double h, int l, int m, ChatHudLine<Text> chatHudLine, double o, int p, int q, int r, double s) {
        if (((IconChatHudLine) chatHudLine).hasIcon()) {
            matrices.pop();
            this.client.getTextureManager().bindTexture(((IconChatHudLine) chatHudLine).chatIconApi_getIcon());
            int size = this.client.textRenderer.fontHeight;
            // Render the texture in the space we cleared.
            DrawableHelper.drawTexture(matrices, 0, (int) (s + h - 1), size, size, 0, 0, 256, 256, 256, 256);
        }
    }
}
