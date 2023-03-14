package xyz.hafemann.fabricdiscordintegration;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hafemann.fabricdiscordintegration.config.ModConfig;
import xyz.hafemann.fabricdiscordintegration.discord.DiscordListeners;

public class FabricDiscordIntegration implements ModInitializer {
	public static final String MOD_ID = "fabricdiscordintegration";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ModConfig config = new ModConfig();
	public static WebhookClient webhookClient;
	public static final String SERVER_NAME = "Minecraft Server";
	public static final String SERVER_AVATAR_URL = "https://cdn.discordapp.com/avatars/1083494604868943903/2a31d56f03af219fb18edb8274722efc.webp";

	private void registerDiscordBot() {
		// Create JDA client
		JDA jda = JDABuilder.createDefault(config.botToken)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.build();

		// Add event listener
		jda.addEventListener(new DiscordListeners());
	}

	private void registerDiscordWebhook() {
		// Create WebHook client
		webhookClient = WebhookClient.withUrl(config.webhookURL);
		ServerMessageEvents.CHAT_MESSAGE.register((chatMessage, player, var) -> {
			String name = player.getName().getString();
			String message = chatMessage.getSignedContent();

			// Build webhook message
			WebhookMessageBuilder builder = new WebhookMessageBuilder();
			builder.setUsername(name); // use this username
			builder.setAvatarUrl("http://cravatar.eu/avatar/" + name + ".png"); // use this avatar
			builder.setContent(message);
			webhookClient.send(builder.build());
		});
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Fabric Discord Integration loaded.");

		config.load();

		registerDiscordBot();
		registerDiscordWebhook();
	}
}
