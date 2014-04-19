package fr.ribesg.bukkit.testplugin;

import org.bukkit.Achievement;
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
            player.sendRichMessage(new RichMessage("Test 1"));
            spoke = true;
        }

        if (all || value == 2) {
            player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 2"));
            spoke = true;
        }

        if (all || value == 3) {
            player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 3: ").append(new ItemStack(Material.GOLDEN_APPLE)));
            spoke = true;
        }

        if (all || value == 4) {
            final ItemStack is = new ItemStack(Material.STONE_SWORD);
            is.setDurability((short) (Material.STONE_SWORD.getMaxDurability() / 2));
            is.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
            final ItemMeta meta = is.getItemMeta();
            meta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "Red" + ChatColor.GOLD + ChatColor.BOLD + " Sword of War " + ChatColor.BOLD + ChatColor.MAGIC + "42");
            meta.setLore(Arrays.asList(
                    ChatColor.AQUA + "Awesomness +7",
                    ChatColor.AQUA + "Stupidity -4",
                    " ",
                    "" + ChatColor.DARK_BLUE + ChatColor.ITALIC + "Built by " + ChatColor.RED + ChatColor.MAGIC + "Ribesg",
                    "%*éàùòß"
            ));
            is.setItemMeta(meta);
            player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 4: ").append(is));
            spoke = true;
        }

        if (all || value == 5) {
            final Achievement a1 = Achievement.OPEN_INVENTORY;
            final Achievement a2 = Achievement.THE_END;
            player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 5a: ").append(a1));
            player.sendRichMessage(new RichMessage(ChatColor.RED + "Test 5b: ").append(a2, "Test 5"));
        }

        if (all || value == 6) {
            player.sendRichMessage(new RichMessage("Test 6: linebreak =>\nTest 6!"));
        }

        if (all || value == 7) {
            player.sendRichMessage(new RichMessage("Test 7: linebreak =>").append("\nTest 7!"));
        }

        if (all || value == 8) {
            player.sendRichMessage(new RichMessage("Test 8: linebreak =>").append("\n").append("Test 8!"));
        }

        if (all || value == 9) {
            player.sendRichMessage(new RichMessage("Test 9a: hover", "hovered"));
            player.sendRichMessage(new RichMessage("Test 9b: hover", "line 1", "line 2"));
            player.sendRichMessage(new RichMessage("Test 9b: hover",
                    ChatColor.RED + "l"
                            + ChatColor.YELLOW + ChatColor.BOLD + "i"
                            + ChatColor.GREEN + "n"
                            + ChatColor.UNDERLINE + "e"
                            + ChatColor.RESET + " "
                            + ChatColor.BLUE + ChatColor.MAGIC + "1",
                    "Blah"
            ));
        }

        if (all || value == 10) {
            player.sendRichMessage(new RichMessage("Test 10a:").append(" Click me to talk", new SuggestChatAction("/me loves Bukkit"), "Click me!"));
            player.sendRichMessage(new RichMessage("Test 10b:").append(" Click me to talk", new ChatAction("/me loves Bukkit"), "Click me!"));
        }

        if (all || value == 11) {
            player.sendRichMessage(new RichMessage("Test 11: ").append(ChatColor.ITALIC + "opening an url", new OpenUrlAction("http://google.com"), "This is a link"));
        }

        if (all || value == 12) {
            player.sendRichMessage(new RichMessage("Test 12: &é\"'(-è_çà)=°+^$*ù!:;,?./§%¨£µ'"));
        }

        if (all || value == 13) {
            player.sendRichMessage(new RichMessage("Test 13: ").append(new LocalizedString("stream.userinfo.unmod")));
        }

        if (all || value == 14) {
            player.sendRichMessage(new RichMessage("Test 14: ").append(new LocalizedString("commands.scoreboard.players.reset.success", "Notch")));
        }

        return spoke;
    }
}
