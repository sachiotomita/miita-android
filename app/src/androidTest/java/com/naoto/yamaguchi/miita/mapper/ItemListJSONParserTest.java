package com.naoto.yamaguchi.miita.mapper;

import android.support.test.runner.AndroidJUnit4;

import com.naoto.yamaguchi.miita.entity.api.AllItem;
import com.naoto.yamaguchi.miita.entity.ui.ItemTag;
import com.naoto.yamaguchi.miita.helper.JSONHelper;
import com.naoto.yamaguchi.miita.parser.ItemListJSONParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test {@link ItemListJSONParser}
 *
 * Created by naoto on 2016/12/31.
 */
@RunWith(AndroidJUnit4.class)
public class ItemListJSONParserTest {
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
        final List<AllItem> items = ItemListJSONParser.map(jsonString, AllItem.class);
        assertNotNull(items);
        assertEquals(1, items.size());

        final AllItem item = items.get(0);
        assertEquals(item.getId(), "19cc04de6c5e0113868a");
        assertEquals(item.getTitle(), "タイトル");
        assertEquals(item.getBody(), "<p>body</p>");
        assertEquals(item.getUrlString(), "http://qiita.com/naoto0822/hoge");
        assertEquals(item.getCreatedAtString(), "2016年12月30日");

        final List<ItemTag> tags = item.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.size());
        final ItemTag tag1 = tags.get(0);
        assertNotNull(tag1);
        assertEquals("swift", tag1.getName());
        final ItemTag tag2 = tags.get(1);
        assertNotNull(tag2);
        assertEquals("android", tag2.getName());
    }
}