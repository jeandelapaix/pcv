package rw.wys.tms.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rw.wys.tms.domain.*;
import rw.wys.tms.service.*;

@Component
public class ReportProject {

	private Project project;
	private List<ReportTask> reportTask;
	public ReportProject() {
		
	}
	
	public ReportProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ReportTask> getReportTask() {
		return reportTask;
	}

	public void setReportTask(List<ReportTask> reportTask) {
		this.reportTask = reportTask;
	}
}
