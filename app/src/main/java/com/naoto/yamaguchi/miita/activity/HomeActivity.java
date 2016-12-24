package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.application.Constants;
import com.naoto.yamaguchi.miita.converter.ItemConverter;
import com.naoto.yamaguchi.miita.converter.TagConverter;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.entity.Tag;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.fragment.AllItemFragment;
import com.naoto.yamaguchi.miita.fragment.FollowTagFragment;
import com.naoto.yamaguchi.miita.fragment.StockItemFragment;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.presenter.HomePresenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;
import com.naoto.yamaguchi.miita.view.alert.MiitaAlertDialogBuilder;
import com.naoto.yamaguchi.miita.view.alert.MiitaAlertDialogListener;
import com.naoto.yamaguchi.miita.view.alert.MiitaAlertDialogType;
import com.naoto.yamaguchi.miita.view.dialog.MiitaProgressDialog;
import com.naoto.yamaguchi.miita.view.dialog.MiitaProgressDialogType;
import com.naoto.yamaguchi.miita.view.navigationview.MiitaNavigationView;
import com.naoto.yamaguchi.miita.view.navigationview.NavigationMenuType;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AllItemFragment.OnItemClickListener,
        StockItemFragment.OnItemClickListener,
        FollowTagFragment.OnTagClickListener,
        FragmentManager.OnBackStackChangedListener,
        HomePresenter.View {

    private static final String INTENT_ITEM_KEY = "item";
    private static final String INTENT_TAG_KEY = "tag";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private MiitaNavigationView navigationView;
    private CoordinatorLayout coordinatorLayout;
    private MiitaProgressDialog progressDialog;
    private HomePresenter presenter;
    private CurrentUser currentUser = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.presenter = new HomePresenter(this);
        this.presenter.attachView(this);
        this.initView();
        this.replaceFragment(NavigationMenuType.ALL_ITEM);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        this.presenter.loadAccessToken(intent);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }

        FragmentManager manager = this.getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: if use menu, delete following comment out.
        // getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // TODO:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item)
                || this.drawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.drawerLayout.closeDrawer(GravityCompat.START);

        final NavigationMenuType type = NavigationMenuType.fromMenuItem(item);
        this.menuItemSelected(type);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        this.updateState();
    }

    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout,
                this.toolbar, R.string.drawer_open, R.string.drawer_close);
        this.drawerLayout.addDrawerListener(this.drawerToggle);
        this.drawerToggle.syncState();

        this.navigationView = (MiitaNavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.navigationView.setCheckedItem(NavigationMenuType.ALL_ITEM);

        this.coordinatorLayout =
                (CoordinatorLayout)findViewById(R.id.home_coordinator_layout);

        this.progressDialog = new MiitaProgressDialog(this,
                MiitaProgressDialogType.LOGGING_IN);
        this.progressDialog.build();
    }

    private void showLoginAlert() {
        new MiitaAlertDialogBuilder(this, MiitaAlertDialogType.LOGIN)
                .setPositiveListener(new MiitaAlertDialogListener() {
                    @Override
                    public void onClick() {
                        Uri uri = Uri.parse(Constants.AUTHORIZE_URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .setNegativeListener(new MiitaAlertDialogListener() {
                    @Override
                    public void onClick() {
                        updateState();
                    }
                })
                .build()
                .show();
    }

    private void updateState() {
        final FragmentManager fm = this.getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.home_container_view);
        if (fragment == null) {
            finish();
            return;
        }

        final NavigationMenuType type = NavigationMenuType.fromFragment(fragment);

        if (type.isNeedAuthorize() && !this.currentUser.isAuthorize()) {
            this.onBackPressed();
        } else {
            this.toolbar.setTitle(type.getTitleRes());
            this.navigationView.setCheckedItem(type);
        }
    }

    private void replaceFragment(NavigationMenuType type) {
        this.toolbar.setTitle(type.getTitleRes());
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container_view, type.getFragment(),
                        type.getFragmentName())
                .addToBackStack(null)
                .commit();
    }

    private void menuItemSelected(NavigationMenuType type) {
        if (type == NavigationMenuType.SETTING) {
            final Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
            return;
        }

        if (type.isNeedAuthorize() && !this.currentUser.isAuthorize()) {
            this.showLoginAlert();
        } else {
            this.replaceFragment(type);
        }
    }

    @Override
    public void onItemClick(AllItem item) {
        final Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
        final Item _item = ItemConverter.convert(item);
        intent.putExtra(INTENT_ITEM_KEY, _item);
        startActivity(intent);
    }

    @Override
    public void onItemClick(StockItem item) {
        final Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
        final Item _item = ItemConverter.convert(item);
        intent.putExtra(INTENT_ITEM_KEY, _item);
        startActivity(intent);
    }

    @Override
    public void onTagClick(FollowTag tag) {
        final Intent intent = new Intent(HomeActivity.this, TagItemActivity.class);
        final Tag _tag = TagConverter.convert(tag);
        intent.putExtra(INTENT_TAG_KEY, _tag);
        startActivity(intent);
    }

    @Override
    public void showLoggingProgress() {
        this.progressDialog.show();
    }

    @Override
    public void hideLoggingProgress() {
        this.progressDialog.dismiss();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(this.coordinatorLayout, "ログインしました", Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showErrorAlert(MiitaException e) {
        // TODO: show error alert.
    }

    @Override
    public void loginSuccess() {
        this.navigationView.updateHeader();
    }
}
