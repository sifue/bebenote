package org.soichiro.babenote.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DayLogServiceTest extends AppEngineTestCase {

    private DayLogService service = new DayLogService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
