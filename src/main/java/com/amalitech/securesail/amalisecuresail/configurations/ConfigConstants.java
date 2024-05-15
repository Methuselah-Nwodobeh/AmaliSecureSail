package com.amalitech.securesail.amalisecuresail.configurations;

public class ConfigConstants {
    public final boolean debug;


    public ConfigConstants() {
        this.debug = Boolean.parseBoolean(System.getenv("DEBUG"));
    }
}
