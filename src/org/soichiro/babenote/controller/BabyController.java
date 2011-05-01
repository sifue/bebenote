package org.soichiro.babenote.controller;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.soichiro.babenote.service.BabyService;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class BabyController extends Controller {
	
	private BabyService babyService = new BabyService();
	
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
    	
    	Map<String, String> jsonResponse = 
    		babyService.getDefaultBaby(strKeyAccount);
    	if(jsonResponse == null) return null;
    	
    	JsonResponseWriter.json(jsonResponse, request, response);
        return null;
    }

}
