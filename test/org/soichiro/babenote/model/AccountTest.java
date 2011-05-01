package org.soichiro.babenote.model;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AccountTest extends AppEngineTestCase {

    private Account model = new Account();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
        
        User testUser = new User("test", "test.com");
        model.setOwner(testUser);
        Date date1 = new Date(1L);
        model.setLastLoginAt(date1);
        Date date2 = new Date(2L);
        model.setCreatedAt(date2);
        
        Baby baby1 = new Baby();
        baby1.setName("testBabyName1");
        Date date3 = new Date(3L);
        baby1.setBirthday(date3);
        baby1.setTimezoneOffset(540);
        Date date4 = new Date(4L);
        baby1.setCreatedAt(date4);
        baby1.getAccountRef().setModel(model);
        
        model.getDefaultBabyRef().setModel(baby1);
        
        Baby baby2 = new Baby();
        baby2.setName("testBabyName2");
        Date date5 = new Date(5L);
        baby2.setBirthday(date5);
        baby2.setTimezoneOffset(-120);
        Date date6 = new Date(6L);
        baby2.setCreatedAt(date6);
        baby2.getAccountRef().setModel(model);
        
        Baby baby3 = new Baby();
        baby3.setName("testBabyName3");
        Date date7 = new Date(7L);
        baby3.setBirthday(date7);
        baby3.setTimezoneOffset(0);
        Date date8 = new Date(8L);
        baby3.setCreatedAt(date8);
        baby3.getAccountRef().setModel(model);

        Datastore.put(model, baby1, baby2, baby3);
        
        Key key = model.getKey();
        assertThat(key, is(notNullValue()));
        
        Account modelRemote = Datastore.get(Account.class, key);
        assertThat(modelRemote.getOwner(), is(testUser));
        assertThat(modelRemote.getLastLoginAt(), is(date1));
        assertThat(modelRemote.getCreatedAt(), is(date2));
        
        Baby dafaultBabyRemote = modelRemote.getDefaultBabyRef().getModel();
        assertThat(dafaultBabyRemote, is(baby1));
        assertThat(dafaultBabyRemote.getName(), is("testBabyName1"));
        assertThat(dafaultBabyRemote.getBirthday(), is(date3));
        assertThat(dafaultBabyRemote.getTimezoneOffset(), is(540));
        assertThat(dafaultBabyRemote.getCreatedAt(), is(date4));
        assertThat(dafaultBabyRemote.getAccountRef().getModel(), is(modelRemote));
        
        List<Baby> modelList = modelRemote.getBabyListRef().getModelList();
        assertThat(modelList.size(), is(3));
        assertThat(modelList.get(0).getName(), is("testBabyName3"));
        assertThat(modelList.get(1).getName(), is("testBabyName2"));
        assertThat(modelList.get(2).getName(), is("testBabyName1"));
    }
}
