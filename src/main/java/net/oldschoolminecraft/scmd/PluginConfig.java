package net.oldschoolminecraft.scmd;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfig extends Configuration
{
    public PluginConfig(File file)
    {
        super(file);
        this.reload();
    }

    public void reload()
    {
        this.load();
        this.write();
        this.save();
    }

    private void write()
    {
        generateIntList("staffInventoryIDs", Arrays.asList(7, 271, 345, 262, 287));
        generateConfigOption("itemDrops.allow", false);
        generateConfigOption("itemDrops.sendAMessage", true);
    }

    private void generateIntList(String key, List<Integer> values)
    {
        this.getIntList(key, values);
    }

    private void generateConfigOption(String key, Object defaultValue)
    {
        if (this.getProperty(key) == null) this.setProperty(key, defaultValue);
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getConfigOption(String key)
    {
        return this.getProperty(key);
    }

    public Object getConfigOption(String key, Object defaultValue)
    {
        Object value = getConfigOption(key);
        if (value == null) value = defaultValue;
        return value;
    }

    public List<String> getConfigList(String key)
    {
        return Arrays.asList(String.valueOf(getConfigOption(key, "")).trim().split(","));
    }
}
