package com.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Entity
@Table(name = "task")
//@JsonInclude(Include.NON_EMPTY)
@XmlRootElement(name = "task")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_desc", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "task_priority")
	private int priority;
	
	@Column(name = "task_start_date", columnDefinition = "DATE")
	private Date startDate;
	
	@Column(name = "task_status")
	private String status;
	
	public Task() {
		
	}
	
	public Task(String tname, String desc, int task_prio, Date start, String status){
		this.taskName = tname;
		this.description = desc;
		this.priority = task_prio;
		this.startDate = start;
		this.status = status;
	}

	@XmlElement
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName() {
		return taskName;
	}
	
	public void setName(String name) {
		this.taskName = name;
	}
	
	@XmlElement
	public String getDesc() {
		return description;
	}
	
	public void setDesc(String description) {
		this.description = description;
	}
	
	@XmlElement
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@XmlElement
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	public Date getDate() {
		return startDate;
	}
	
	public void setDate(Date date) {
		this.startDate = date;
	}
	
	//@XmlElement
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}

class DateAdapter extends XmlAdapter<String, Date> {

    // the desired format
    private String pattern = "yyyy-MM-dd";

    public String marshal(Date date) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date unmarshal(String dateString) throws Exception {
        return (Date) new SimpleDateFormat(pattern).parse(dateString);
    }   
}