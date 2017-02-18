package com.naoto.yamaguchi.miita.mapper;

import android.support.test.runner.AndroidJUnit4;

import com.naoto.yamaguchi.miita.entity.ui.ItemTag;
import com.naoto.yamaguchi.miita.helper.JSONHelper;
import com.naoto.yamaguchi.miita.parser.ItemTagListJSONParser;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test {@link ItemTagListJSONParser}
 *
 * Created by naoto on 2016/12/31.
 */
@RunWith(AndroidJUnit4.class)
public class ItemTagListJSONParserTest {
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
                .getJSONString("item_tag_response.json");
        final JSONArray json = new JSONArray(jsonString);
        final List<ItemTag> itemTags = ItemTagListJSONParser.map(ItemTag.class, json);

        assertNotNull(itemTags);
        assertEquals(3, itemTags.size());

        final ItemTag itemTag1 = itemTags.get(0);
        assertNotNull(itemTag1);
        assertEquals("ruby", itemTag1.getName());

        final ItemTag itemTag2 = itemTags.get(1);
        assertNotNull(itemTag2);
        assertEquals("node", itemTag2.getName());

        final ItemTag itemTag3 = itemTags.get(2);
        assertNotNull(itemTag3);
        assertEquals("php", itemTag3.getName());
    }
}