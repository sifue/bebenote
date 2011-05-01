package org.soichiro.babenote.controller;

import org.slim3.memcache.Memcache;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class IndexControllerTest extends ControllerTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Memcache.put("org.soichiro.babenote.isTest", Boolean.TRUE);
	}
	
    @Test
    public void run() throws Exception {
        tester.start("/");
        IndexController controller = tester.getController();
        
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/index.jsp"));
        
        UserService userService = UserServiceFactory.getUserService();
        assertThat((String)tester.requestScope("logoutURL"),  is(userService.createLogoutURL("/")));
    }
}
