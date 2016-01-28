package Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TicketController {
	private HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();

	public HashMap<Integer, Ticket> getTickets() {
		return tickets;
	}

	public Ticket createTicket(HashMap<String, String> attributes) {
		Logger.info("Create ticket operation started!");
		// cjm - if you have a separate class to provide factory methods,
		// it's most common to either
		// 1) Create a persistent TicketFactory and re-use it, or
		// 2) Use static methods in the TicketFactory class.

		Ticket ticket = TicketFactory.createTicket(attributes);
		getTickets().put(ticket.getId(), ticket);
		return ticket;
	}

	// Providing string names to update properties can be problematic.
	// Usually within Java code if you need to do something like this you
	// would try to use an enum rather than just strings. If you do need
	// to use strings, define common string constants in a class somehwere,
	// and use them consistently.
	public Ticket updateTicket(HashMap<String, String> attributes, int id) {
		Logger.info("Update ticket operation started!");
		try {
			Ticket ticket = getTickets().get(id);

			if (ticket != null) {
				return TicketFactory.updateTicket(attributes, ticket);
			} else
				Logger.info("Ticket with id " + id + " not found!");
		} catch (Exception e) {
			Logger.info("Exception occurred while updating ticket!");
			return null;
		}
		return null;
	}

	public Ticket deleteTicket(int id) {
		Logger.info("Delete ticket operation started!");
		if (getTickets().get(id) != null) {
			return getTickets().remove(id);
		} else Logger.info("Delete ticket operation failed! Ticket not found!");
		return null;
	}

	public Ticket getTicket(int id) {
		Logger.info("Get ticket detail operation started!");
		return getTickets().get(id);
	}
	
	public List<Ticket> getList() {
		List<Ticket> ticketList =  new ArrayList<Ticket>(getTickets().values());
		// having a "mode" like this is problematic for some of the reasons
		// mentioned in the other comment. I strongly recommend just implementing
		// separate methods for the various use cases.
		return getListDescendingByModified(ticketList);
	}
	
	public List<Ticket> getListDescendingByModified(List<Ticket> ticketList) {
		return ticketList
                .stream()
                .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                .collect(Collectors.toList());
	}
	
	public List<Ticket> getListByAgentName(String agentName) {
		// Note that you /assign/ to the method variable 'ticketList'; you never read from it
		// So that parameter isn't needed at all.
		// For this reason it's common to make method parameters final--the compiler would not allow this in that case.
		List<Ticket> ticketList =  new ArrayList<Ticket>(getTickets().values());
		return getListDescendingByModified(ticketList.stream().filter(
				ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase())
			).collect(Collectors.toList()));
	}
	
	public List<Ticket> getListByTagName(String tagName) {
		List<Ticket> ticketList =  new ArrayList<Ticket>(getTickets().values());
		return getListDescendingByModified(ticketList.stream().filter(
			ticket -> ticket.getTags().contains(tagName.toLowerCase())
			).collect(Collectors.toList()));
	}

	public void printTicket(Ticket ticket) {
		System.out.println("Ticket data id: " + ticket.getId() + " \n subject: " + ticket.getSubject() + " \n agent Name: "
				+ ticket.getAgentName() + " \n Tags : " + ticket.getTags().toString() + " \n Created " + ticket.getCreated().toString()
				+ " \n Modified " + ticket.getModified().toString()
				);
	}
}