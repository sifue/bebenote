package org.soichiro.babenote.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.soichiro.babenote.model.Account;
import org.soichiro.babenote.service.DayLogService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DaylogController extends Controller {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	
	private DayLogService dayLogService = new DayLogService();

    @Override
    public Navigation run() throws Exception {
    	UserService userService = UserServiceFactory.getUserService();
    	if(!userService.isUserLoggedIn()){
        	Map<String, String> jsonResponse = new HashMap<String, String>();
        	jsonResponse.put("message", "Please login with google account with Google Account API");
        	JsonResponseWriter.json(jsonResponse, request, response);
    		return null;
    	}
    	
    	if(!isGet()) return null;
    	
    	String strKeyAccount = asString("keyAccount");
    	if(strKeyAccount == null) return null;
    	
    	String strDate = asString("date");
    	if(strDate == null) return null;
    	Date date = SDF.parse(strDate);
    	
    	Key keyAccount = KeyFactory.stringToKey(strKeyAccount);
    	Account account = Datastore.get(Account.class, keyAccount);

    	// create map for json
    	Map<String, Object> jsonDataDayLog = dayLogService.getDayLog(date, account);
    	
    	JsonResponseWriter.json(jsonDataDayLog, request, response);
    	
        return null;
    }

}
