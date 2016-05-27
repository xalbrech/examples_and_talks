package springdatajpatalk.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task {

	@Id
	String summary;
	@ManyToOne
	Developer developer;
	
	public Task() {
		super();
	}
		
	public Task(String summary, Developer developer) {
		super();
		this.summary = summary;
		this.developer = developer;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [summary=" + summary + "]";
	}
	
	
	
	
	
}
