package xyz.hafemann.fabricdiscordintegration.mixin;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hafemann.fabricdiscordintegration.FabricDiscordIntegration;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getMeasuringTimeMs()J", ordinal = 0))
    private void styledChat_registerStarting(CallbackInfo ci) {
        // Build embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x55FF55)
                .setTitle(new WebhookEmbed.EmbedTitle("Server started", null))
                .build();

        // Build webhook message
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
        builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
        builder.addEmbeds(embed); // add embed
        FabricDiscordIntegration.webhookClient.send(builder.build());
    }

    @Inject(method = "shutdown", at = @At("TAIL"))
    private void styledChat_registerStopping(CallbackInfo ci) {
        // Build embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF5555)
                .setTitle(new WebhookEmbed.EmbedTitle("Server stopped", null))
                .build();

        // Build webhook message
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
        builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
        builder.addEmbeds(embed); // add embed
        FabricDiscordIntegration.webhookClient.send(builder.build());
    }
}
