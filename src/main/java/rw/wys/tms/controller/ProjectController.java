package rw.wys.tms.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;
import rw.wys.tms.domain.User;
import rw.wys.tms.service.ProjectService;
import rw.wys.tms.service.UserService;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/project/create", method = RequestMethod.POST)
	public String createProject(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult, @ModelAttribute("userId") String userId) throws Exception {
		try {
			if (bindingResult.hasErrors()) {

				return "forward:/createProject";

			}
			else {
				projectService.createProject(project);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/projects";
	}

	@RequestMapping("/createProject")
	public String viewProjects(Model model, @ModelAttribute("userId") String userId) {
		try {
			
			model.addAttribute("userList", userService.findByEnabled(true));
			model.addAttribute("projectList", projectService.viewAllProject());
			model.addAttribute("project", new Project());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "createProject";
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	@RequestMapping("/projects")
	public String viewProjectsLists(Model model) {
		try {
			model.addAttribute("userList", userService.findByEnabled(true));
			model.addAttribute("projectList", projectService.viewAllProject());
			model.addAttribute("project", new Project());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "projects";
	}
	@RequestMapping(value = "/project/edit", method = RequestMethod.POST)
	public String editProject( Model model, @ModelAttribute("project") Project project, @ModelAttribute("projectId") String projectId,@ModelAttribute("userId") String userId) {

		try {
			projectService.updateProject(project);
//			if (!userId.equals("")) {
//				User user = userService.findbyId(Long.parseLong(userId));
//				if (user != null) {
//					assignmentService.save(task, user);
//				}
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/projects";
	}
	
	@RequestMapping(value = "/project/delete", method = RequestMethod.POST)
	public String deleteProject(Model model, @ModelAttribute("project") Project project,@ModelAttribute("projectId") String projectId, @ModelAttribute("userId") String userId) {

		try {			
			projectService.removeProject(Long.parseLong(projectId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/projects";
	}
	
	
	@RequestMapping("/project/view/details")
	public String viewProject(Model model, @ModelAttribute("projectId") String projectId) {
		try {
			Project project=projectService.findById(Long.parseLong(projectId));
			model.addAttribute("userList", userService.findByEnabled(true));
			model.addAttribute("project", project);
			model.addAttribute("tasks", project.getTasks());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewProject";
	}
	
	
	
	
	
}
