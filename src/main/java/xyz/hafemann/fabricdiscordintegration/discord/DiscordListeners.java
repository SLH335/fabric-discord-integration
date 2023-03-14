package xyz.hafemann.fabricdiscordintegration.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import xyz.hafemann.fabricdiscordintegration.FabricDiscordIntegration;

public class DiscordListeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) return;
        if (event.isWebhookMessage()) return;
        if (!event.getChannel().getId().equals(FabricDiscordIntegration.config.botChannelID)) return;

        MinecraftServer server = (MinecraftServer) FabricLoader.getInstance().getGameInstance();

        server.getPlayerManager().broadcast(
                Text.literal("Discord ● " + event.getMember().getEffectiveName())
                        .setStyle(Style.EMPTY.withColor(0x5165f7))
                        .append(Text.literal(" >> ").formatted(Formatting.GRAY))
                        .append(Text.literal(event.getMessage().getContentDisplay()).formatted(Formatting.WHITE)),
                false
        );
    }
}
