/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blibliBot.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 *
 * @author Zonthem
 */
public class Runner extends ListenerAdapter {

    JDA jda;
    
    AudioController ac;

    public Runner(String arg) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(arg).buildBlocking();
            jda.addEventListener(new ListenerPing(jda));
            ac = new AudioController(jda, jda.getGuilds().get(0));
            ac.setup();
        } catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            Runner runner = new Runner(args[0]);
        } else {
            System.out.println("Token absent");
        }
    }
}
