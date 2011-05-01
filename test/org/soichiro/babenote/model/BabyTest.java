package org.soichiro.babenote.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;

import com.google.appengine.api.datastore.Key;

public class BabyTest extends AppEngineTestCase {

    private Baby model = new Baby();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
        
        model.setName("testBabyName1");
        Date date1 = new Date(1L);
        model.setBirthday(date1);
        model.setTimezoneOffset(-240);
        Date date2 = new Date(2L);
        model.setCreatedAt(date2);
        
        DayLog dayLog1 = new DayLog();
        Date date3 = new Date(3L);
        dayLog1.setDate(date3);
        dayLog1.getBabyRef().setModel(model);
        
        DayLog dayLog2 = new DayLog();
        Date date4 = new Date(4L);
        dayLog2.setDate(date4);
        dayLog2.getBabyRef().setModel(model);
        
        DayLog dayLog3 = new DayLog();
        Date date5 = new Date(5L);
        dayLog3.setDate(date5);
        dayLog3.getBabyRef().setModel(model);
        
        Datastore.put(model, dayLog1, dayLog2, dayLog3);
        
        Key key = model.getKey();
        assertThat(key, is(notNullValue()));
        
        Baby modelRemote = Datastore.get(Baby.class, key);
        assertThat(modelRemote.getBirthday(), is(date1));
        assertThat(modelRemote.getTimezoneOffset(), is(-240));
        assertThat(modelRemote.getCreatedAt(), is(date2));
        
        List<DayLog> modelList = modelRemote.getDayLogListRef().getModelList();
        assertThat(modelList.size(), is(3));
        assertThat(modelList.get(0).getDate(), is(date5));
        assertThat(modelList.get(1).getDate(), is(date4));
        assertThat(modelList.get(2).getDate(), is(date3));
    }
}
