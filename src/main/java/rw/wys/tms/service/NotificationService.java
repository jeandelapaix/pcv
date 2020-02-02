package rw.wys.tms.service;

import java.util.List;

import rw.wys.tms.domain.Notification;

public interface NotificationService {
	
	public List<Notification> getNotifications();

	public void saveNotification(Notification theNotification);

	public Notification getNotificationById(Long theId);

	public void deleteNotificationById(Long theId);
	
	public Notification notifyNow();
}
