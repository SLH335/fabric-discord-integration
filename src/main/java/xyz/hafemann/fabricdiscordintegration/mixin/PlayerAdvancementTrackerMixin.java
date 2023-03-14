package xyz.hafemann.fabricdiscordintegration.mixin;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.hafemann.fabricdiscordintegration.FabricDiscordIntegration;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/PlayerAdvancementTracker;updateDisplay(Lnet/minecraft/advancement/Advancement;)V"))
    private void advancement(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir){
        if (advancement != null &&advancement.getDisplay() != null && advancement.getDisplay().shouldAnnounceToChat()) {
            String advancementMessage = owner.getDisplayName().getString() + " has made the advancement **" + advancement.getDisplay().getTitle().getString() + "**";
            String advancementDescription = advancement.getDisplay().getDescription().getString();

            // Build embed
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xFFFF55)
                    .setTitle(new WebhookEmbed.EmbedTitle(advancementMessage, null))
                    .setDescription(advancementDescription)
                    //.addField(new WebhookEmbed.EmbedField(true, "", advancementDescription))
                    .build();

            // Build webhook message
            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(FabricDiscordIntegration.SERVER_NAME); // use this username
            builder.setAvatarUrl(FabricDiscordIntegration.SERVER_AVATAR_URL); // use this avatar
            builder.addEmbeds(embed); // add embed
            FabricDiscordIntegration.webhookClient.send(builder.build());
        }
    }
}
