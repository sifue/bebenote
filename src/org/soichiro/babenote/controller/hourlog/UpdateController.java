package org.soichiro.babenote.controller.hourlog;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.soichiro.babenote.controller.JsonResponseWriter;
import org.soichiro.babenote.meta.HourLogMeta;
import org.soichiro.babenote.model.HourLog;
import org.soichiro.babenote.service.HourLogService;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UpdateController extends Controller {
	
	private HourLogService hourLogService = new HourLogService();
	
    @Override
    public Navigation run() throws Exception {
    	UserService userService = UserServiceFactory.getUserService();
    	if(!userService.isUserLoggedIn()){
        	Map<String, String> jsonResponse = new HashMap<String, String>();
        	jsonResponse.put("message", "Please login with google account with Google Account API");
        	JsonResponseWriter.json(jsonResponse, request, response);
    		return null;
    	}
    	
    	if(!isPost()) return null;
    	
    	String strKeyDayLog = asString("keyDayLog");
    	if(strKeyDayLog == null) return null;
    	
    	Integer hour = asInteger("hour");
    	if(hour == null) return null;
    	
    	Boolean isNurse = asBoolean("isNurse");
    	Boolean isMilk = asBoolean("isMilk");
    	Boolean isCrap = asBoolean("isCrap");
    	Boolean isPiss = asBoolean("isPiss");
    	Boolean isSleep = asBoolean("isSleep");
    	String memo = asString("memo");
    	
    	HourLog hourLog = hourLogService.updateHourLog(strKeyDayLog, hour, isNurse, isMilk,
				isCrap, isPiss, isSleep, memo);
    	
    	JsonResponseWriter.json(HourLogMeta.get().modelToJson(hourLog),
    			request,
    			response);
    	
        return null;
    }

}
