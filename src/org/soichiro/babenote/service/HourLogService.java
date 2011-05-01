package org.soichiro.babenote.service;

import org.slim3.datastore.Datastore;
import org.soichiro.babenote.meta.HourLogMeta;
import org.soichiro.babenote.model.DayLog;
import org.soichiro.babenote.model.HourLog;

import com.google.appengine.api.datastore.KeyFactory;


public class HourLogService {
	
	/**
	 * @param strKeyDayLog
	 * @param hour
	 * @param isNurse
	 * @param isMilk
	 * @param isCrap
	 * @param isPiss
	 * @param isSleep
	 * @param memo
	 * @return
	 */
	public HourLog updateHourLog(String strKeyDayLog, Integer hour,
			Boolean isNurse, Boolean isMilk, Boolean isCrap, Boolean isPiss,
			Boolean isSleep, String memo) {
		DayLog dayLog = Datastore.get(DayLog.class, KeyFactory.stringToKey(strKeyDayLog));
    	
    	HourLogMeta hm = HourLogMeta.get();
    	HourLog hourLog = Datastore
    	.query(hm)
    	.filter(hm.dayLogRef.equal(dayLog.getKey()),
    			hm.hour.equal(hour))
    	.asSingle();
    	
    	if(hourLog == null){
    		hourLog = new HourLog();
    		hourLog.setHour(hour);
    		hourLog.getDayLogRef().setModel(dayLog);
    	}
    	
    	if(isNurse != null) hourLog.setIsNurse(isNurse);
    	if(isMilk != null) hourLog.setIsMilk(isMilk);
    	if(isCrap != null) hourLog.setIsCrap(isCrap);
    	if(isPiss != null) hourLog.setIsPiss(isPiss);
    	if(isSleep != null) hourLog.setIsSleep(isSleep);
    	if(memo != null) hourLog.setMemo(memo);
    	
    	Datastore.put(dayLog, hourLog);
		return hourLog;
	}

}
