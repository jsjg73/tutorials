package lifecycleevents.model;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditTrailListener {

	private static Log log = LogFactory.getLog(AuditTrailListener.class);
	
	@PrePersist
	@PreUpdate
	@PreRemove
	private void beforeAnyUpdate(User user) {
		if(user.getId()==0) {
			log.info("[USER AUDIT] About to add a user");
		}else {
			log.info(String.format("[USER AUDIT] About to update/delete user: %d", user.getId()));
		}
	}
	
	@PostPersist
	@PostUpdate
	@PostRemove
	private void afterAnyUpdate(User user) {
		log.info(String.format("[USER AUDIT] add/update/delete compelte for user: %d", user.getId()));
	}
	
	@PostLoad
	private void afterLoad(User user) {
		log.info(String.format("[USER AUDIT] user loaded from database: %d", user.getId()));
	}
}
