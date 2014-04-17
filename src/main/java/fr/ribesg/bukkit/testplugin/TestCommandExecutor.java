package fr.ribesg.bukkit.testplugin;
import org.bukkit.Achievement;
import org.bukkit.chat.AchievementHoverAction;
import org.bukkit.chat.ClickAction;
import org.bukkit.chat.RichMessage;
import org.bukkit.chat.RichMessagePart;
import org.bukkit.chat.SuggestChatAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        RichMessage richMessage = new RichMessage();
        richMessage
                .append(new RichMessagePart("Click me to say 'hey'", new SuggestChatAction("hey"), null))
                .append(" ")
                .append(
                    new RichMessagePart(
                        "Hover to know what you see when you get iron'",
                        null,
                        new AchievementHoverAction(Achievement.ACQUIRE_IRON)
                    )
                );

        player.sendRichMessage(richMessage);
        return true;
	}
}
