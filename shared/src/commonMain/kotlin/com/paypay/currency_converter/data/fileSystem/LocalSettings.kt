package com.paypay.currency_converter.data.fileSystem

import com.russhwolf.settings.Settings
import com.russhwolf.settings.long

class LocalSettings constructor(settings: Settings) {
    var latestUpdateTime by settings.long(defaultValue = 0)
}