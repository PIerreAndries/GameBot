package fr.pierre.gamebot;
import java.io.IOException;
import java.security.DigestException;

import javax.security.auth.login.LoginException;
import event.BotListener;
import fr.litarvan.pronote.FetchRequest;
import fr.litarvan.pronote.FetchResponse;
import fr.litarvan.pronote.LoginRequest;
import fr.litarvan.pronote.PronoteAPI;
import fr.litarvan.pronote.RequestException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;;


public class Main {

	public static void main(String[] args) {
						
		
		
		
		JDA jda;
		 try {
			jda = new JDABuilder(AccountType.BOT).setToken("Njk5NTY5MDA2MTA5MTMwNzcy.XpX4SA.uPLJCVRlH-TPXyg3_itrO9zMsUo").build();
			jda.addEventListener(new BotListener());
			jda.getPresence().setStatus(OnlineStatus.ONLINE);
			jda.getPresence().setActivity(Activity.playing("vous aider !"));		
			
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
