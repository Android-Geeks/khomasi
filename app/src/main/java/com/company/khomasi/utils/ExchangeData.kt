package com.company.khomasi.utils

import java.util.concurrent.atomic.AtomicReference

internal object ExchangeData {
    var email: AtomicReference<String> = AtomicReference("")
    var otp: AtomicReference<Int> = AtomicReference(0)
}