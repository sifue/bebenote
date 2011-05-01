package org.soichiro.babenote.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.soichiro.babenote.meta.DayLogMeta;
import org.soichiro.babenote.model.Account;
import org.soichiro.babenote.model.Baby;
import org.soichiro.babenote.model.DayLog;
import org.soichiro.babenote.model.HourLog;

import com.google.appengine.api.datastore.KeyFactory;


public class DayLogService {
	
	/**
	 * @param date
	 * @param account
	 * @return
	 */
	public Map<String, Object> getDayLog(Date date, Account account) {
		Map<String, Object> jsonDataDayLog = new HashMap<String, Object>();
    	List<Map<String, Object>> listHourLogJson = new ArrayList<Map<String,Object>>();
    	jsonDataDayLog.put("hourLogListRef", listHourLogJson);
    	
    	Baby defaultBaby = account.getDefaultBabyRef().getModel();
    	if(defaultBaby != null)
    	{
	    	DayLogMeta dm = DayLogMeta.get();
	    	DayLog dayLog = Datastore
	    	.query(dm)
	    	.filter(dm.babyRef.equal(defaultBaby.getKey()),
	    			dm.date.equal(date))
	    	.asSingle();
	    	
	    	if(dayLog == null){
	    		dayLog = new DayLog();
	    		dayLog.setDate(date);
	    		dayLog.getBabyRef().setModel(defaultBaby);
	    		Datastore.put(defaultBaby, dayLog);
	    	}
	    	String keyDayLog = KeyFactory.keyToString(dayLog.getKey());
	    	Long longDate = dayLog.getDate().getTime();
    	
	    	
	    	// create empty hour log
	    	for (int i = 0; i < 24; i++) {
	    		Map<String, Object> jsonDataHourLog = new HashMap<String, Object>();
	    		listHourLogJson.add(jsonDataHourLog);
	    		
	    		jsonDataHourLog.put("keyDayLog", keyDayLog);
	    		jsonDataHourLog.put("date", longDate);
	    		
	    		jsonDataHourLog.put("hour", i);
	    		jsonDataHourLog.put("isNurse", false);
	    		jsonDataHourLog.put("isMilk", false);
	    		jsonDataHourLog.put("isCrap", false);
	    		jsonDataHourLog.put("isPiss", false);
	    		jsonDataHourLog.put("isSleep", false);
	    		jsonDataHourLog.put("memo", null);
			}
	    	
	    	// update from datastore
	    	for (HourLog hourLog : dayLog.getHourLogListRef().getModelList()) {
	    		Map<String, Object> jsonDataHourLog = listHourLogJson.get(hourLog.getHour());
	    		
	    		jsonDataHourLog.put("keyDayLog", keyDayLog);
	    		jsonDataHourLog.put("date", longDate);
	    		
	    		jsonDataHourLog.put("keyHourLog", KeyFactory.keyToString(hourLog.getKey()));
	    		jsonDataHourLog.put("hour", hourLog.getHour());
	    		jsonDataHourLog.put("isNurse", hourLog.getIsNurse());
	    		jsonDataHourLog.put("isMilk", hourLog.getIsMilk());
	    		jsonDataHourLog.put("isCrap", hourLog.getIsCrap());
	    		jsonDataHourLog.put("isPiss", hourLog.getIsPiss());
	    		jsonDataHourLog.put("isSleep", hourLog.getIsSleep());
	    		jsonDataHourLog.put("memo", hourLog.getMemo());
			}
    	}
		return jsonDataDayLog;
	}

}
