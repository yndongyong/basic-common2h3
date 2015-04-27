package me.yndy.basic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
	private static Map<String,Properties> maps = new HashMap<String, Properties>();
	
	/**
	 * 使用map来实现第二种单例模式
	 * 读取Src的根路径下的name
	 * @param name
	 * @return
	 */
	public static Properties createProperties(String name) {
		if(maps.get(name)!=null) {
			return maps.get(name);
		} else {
			Properties properties = new Properties();
			try {
				properties.load(PropertiesUtils.class
						.getClassLoader()
						.getResourceAsStream(name+".properties"));
			} catch (IOException e) {
				return null;
			}
			maps.put(name,properties);
			return properties;
		}
	}
	
	/**
	 * 使用map来实现第二种单例模式
	 * 读取某个路径下的name
	 * @param path 路径的最后一定要增加/,xxx/
	 * @param name
	 * @return
	 */
	public static Properties createProperties(String path,String name) {
		String realname = path+name;
		if(maps.get(realname)!=null) {
			return maps.get(realname);
		} else {
			Properties properties = new Properties();
			try {
				properties.load(PropertiesUtils.class
						.getClassLoader()
						.getResourceAsStream(realname+".properties"));
			} catch (IOException e) {
				return null;
			}
			maps.put(realname,properties);
			return properties;
		}
	}
	
}
