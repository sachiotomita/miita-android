![app icon](./app/src/main/res/mipmap-hdpi/ic_launcher.png)

# Miita for Android

[![CircleCI](https://circleci.com/gh/naoto0822/miita-android.svg?style=svg)](https://circleci.com/gh/naoto0822/miita-android)
[<img src="https://dply.me/4j2gcf/button/large" alt="Try it on your device via DeployGate">](https://dply.me/4j2gcf#install)
[![GitHub release](https://img.shields.io/github/release/naoto0822/miita-android.svg)](https://github.com/naoto0822/miita-android/releases)
[![GitHub issues](https://img.shields.io/github/issues/naoto0822/miita-android.svg)](https://github.com/naoto0822/miita-android/issues)
[![license](https://img.shields.io/github/license/naoto0822/miita-android.svg)](https://github.com/naoto0822/miita-android/blob/master/LICENSE)

The unofficial android app for [Qiita](http://qiita.com "Qiita").
origin of the app name(Miita) is "Mobile for Qiita".

- browse all items and item detail.
- browse tag items.
- browse stock items, follow tags after login.
- stock/unstock item after login.

> TODO: app image.

## Google Play Store

https://play.google.com/store/apps/details?id=com.naoto.yamaguchi.miita

## Getting Started

Only debug build and only owner can release.

> `./deploy` directory is private repository.

1. clone this repositpry.

 ```sh
 $ git clone https://github.com/naoto0822/miita-android.git
 ```

2. `cd` in project and run `make bootstrap` that generate `debug.properties`.

 ```sh
 $ make bootstrap
 ```

3. register new Application in [Qiita Settings](https://qiita.com/settings/applications).
   enter the following value.

 ```
 Authorization callback URL: miita://callback.debug
 ```

4. edit `debug.properties`.

 ```
 qiitaClientID=<YOUR_CLIENT_ID>
 qiitaClientSecret=<YOUR_CLIENT_SECRET>
 ```

5. run in debug build.

## CI

> TODO: image

## OSS

- [Realm](https://realm.io/jp/)
- [flexbox-layout](https://github.com/google/flexbox-layout)

## 3rd Party SDK

#### Analytics

- [Firebase](https://firebase.google.com/ "Firebase")

#### Crash

- [Fabric](https://fabric.io, "Fabric")

## License

```
Copyright 2016 Naoto Yamaguchi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
