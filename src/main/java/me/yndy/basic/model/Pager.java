package me.yndy.basic.model;


import java.util.List;

/**
 * 分页对象
 * @author Administrator
 *
 * @param <T>具体的分页说存储的元素对象
 */
public class Pager<T> {
	/**
	 * 分页的元素列表
	 */
	private List<T> datas;
	/**
	 * 分页的第几个数据，从xx开始
	 */
	private int offset;
	/**
	 * 分页的大小
	 */
	private int size;
	/**
	 * 当前页
	 */
	private int index;
	/**
	 * 总共多少页
	 */
	private int totalPage;
	/**
	 * 总记录数
	 */
	private int totalRecord;
	
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}
