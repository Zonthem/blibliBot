/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blibliBot.core;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

/**
 *
 * @author Zonthem
 */
public class AudioController {

    private Guild guild;
    private JDA jda;
    
    public AudioController(JDA jda, Guild guild) {
        this.jda = jda;
        this.guild = guild;
    }
    
    public void setup() {
        VoiceChannel vc = guild.getVoiceChannelsByName("Pour pas faire chier Hory!", true).get(0);
        AudioManager am = guild.getAudioManager();

        //lavaPlayer
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioPlayer player = playerManager.createPlayer();
        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);

        playerManager.loadItem("File:\\\\D:\\Dossiers\\Musiques\\Discord.mp3", new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                playlist.getTracks().forEach((track) -> {
                    trackScheduler.queue(track);
                });
            }

            @Override
            public void noMatches() {
                System.out.println("rien");
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                System.out.println("erreur");
            }
        });

        am.setSendingHandler(new AudioPlayerSendHandler(player));
        am.openAudioConnection(vc);
    }
}
