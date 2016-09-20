package com.trademarket.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;

public class ModelEntityMapper {
	
	private static final Log logger = LogFactory.getLog(ModelEntityMapper.class);
	
	public static Object converModelToEntity(Object modelObject,Class entityName){
		try{
			
			if(modelObject==null)return null;
			
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(modelObject, entityName);
			
		}
		catch(Exception e){
			logger.error("Error while Conver ObjectMapper for Class "+entityName.getName(), e);
			return null;
		}
	
	}
	
	public static Object converEntityToModel(Object entityObject,Class modelName){
		try{
			
			if(entityObject==null)return null;
			
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(entityObject, modelName);
			
		}
		catch(Exception e){
			logger.error("Error while Conver ObjectMapper for Class "+modelName.getName(), e);
			return null;
		}
	
	}

}
