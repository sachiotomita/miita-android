package com.naoto.yamaguchi.miita.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit Test {@link UrlBuilder}
 *
 * Created by naoto on 2016/12/29.
 */
public class UrlBuilderTest {
    private UrlBuilder builder;

    @Before
    public void setUp() throws Exception {
        this.builder = new UrlBuilder();
    }

    @After
    public void tearDown() throws Exception {
        // NOOP
    }

    @Test
    public void onlyBuild() throws Exception {
        String urlString = this.builder.build();
        assertEquals("https://qiita.com/api/v2", urlString);
    }

    @Test
    public void buildAndPath() throws Exception {
        final String path = "/hoge/foo/";
        String urlString = this.builder.setPath(path).build();
        assertEquals("https://qiita.com/api/v2/hoge/foo/", urlString);
    }

    @Test
    public void buildAndPathAndQuery() throws Exception {
        final String path = "/foo/";
        final Map<String, String> params = new HashMap<String, String>() {
            {
                put("name", "jack");
                put("age", "10");
            }
        };
        String urlString = this.builder.setPath(path).setParams(params).build();
        assertEquals("https://qiita.com/api/v2/foo/?name=jack&age=10", urlString);
    }
}