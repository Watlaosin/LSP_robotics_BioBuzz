package org.firstinspires.ftc.teamcode.config;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class RobotConfig {

    private final Properties properties = new Properties();

    public RobotConfig(Context context) {
        try (InputStream input =
                     context.getAssets().open("Robot.config")) {

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load Robot.config",
                    e
            );
        }
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        value = value.trim();

        if (value.equalsIgnoreCase("true")) {
            return true;
        }

        if (value.equalsIgnoreCase("false")) {
            return false;
        }

        return defaultValue;
    }
}