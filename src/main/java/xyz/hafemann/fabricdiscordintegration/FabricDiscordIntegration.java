package xyz.hafemann.fabricdiscordintegration;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricDiscordIntegration implements ModInitializer {
	public static final String MOD_ID = "fabricdiscordintegration";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Fabric Discord Integration loaded.");
	}
}
