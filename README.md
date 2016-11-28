# LogCleaner [![Travis master](https://img.shields.io/travis/Poeschl/LogCleaner/master.svg?maxAge=3600)](https://travis-ci.org/Poeschl/LogCleaner) [![Coveralls](https://img.shields.io/coveralls/Poeschl/LogCleaner/master.svg?maxAge=3600)](https://coveralls.io/github/Poeschl/LogCleaner)
The log folder of your bukkit / spigot server gets more and more filled by log archives?
This plugin will clean up this folder for you, completely free of charge.

The plugin checks on every server start if there are archives in the log folder, which are older then `x` days (configurable).
If there are some files with this condition they will get deleted and that's all the plugin does.

## Configuration

```yml
keepLogsFromLastDays: 2
```

The config just contains one setting, which let you set the past days you want to keep the log archives.
In the sample config above it is set to two days, so the archives of the last two days will be kept and every archive older then that will be deleted.


---

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/Poeschl/LogCleaner/master/LICENSE)
