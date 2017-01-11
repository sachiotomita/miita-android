> TODO: app icon.

# Miita for Android

[![CircleCI](https://circleci.com/gh/naoto0822/miita-android.svg?style=svg)](https://circleci.com/gh/naoto0822/miita-android)

> TODO: deplygate badge.

> TODO: app description.

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

- image

## Use 3rd Party SDK

- Firebase, Fabric(Crash)

## License

-

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
- [ ] DeployGate. (**must**)
- [ ] app icon. (**must**)
- [ ] share item. (low priority)
- [ ] search item ,tag. (low priority)
- [ ] bitmap disk cache (low priority)
