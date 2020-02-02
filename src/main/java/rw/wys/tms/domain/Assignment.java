package rw.wys.tms.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "Assignment", 
uniqueConstraints = { @UniqueConstraint(columnNames = 
                                        { "user_id", "task_id", "active" }) })
public class Assignment implements Serializable {
	
	private static final long serialVersionUID = -5840618284564424160L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long assignmentId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@JoinColumn(name = "active")
	private boolean active;

	public Assignment() {
		super();
	}
	
	public Assignment(Task task, User user, boolean date) {
		super();
		this.user = user;
		this.task = task;
		this.active = date;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean getDate() {
		return active;
	}

	public void setDate(boolean date) {
		this.active = date;
	}
}
