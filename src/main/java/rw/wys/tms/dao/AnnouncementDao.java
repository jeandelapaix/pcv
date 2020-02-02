package rw.wys.tms.dao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import rw.wys.tms.domain.*;

public interface AnnouncementDao extends CrudRepository<Announcement, Long> {
	List<Announcement> findByCreater(User user);
}
