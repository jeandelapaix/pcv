package rw.wys.tms.dao;
import org.springframework.data.repository.CrudRepository;

import rw.wys.tms.domain.Notification;

public interface NotificationDAO  extends CrudRepository<Notification, Long> {
	
}
