package me.yndy.basic.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.yndy.basic.model.BackupFile;


public class MySQLUtil {
	private static MySQLUtil util = new MySQLUtil();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
	
	private String user;
	private String password;
	private String dbname;
	private String filename;
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	private MySQLUtil(){}
	
	public static MySQLUtil getInstance() {
		return util;
	}
	
	public void setCfg(String username,String password,String dbname,String filename,String path) {
		this.user = username;
		this.password = password;
		this.dbname = dbname;
		this.filename = filename;
		this.path = path;
	}
	
	public void setCfg(String username,String password,String dbname,String path) {
		this.user = username;
		this.password = password;
		this.dbname = dbname;
		this.path = path;
	}
/**
 * 
 * @param hasAddDate
 * @return
 */
	public String backup(boolean hasAddDate) {//备份  
	    try {  
	        Runtime rt = Runtime.getRuntime();  
	  
	        String mysql = "mysqldump -u"+user+" -p"+password+" "+dbname;  
	  
	        // 调用 mysql 的 cmd:  
	        Process child = rt  
	        .exec("cmd /c "+mysql);// 设置导出编码为utf8。这里必须是utf8  
	  
	        // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行  
	        InputStream in = child.getInputStream();// 控制台的输出信息作为输入流  
	  
	        InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码  
	  
	        String inStr;  
	        StringBuffer sb = new StringBuffer("");  
	        String outStr;  
	        // 组合控制台输出信息字符串  
	        BufferedReader br = new BufferedReader(xx);  
	        while ((inStr = br.readLine()) != null) {  
	            sb.append(inStr + "\r\n");  
	        }  
	        outStr = sb.toString();  
	        
	        if(hasAddDate) {
	        	filename = filename+"_"+sdf.format(new Date())+".sql";
	        } else {
	        	filename = filename+".sql";
	        }
	        System.out.println(filename);
	        // 要用来做导入用的sql目标文件：  
	        FileOutputStream fout = new FileOutputStream(path+"/"+filename);  
	        OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");  
	        writer.write(outStr);  
	        // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免  
	        writer.flush();  
	  
	        // 别忘记关闭输入输出流  
	        in.close();  
	        xx.close();  
	        br.close();  
	        writer.close();  
	        fout.close();  
	        System.out.println("/* Output OK! */");
	        return filename;
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return null;  
	}
	
	public String[] listAllBackupFile() {
		File f = new File(path);
		String[] files = f.list(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				if(name.endsWith(".sql"))
					return true;
				return false;
			}
		});
		return files;
	}
	
	public List<BackupFile> listBackupFile() {
		List<BackupFile> list = new ArrayList<BackupFile>();
		File [] fs = listAllBackupFileObj();
		BackupFile bf = null;
		for(File f:fs) {
			bf = new BackupFile();
			bf.setName(f.getName());
			bf.setSize(f.length());
			bf.setkSize(Math.round((f.length()/1024)));
			bf.setCreateDate(new Date(f.lastModified()));
			list.add(bf);
		}
		return list;
	}
	
	public File[] listAllBackupFileObj() {
		File f = new File(path);
		File[] files = f.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				if(name.endsWith(".sql"))
					return true;
				return false;
			}
		});
		return files;
	}
	  
	public boolean load() {//还原  
	    try {  
	        String fPath = path+"/"+filename;  
	        Runtime rt = Runtime.getRuntime();  
	  
	        // 调用 mysql 的 cmd:  
	        Process child = rt.exec("mysql -u"+user+" -p"+password+" "+dbname);  
	        OutputStream out = child.getOutputStream();//控制台的输入信息作为输出流  
	        String inStr;  
	        StringBuffer sb = new StringBuffer("");  
	        String outStr;  
	        BufferedReader br = new BufferedReader(new InputStreamReader(  
	                new FileInputStream(fPath), "utf8"));  
	        while ((inStr = br.readLine()) != null) {  
	            sb.append(inStr + "\r\n");  
	        }  
	        outStr = sb.toString();  
	        OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");  
	        writer.write(outStr);  
	        // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免  
	        writer.flush();  
	        // 别忘记关闭输入输出流  
	        out.close();  
	        br.close();  
	        writer.close();  
	          
	  
	        System.out.println("/* Load OK! */");  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return false;
	    }  
	    return true;  
	} 
}
