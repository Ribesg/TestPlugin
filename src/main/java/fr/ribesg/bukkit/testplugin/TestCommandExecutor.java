package fr.ribesg.bukkit.testplugin;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.chat.AchievementHoverAction;
import org.bukkit.chat.ChatAction;
import org.bukkit.chat.ItemHoverAction;
import org.bukkit.chat.RichMessage;
import org.bukkit.chat.RichMessagePart;
import org.bukkit.chat.SuggestChatAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		if(!(sender instanceof Player)) {
            sender.sendMessage("Nope");
            return true;
        }
        Player player = ((Player) sender);
        if(args.length == 0) {
            sendSample(player);
        } else if(args[0].equals("achievements")) {
            sendAchievements(player);
        } else if(args[0].equals("items")) {
            sendItems(player);
        }

        return true;
	}

    private void sendSample(Player player) {
        RichMessage richMessage = new RichMessage();
        richMessage
            .append(new RichMessagePart("Click me to say 'hey'", new SuggestChatAction("hey"), null))
            .append(
                    new RichMessagePart("Click to reveal your true self", new ChatAction("/me is gay"), new ItemHoverAction(new ItemStack(Material.CARROT_ITEM)))
            );
        player.sendRichMessage(richMessage);
    }

    private void sendAchievements(Player player) {
        RichMessage achievs = new RichMessage();
        for(Achievement a : Achievement.values()) {
            achievs.append(new RichMessagePart(a.name(), null, new AchievementHoverAction(a)));
        }
        player.sendRichMessage(achievs);
    }

    private void sendItems(Player player) {
        RichMessage items = new RichMessage();
        for(Material m : Material.values()) {
            items.append(new RichMessagePart(m.name(), null, new ItemHoverAction(new ItemStack(m))));
        }
        player.sendRichMessage(items);
    }

}
