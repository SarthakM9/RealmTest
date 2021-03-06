package com.ityx.messenger.android.repository.entitites

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class TextMessage : RealmModel {

    @PrimaryKey
    // random uuid at present
    open var fId: String? = null

    open var message: String? = null
    open var date: Date = Date()
}