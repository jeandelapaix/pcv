package rw.wys.tms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Audited
@Table(name = "Announcement")
public class Announcement implements Serializable {
	private static final long serialVersionUID = 8683520604916034653L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long assignmentId;

	@ManyToOne
	@JoinColumn(name = "creater_id")
	private User creater;
	
	@NotNull
	private String message;
	
	@NotNull
	private String subject;
	
	private Date date;
	
	@Value("${some.key:false}")
	private boolean priority;
	
	

	public Announcement(User creater, String message, String subject, boolean priority) {
		super();
		this.creater = creater;
		this.message = message;
		this.subject = subject;
		this.priority = priority;
		date = new Date();
	}

	public Announcement() {
		super();
		date = new Date();
	}



	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isPriority() {
		return priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@PrePersist
	public void onCreate() {
		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Announcement [assignmentId=" + assignmentId + ", creater=" + creater + ", message=" + message
				+ ", subject=" + subject + ", date=" + date + ", priority=" + priority + "]";
	}
	
	

}
