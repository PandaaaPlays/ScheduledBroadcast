package ca.pandaaa.scheduledbroadcast;

import ca.pandaaa.automaticbroadcast.AutomaticBroadcast;
import ca.pandaaa.automaticbroadcast.api.BroadcastAPI;
import ca.pandaaa.automaticbroadcast.api.PluginReloadEvent;
import ca.pandaaa.scheduledbroadcast.broadcast.BroadcastScheduler;
import ca.pandaaa.scheduledbroadcast.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ScheduledBroadcast extends JavaPlugin implements Listener {
    private ConfigManager configManager;
    private File broadcastsFile;
    private FileConfiguration broadcastsConfig;
    private BroadcastScheduler scheduler;
    private BroadcastAPI api;
    private BukkitTask scheduledBroadcastTask;

    @Override
    public void onEnable() {
        AutomaticBroadcast automaticBroadcast = (AutomaticBroadcast) Bukkit.getPluginManager().getPlugin("AutomaticBroadcast");
        if(automaticBroadcast == null) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAutomaticBroadcast not found."));
            return;
        }

        api = automaticBroadcast.getBroadcastAPI();
        if(api == null)
            return;

        configManager = new ConfigManager(getConfig());
        saveDefaultConfigurations();
        loadConfigurations();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        configManager = new ConfigManager(getConfig(), broadcastsConfig);

        scheduler = new BroadcastScheduler(createBroadcastList());
        scheduledBroadcasting();
    }

    private void saveDefaultConfigurations() {
        this.saveDefaultConfig();
        broadcastsFile = new File(getDataFolder(), "broadcasts.yml");
        if (!broadcastsFile.exists())
            saveResource("broadcasts.yml", false);
        broadcastsConfig = new YamlConfiguration();
    }

    private void loadConfigurations() {
        try {
            broadcastsConfig.load(broadcastsFile);
        } catch (IOException | InvalidConfigurationException exception) {
            System.out.println(exception);
        }
    }

    @EventHandler
    private void onAutomaticBroadcastReload(PluginReloadEvent event) {
        this.reloadConfig();
        broadcastsConfig = YamlConfiguration.loadConfiguration(broadcastsFile);

        configManager = new ConfigManager(getConfig(), broadcastsConfig);
        scheduler = new BroadcastScheduler(createBroadcastList());

        event.getReloadSender().sendMessage(configManager.getPluginReloadMessage());
        scheduledBroadcasting();
    }

    private List<ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast> createBroadcastList() {
        List<ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast> broadcastList = new ArrayList<>();
        String[] broadcastTitles = configManager.getBroadcastTitles();

        for (String title : broadcastTitles) {
            ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast broadcast =
                    new ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast(
                            title, configManager.getBroadcastMessagesList(title),
                            configManager.getBroadcastSound(title), configManager.getBroadcastHoverList(title),
                            configManager.getBroadcastClick(title), configManager.getBroadcastExemptedPlayers(title),
                            configManager.getBroadcastConsoleCommands(title), configManager.getSchedule(title));
            broadcastList.add(broadcast);
        }

        api.createScheduledBroadcastList(broadcastList.stream()
                .map(ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast::getConvertedBroadcast)
                .collect(Collectors.toList()));

        return broadcastList;
    }

    private void scheduledBroadcasting() {
        long delayInSeconds = 63 - LocalTime.now().getSecond();
        long delayInTicks = delayInSeconds * 20L;
        if (scheduledBroadcastTask != null) scheduledBroadcastTask.cancel();
        scheduledBroadcastTask = new BukkitRunnable() {
            @Override
            public void run() {
                for(ca.pandaaa.scheduledbroadcast.broadcast.ScheduledBroadcast broadcast : scheduler.scanForScheduledTimes())
                    api.sendBroadcast(broadcast);
            }
        }.runTaskTimer(this, delayInTicks, 20L * 60L);
    }
}
