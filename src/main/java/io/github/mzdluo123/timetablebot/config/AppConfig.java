package io.github.mzdluo123.timetablebot.config;

import io.github.mzdluo123.timetablebot.utils.Dependencies;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AppConfig {
    private static AppConfig INSTANCE = null;

    public List<Account> botAccounts;
    public String dbUrl;
    public String dbUser;
    public String dbPwd;
    public String baseUrl;
    public String authUrl;
    public List<Long> admin;
    public List<String> classTime;
    public int year;
    public int term;
    public String termBegin;

    private static File configFile;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public boolean isAdmin(Long user) {
        if (admin == null) {
            return false;
        }
        return admin.contains(user);
    }

    public static void loadConfig(File file) throws IOException {
        if (INSTANCE != null) {
            return;
        }
        configFile = file;
        Yaml yaml = Dependencies.getYaml();
        FileInputStream fileInputStream = new FileInputStream(file);
        INSTANCE = yaml.load(fileInputStream);
        fileInputStream.close();
    }

    public static void reload() throws IOException {
        Yaml yaml = Dependencies.getYaml();
        FileInputStream fileInputStream = new FileInputStream(configFile);
        INSTANCE = yaml.load(fileInputStream);
        fileInputStream.close();
    }

}

