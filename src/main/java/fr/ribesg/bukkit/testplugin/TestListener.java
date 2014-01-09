package fr.ribesg.bukkit.testplugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

import java.util.logging.Logger;

/** @author Ribesg */
public class TestListener implements Listener {

	private static final Logger LOGGER = Logger.getLogger(TestListener.class.getName());

	private final TestPlugin plugin;

	public TestListener(final TestPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onEntityTeleportEvent(final EntityTeleportEvent event) {
		LOGGER.info(event.getEventName() + " - Cause=" + event.getCause());
	}

	@EventHandler
	public void onEntityPortalExitEvent(final EntityPortalExitEvent event) {
		LOGGER.info(event.getEventName() + " - Cause=" + event.getCause());
	}

	@EventHandler
	public void onEntityPortalEvent(final EntityPortalEvent event) {
		LOGGER.info(event.getEventName() + " - Cause=" + event.getCause());
	}
}
