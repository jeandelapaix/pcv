package rw.wys.tms.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.wys.tms.dao.AnnouncementDao;
import rw.wys.tms.dao.AssignmentDao;
import rw.wys.tms.domain.Announcement;
import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.User;
import rw.wys.tms.service.AnnouncementService;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService{

	@Autowired
	private AnnouncementDao announcementDao;

	@Override
	public List<Announcement> findByCreater(User user) {
		if(user != null)
			return announcementDao.findByCreater(user);
		return null;
	}

	@Override
	public Announcement findById(Long id) {
		if(id != 0 && id != null)
			return announcementDao.findOne(id);
		return null;
	}

	@Override
	public boolean createAnnouncement(Announcement an) {
		try{
			announcementDao.save(an);
			return true;
		}
		catch(Exception e) {
			System.err.println(e);
			return false;
		}
		
	}
	
	@Override
	public List<Announcement> viewAllAnnouncements() {
		return (List<Announcement>) announcementDao.findAll();
	}

}
