package com.fjsh.bean;

public class student {
	private Integer id;
	private int age;
	private String name;
	private int pid;
	private int tid;
	
	public student() {
		
	}
	public student(Integer id, int age, String name, int pid, int tid) {
		
		this.id = id;
		this.age = age;
		this.name = name;
		this.pid = pid;
		this.tid = tid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}

}
