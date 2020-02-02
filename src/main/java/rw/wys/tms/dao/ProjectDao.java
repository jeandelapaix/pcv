package rw.wys.tms.dao;

import org.springframework.data.repository.CrudRepository;

import rw.wys.tms.domain.Project;

public interface ProjectDao extends CrudRepository<Project, Long> {
	Project findByName(String name);
}
