package com.naoto.yamaguchi.miita.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Unit Test {@link AllItem}
 *
 * Created by naoto on 2016/12/30.
 */
public class AllItemTest {
    @Before
    public void setUp() throws Exception {
        // NOOP
    }

    @After
    public void tearDown() throws Exception {
        // NOOP
    }

    @Test
    public void baseField() throws Exception {
        final AllItem item = new AllItem();
        item.setId("naoto0822");
        item.setTitle("hoge-title");
        item.setBody("<p>body</p>");
        item.setUrlString("https://google.com");

        assertEquals("naoto0822", item.getId());
        assertEquals("hoge-title", item.getTitle());
        assertEquals("<p>body</p>", item.getBody());
        assertEquals("https://google.com", item.getUrlString());
    }

    @Test
    public void createdAt() throws Exception {
        final AllItem item = new AllItem();
        item.setCreatedAt(this.getDate("2016-12-30T17:07:09+09:00"));

        assertEquals("2016年12月30日", item.getCreatedAtString());
    }

    private Date getDate(String dateString) throws ParseException {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",
                Locale.JAPAN);
        return df.parse(dateString);
    }
}