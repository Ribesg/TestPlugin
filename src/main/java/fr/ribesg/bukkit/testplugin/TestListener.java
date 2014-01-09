package fr.ribesg.bukkit.testplugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

/** @author Ribesg */
public class TestListener implements Listener {

	private static final Logger LOGGER = Logger.getLogger(TestListener.class.getName());

	private final TestPlugin plugin;

	public TestListener(final TestPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}
}
