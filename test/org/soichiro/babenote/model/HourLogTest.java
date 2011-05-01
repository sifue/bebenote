package org.soichiro.babenote.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;

import com.google.appengine.api.datastore.Key;

public class HourLogTest extends AppEngineTestCase {

    private HourLog model = new HourLog();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
        
        model.setHour(23);
        model.setIsNurse(false);
        model.setIsMilk(true);
        model.setIsCrap(false);
        model.setIsPiss(true);
        model.setIsSleep(false);
        model.setMemo("testMemo");
        
        Datastore.put(model);
        
        Key key = model.getKey();
        assertThat(key, is(notNullValue()));
        
        HourLog modelRemote = Datastore.get(HourLog.class, key);
        assertThat(modelRemote.getHour(), is(23));
        assertThat(modelRemote.getIsNurse(), is(false));
        assertThat(modelRemote.getIsMilk(), is(true));
        assertThat(modelRemote.getIsCrap(), is(false));
        assertThat(modelRemote.getIsPiss(), is(true));
        assertThat(modelRemote.getIsSleep(), is(false));
        assertThat(modelRemote.getMemo(), is("testMemo"));
    }
}
