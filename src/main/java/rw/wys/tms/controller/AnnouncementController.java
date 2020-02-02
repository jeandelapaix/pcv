package rw.wys.tms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.wys.tms.domain.Announcement;
import rw.wys.tms.domain.User;
import rw.wys.tms.service.*;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class AnnouncementController {
	@Autowired
	AnnouncementService announcementService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/announcement")
	public String userFront(Model model) {
		try {
			model.addAttribute("announcementList", announcementService.viewAllAnnouncements());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "announcement";
	}
	
	@RequestMapping(value = "/announcement/create", method = RequestMethod.POST)
	public String createProject(@ModelAttribute("announcement") Announcement announcement, Principal principal) throws Exception {
		System.out.println("Test 101: "+announcement);
		try {
			
			User user = userService.findByUsername(principal.getName());
			announcement.setCreater(user);
			announcementService.createAnnouncement(announcement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/announcement";
	}
	

}
