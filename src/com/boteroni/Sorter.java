package com.boteroni;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Sorter extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User person = event.getAuthor();
        User bot = null;
        if (person.isBot()) {
            bot = person;
            return; // make sure not to respond to itself and record bot
        }

        //gets the message from discord & extracts the String
        Message message = event.getMessage();
        String content = message.getRawContent().toLowerCase().replaceAll("\\s+","");
        if (hasExplicit(content)) {
            MessageChannel channel = event.getChannel();
            message.delete().queue();
            channel.sendMessage("Hey man, no cussing.").queue(m -> m.delete().queueAfter(5, SECONDS));
        }


    }


    public boolean hasExplicit(String message) {
        return message.matches("(.*)fuck(.*)");
    }
}