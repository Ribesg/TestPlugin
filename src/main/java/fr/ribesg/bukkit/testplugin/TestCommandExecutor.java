package fr.ribesg.bukkit.testplugin;

import org.bukkit.*;
import org.bukkit.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Tree;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
            player.sendMessage(new RichMessage("Test 1"));
            spoke = true;
        }

        if (all || value == 2) {
            player.sendMessage(new RichMessage(ChatColor.RED + "Test 2"));
            spoke = true;
        }

        if (all || value == 3) {
            player.sendMessage(new RichMessage(ChatColor.RED + "Test 3: ").append(new ItemStack(Material.GOLDEN_APPLE)));
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
            player.sendMessage(new RichMessage(ChatColor.RED + "Test 4: ").append(is));
            spoke = true;
        }

        if (all || value == 5) {
            final Achievement a1 = Achievement.OPEN_INVENTORY;
            final Achievement a2 = Achievement.THE_END;
            player.sendMessage(new RichMessage(ChatColor.RED + "Test 5a: ").append(a1));
            player.sendMessage(new RichMessage(ChatColor.RED + "Test 5b: ").append(a2, "Test 5"));
            spoke = true;
        }

        if (all || value == 6) {
            player.sendMessage(new RichMessage("Test 6: linebreak =>\nTest 6!"));
            spoke = true;
        }

        if (all || value == 7) {
            player.sendMessage(new RichMessage("Test 7: linebreak =>").append("\nTest 7!"));
            spoke = true;
        }

        if (all || value == 8) {
            player.sendMessage(new RichMessage("Test 8: linebreak =>").append("\n").append("Test 8!"));
            spoke = true;
        }

        if (all || value == 9) {
            player.sendMessage(new RichMessage("Test 9a: hover", "hovered"));
            player.sendMessage(new RichMessage("Test 9b: hover", "line 1", "line 2"));
            player.sendMessage(new RichMessage("Test 9c: hover",
                    ChatColor.RED + "l"
                            + ChatColor.YELLOW + ChatColor.BOLD + "i"
                            + ChatColor.GREEN + "n"
                            + ChatColor.UNDERLINE + "e"
                            + ChatColor.RESET + " "
                            + ChatColor.BLUE + ChatColor.MAGIC + "1",
                    "Blah"
            ));
            spoke = true;
        }

        if (all || value == 10) {
            player.sendMessage(new RichMessage("Test 10a:").append(" Click me to talk", new SuggestChatAction("/me loves Bukkit"), "Click me!"));
            player.sendMessage(new RichMessage("Test 10b:").append(" Click me to talk", new ChatAction("/me loves Bukkit"), "Click me!"));
            spoke = true;
        }

        if (all || value == 11) {
            player.sendMessage(new RichMessage("Test 11: ").append(ChatColor.ITALIC + "opening an url", new OpenUrlAction("http://google.com"), "This is a link"));
            spoke = true;
        }

        if (all || value == 12) {
            player.sendMessage(new RichMessage("Test 12: &é\"'(-è_çà)=°+^$*ù!:;,?./§%¨£µ'"));
            spoke = true;
        }

        if (all || value == 13) {
            player.sendMessage(new RichMessage("Test 13: ").append(new LocalizedText("stream.userinfo.unmod")));
            spoke = true;
        }

        if (all || value == 14) {
            player.sendMessage(new RichMessage("Test 14: ").append(new LocalizedText("commands.scoreboard.players.reset.success", "Notch")));
            spoke = true;
        }

        if (all || value == 15) {
            plugin.getServer().broadcastMessage(new RichMessage("Test 15: ").append("User broadcast", "Come buy some lilipads"));
            spoke = true;
        }

        if (all || value == 16) {
            plugin.getServer().broadcast(new RichMessage("Test 16: ").append("Admin broadcast", "if you read this, you're an admin"), Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
            spoke = true;
        }

        if (all || value == 17) {
            plugin.getServer().broadcastMessage(new RichMessage(ChatColor.GREEN + "Test 17: ").append(ChatColor.RED + "Console test", "if you read this, you're not the console"));
            spoke = true;
        }

        if (all || value == 18) {
            Bukkit.broadcast(new RichMessage(ChatColor.YELLOW + "Test 18: ").append(new LocalizedText("commands.scoreboard.players.reset.success", "Notch"), "bendem hates JavaDocs!"), Server.BROADCAST_CHANNEL_USERS);
            spoke = true;
        }

        if (all || value == 19) {
            // Modifiable RichMessage example
            final CustomMessagePart testNb = new CustomMessagePart(ChatColor.AQUA + "Test 19: ");
            ItemStack is = new ItemStack(Material.APPLE);
            final ItemMessagePart itemPart = new ItemMessagePart(is);
            final CustomMessagePart textPart = new CustomMessagePart(is.getType().name());
            final RichMessage message = new RichMessage(testNb).append(itemPart).append(" <= This is a '").append(textPart).append("'");
            player.sendMessage(message);

            // Let's modify it
            char c = 'a';
            for (final TreeSpecies tree : TreeSpecies.values()) {
                is = new Tree(tree).toItemStack();
                testNb.setText(ChatColor.AQUA + "Test 19" + c++ + ": ");
                itemPart.setItem(is);
                textPart.setText(is.getType().name());
                player.sendMessage(message);
            }
            spoke = true;
        }

        if (value == 20) {
            // Caching test
            final List<RichMessage> messages1 = new LinkedList<RichMessage>();
            for (int i = 0; i < 10000; i++) {
                messages1.add(new RichMessage("Test 20a: " + i));
            }
            final RichMessage message2 = new RichMessage("Test 20b: idem");
            final List<RichMessage> messages2 = new LinkedList<RichMessage>();
            for (int i = 0; i < 10000; i++) {
                messages2.add(message2);
            }

            // Dummy execution #1 to prevent false results
            for (final RichMessage m : messages1) {
                player.sendMessage(m);
            }
            for (final RichMessage m : messages2) {
                player.sendMessage(m);
            }

            long start1 = System.nanoTime();
            for (final RichMessage m : messages1) {
                player.sendMessage(m);
            }
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            for (final RichMessage m : messages2) {
                player.sendMessage(m);
            }
            long end2 = System.nanoTime();

            // Dummy execution #2 to prevent false results
            for (final RichMessage m : messages2) {
                player.sendMessage(m);
            }
            for (final RichMessage m : messages1) {
                player.sendMessage(m);
            }

            long start3 = System.nanoTime();
            for (final RichMessage m : messages2) {
                player.sendMessage(m);
            }
            long end3 = System.nanoTime();

            long start4 = System.nanoTime();
            for (final RichMessage m : messages1) {
                player.sendMessage(m);
            }
            long end4 = System.nanoTime();

            final DecimalFormat f = new DecimalFormat("#.###");
            System.out.println("Sent " + messages1.size() + " different messages #1: " + f.format((end1 - start1) / 1000000.0) + " ms");
            System.out.println("Sent " + messages2.size() + " identical messages #1: " + f.format((end2 - start2) / 1000000.0) + " ms");
            System.out.println("Sent " + messages1.size() + " different messages #2: " + f.format((end4 - start4) / 1000000.0) + " ms");
            System.out.println("Sent " + messages2.size() + " identical messages #2: " + f.format((end3 - start3) / 1000000.0) + " ms");
            player.sendMessage("Done");
            spoke = true;
        }

        if (value == 21) {
            final ItemStack is = new ItemStack(Material.STONE_SWORD);
            is.setDurability((short) (Material.STONE_SWORD.getMaxDurability() / 2));
            is.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
            final ItemMeta meta = is.getItemMeta();
            meta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "Red" + ChatColor.GOLD + ChatColor.BOLD + " Sword of War " + ChatColor.BOLD + "n°42");
            meta.setLore(Arrays.asList(
                    ChatColor.AQUA + "Awesomness +7",
                    ChatColor.AQUA + "Stupidity -4",
                    " ",
                    "" + ChatColor.DARK_BLUE + ChatColor.ITALIC + "Built by " + ChatColor.RED + "Ribesg",
                    "%*éàùòß"
            ));
            is.setItemMeta(meta);
            final RichMessage message = new RichMessage(ChatColor.RED + "Test 21: ", "Here's a tooltip", "another line", ChatColor.RED + "A red one").append(is).append(Achievement.BOOKCASE, "KNOWLEDGE!");

            final YamlConfiguration config = new YamlConfiguration();
            config.set("richMessage", message);
            try {
                config.save("tmp.yml");
                System.out.println(config.saveToString());
                config.load("tmp.yml");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }

            final RichMessage message2 = (RichMessage) config.get("richMessage");
            player.sendMessage(message);
            player.sendMessage(message2);
            spoke = true;
        }

        return spoke;
    }
}
