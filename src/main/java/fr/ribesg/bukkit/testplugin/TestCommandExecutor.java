package fr.ribesg.bukkit.testplugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Ribesg
 */
public class TestCommandExecutor implements CommandExecutor {

	private static final Logger LOGGER = Logger.getLogger(TestCommandExecutor.class.getName());

	private final TestPlugin plugin;

	public TestCommandExecutor(final TestPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("foo").setExecutor(this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Nope");
			return true;
		}
		Player player = ((Player) sender);

		if (args.length < 1) {
			return false;
		}
		final String arg = args[0].toLowerCase();
		boolean all = false;
		int value = -1;
		if ("all".equals(arg)) {
			all = true;
		} else {
			try {
				value = Integer.parseInt(arg);
			} catch (final NumberFormatException e) {
				return false;
			}
		}

		boolean spoke = false;

		if (all || value == 1) {
			player.sendRichMessage(new RichMessage("Test 1", null, null));
			spoke = true;
		}

		if (all || value == 2) {
			player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 2", null, null));
			spoke = true;
		}

		if (all || value == 3) {
			player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 3: ", null, null).append(new ItemMessagePart(new ItemStack(Material.GOLDEN_APPLE))));
			spoke = true;
		}

		if (all || value == 4) {
			final ItemStack is = new ItemStack(Material.STONE_SWORD);
			is.setDurability((short) (Material.STONE_SWORD.getMaxDurability() / 2));
			is.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
			final ItemMeta meta = is.getItemMeta();
			meta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "Red" + ChatColor.GOLD + ChatColor.BOLD + " Sword of War " + ChatColor.BOLD + ChatColor.MAGIC + "42");
			meta.setLore(Arrays.asList(new String[] {
					ChatColor.AQUA + "Awesomness +7",
					ChatColor.AQUA + "Stupidity -4",
					" ",
					"" + ChatColor.DARK_BLUE + ChatColor.ITALIC + "Built by " + ChatColor.RED + ChatColor.MAGIC + "Ribesg"
			}));
			is.setItemMeta(meta);
			player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 4: ", null, null).append(new ItemMessagePart(is)));
			spoke = true;
		}

		return spoke;
	}
}
