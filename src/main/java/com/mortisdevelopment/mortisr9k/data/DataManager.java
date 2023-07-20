package com.mortisdevelopment.mortisr9k.data;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DataManager {

    private final MortisR9K plugin = MortisR9K.getInstance();
    private final H2Database database;
    private final Set<String> originals;

    public DataManager(H2Database database) {
        this.database = database;
        this.originals = new HashSet<>();
        initializeDatabase();
        loadDatabase();
    }

    private void initializeDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.execute("CREATE TABLE IF NOT EXISTS R9K(original tinytext)");
            }
        }.runTask(plugin);
    }

    public void loadDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ResultSet result = database.query("SELECT * FROM R9K");
                    while (result.next()) {
                        String original = result.getString("original");
                        originals.add(original);
                    }
                }catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    public synchronized void add(String original) {
        new BukkitRunnable() {
            @Override
            public void run() {
                originals.add(original);
                database.update("INSERT INTO R9K(original) VALUES (?)", original);
            }
        }.runTask(plugin);
    }

    public synchronized void clear() {
        new BukkitRunnable() {
            @Override
            public void run() {
                originals.clear();
                database.update("DELETE FROM R9K");
            }
        }.runTask(plugin);
    }

    public Set<String> getOriginals() {
        return originals;
    }
}
