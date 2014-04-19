package fr.ribesg.bukkit.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ribesg
 */
public class TestPlugin extends JavaPlugin {

    private TestListener listener;
    private TestCommandExecutor executor;

    @Override
    public void onLoad() {
        // NOP
    }

    @Override
    public void onEnable() {
        this.listener = new TestListener(this);
        this.executor = new TestCommandExecutor(this);
    }

    @Override
    public void onDisable() {
        // NOP
    }
}
