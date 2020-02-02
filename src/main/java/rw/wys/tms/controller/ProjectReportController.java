package rw.wys.tms.controller;

import java.security.Principal;

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
import rw.wys.tms.domain.User;
import rw.wys.tms.service.ProjectService;
import rw.wys.tms.service.UserService;
import rw.wys.tms.serviceImpl.ProjectReportService;
import rw.wys.tms.util.ReportProject;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class ProjectReportController {
	
	@Autowired
	ProjectReportService projectReportService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;	
	
	private ReportProject reportProject;
	
	@RequestMapping(value = "/report/select", method = RequestMethod.POST)
	public String createProject(@ModelAttribute("projectId") Long projectId, BindingResult bindingResult) throws Exception {
		try {
			if (bindingResult.hasErrors()) {
				System.err.println("Errors");
				return "forward:/report";

			}
			else {
				
				Project project = projectService.findById(projectId);
				reportProject = projectReportService.findReport(project);
				System.out.println("OK");
			}
			

		} catch (Exception e) {
			System.err.println("Don't work");
			e.printStackTrace();
		}
		return "forward:/report";
	}

	@RequestMapping("/report")
	public String viewProjects(Model model) {
		try {
			model.addAttribute("projectList", projectService.viewAllProject());
			if(reportProject != null)
				model.addAttribute("reportProject",reportProject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "report";
	}
}
