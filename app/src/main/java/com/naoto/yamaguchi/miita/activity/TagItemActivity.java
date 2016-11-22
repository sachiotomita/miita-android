package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.entity.Tag;
import com.naoto.yamaguchi.miita.fragment.TagItemFragment;
import com.naoto.yamaguchi.miita.util.fragment.FragmentRouter;
import com.naoto.yamaguchi.miita.util.intent.IntentHandler;

public class TagItemActivity extends AppCompatActivity
        implements TagItemFragment.OnItemClickListener {

  private static final String INTENT_ITEM_KEY = "item";

  private Toolbar toolbar;
  private ActionBar actionBar;
  private CollapsingToolbarLayout toolbarLayout;

  private Tag tag;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tag_item);

    this.parseIntent();
    this.initView();
    this.setFragment();
  }

  private void initView() {
    this.toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(this.toolbar);

    this.actionBar = getSupportActionBar();
    this.actionBar.setDisplayHomeAsUpEnabled(true);
    this.actionBar.setHomeButtonEnabled(true);

    this.toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
    this.toolbarLayout.setTitle(this.tag.getId());
  }

  private void parseIntent() {
    this.tag = IntentHandler.getTag(this.getIntent());
  }

  private void setFragment() {
    FragmentRouter.newInstance()
            .begin(this.getSupportFragmentManager(), TagItemFragment.newInstance(this.tag.getId()))
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
  public void onItemClick(Item item) {
    final Intent intent = new Intent(TagItemActivity.this, ItemActivity.class);
    intent.putExtra(INTENT_ITEM_KEY, item);
    startActivity(intent);
  }
}
