package fr.ribesg.bukkit.testplugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Logger;

/** @author Ribesg */
public class TestCommandExecutor implements CommandExecutor {

	private static final Logger LOGGER = Logger.getLogger(TestCommandExecutor.class.getName());

	private final TestPlugin plugin;

	public TestCommandExecutor(final TestPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("foo").setExecutor(this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equals("foo")) {
			if (sender instanceof Player) {
				final Player player = (Player) sender;
				final List<Entity> entities = player.getNearbyEntities(10.0, 10.0, 10.0);
				for (final Entity e : entities) {
					if (e instanceof LivingEntity) {
						final Location from = e.getLocation();
						e.teleport(from.clone().add(2, 3, 4));
						player.sendMessage(ChatColor.GREEN + "Teleported a " + e.getType().name() + " randomly");
						return true;
					}
				}
				player.sendMessage(ChatColor.RED + "No living entity nearby");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "This command is for Players");
				return true;
			}
		}
		return false;
	}
}
