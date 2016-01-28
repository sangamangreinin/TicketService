package Ticket;

import java.util.Date;
import java.util.Set;

public class Ticket {
	private final int id;
	private String subject;
	private String agentName;
	private Set<String> tags;
	private final Date created;
	private Date modified;

	private Ticket(TicketBuilder builder) {
		this.id = builder.id;
		this.subject = builder.subject;
		this.agentName = builder.agentName;
		this.tags = builder.tags;
		this.created = builder.created;
		this.modified = builder.modified;
	}

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
		this.setModified();
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
		this.setModified();
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
		this.setModified();
	}

	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	private void setModified() {
		this.modified = new Date();
	}

	static class TicketBuilder {
		private int id = 0;
		private String subject = "";
		private String agentName = "";
		private Set<String> tags = null;
		private Date created = new Date();
		private Date modified = new Date();

		TicketBuilder withId(int id) {
			this.id = id;
			return this;
		}

		TicketBuilder withSubject(String subject) {
			this.subject = subject;
			return this;
		}

		TicketBuilder withAgent(String agentName) {
			this.agentName = agentName;
			return this;
		}

		TicketBuilder withTags(Set<String> tags) {
			this.tags = tags;
			return this;
		}

		TicketBuilder withCreated() {
			this.created = new Date();
			this.modified = new Date();
			return this;
		}

		Ticket build() {
			this.withCreated();
			return new Ticket(this);
		}
	}
}