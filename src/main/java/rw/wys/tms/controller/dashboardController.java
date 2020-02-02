package rw.wys.tms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.wys.tms.domain.Task;
import rw.wys.tms.service.AssignmentService;
import rw.wys.tms.service.ProjectService;
import rw.wys.tms.service.TaskService;
import rw.wys.tms.service.UserService;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class dashboardController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping("dashboard")
	public String taskPage(@ModelAttribute("task") Task task, Model model) {
		
		model.addAttribute("userList", userService.findByEnabled(true));
		model.addAttribute("projectList", projectService.viewAllProject());
		model.addAttribute("taskList", taskService.findAllTask());
		model.addAttribute("taskSize", taskService.findAllTask().size());

		return "dashboard";
	}
}
