package com.boteroni;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;
import java.util.ArrayList;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Sorter extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User person = event.getAuthor();
        if (person.isBot()) return; // make sure not to respond to itself

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
        ArrayList<String> list = getList();
        for(String word : list) {
            if(message.matches("(.*)" + word + "(.*)"))
                return true;
        }
        return false;
    }

    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<String>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Constants.FILTER_NAME));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            return list;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}


