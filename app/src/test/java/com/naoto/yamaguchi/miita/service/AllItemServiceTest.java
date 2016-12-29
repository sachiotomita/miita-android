package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.api.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Test {@link AllItemService}
 *
 * Created by naoto on 2016/12/29.
 */
public class AllItemServiceTest {
    private AllItemService service;

    @Before
    public void setUp() throws Exception {
        this.service = new AllItemService();
    }

    @After
    public void tearDown() throws Exception {
        // NOOP
    }

    @Test
    public void getMethod() throws Exception {
        assertEquals(Method.GET, this.service.getMethod());
    }

    @Test
    public void getPath() throws Exception {
        assertEquals("/items", this.service.getPath());
    }

    @Test
    public void processResponse() throws Exception {
        // TODO: create json string.
    }
}