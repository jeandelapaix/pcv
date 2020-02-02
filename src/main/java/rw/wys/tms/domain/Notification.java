package rw.wys.tms.domain;

import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name ="notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String desciption;
	private Timestamp duration;
	private Date startTime;
	
	
	public Notification(long id, String name, String desciption, Timestamp duration, Date startTime) {
		super();
		this.id = id;
		this.name = name;
		this.desciption = desciption;
		this.duration = duration;
		this.startTime = startTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public Timestamp getDuration() {
		return duration;
	}
	public void setDuration(Timestamp duration) {
		this.duration = duration;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", desciption=" + desciption + ", duration=" + duration + ", startTime=" + startTime +"]";
	}
	
	

}
