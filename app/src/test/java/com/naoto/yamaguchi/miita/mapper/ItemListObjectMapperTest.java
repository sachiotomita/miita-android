package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.helper.JSONHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test {@link ItemListObjectMapper}
 *
 * Created by naoto on 2016/12/30.
 */
public class ItemListObjectMapperTest {
    @Before
    public void setUp() throws Exception {
        // NOOP
    }

    @After
    public void tearDown() throws Exception {
        // NOOP
    }

    @Test
    public void map() throws Exception {
        final String jsonString = JSONHelper.getInstance()
                .getJSONString("all_item_response.json");
        final List<AllItem> items = ItemListObjectMapper.map(jsonString, AllItem.class);

        assertEquals(1, items.size());
    }
}