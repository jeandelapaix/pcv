package rw.wys.tms.util;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rw.wys.tms.domain.*;

@Component
public class ReportTask {

	
	private Task task;
	private Map<String, Date> status;
	public Map<User, Date> users;
	
	public ReportTask() {
		
	}
	
	public ReportTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Date> getStatus() {
		return status;
	}

	public void setStatus(Map<String, Date> status) {
		this.status = status;
	}

	public Map<User, Date> getUser_Assigned() {
		return users;
	}

	public void setUser_Assigned(Map<User, Date> users) {
		this.users = users;
	}
	
	
}
