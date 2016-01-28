package Test;

import org.junit.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import Ticket.*;

public class TestTicket {
	private static TicketController tController = new TicketController();

	@Test
	public void testCreateTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Assert.assertEquals("Test 1", ticket.getSubject());
		Assert.assertEquals("Sangam", ticket.getAgentName());
		Assert.assertEquals(1, ticket.getId());
		Assert.assertNotNull(ticket.getCreated());
		Assert.assertNotNull(ticket.getModified());
		Assert.assertNotNull(ticket.getTags());

		Set<String> tags = ticket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}
		tController.deleteTicket(ticket.getId());
	}

	@Test
	public void testUpdateTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Assert.assertEquals("Test 1", ticket.getSubject());
		Assert.assertEquals("Sangam", ticket.getAgentName());
		Assert.assertEquals(2, ticket.getId());
		Assert.assertNotNull(ticket.getCreated());
		Assert.assertNotNull(ticket.getModified());
		Assert.assertNotNull(ticket.getTags());

		Set<String> tags = ticket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}

		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("subject", "Test 2");
		data1.put("agentName", "Pradeep");
		data1.put("tags", "max,min");
		Ticket ticket1 = tController.updateTicket(data1, ticket.getId());
		Assert.assertEquals("Test 2", ticket1.getSubject());
		Assert.assertEquals("Pradeep", ticket1.getAgentName());
		Logger.info(" " + ticket1.getId());
		Assert.assertEquals(2, ticket1.getId());
		Assert.assertNotNull(ticket1.getCreated());
		Assert.assertNotNull(ticket1.getModified());
		Assert.assertNotNull(ticket1.getTags());

		Set<String> tags1 = ticket1.getTags();
		int j = 0;
		for (String s : tags1) {
			Logger.info(s);
			if (j == 0)
				Assert.assertEquals("min", s);
			else
				Assert.assertEquals("max", s);
			j++;
		}
		tController.deleteTicket(ticket1.getId());
	}
	
	@Test
	public void testDeleteTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		tController.deleteTicket(ticket.getId());
		Assert.assertEquals(0, tController.getTickets().size());
	}
	
	@Test
	public void testGetTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		
		Ticket detailTicket = tController.getTicket(ticket.getId());
		Assert.assertEquals("Test 1", detailTicket.getSubject());
		Assert.assertEquals("Sangam", detailTicket.getAgentName());
		Assert.assertEquals(ticket.getId(), detailTicket.getId());
		Assert.assertNotNull(detailTicket.getCreated());
		Assert.assertNotNull(detailTicket.getModified());
		Assert.assertNotNull(detailTicket.getTags());

		Set<String> tags = detailTicket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}
		tController.deleteTicket(detailTicket.getId());
	}
	
	
	@Test
	public void testListSortedByModifiedDate() throws InterruptedException {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Thread.sleep(500); // this will halt the execution in result created and modified date will be different for new ticket
		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("subject", "Test 2");
		data1.put("agentName", "Pradeep");
		data1.put("tags", "foo,bar");
		Ticket ticket1 = tController.createTicket(data1);
		Thread.sleep(500);
		HashMap<String, String> data2 = new HashMap<String, String>();
		data2.put("subject", "Test 3");
		data2.put("agentName", "Ganesh");
		data2.put("tags", "foo,bar");
		Ticket ticket2 = tController.createTicket(data2);
		Thread.sleep(500); 
		List<Ticket> ticketList = tController.getList();
		/*ticketList.forEach((temp) -> {
			 tController.printTicket(temp);
		});*/
		Assert.assertEquals("Test 3", ticketList.get(0).getSubject());
		Assert.assertEquals("Test 2", ticketList.get(1).getSubject());	
		Assert.assertEquals("Test 1", ticketList.get(2).getSubject());
		Thread.sleep(2000); 
		// now update ticket 2 and it should go on index 0
		HashMap<String, String> data4 = new HashMap<String, String>();
		data4.put("subject", "Test 4");
		data4.put("agentName", "Pradeep");
		data4.put("tags", "max,min");
		ticket1 = tController.updateTicket(data4, ticket1.getId());
		
		List<Ticket> newTicketList = tController.getList();
		/*ticketList.forEach((temp) -> {
			tController.printTicket(temp);
		});*/
		Assert.assertEquals("Test 4", newTicketList.get(0).getSubject());
		Assert.assertEquals("Test 3", newTicketList.get(1).getSubject());	
		Assert.assertEquals("Test 1", newTicketList.get(2).getSubject());
		
		tController.deleteTicket(ticket.getId());
		tController.deleteTicket(ticket1.getId());
		tController.deleteTicket(ticket2.getId());
	}
	
	@Test
	public void testListByAgentName() throws InterruptedException{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		
		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("subject", "Test 2");
		data1.put("agentName", "Pradeep");
		data1.put("tags", "foo,bar");
		Ticket ticket1 = tController.createTicket(data1);

		HashMap<String, String> data2 = new HashMap<String, String>();
		data2.put("subject", "Test 3");
		data2.put("agentName", "GanesH");
		data2.put("tags", "foo,bar");
		Ticket ticket2 = tController.createTicket(data2);
		
		List<Ticket> newTicketList = tController.getListByAgentName("ganesh");
		Assert.assertEquals(1, newTicketList.size());
		Assert.assertEquals("Test 3", newTicketList.get(0).getSubject());
		
		List<Ticket> newTicketList1 = tController.getListByAgentName("GANESH");
		Assert.assertEquals(1, newTicketList1.size());
		Assert.assertEquals("Test 3", newTicketList1.get(0).getSubject());
		
		HashMap<String, String> data3 = new HashMap<String, String>();
		data3.put("subject", "Test 4");
		data3.put("agentName", "ganesh");
		data3.put("tags", "foo,bar");
		Ticket ticket3 = tController.createTicket(data3);
		Thread.sleep(500);
		List<Ticket> newTicketList2 = tController.getListByAgentName("GANESH");
		Assert.assertEquals(2, newTicketList2.size());
		Assert.assertEquals("Test 4", newTicketList2.get(0).getSubject());
		Assert.assertEquals("Test 3", newTicketList2.get(1).getSubject());
		
		tController.deleteTicket(ticket.getId());
		tController.deleteTicket(ticket1.getId());
		tController.deleteTicket(ticket2.getId());
		tController.deleteTicket(ticket3.getId());
	}
	
	@Test
	public void testListByTagName() throws InterruptedException{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Thread.sleep(500);
		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("subject", "Test 2");
		data1.put("agentName", "Pradeep");
		data1.put("tags", "min,bar");
		Ticket ticket1 = tController.createTicket(data1);
		Thread.sleep(500);
		HashMap<String, String> data2 = new HashMap<String, String>();
		data2.put("subject", "Test 3");
		data2.put("agentName", "GanesH");
		data2.put("tags", "foo,max");
		Ticket ticket2 = tController.createTicket(data2);
		
		List<Ticket> newTicketList = tController.getListByTagName("BAR");
		Assert.assertEquals(2, newTicketList.size());
		Assert.assertEquals("Test 2", newTicketList.get(0).getSubject());
		Assert.assertEquals("Test 1", newTicketList.get(1).getSubject());
		
		List<Ticket> newTicketList1 = tController.getListByTagName("Bar");
		Assert.assertEquals(2, newTicketList1.size());
		Assert.assertEquals("Test 2", newTicketList1.get(0).getSubject());
		Assert.assertEquals("Test 1", newTicketList1.get(1).getSubject());
		
		tController.deleteTicket(ticket.getId());
		tController.deleteTicket(ticket1.getId());
		tController.deleteTicket(ticket2.getId());
	}
	
}