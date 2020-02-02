package rw.wys.tms.service;

import java.util.List;
import rw.wys.tms.domain.Announcement;
import rw.wys.tms.domain.User;

public interface AnnouncementService {
	List<Announcement> findByCreater(User user);
	Announcement findById(Long id);
	boolean createAnnouncement(Announcement an);
	List<Announcement> viewAllAnnouncements();
}
