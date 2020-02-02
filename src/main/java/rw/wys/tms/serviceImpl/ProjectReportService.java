package rw.wys.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.wys.tms.domain.*;
import rw.wys.tms.util.ReportProject;
import rw.wys.tms.util.ReportTask;
import java.util.*;
@Service
public class ProjectReportService {

	@Autowired
	private ReportProject reportProject;
	
	@Autowired 
	private TaskServiceImpl taskService;
	
	@Autowired
	private AssignmentServiceImpl assignmentService;
	public ReportProject findReport(Project project) {
		try {
		reportProject.setProject(project);
		List<Task> tasks= taskService.findByProject(project.getId());
		List<ReportTask> reportTasks = new ArrayList();
		
		for( Task task:tasks) {
			ReportTask rt = new ReportTask();
			rt.setTask(task);
			List<Assignment> assignments = assignmentService.findActiveByTask(task);
			Map<User, Date> users = new HashMap<>();
			Map<String, Date> status = new HashMap<>();
			for(Assignment assignment: assignments) {
				users.put(assignment.getUser(), new Date());
				rt.setUser_Assigned(users);
			}
			reportTasks.add(rt);
		}
		reportProject.setReportTask(reportTasks);
		return reportProject;
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
