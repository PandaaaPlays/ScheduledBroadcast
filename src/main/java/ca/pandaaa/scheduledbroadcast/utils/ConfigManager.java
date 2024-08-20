package ca.pandaaa.scheduledbroadcast.utils;

import ca.pandaaa.automaticbroadcast.utils.Utils;
import ca.pandaaa.scheduledbroadcast.schedule.*;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class ConfigManager {
    private final FileConfiguration configuration;
    private final FileConfiguration broadcasts;

    public ConfigManager(FileConfiguration configuration) {
        this.configuration = configuration;
        this.broadcasts = null;
    }

    public ConfigManager(FileConfiguration configuration, FileConfiguration broadcasts) {
        this.configuration = configuration;
        this.broadcasts = broadcasts;
    }

    public String getPluginReloadMessage() {
        return Utils.applyFormat(configuration.getString("plugin-reload"));
    }

    public String[] getBroadcastTitles() {
        ConfigurationSection broadcastsTitleSection = broadcasts.getConfigurationSection("scheduled-broadcasts");
        if(broadcastsTitleSection == null)
            return new String[0];

        Set<String> broadcastsTitleSet = broadcastsTitleSection.getKeys(false);
        String[] broadcastTitlesList = new String[broadcastsTitleSet.size()];
        return broadcastsTitleSet.toArray(broadcastTitlesList);
    }

    public List<String> getBroadcastMessagesList(String broadcastTitle) {
        return broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".messages");
    }

    public String getBroadcastClick(String broadcastTitle) {
        return broadcasts.getString("scheduled-broadcasts." + broadcastTitle + ".click");
    }

    public List<String> getBroadcastHoverList(String broadcastTitle) {
        return broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".hover");
    }

    public Sound getBroadcastSound(String broadcastTitle) {
        try {
            return Sound.valueOf(broadcasts.getString("scheduled-broadcasts." + broadcastTitle + ".sound"));
        } catch(Exception exception) {
            return null;
        }
    }

    public List<String> getBroadcastExemptedPlayers(String broadcastTitle) {
        return broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".exempted-players");
    }

    public List<String> getBroadcastConsoleCommands(String broadcastTitle) {
        return broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".console-commands");
    }

    public Schedule getSchedule(String broadcastTitle) {
        String type = broadcasts.getString("scheduled-broadcasts." + broadcastTitle + ".schedule.type");
        List<String> times = broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".schedule.times");

        switch(type.toLowerCase()) {
            case "monthly":
                List<Integer> daysOfMonth = broadcasts.getIntegerList("scheduled-broadcasts." + broadcastTitle + ".schedule.days-of-month");
                return new MonthlySchedule(daysOfMonth, times);
            case "weekly":
                List<String> days = broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".schedule.days");
                return new WeeklySchedule(days, times);
            case "daily":
                return new DailySchedule(times);
            case "hourly":
                List<Integer> minutes = broadcasts.getIntegerList("scheduled-broadcasts." + broadcastTitle + ".schedule.minutes");
                return new HourlySchedule(minutes);
            case "one-time":
                List<String> date = broadcasts.getStringList("scheduled-broadcasts." + broadcastTitle + ".schedule.dates");
                return new OneTimeSchedule(date, times);
            default:
                return null;
        }
    }
}
