package com.boteroni;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) {
        JDA discord = null;

        try {
            discord = new JDABuilder(AccountType.BOT)
                    .setToken(Constants.BOT_TOKEN)
                    .buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
        discord.addEventListener(new Commands());
        discord.addEventListener(new Sorter());
    }
}
