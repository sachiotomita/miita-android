package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.helper.JSONHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Test {@link UserObjectMapper}
 *
 * Created by naoto on 2017/01/22.
 */
public class UserObjectMapperTest {
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
                .getJSONString("user_response.json");
        final User user = UserObjectMapper.map(jsonString);

        assertNotNull(user);

        assertEquals("naoto0822", user.getId());
        assertEquals("user_name", user.getName());
        assertEquals("desc", user.getDescription());
        assertEquals("https://qiita-image-store.s3.amazonaws.com/profile-images/", user.getImageUrlString());
        assertEquals(16, user.getFolloweesCount());
        assertEquals(8, user.getFollowersCount());
    }
}