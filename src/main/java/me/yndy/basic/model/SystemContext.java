package me.yndy.basic.model;

/**
 * 通过Threadlocal完成界面层数据的传递
 * @author Administrator
 *
 */
public class SystemContext {
	/**
	 * 分页的第几条数据
	 */
	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();
	/**
	 * 分页的大小
	 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	/**
	 * 列表数据的排序字段
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	/**
	 * 列表数据的排序的方式 asc|desc
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	
	private static ThreadLocal<String> realPath = new ThreadLocal<String>();
	
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	
	public static void setPageOffset(int offset) {
		pageOffset.set(offset);
	}
	
	public static void removePageOffset() {
		pageOffset.remove();
	}
	
	public static Integer getPageSize() {
		return pageSize.get();
	}
	
	public static void setPageSize(int size) {
		pageSize.set(size);
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static String getSort() {
		return sort.get();
	}
	
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static String getOrder() {
		return order.get();
	}
	
	public static void setOrder(String _order) {
		order.set(_order);
	}
	
	public static void removeOrder() {
		order.remove();
	}
	public static String getRealPath() {
		return realPath.get();
	}
	
	public static void setRealPath(String path) {
		realPath.set(path);
	}
	
	public static void removeRealPath() {
		realPath.remove();
	}
}
