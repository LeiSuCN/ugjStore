package com.uguanjia.o2o.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*******************************************
 * @CLASS:PropsConfigration
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class PropsConfigration implements Configration{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private String properties;
	
	private Map<String, String> props;

	@SuppressWarnings("unchecked")
	@Override
	public  synchronized void refresh() {
		
		Map<String, String> tempMap = new HashMap<String, String>();
		
		try(InputStream is = getClass().getResourceAsStream("/" + properties)){
			
			if( is == null ){
				throw new FileNotFoundException();
			}
			
			Properties properties = new Properties();
			properties.load(is);
			
			Enumeration<String> names = (Enumeration<String>) properties.propertyNames();
			
			while( names.hasMoreElements() ){
				String name = names.nextElement();
				tempMap.put(name, properties.getProperty(name));
			}
			
		} catch (IOException e) {
			logger.error(e);
		} 
		
		if( tempMap.size() > 0 )this.props = tempMap;
		
	}

	@Override
	public String getName() {
		return getProperties();
	}

	@Override
	public String getValue(String cfgName) {
		
		if( props == null ){
			this.refresh();
		}
		
		return props == null ? null : props.get(cfgName);
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
}

