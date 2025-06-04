package com.tilldawn.Model.Enums;

import java.util.*;

public enum Ability {
    VITALITY,    // افزایش ماکس HP
    DAMAGER,     // 25% افزایش دمیج سلاح به مدت 10 ثانیه
    PROCLEASE,   // افزایش 1 واحد پروژه‌تیل سلاح
    AMOCREASE,   // افزایش 5 واحد ماکزیمم تیر سلاح
    SPEEDY;      // دو برابر شدن سرعت حرکت به مدت 10 ثانیه

    public static Ability getRandomAbility() {
        Ability[] values = values();
        return values[new Random().nextInt(values.length)];
    }

    public static List<Ability> getRandomAbilities(int count) {
        List<Ability> list = new ArrayList<>(Arrays.asList(values()));
        Collections.shuffle(list);
        return list.subList(0, count);
    }
}

