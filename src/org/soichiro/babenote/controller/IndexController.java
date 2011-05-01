package org.soichiro.babenote.controller;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;
import org.soichiro.babenote.meta.AccountMeta;
import org.soichiro.babenote.model.Account;
import org.soichiro.babenote.model.Baby;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
    	
    	// Check warm up request
    	Boolean isWarmUp = asBoolean("isWarmUp");
    	if(isWarmUp != null && isWarmUp.booleanValue()) return null;
    	
    	UserService userService = UserServiceFactory.getUserService();
    	
    	String thisURL = request.getRequestURI();
    	User currentUser = userService.getCurrentUser();
    	
    	Boolean isTest = Memcache.get("org.soichiro.babenote.isTest");
    	if(isTest == null) isTest = Boolean.FALSE;

    	if(currentUser != null || isTest.booleanValue()){
    		
    		AccountMeta a = AccountMeta.get();
    		Account account = Datastore.query(a).filter(a.owner.equal(currentUser)).asSingle();
    		
    		Date now = new Date();
    		if(account == null){
    			account  = new Account();
    			account.setOwner(currentUser);
    			account.setCreatedAt(now);
    		}
    		
    		// count up loadCount
			Long loadCount = account.getLoginCount();
			if(loadCount == null){
				account.setLoginCount(1L);
			}else{
				account.setLoginCount(loadCount.longValue() + 1L);
			}
			
			account.setLastLoginAt(now);
			Datastore.put(account);
			
			Baby defaultBaby = account.getDefaultBabyRef().getModel();
			boolean hasDefaultBaby = defaultBaby != null;

			String logoutURL = userService.createLogoutURL(thisURL);
			requestScope("logoutURL", logoutURL);
			requestScope("keyAccount", KeyFactory.keyToString(account.getKey()));
			requestScope("loginCount", account.getLoginCount().toString());
			requestScope("hasDefaultBaby", Boolean.toString(hasDefaultBaby));
			requestScope("birthday", hasDefaultBaby ? 
					Long.toString(defaultBaby.getBirthday().getTime()) :
						"0"
			);
			return forward("index.jsp");
		}else{
			return redirect(userService.createLoginURL(thisURL));
		}
    }
    
}
