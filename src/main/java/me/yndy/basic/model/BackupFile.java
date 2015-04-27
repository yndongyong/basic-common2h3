package me.yndy.basic.model;

import java.util.Date;

public class BackupFile implements Comparable<BackupFile>{
	private String name;
	private Date createDate;
	private long size;
	private int kSize;
	
	
	public int getkSize() {
		return kSize;
	}
	public void setkSize(int kSize) {
		this.kSize = kSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public int compareTo(BackupFile o) {
		return this.createDate.compareTo(o.getCreateDate());
	}
	
	
}
