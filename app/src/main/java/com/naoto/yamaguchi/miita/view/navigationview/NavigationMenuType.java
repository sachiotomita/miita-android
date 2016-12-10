package com.naoto.yamaguchi.miita.view.navigationview;

/**
 * {@MiitaNavigationView} Menu Type.
 *
 * TODO Later:
 * - http://qiita.com/KeithYokoma/items/51852d343002d969d841
 * - http://qiita.com/wasnot/items/dfc33bb91f64abfec04c
 *
 * Created by naoto on 2016/12/09.
 */

public enum NavigationMenuType {
    ALL_ITEM {
        @Override
        public int toInt() {
            return 0;
        }

        @Override
        public String toFragmentName() {
            return "AllItemFragment";
        }
    },
    STOCK_ITEM {
        @Override
        public int toInt() {
            return 1;
        }

        @Override
        public String toFragmentName() {
            return "StockItemFragment";
        }
    },
    FOLLOW_TAG {
        @Override
        public int toInt() {
            return 2;
        }

        @Override
        public String toFragmentName() {
            return "FollowTagFragment";
        }
    },
    SETTING {
        @Override
        public int toInt() {
            return 3;
        }

        @Override
        public String toFragmentName() {
            return "SettingFragment";
        }
    };

    public abstract int toInt();
    public abstract String toFragmentName();
}
