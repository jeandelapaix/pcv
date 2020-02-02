package rw.wys.tms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import rw.wys.tms.dao.NotificationDAO;
import rw.wys.tms.domain.Notification;
import rw.wys.tms.service.NotificationService;

public class NotificationServiceImpl implements NotificationService{

	@Autowired
	NotificationDAO notificationDAO;
	
	@Override
	@Transactional
	public List<Notification> getNotifications() {
		List<Notification> aList = (List<Notification>) notificationDAO.findAll();
		return aList;
	}

	@Override
	@Transactional
	public void saveNotification(Notification theNotification) {
		notificationDAO.save(theNotification);
	}

	@Override
	@Transactional
	public Notification getNotificationById(Long theId) {
		return notificationDAO.findOne(theId);
	}

	@Override
	@Transactional
	public void deleteNotificationById(Long theId) {
		notificationDAO.delete(theId);
		
	}

	@Override
	@Transactional
	public Notification notifyNow() {
		
		int last=getNotifications().size();
		return getNotifications().get(last-1);
	}

}
