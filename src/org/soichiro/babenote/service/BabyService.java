package org.soichiro.babenote.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.soichiro.babenote.model.Account;
import org.soichiro.babenote.model.Baby;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class BabyService {
	
	/**
	 * @param strKeyAccount
	 * @param name
	 * @param timezoneOffcet
	 * @param birthday
	 * @return
	 */
	public Baby updateDafaultBaby(String strKeyAccount,
			String name,
			Integer timezoneOffcet,
			Date birthday) {
		Key keyAccount = KeyFactory.stringToKey(strKeyAccount);
    	Account account = Datastore.get(Account.class, keyAccount);
    	
    	Baby defaultBaby = account.getDefaultBabyRef().getModel();
    	if(defaultBaby == null){
    		defaultBaby = new Baby();
    		Date now = new Date();
    		defaultBaby.setCreatedAt(now);
    		defaultBaby.getAccountRef().setModel(account);
    	}
    	
    	defaultBaby.setName(name);
    	defaultBaby.setTimezoneOffset(timezoneOffcet);
    	defaultBaby.setBirthday(birthday);
    	
    	account.getDefaultBabyRef().setModel(defaultBaby);
    	
    	Datastore.put(account, defaultBaby);
    	
    	return defaultBaby;
	}
	
	public Map<String, String> getDefaultBaby(String strKeyAccount) {
		Key keyAccount = KeyFactory.stringToKey(strKeyAccount);
    	Account account = Datastore.get(Account.class, keyAccount);
    	
    	Baby defaultBaby = account.getDefaultBabyRef().getModel();
    	if(defaultBaby == null) return null;
    	
    	Map<String, String> jsonResponse = new HashMap<String, String>();
    	jsonResponse.put("keyBaby", KeyFactory.keyToString(defaultBaby.getKey()));
    	jsonResponse.put("name", defaultBaby.getName());
    	jsonResponse.put("birthday", Long.toString(defaultBaby.getBirthday().getTime()));
    	jsonResponse.put("timezoneOffset", defaultBaby.getTimezoneOffset().toString());
		return jsonResponse;
	}

}
