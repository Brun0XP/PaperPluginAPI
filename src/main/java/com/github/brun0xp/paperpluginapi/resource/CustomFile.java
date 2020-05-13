package com.github.brun0xp.paperpluginapi.resource;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class CustomFile {

    private final Plugin plugin;
    private final String resource;
    private final File file;
    private FileConfiguration fileConfiguration;

    public CustomFile(Plugin plugin, String resource) {
        this.plugin = plugin;
        this.resource = resource;
        this.file = new File(this.plugin.getDataFolder(), resource);
    }

    private void reloadFile() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);

        final InputStream defLangStream = this.plugin.getResource(this.resource);
        if (defLangStream == null) {
            return;
        }

        this.fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defLangStream, Charsets.UTF_8)));
    }

    public void saveFile() {
        try {
            this.getConfig().save(this.file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.file, e);
        }
    }

    public FileConfiguration getConfig() {
        if (this.fileConfiguration == null) {
            this.reloadFile();
        }
        return this.fileConfiguration;
    }

}
