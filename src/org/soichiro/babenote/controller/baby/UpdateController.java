package org.soichiro.babenote.controller.baby;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.soichiro.babenote.controller.JsonResponseWriter;
import org.soichiro.babenote.meta.BabyMeta;
import org.soichiro.babenote.model.Baby;
import org.soichiro.babenote.service.BabyService;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UpdateController extends Controller {

	private BabyService babyService = new BabyService();
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	
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
    	
    	String strKeyAccount = asString("keyAccount");
    	if(strKeyAccount == null) return null;
    	
    	Date birthday = SDF.parse(asString("birthday"));  
    	Baby defaultBaby =  babyService.updateDafaultBaby(strKeyAccount,
    			asString("name"),
    			asInteger("timezoneOffset"),
    			birthday);
    	
    	JsonResponseWriter.json(BabyMeta.get().modelToJson(defaultBaby),
    			request,
    			response);
    	
    	return null;
    }
    
}
