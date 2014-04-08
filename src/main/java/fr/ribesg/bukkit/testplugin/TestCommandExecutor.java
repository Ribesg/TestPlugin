package fr.ribesg.bukkit.testplugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Logger;

/** @author Ribesg */
public class TestCommandExecutor implements CommandExecutor {

	private static final Logger LOGGER = Logger.getLogger(TestCommandExecutor.class.getName());

	private final TestPlugin plugin;

	public TestCommandExecutor(final TestPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("glow").setExecutor(this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if ("glow".equals(cmd.getName())) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "I would be happy to toggle glow on the item in your hand, but where's your hand?");
				return true;
			}
			final Player player = (Player) sender;
			final ItemStack is = player.getItemInHand();
			if (is == null || is.getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "I would be happy to toggle glow on the item in your hand, but your hand is empty");
				return true;
			}
			final ItemMeta meta = is.getItemMeta();
			final boolean changed = meta.setGlow(!meta.hasGlow());
			is.setItemMeta(meta);
			if (changed) {
				if (meta.hasGlow()) {
					player.sendMessage(ChatColor.GREEN + "Glowing effect enabled!");
				} else {
					player.sendMessage(ChatColor.GREEN + "Glowing effect disabled!");
				}
			} else {
				if (meta.hasGlow()) {
					player.sendMessage(ChatColor.RED + "Failed to disable glowing effect: item has enchantments!");
				} else {
					player.sendMessage(ChatColor.RED + "Failed to enable glowing effect: /!\\ this should not be possible!");
				}
			}
			return true;
		}
		return false;
	}
}
