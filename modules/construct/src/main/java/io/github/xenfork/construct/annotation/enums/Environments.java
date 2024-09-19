package io.github.xenfork.construct.annotation.enums;

public enum Environments {
    all("*"), client, server;
    private final String name;
    Environments(String name) {
        this.name = name;
    }
    Environments() {
        this.name = name();
    }

    public String getName() {
        return name;
    }
}
