package me.yndy.basic.peresist;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import me.yndy.basic.model.Pager;
import me.yndy.basic.model.SystemContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository("baseDao")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	/**
	 * 获取泛型的class
	 */
	private Class<T> clz;

	@SuppressWarnings("unchecked")
	private Class<T> getClz() {
		if (clz == null)
			clz = (Class<T>) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		return clz;
	}

	/**
	 * 注入SessionFactory
	 * 
	 * @param factory
	 */
	@Inject
	public void setSuperSessionFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#add(java.lang.Object)
	 */
	public T add(T t) {
		this.getHibernateTemplate().save(t);
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#update(java.lang.Object)
	 */
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#delete(java.lang.Object)
	 */
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#delete(int)
	 */
	public void delete(int id) {
		delete(load(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#list(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(String hql, Object[] args) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql = hql + " order by " + sort;
			if (order != null && !"".equals(order.trim())) {
				hql = hql + " " + order;
			} else {
				hql = hql + " asc";
			}
		}
		Query q = this.getSession().createQuery(hql);
		if (args != null) {
			int index = 0;
			for (Object arg : args) {
				q.setParameter(index++, arg);
			}
		}

		return q.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#list(java.lang.String)
	 */
	public List<T> list(String hql) {
		return list(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#list(java.lang.String,
	 * java.lang.Object)
	 */
	public List<T> list(String hql, Object arg) {
		return list(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#find(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Pager<T> find(String hql, Object[] args) {
		Pager<T> pages = new Pager<T>();
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String countHql = getCountHql(hql);
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (order != null && !"".equals(order.trim())) {
				hql += " " + order;
			} else {
				hql += " asc";
			}
		}
		Query query = setQuery(hql, args);
		Query countQuery = setQuery(countHql, args);
		List<T> datas = query.setFirstResult(pageOffset)
				.setMaxResults(pageSize).list();
		pages.setDatas(datas);
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		Long totalRecord = (Long) countQuery.uniqueResult();
		pages.setTotalRecord(totalRecord.intValue());
		return pages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#find(java.lang.String,
	 * java.lang.Object)
	 */
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#find(java.lang.String)
	 */
	public Pager<T> find(String hql) {
		return find(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadByHql(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public T loadByHql(String hql, Object[] args) {
		T t = null;
		Query q = setQuery(hql, args);
		t = (T) q.uniqueResult();
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadByHql(java.lang.String,
	 * java.lang.Object)
	 */
	public T loadByHql(String hql, Object arg) {
		return loadByHql(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadByHql(java.lang.String)
	 */
	public T loadByHql(String hql) {
		return loadByHql(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#load(int)
	 */
	public T load(int id) {
		return this.getHibernateTemplate().load(getClz(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadObjByHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public Object loadObjByHQL(String hql, Object[] args) {
		Object t = null;
		Query q = setQuery(hql, args);
		t = (Object) q.uniqueResult();
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadObjByHQL(java.lang.String,
	 * java.lang.Object)
	 */
	public Object loadObjByHQL(String hql, Object arg) {
		return loadObjByHQL(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#loadObjByHQL(java.lang.String)
	 */
	public Object loadObjByHQL(String hql) {
		return loadObjByHQL(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#updateByHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public void updateByHQL(String hql, Object[] args) {
		Query q = setQuery(hql, args);
		q.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#updateByHQL(java.lang.String,
	 * java.lang.Object)
	 */
	public void updateByHQL(String hql, Object arg) {
		this.updateByHQL(hql, new Object[] { arg });

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.konghao.basic.peresist.IBaseDao#updateByHQL(java.lang.String)
	 */
	public void updateByHQL(String hql) {
		this.updateByHQL(hql, null);
	}

	private String getCountHql(String hql) {
		String str = hql.substring(hql.indexOf("from "));
		str = "select count(*) " + str;
		str = str.replaceAll("fetch", "");
		return str;
	}

	private Query setQuery(String hql, Object[] args) {
		Query q = this.getSession().createQuery(hql);
		if (args != null) {
			int index = 0;
			for (Object arg : args) {
				q.setParameter(index++, arg);
			}
		}
		return q;
	}

	@SuppressWarnings("unchecked")
	public Pager<T> findByAlias(String hql, Object[] args,
			Map<String, Object> alias) {
		Pager<T> pages = new Pager<T>();
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String countHql = getCountHql(hql);
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (order != null && !"".equals(order.trim())) {
				hql += " " + order;
			} else {
				hql += " asc";
			}
		}
		Query query = setQuery(hql, args);
		setAliasQuery(query, alias);
		Query countQuery = setQuery(countHql, args);
		setAliasQuery(countQuery, alias);
		List<T> datas = query.setFirstResult(pageOffset)
				.setMaxResults(pageSize).list();
		pages.setDatas(datas);
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		Long totalRecord = (Long) countQuery.uniqueResult();
		pages.setTotalRecord(totalRecord.intValue());
		return pages;
	}

	@SuppressWarnings("rawtypes")
	private void setAliasQuery(Query query, Map<String, Object> alias) {
		Set<String> keys = alias.keySet();
		for (String key : keys) {
			Object obj = alias.get(key);
			if (obj instanceof Collection) {
				query.setParameterList(key, (Collection) obj);
			} else {
				query.setParameter(key, obj);
			}
		}
	}

	public Pager<T> findByAlias(String hql, Object arg,
			Map<String, Object> alias) {
		return findByAlias(hql, new Object[] { arg }, alias);
	}

	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return findByAlias(hql, null, alias);
	}

	@SuppressWarnings("unchecked")
	public List<T> listByAlias(String hql, Object[] args,
			Map<String, Object> alias) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql = hql + " order by " + sort;
			if (order != null && !"".equals(order.trim())) {
				hql = hql + " " + order;
			} else {
				hql = hql + " asc";
			}
		}
		Query q = this.getSession().createQuery(hql);
		if (args != null) {
			int index = 0;
			for (Object arg : args) {
				q.setParameter(index++, arg);
			}
		}
		setAliasQuery(q, alias);
		return q.list();
	}

	public List<T> listByAlias(String hql, Object obj, Map<String, Object> alias) {
		return this.listByAlias(hql, new Object[] { obj }, alias);
	}

	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.listByAlias(hql, null, alias);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByHql(String hql, Object[] args, int startId, int offset) {
		Query q = this.getSession().createQuery(hql);
		if (args != null) {
			int index = 0;
			for (Object arg : args) {
				q.setParameter(index++, arg);
			}
		}
		return q.setFirstResult(startId).setMaxResults(offset).list();
	}

	@Override
	public Session getCurrentSession() {
		return this.getHibernateTemplate().getSessionFactory().openSession();
	}

}
