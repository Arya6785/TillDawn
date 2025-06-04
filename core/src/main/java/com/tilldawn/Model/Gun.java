package com.tilldawn.Model;

public class Gun {
    public String name;
    public float damage;
    public int projectile;
    public int TimeReload;
    public int maxAmmo;
    public int currentAmmo ;
    public boolean isReloading = false;
    private float reloadTimer = 0;

    public Gun (String name, int damage, int projectile, int TimeReload, int maxAmmo) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.TimeReload = TimeReload;
        this.maxAmmo = maxAmmo;
        currentAmmo = maxAmmo;
    }
    public void startReloading() {
        if (!isReloading && currentAmmo < maxAmmo) {
            isReloading = true;
            reloadTimer = TimeReload; // زمان بر حسب ثانیه
        }
    }

    public void updateReload(float delta) {
        if (isReloading) {
            reloadTimer -= delta;
            if (reloadTimer <= 0) {
                isReloading = false;
                reload();
            }
        }
    }
    public void reload() {
        currentAmmo = maxAmmo;
    }


}
