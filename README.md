> TODO: app icon.

# Miita for Android

[![CircleCI](https://circleci.com/gh/naoto0822/miita-android.svg?style=svg)](https://circleci.com/gh/naoto0822/miita-android)
[<img src="https://dply.me/4j2gcf/button/large" alt="Try it on your device via DeployGate">](https://dply.me/4j2gcf#install)

The unofficial Android app for [Qiita](http://qiita.com "Qiita").

> TODO: app image.

## Getting Started

1. clone this repositpry.

 ```sh
 $ git clone https://github.com/naoto0822/miita-android.git
 ```

2. `cd` in project and run `make bootstrap` that generate `debug.properties`.

 ```sh
 $ make bootstrap
 ```

3. register new Application in Qiita(https://qiita.com/settings/applications).
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

## TODO
- [☓] ~~navigation selected when backpress. (**must**)~~
- [☓] ~~bitmap memory cache. (**must**)~~
- [☓] ~~adapter ViewHolder. (**must**)~~
- [☓] ~~list layout. (**must**)~~
- [☓] ~~item header multi line. (**must**)~~
- [☓] ~~additional entity variable and fix item header. (**must**)~~
- [☓] ~~license. (**must**)~~
- [☓] ~~setting, license activity back button. (**must**)~~
- [☓] ~~integrate crashlytics. (**must**)~~
- [☓] ~~integrate Firebase. (**must**)~~
- [☓] ~~circle CI. (**must**)~~
- [☓] ~~DeployGate. (**must**)~~
- [ ] app icon. (**must**)
- [ ] share item.
- [ ] search item ,tag.
- [ ] bitmap disk cache.
