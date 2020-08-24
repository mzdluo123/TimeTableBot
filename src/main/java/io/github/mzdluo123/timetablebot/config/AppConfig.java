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
    public List<Long> admin;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public boolean isAdmin(Long user){
        return admin.contains(user);
    }

    public static void loadConfig(File file) throws IOException {
        if (INSTANCE != null){
            return;
        }
        Yaml yaml = Dependencies.getYaml();
        FileInputStream fileInputStream = new FileInputStream(file);
        INSTANCE = yaml.load(fileInputStream);
        fileInputStream.close();
    }

}

