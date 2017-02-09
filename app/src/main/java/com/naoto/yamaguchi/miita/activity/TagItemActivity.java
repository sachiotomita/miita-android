package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.entity.ItemTag;
import com.naoto.yamaguchi.miita.entity.Tag;
import com.naoto.yamaguchi.miita.fragment.TagItemFragment;
import com.naoto.yamaguchi.miita.util.fragment.FragmentRouter;

public class TagItemActivity extends AppCompatActivity
        implements TagItemFragment.OnItemClickListener {

    private static final String INTENT_ITEM_KEY = "item";
    private static final String INTENT_TAG_KEY = "tag";
    private static final String INTENT_ITEM_TAG_KEY = "item_tag";

    private Toolbar toolbar;
    private ActionBar actionBar;
    private CollapsingToolbarLayout toolbarLayout;

    private Tag tag;
    private ItemTag itemTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_item);

        this.parseIntent();
        this.initView();
        this.setFragment();
    }

    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        this.actionBar = getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.setHomeButtonEnabled(true);

        this.toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        // TODO: refactor
        String title = null;

        if (this.tag != null) {
            title = this.tag.getId();
        }

        if (this.itemTag != null) {
            title = this.itemTag.getName();
        }

        this.toolbarLayout.setTitle(title);
    }

    private void parseIntent() {
        final Intent intent = this.getIntent();

        if (intent.getParcelableExtra(INTENT_TAG_KEY) != null) {
            this.tag = intent.getParcelableExtra(INTENT_TAG_KEY);
        }

        if (intent.getParcelableExtra(INTENT_ITEM_TAG_KEY) != null) {
            this.itemTag = intent.getParcelableExtra(INTENT_ITEM_TAG_KEY);
        }
    }

    private void setFragment() {
        // TODO: refactor
        String tagId = null;

        if (this.tag != null) {
            tagId = this.tag.getId();
        }

        if (this.itemTag != null) {
            tagId = this.itemTag.getName();
        }

        FragmentRouter.newInstance()
                .begin(this.getSupportFragmentManager(), TagItemFragment.newInstance(tagId))
                .replace(R.id.tag_item_container_view)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Item item, Pair<View, String>... sharedElements) {
        final Intent intent = new Intent(TagItemActivity.this, ItemActivity.class);
        final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(TagItemActivity.this, sharedElements);

        intent.putExtra(INTENT_ITEM_KEY, item);
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void onTagClick(ItemTag itemTag) {
        final Intent intent = new Intent(TagItemActivity.this, TagItemActivity.class);
        intent.putExtra(INTENT_ITEM_TAG_KEY, itemTag);
        startActivity(intent);
    }
}
