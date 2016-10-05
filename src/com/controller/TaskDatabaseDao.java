package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import com.model.Task;

public class TaskDatabaseDao {
	public static SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public List<Task> listTask() {
		Session session = factory.openSession();
		Transaction tx = null;
		List tasks = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM Task";
			tasks = session.createQuery(hql).list();
			Iterator iterator = tasks.iterator();
//			while (iterator.hasNext()) {
//				Task task = (Task) iterator.next();
//				System.out.println("Task Name: " + task.getName());
//				System.out.println("Task Desc: " + task.getDesc());
//				System.out.println("Task status: " + task.getStatus());
//				System.out.println("Task Date: " + task.getDate());
//			}
			tx.commit();
		} catch (HibernateException e) {
			System.out.println("error here!!!");
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		saveTaskList(tasks);
		return tasks;
	}
	
	public List<Task> SearchTask(String content, String type) {
		Session session = factory.openSession();
		List tasks = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String HQL1 = "FROM Task as T where T.description like :desc";
			String HQL2 = "FROM Task as T where T.taskName like :desc";
			String HQL3 = "FROM Task as T where T.status like :desc";
			if (type.equals("description")){
				tasks = session.createQuery(HQL1).setString("desc", "%" + content + "%").list();
				System.out.println("success get search list!!!");
			}
			else if (type.equals("taskname")) {
				tasks = session.createQuery(HQL2).setString("desc", "%" + content + "%").list();
				System.out.println("success get search list!!!");
			}
			else {
				tasks = session.createQuery(HQL3).setString("desc", "%" + content + "%").list();
				System.out.println("success get search list!!!");
			}
//			Iterator iterator = tasks.iterator();
//			while (iterator.hasNext()) {
//				Task task = (Task) iterator.next();
//				System.out.println("Task Name: " + task.getDesc());
//			}
			tx.commit();
		} catch (HibernateException e) {
			System.out.println("error here!!!");
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tasks;
	}
	
	public Task getTask(int taskid) {
		List<Task> list = listTask();
		for (int i=0; i<list.size(); i++){
			if (list.get(i).getId() == taskid){
				return list.get(i);
			}
		}
		return null;
	}
	
	public void updateTask(Task task1) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			System.out.println("Now the task date is:"+ task1.getDate());
			Task task = (Task) session.get(Task.class, task1.getId());
			
			task.setName(task1.getName());
			task.setDesc(task1.getDesc());
			task.setStatus(task1.getStatus());
			task.setDate(task1.getDate());
			task.setPriority(task1.getPriority());
			
			session.update(task);
			System.out.println("Success Update!!!");
			tx.commit();
		} catch (HibernateException e) {
			System.out.println("error here!!!");
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void addTask(String taskname, String desc, int proity, String status, Date date){
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Task task = new Task(taskname,  desc,  proity,  date,  status);
			int taskID = (Integer) session.save(task);
			System.out.println("success save Task!!!!!");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void deleteTask(int taskid){
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Task task = (Task) session.get(Task.class, taskid);
			session.delete(task);
			System.out.println("success delete task!!!!!");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void saveTaskList(List<Task> tasklist){
	      try {
	         File file = new File("Tasks.dat");
	         FileOutputStream fos;

	         fos = new FileOutputStream(file);

	         ObjectOutputStream oos = new ObjectOutputStream(fos);		
	         oos.writeObject(tasklist);
	         oos.close();
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	
	public void close() {
		factory.close();
	}
}
