package rw.wys.tms.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import rw.wys.tms.domain.Project;

public interface ProjectService {
	public Project createProject(Project project);

	public List<Project> viewAllProject();
	@Secured("ROLE_ADMIN")
	public void updateProject(Project project) throws Exception;
	
	public Project findById(Long id);
	
	public Project findByName(String name);
	@Secured("ROLE_ADMIN")
	void removeProject(Long id) throws Exception;
	

}
