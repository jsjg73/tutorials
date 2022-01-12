package lifecycleevents.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@EntityListeners(AuditTrailListener.class)
@Entity
public class User {
	private static Log log = LogFactory.getLog(User.class);
	
	@Id
	@GeneratedValue
	private int id;
	
	private String userName;
	private String firstName;
	private String lastName;
	
	@Transient
	private String fullName;

	@PrePersist
	public void logNewUserAttempt() {
		log.info("Attempting to add new user with username: "+ userName);
	}
	
	@PostPersist
	public void logNewUserAdded() {
		log.info(String.format("Added user '%s' with ID: %d", userName, id));
	}
	
	@PreRemove
	public void logUserRemovalAttempt() {
		log.info(String.format("Attempting to delete user: %s", userName));
	}
	
	@PostRemove
	public void logUserRemoval() {
		log.info(String.format("Deleted user: %s", userName));
	}
	
	@PreUpdate
	public void logUserUpdateAttempt() {
		log.info(String.format("Attempting to update user: %s", userName));
	}
	
	@PostUpdate
	public void logUserUpdate() {
		log.info(String.format("Updated user: %s", userName));
	}
	
	@PostLoad
	public void logUserLoad() {
		fullName = firstName+" "+lastName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
