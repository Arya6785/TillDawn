package com.tilldawn.Model;

public class Gun {
    public String name;
    public int damage;
    public int projectile;
    public int TimeReload;
    public int maxAmmo;
    public Gun (String name, int damage, int projectile, int TimeReload, int maxAmmo) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.TimeReload = TimeReload;
        this.maxAmmo = maxAmmo;
    }

}
