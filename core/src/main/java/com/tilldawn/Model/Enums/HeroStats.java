package com.tilldawn.Model.Enums;

public enum HeroStats {
    SHANA(4, 4),
    DIAMOND(7, 2),
    SCARLET(3, 5),
    LILITH(5, 3),
    DASHER(2, 7);

    public final int health;
    public final int speed;

    HeroStats(int health, int speed) {
        this.health = health;
        this.speed = speed;
    }
}

