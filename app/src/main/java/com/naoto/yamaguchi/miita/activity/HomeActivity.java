package com.naoto.yamaguchi.miita.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.application.Constants;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.fragment.AllItemFragment;
import com.naoto.yamaguchi.miita.fragment.StockItemFragment;
import com.naoto.yamaguchi.miita.model.CurrentUserModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.task.DownloadImageTask;
import com.naoto.yamaguchi.miita.util.fragment.FragmentRouter;
import com.naoto.yamaguchi.miita.view.MiitaAlertDialogBuilder;
import com.naoto.yamaguchi.miita.view.MiitaAlertDialogType;

public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AllItemFragment.OnItemClickListener,
        StockItemFragment.OnItemClickListener {

    // FIXME: NavMenuType, FragmentTypeをenumにする
    public class NavMenuType {
        public static final int ALL_ITEM = 0;
        public static final int STCOK_ITEM = 1;
        public static final int FOLLOW_TAG = 2;
        public static final int SETTING = 3;

        private NavMenuType() {}
    }

    public class FragmentType {
        public static final String ALL_ITEM = "AllItemFragment";
        public static final String STOCK_ITEM = "StockItemFragment";
        public static final String FOLLOW_TAG = "FollowTagFragment";
        public static final String SETTING = "SettingFragment";

        private FragmentType() {}
    }

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private CurrentUser currentUser;
    private CurrentUserModel currentUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.init();
        this.setLayout();

        FragmentRouter.newInstance()
                .begin(this.getSupportFragmentManager(), AllItemFragment.newInstance())
                .replace(R.id.home_container_view)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!this.currentUser.isAuthorize(this)
                && this.currentUserModel.isExistCodeQuery(this.getIntent())) {

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("ログイン中...");
            dialog.show();

            String code = this.currentUserModel.getCodeQuery(this.getIntent());
            this.currentUserModel.request(code, new OnModelListener<Void>() {
                @Override
                public void onSuccess(Void results) {
                    dialog.dismiss();
                }

                @Override
                public void onError(APIException e) {
                    dialog.dismiss();
                }

                @Override
                public void onComplete() {
                    // NOOP
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        FragmentManager manager = this.getSupportFragmentManager();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (manager.getBackStackEntryCount() > 0) {
            int backStackCount = manager.getBackStackEntryCount();
            String popFragment =
                    manager.getFragments().get(backStackCount - 1).getClass().getSimpleName();

            // TODO: use util class
            switch (popFragment) {
                case FragmentType.ALL_ITEM:
                    this.navigationView.getMenu()
                            .getItem(NavMenuType.ALL_ITEM)
                            .setChecked(true);
                    break;
                case FragmentType.STOCK_ITEM:
                    this.navigationView.getMenu()
                            .getItem(NavMenuType.STCOK_ITEM)
                            .setChecked(true);
                    break;
                case FragmentType.FOLLOW_TAG:
                    this.navigationView.getMenu()
                            .getItem(NavMenuType.FOLLOW_TAG)
                            .setChecked(true);
                    break;
                case FragmentType.SETTING:
                    this.navigationView.getMenu()
                            .getItem(NavMenuType.SETTING)
                            .setChecked(true);
                    break;
            }

            manager.popBackStack();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

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
        int id = item.getItemId();
        boolean isSameItem = item.isChecked();
        FragmentManager manager = this.getSupportFragmentManager();

        // TODO: use util class
        switch (id) {
            case R.id.nav_all_item:
                FragmentRouter.newInstance()
                        .begin(manager, AllItemFragment.newInstance())
                        .replace(R.id.home_container_view)
                        .addStack(isSameItem)
                        .commit();
                break;
            case R.id.nav_stock_item:
                if (this.currentUser.isAuthorize(this)) {
                    FragmentRouter.newInstance()
                            .begin(manager, StockItemFragment.newInstance())
                            .replace(R.id.home_container_view)
                            .addStack(isSameItem)
                            .commit();
                } else {
                    this.showLoginAlert();
                }
                break;
            case R.id.nav_follow_tag:
                // TODO: follow tag fragment
                if (this.currentUser.isAuthorize(this)) {

                } else {
                    this.showLoginAlert();
                }
                break;
            case R.id.nav_setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
        return true;
    }

    private void init() {
        this.currentUser = CurrentUser.getInstance();
        this.currentUserModel = new CurrentUserModel(this);
    }

    private void setLayout() {
        this.toolbar = (Toolbar)findViewById(R.id.toolbar);
        this.toolbar.setTitle("");
        setSupportActionBar(this.toolbar);

        this.drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        this.drawerToggle = new ActionBarDrawerToggle(
                this,
                this.drawerLayout,
                this.toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        // FIXME: may be unnecessary
        this.drawerLayout.setDrawerListener(this.drawerToggle);
        this.drawerToggle.syncState();

        this.navigationView = (NavigationView)findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
    }

    // TODO: create header view class
    private void updateDrawerHeader() {
        View header = this.navigationView.getHeaderView(0);

        ImageView userImage = (ImageView)header.findViewById(R.id.user_image);
        TextView userNameView = (TextView)header.findViewById(R.id.user_name_text);
        TextView userIdView = (TextView)header.findViewById(R.id.user_id_text);

        // TODO: get user name
        String imageUrlString = this.currentUser.getImageUrl(this);
        String userId = this.currentUser.getID(this);

        userNameView.setText("TODO");
        userIdView.setText(userId);

        DownloadImageTask task = new DownloadImageTask(this, userImage);
        task.execute(imageUrlString);
    }

    private void showLoginAlert() {
        new MiitaAlertDialogBuilder(this)
                .build(MiitaAlertDialogType.LOGIN)
                .show();
    }

    @Override
    public void onItemClick(AllItem item) {

    }

    @Override
    public void onItemClick(StockItem item) {

    }
}
