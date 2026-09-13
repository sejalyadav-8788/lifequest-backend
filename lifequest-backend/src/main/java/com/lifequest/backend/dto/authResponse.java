package com.lifequest.backend.dto;

public class authResponse {

    public Long id;
    public String username;
    public String email;

    public int level;
    public int xp;
    public int gold;

    public int strength;
    public int intellect;
    public int discipline;

    public int streak;

    public authResponse(
            Long id,
            String username,
            String email,
            int level,
            int xp,
            int gold,
            int strength,
            int intellect,
            int discipline,
            int streak) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.level = level;
        this.xp = xp;
        this.gold = gold;
        this.strength = strength;
        this.intellect = intellect;
        this.discipline = discipline;
        this.streak = streak;
    }
}