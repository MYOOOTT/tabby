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
        Message message = event.getMessage();
        User person = event.getAuthor();
        String content = message.getRawContent().toLowerCase().replaceAll("\\s+","");
        if (person.isBot() && content.equals("noswearingonmychristianserver")) {
            message.delete().queue();
        }

        if (hasExplicit(content)) {
            MessageChannel channel = event.getChannel();
            message.delete().queue();
            channel.sendMessage("No SwEaRiNg On My ChRiStIaN sErVeR").queue();
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


