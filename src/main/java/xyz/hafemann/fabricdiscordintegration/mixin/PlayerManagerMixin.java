package xyz.hafemann.fabricdiscordintegration.mixin;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hafemann.fabricdiscordintegration.FabricDiscordIntegration;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void onJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        String name = player.getDisplayName().getString();

        // Build embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x55FF55)
                .setTitle(new WebhookEmbed.EmbedTitle(name + " joined", null))
                .build();

        // Build webhook message
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
        builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
        builder.addEmbeds(embed); // add embed
        FabricDiscordIntegration.webhookClient.send(builder.build());
    }

    @Inject(method = "remove(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void onQuit(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        String name = player.getDisplayName().getString();

        // Build embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF5555)
                .setTitle(new WebhookEmbed.EmbedTitle(name + " left", null))
                .build();

        // Build webhook message
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
        builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
        builder.addEmbeds(embed); // add embed
        FabricDiscordIntegration.webhookClient.send(builder.build());
    }
}
