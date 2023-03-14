package xyz.hafemann.fabricdiscordintegration.mixin;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hafemann.fabricdiscordintegration.FabricDiscordIntegration;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(at = @At(value = "TAIL"), method = "onDeath")
    private void onPlayerDeath(DamageSource s, CallbackInfo info) {
        FabricDiscordIntegration.LOGGER.warn("Player died");
        ServerPlayerEntity p = (ServerPlayerEntity) (Object) this;
        final String deathMessage = s.getDeathMessage(p).getString();

        // Build embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xAA0000)
                .setTitle(new WebhookEmbed.EmbedTitle(deathMessage, null))
                .build();

        // Build webhook message
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
        builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
        builder.addEmbeds(embed); // add embed
        FabricDiscordIntegration.webhookClient.send(builder.build());
    }
}
