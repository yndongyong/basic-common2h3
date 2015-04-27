# basic-common2h3
实现一个基于hibernate3的基本的dao，实现CRUD；
一行代码完成CRUD，提供多种重载方法详见IBaseDao，但是项目需要基于SSH框架。
使用方法，以用户添加为例：
1、定义User类，并提供get和set方法。
2、定义IUserDao接口，使其继承IBaseDao并传入泛型。若提供的方法不满足需求，可以在此接口中定义自己的业务方法
，然后在IUserDao的实现类中通过getCurrentSession方法获取session操作对象。
示例：
    public interface IUserDao extends IBaseDao<User> {
	        
    }
3、定义UserDao并实现IUserDao接口，且要实现BaseDao类还需要传入泛型。BaseDao完成了具体的CRUD的方法。
示例：
    @Repository(value="userDao")
    public class UserDao extends BaseDao<User> implements IUserDao {
      
    }
4、使用
在spring 项目中得到userDao的bean;如：
sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
Session s = sessionFactory.openSession();
TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
userDao = (IUserDao)factory.getBean("userDao");
通过userDao完成添加：userDao.add(new User(1,"xxxx"));
其它方法详见源码！
  注：由于使用了spring框架，所以hibernate的session关闭是由spring的事务管理。
