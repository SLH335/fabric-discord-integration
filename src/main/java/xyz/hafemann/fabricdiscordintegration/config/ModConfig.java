package xyz.hafemann.fabricdiscordintegration.config;

import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

public class ModConfig extends Config {

    public ModConfig() {
        super(ConfigOptions.mod("fabricdiscordintegration"));

    }

    @ConfigEntry(comment = "Enter your bot token here!")
    public String botToken = "ENTER_TOKEN_HERE";

    @ConfigEntry(comment = "The ID of the channel the bot will be working in")
    public String botChannelID = "ENTER_CHANNEL_ID_HERE";

    @ConfigEntry(comment = "The Webhook URL used for messages")
    public String webhookURL = "ENTER_URL_HERE";
}
