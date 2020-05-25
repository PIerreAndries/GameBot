package event;

import java.util.List;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.RestAction;

public class BotListener implements EventListener 
{
	
	
	Message messagereaction;
	@Override
	public void onEvent(GenericEvent event) 
	{
		System.out.println(event.getClass().getSimpleName());
		if(event instanceof GuildMessageReceivedEvent) 
		{
			onMessageReceived(((GuildMessageReceivedEvent) event).getMessage(), (GuildMessageReceivedEvent) event);
		}
		else if(event instanceof MessageReactionAddEvent) 
		{
			onReactionAdded((MessageReactionAddEvent) event);
		}
		else if(event instanceof GuildMessageEmbedEvent) 
		{
			
		}
			
	} 
	

	private void onMessageReceived(Message message, GuildMessageReceivedEvent event) 
	{
		
		message.getChannel().deleteMessageById(message.getId());
		if(message.getAuthor().equals(message.getJDA().getSelfUser())) 
		{
			return;
		}
		else if(message.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE))
		{
			String[] args = message.getContentRaw().split("\\s+");
			
			if(args[0].equalsIgnoreCase("!info")) 
			{
				message.delete().complete();
				EmbedBuilder info = new EmbedBuilder();
				info.setTitle("Informations !");
				info.setDescription("Liste des commandes disponibles :");
				info.addField("!info", "!info permet d'afficher ce message d'information☜(ﾟヮﾟ☜) ", false);
				info.addField("!clear", "!clear permet de supprimer le nombre de messages souhaités dans l'ordre chronologique exmple : !clear 5 supprime 5 messages ❌", false);
				info.addField("!sondage", "!sondage donne des informations sur le(s) sondage(s) en cours (allez y jeter un oeil) 👍", false);
				info.addField("!roles", "!roles permet d'afficher les roles discord que tout le monde possède comme les jeux auxquels on joue ou notre hiérarchie au sein du serveur ✔", false);
				info.setColor(0x5ccdfa);
				sendMessage(info.build(), event);
				addReaction(event, event.getChannel().getLatestMessageId(), "❌");
				message.delete().complete();



				;
				

			}
			
			else if(args[0].equalsIgnoreCase("!clear") && args.length == 2) 
			{
				message.delete().complete();

				try 
				{
					if(message.getAuthor().getName().equalsIgnoreCase("Pierre_Andries") || message.getAuthor().getName().equalsIgnoreCase("Fantin") || Integer.parseInt(args[1]) <= 5  || message.getAuthor().getName().equalsIgnoreCase("Dr Daunt")) 
					{
						List<Message> messages = message.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
						event.getChannel().deleteMessages(messages).queue();
						EmbedBuilder succes = new EmbedBuilder();
						succes.setTitle("Réussi");
						succes.setDescription(args[1] + " messages ont bien été supprimés ✔");
						succes.setColor(0x5cfa91);
						sendMessage(succes.build(), event);
						addReaction(event, event.getChannel().getLatestMessageId(), "❌");


					}
					else 
					{
						EmbedBuilder OnSeCalme = new EmbedBuilder();
						OnSeCalme.setTitle("On Se CALME !!!");
						OnSeCalme.setDescription("Vous voulez supprimer beaucoup trop de messages !♦♦");
						OnSeCalme.setColor(0xff0000);
						sendMessage(OnSeCalme.build(), event);
						addReaction(event, event.getChannel().getLatestMessageId(), "❌");

								
					}
					

				}
				catch(IllegalArgumentException e)
				{
					
						EmbedBuilder error = new EmbedBuilder();
						error.setTitle("Le fail !");
						error.setColor(0xff0000);
						error.addField("erreur", "Aucun message valide ici ♦", false);
						sendMessage(error.build(), event);
						addReaction(event, event.getChannel().getLatestMessageId(), "❌");

				}
				
					
			}
			else if(args[0].equalsIgnoreCase("!roles")) 
			{
				message.delete().complete();
				String nickname ;
				List<Role> Role;
				List<Member> members = message.getGuild().getMembers();
				EmbedBuilder rolesEmbed = new EmbedBuilder();
				rolesEmbed.setTitle("Rôles");
				rolesEmbed.setDescription("Voici les rôles de chacun :");
				for(Member s : members) 
				{
					nickname = s.getAsMention();
					Role = s.getRoles();
					rolesEmbed.addField("", nickname + " " + Role.toString().replaceAll("[1234567890]", ""), false);
					rolesEmbed.setColor(0x37ded6);
					
				}
				sendMessage(rolesEmbed.build(), event);
				addReaction(event, event.getChannel().getLatestMessageId(), "❌");


				
				
			
				
				
					
					
			}
			else if(args[0].equalsIgnoreCase("!présentation")) 
			{
				message.delete().complete();
				EmbedBuilder pres = new EmbedBuilder();
				pres.setTitle("Team Kwin !😲");
				pres.setDescription("Bonjour et bienvenue dans la team Kwin !! 👍");
				pres.addField("", "Cette team est spécialisée dans les jeux suivants :", false);
				pres.addField("", "Fortnite, Rocket League, League of Legends CS:GO, Warzone, Minecraft et bien d'autres !✨", false);
				pres.addField("", "Nous sommes une team fun et compétitive dans le but d'amuser le grand public sur notre chaine youtube : https://www.youtube.com/channel/UC-vjfRz-_tBeFDPgtJWXL_Q  👈", false);
				pres.addField("", "Nous sommes très heureux de vous avoir parmis nous😊,  bien à vous,", false);
				pres.addField("", "Dr Daunt, fondateur de la team", false);
				pres.setColor(0x42f5ce);
				sendMessage(pres.build(), event);

				
				
			}
			else if(args[0].equalsIgnoreCase("!mute") && args.length == 2 && event.getAuthor().getName().equalsIgnoreCase("Pierre_Andries") || event.getAuthor().getName().equalsIgnoreCase("Fantin")) 
			{
				Member memberbann;
				List<Member> bannedMember = event.getGuild().getMembersByName(args[1], true);
				for(Member m: bannedMember) 
				{
					memberbann = m;
				}
				
			}
			else if(args[0].equalsIgnoreCase("!sondage") || args[0].equalsIgnoreCase("!sondages")) 
			{
				Message messagereact = event.getMessage();
				RestAction<List<Message>> message1 = event.getChannel().getHistory().retrievePast(1);
				for(Message message2: message1.complete()) 
				{
					messagereact = message2;
					messagereact.addReaction("❌");
				}
					
				onPrintResults(event, event.getGuild(), messagereact);
				
					
				
				}
			
			
	
		
				
					
				
				
		}
		
	}
	public void addReaction(GuildMessageReceivedEvent event,String messageId, String emote) {
		 event.getChannel().addReactionById(messageId, emote).queue();
	}
	
	public void onReactionAdded(MessageReactionAddEvent event) 

	{
		if(event.getReactionEmote().getName().equals("❌") && !event.getUser().equals(event.getJDA().getSelfUser())) 
		{
			event.getChannel().deleteMessageById(event.getMessageId()).complete();
		}
	}
	
	public void sendMessage(MessageEmbed message, GuildMessageReceivedEvent event)

	{
		Message messageBot ;
		event.getChannel().sendMessage(message).complete();
		List<Message> messages = event.getChannel().getHistory().retrievePast(1).complete();
		for(Message s : messages) {
			messageBot = s;
			messageBot.addReaction("❌").complete();
		}
		

		

	}
	
	public void onPrintResults(GuildMessageReceivedEvent event,Guild guild, Message message) 

	{
		
		int un = 0;
		int deux = 0;
		List<MessageReaction> react = message.getReactions();
		for(MessageReaction s : react) 
		{
			if(s.getReactionEmote().equals("✔")) {
				un += 1;
			}
			else if(s.getReactionEmote().equals("❌")) {
				deux +=1;
			}
			
			
			
		}
		
		EmbedBuilder results = new EmbedBuilder();
		
		results.setColor(0xfffff);
		results.setTitle("Sondage :");
		results.setDescription("Avez-vous envie de retourner au collège ?");
		if(un > deux) {
			results.addField("victoire du oui","le choix oui possède " + un + " voies", false);
		}
		else if(deux > un) 
		{
			results.addField("victoire du non", "le choix non possède " + deux + " voies", false);
		}
		else if(un == deux) {
			results.addField("égalité !", un + " voies contre " + deux + " voies !", false);
		}	
		message.getChannel().sendMessage(results.build());
		addReaction(event, message.getChannel().getLatestMessageId(), "❌");
		message.delete().complete();


		
			
		
	}
	
	
		
	
		
		
}

	

	
