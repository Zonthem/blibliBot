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

    public Runner(String arg) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(arg).buildBlocking();
            jda.addEventListener(new ListenerPing());
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
    
    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        if (evt.getAuthor().isBot()) return;
        
        System.out.println("Message reçu");
        
        String message = evt.getMessage().getRawContent();
        
        if (message.equals("!ping")) {
            MessageChannel channel = evt.getChannel();
            channel.sendMessage("Pong !").queue();
        } else if (message.equals("!stop")) {
            MessageChannel channel = evt.getChannel();
            channel.sendMessage("Stoppage du bot...").queue();
            jda.shutdown();
        } else {
            evt.getChannel().sendMessage(message).queue();
        }
    }
}
