package rw.wys.tms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;
import rw.wys.tms.domain.User;
import rw.wys.tms.service.AssignmentService;
import rw.wys.tms.service.ProjectService;
import rw.wys.tms.service.TaskService;
import rw.wys.tms.service.UserService;

@Controller
@SessionAttributes(value = { "loggedUser", "userRoles" })
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private AssignmentService assignmentService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@RequestMapping("createTask")
	public String taskPage(@ModelAttribute("task") Task task, Model model) {
		List<String> list = new ArrayList<String>();
		for (User us : userService.findByEnabled(true)) {
			list.add(us.getEmail());
		}

		model.addAttribute("emailList", list.toArray());
		model.addAttribute("userList", userService.findByEnabled(true));
		model.addAttribute("projectList", projectService.viewAllProject());
		model.addAttribute("taskList", taskService.findAllTask());

		return "createTask";
	}

	@RequestMapping(value = "/task/create", method = RequestMethod.POST)
	public String createTask(Model model, @ModelAttribute("task") @Valid Task task,
			@ModelAttribute("projectId") String projectId, @ModelAttribute("assignedUser") String assignedUser,
			BindingResult bindingResult) {

		try {
			if (bindingResult.hasErrors()) {
				
				return "createTask";
				
			} else {
				Project project = projectService.findById(Long.parseLong(projectId));
				task.setProject(project);

				task = taskService.createTask(task);
				if (!assignedUser.equals("")) {

					for (String email : assignedUser.split(",")) {
						User user = userService.findByEmail(email);
						if (user != null) {
							assignmentService.save(task, user);
						}
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/tasks";
	}

	@RequestMapping(value = "/task/edit", method = RequestMethod.POST)
	public String editTask(Model model, @ModelAttribute("task") Task task,
			@ModelAttribute("projectId") String projectId, @ModelAttribute("userId") String userId) {

		try {

			Project project = projectService.findById(Long.parseLong(projectId));
			task.setProject(project);

			taskService.updateTask(task);
			// if (!userId.equals("")) {
			// User user = userService.findbyId(Long.parseLong(userId));
			// if (user != null) {
			// assignmentService.save(task, user);
			// }
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/tasks";
	}

	@RequestMapping(value = "/task/changestatus", method = RequestMethod.POST)
	public String changeStatus(Model model, @ModelAttribute("task") Task task, @ModelAttribute("taskId") String taskId,
			@ModelAttribute("userId") String userId) {

		try {
			task = taskService.findById(Long.parseLong(taskId));
			assignmentService.changeTaskStatus(task);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/tasks";

	}

	@RequestMapping("tasks")
	public String tasks(@ModelAttribute("task") Task task, Model model) {

		model.addAttribute("userList", userService.findByEnabled(true));
		model.addAttribute("projectList", projectService.viewAllProject());
		model.addAttribute("taskList", taskService.findAllTask());

		return "tasks";
	}

	@RequestMapping("todo")
	public String todo(Authentication auth, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("taskList", assignmentService.findActiveByUser(user));
		return "todo";
	}
}
