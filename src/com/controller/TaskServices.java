package com.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.*;

@Path("/TaskService")
public class TaskServices {
	
	public static int count = 0;
	TaskDatabaseDao taskDao = new TaskDatabaseDao();
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	
	@GET
	@Path("/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public String tasks_list() {
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX==============" + count++);
		System.out.println(gson.toJson(taskDao.listTask()));
		return gson.toJson(taskDao.listTask());
	}
	
	@POST
	@Path("/searchTask")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String searchTask(@FormParam("content") String content, @FormParam("type") String type){
		System.out.println("pass serach !!!" + content +"  " + type);
		List task_list = taskDao.SearchTask(content, type);
		
		return gson.toJson(task_list);
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateTasks(@FormParam("id") int id,
			@FormParam("taskName") String taskName,@FormParam("description") String description,
			@FormParam("priority") int priority,@FormParam("startDate") String startDate,
			@FormParam("status") String status,
			@Context HttpServletResponse servletResponse) throws ParseException{
		
		System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYY==============" + (count++) +"   "+ id);
		System.out.println(startDate+"-----------" + taskName);
		Task task = taskDao.getTask(id);
		task.setName(taskName);
		task.setDesc(description);
		task.setPriority(priority);
		task.setStatus(status);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date newdate =  new java.sql.Date(df.parse(startDate).getTime());
		System.out.println("Transfer!!");
		task.setDate(newdate);
		taskDao.updateTask(task);
		return null;
	}
	
	@POST
	@Path("/deleteTask")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String DeleteTasks(@FormParam("id") int id) {
		
		System.out.println("success pass task!!!!!");
		taskDao.deleteTask(id);
		
		return gson.toJson(taskDao.listTask());
	}
	
	@POST
	@Path("/addTask")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String AddTasks(@FormParam("taskName") String taskName,@FormParam("description") String description,
			@FormParam("priority") int priority,@FormParam("startDate") String startDate,
			@FormParam("status") String status,
			@Context HttpServletResponse servletResponse) throws ParseException{
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date newdate =  new java.sql.Date(df.parse(startDate).getTime());
		System.out.println("success pass task!!!!!");
		taskDao.addTask(taskName, description, priority, status, newdate);
//		return null;
		return gson.toJson(taskDao.listTask());
	}
}
