package com.payegis.caesar.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * 配置log4j 
 * @Return Logger 对象
 */
public class Log4jFactory {

	/**
	 * @deprecated
	 * @description 根据类名和默认的路径来配置日志对象
	 * @param className
	 * @return 
	 */
	public static Logger getLogger(String className) {
		Logger loger = Logger.getLogger(className);
		return loger;
	}
	
	/**
	 * @deprecated
	 * WARNNING: 线程非安全
	 * @param moduleName
	 * @return
	 */
	public static Log getModuleLogger(String moduleName)
	{
		Log logger = LogFactory.getLog(moduleName);
		
		return logger;
	}
	
	public static Log getLog(String moduleName, Class<?> clazz)
	{
		Log logger = LogFactory.getLog(moduleName);

		Config cfg = Config.getInstance();
		String devMode = cfg.getString("dev.mode");
		if(devMode!=null && devMode.equals("false"))//不是devmode
		{
			logger = LogFactory.getLog(clazz);
		}
		
		return logger;
	}

}
