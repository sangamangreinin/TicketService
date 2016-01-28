package Ticket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TicketFactory{
	private static int id = 0;
	
	public static Ticket createTicket(HashMap<String, String> attributes){
		TicketFactory.id++;
		// I like the use of Logger rather than just directly writing to the console
		Logger.info("Setting up new id for ticket " + TicketFactory.id);
		String tagStr = attributes.get("tags");
		HashSet<String> tags = new HashSet<String> (Arrays.asList(tagStr.split(",")));
		// See my other comment about hard-coding field names like "agentName".
		Ticket ticket = new Ticket.TicketBuilder()
				.withId(TicketFactory.id)
				.withAgent(attributes.get("agentName"))
				.withSubject(attributes.get("subject"))
				.withTags(tags)
				.build();
		Logger.info("New ticket is created successfully!");
		return ticket;
	}

	// I would implement separate methods rather than sending the update in a Map like this.
	// If you do want a way to encapsulate ticket updates into one object, I would create
	// a dedicated 'TicketUpdate' class to describe the update.
	public static Ticket updateTicket(HashMap<String, String> attributes, Ticket ticket) {
		String s = null;
		if ((s = attributes.get("agentName")) != null) {
			Logger.info("Updating agent name of ticket");
			ticket.setAgentName(s);
		}
		if ((s = attributes.get("subject")) != null) {
			Logger.info("Updating subject of ticket");
			ticket.setSubject(s);
		}
		if ((s = attributes.get("tags")) != null) {
			Logger.info("Updating tags of ticket");
			HashSet<String> tags = new HashSet<String>(Arrays.asList(s.split(",")));
			ticket.setTags(tags);
		}
		Logger.info("Update operation is successful for ticket id " + ticket.getId());
		return ticket;
	}
}