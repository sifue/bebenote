package org.soichiro.babenote.model;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DayLogTest extends AppEngineTestCase {

    private DayLog model = new DayLog();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
        
        Date date1 = new Date(1L);
        model.setDate(date1);
        
        HourLog hourLog1 = new HourLog();
        hourLog1.setHour(3);
        hourLog1.getDayLogRef().setModel(model);
        
        HourLog hourLog2 = new HourLog();
        hourLog2.setHour(6);
        hourLog2.getDayLogRef().setModel(model);
        
        HourLog hourLog3 = new HourLog();
        hourLog3.setHour(9);
        hourLog3.getDayLogRef().setModel(model);
        
        Datastore.put(model, hourLog2, hourLog1, hourLog3);
        
        Key key = model.getKey();
        assertThat(key, is(notNullValue()));
        
        DayLog modelRemote = Datastore.get(DayLog.class, key);
        assertThat(modelRemote.getDate(), is(date1));
        
        List<HourLog> modelList = modelRemote.getHourLogListRef().getModelList();
        assertThat(modelList.size(), is(3));
        assertThat(modelList.get(0).getHour(), is(3));
        assertThat(modelList.get(1).getHour(), is(6));
        assertThat(modelList.get(2).getHour(), is(9));
    }
}
