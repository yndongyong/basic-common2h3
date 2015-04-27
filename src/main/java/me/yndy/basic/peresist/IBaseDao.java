package me.yndy.basic.peresist;


import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import me.yndy.basic.model.Pager;


/**
 * 基于hibernate公共的数据库处理接口
 * @author Administrator
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * 添加对象
	 * @param t 要添加的对象
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	/**
	 * 根据对象进行删除
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 根据id进行删除
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 根据条件列表所有对象，不分页
	 * @param hql 查询条件
	 * @param args 条件参数
	 * @return 返回查询出来的所有数据
	 */
	public List<T> list(String hql,Object[] args);
	/**
	 * 根据条件列表所有对象，不分页
	 * @param hql 查询条件
	 * @return 返回查询出来的所有数据
	 */
	public List<T> list(String hql);
	/**
	 * 根据条件列表所有对象，不分页
	 * @param hql 查询条件
	 * @param arg 一个条件参数
	 * @return 返回查询出来的所有数据
	 */
	public List<T> list(String hql,Object arg);
	
	
	/**
	 * 根据条件列表对象，进行分页，分页参数通过SystemContext传递
	 * @param hql 查询字符串
	 * @param args 条件参数
	 * @return
	 */
	public Pager<T> find(String hql,Object[] args);
	
	/**
	 * 根据条件列表对象，进行分页，分页参数通过SystemContext传递
	 * 支持带别名的查询
	 * @param hql 查询字符串
	 * @param args 条件参数
	 * @param alias 别名信息
	 * @return
	 */
	public Pager<T> findByAlias(String hql,Object[] args,Map<String,Object> alias);
	
	public Pager<T> findByAlias(String hql,Object obj,Map<String,Object> alias);
	
	public Pager<T> findByAlias(String hql,Map<String,Object> alias);
	
	public List<T> listByAlias(String hql,Object[] args,Map<String,Object> alias);
	
	public List<T> listByAlias(String hql,Object obj,Map<String,Object> alias);
	
	public List<T> listByAlias(String hql,Map<String,Object> alias);
	
	/**
	 * 根据条件列表对象，进行分页，分页参数通过SystemContext传递
	 * @param hql 查询字符串
	 * @param arg 一个条件参数
	 * @return
	 */
	public Pager<T> find(String hql,Object arg);
	/**
	 * 根据条件列表对象，进行分页，分页参数通过SystemContext传递
	 * @param hql 查询字符串
	 * @return
	 */
	public Pager<T> find(String hql);
	/**
	 * 根据HQL加载一个对象
	 * @param hql 要加载对象的HQL
	 * @param args 加载的条件参数
	 * @return 加载的对象
	 */
	public T loadByHql(String hql,Object[] args);
	/**
	 * 根据HQL加载一个对象
	 * @param hql
	 * @param arg
	 * @return
	 */
	public T loadByHql(String hql,Object arg) ;
	/**
	 * 根据HQL加载一个对象
	 * @param hql
	 * @return
	 */
	public T loadByHql(String hql);
	/**
	 * 根据ID加载对象
	 * @param id 要加载对象的id
	 * @return 要加载对象的id
	 */
	public T load(int id);
	
	/**
	 * 根据HQL加载非泛型对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object loadObjByHQL(String hql,Object[] args);
	/**
	 * 加载非泛型对象
	 * @param hql
	 * @param arg
	 * @return
	 */
	public Object loadObjByHQL(String hql,Object arg);
	/**
	 * 加载非泛型对象
	 * @param hql
	 * @return
	 */
	public Object loadObjByHQL(String hql);
	/**
	 * 根据hql更新对象
	 * @param hql 要进行更新操作的hql
	 * @param args 更新参数
	 */
	public void updateByHQL(String hql,Object[]args);
	public void updateByHQL(String hql,Object arg);
	public void updateByHQL(String hql);
	/**
	 * hibernate 的limit
	 * @param hql
	 * @param args
	 * @param startId
	 * @param offset
	 * @return
	 */
	public List<T> listByHql(String hql, Object[] args, int startId, int offset);
	/**
	 * 获取从sessionFactory中获取session
	 * @return Session
	 */
	public Session getCurrentSession();
}
