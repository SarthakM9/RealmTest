package com.ityx.messenger.android.repository.entitites

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Conversation : RealmModel {

    @PrimaryKey
    // random uuid at present
    open var fId: String? = null
    open var textMessages: RealmList<TextMessage>? = null
}