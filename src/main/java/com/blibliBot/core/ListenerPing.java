/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blibliBot.core;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 *
 * @author Zonthem
 */
public class ListenerPing extends ListenerAdapter {
    
    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        if (evt.getAuthor().isBot()) return;
        
        System.out.println("Message reçu v2");
        
        String message = evt.getMessage().getRawContent();
        
        if (message.equals("!ping")) {
            MessageChannel channel = evt.getChannel();
            channel.sendMessage("Pong !").queue();
        } else if (message.equals("!stop")) {
            MessageChannel channel = evt.getChannel();
            channel.sendMessage("Stoppage du bot...").queue();
            channel.sendMessage("Non, le développeur ne sait pas faire huhu.").queue();
        } else {
            evt.getChannel().sendMessage(message).queue();
        }
    }
}
