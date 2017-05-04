package com.ityx.messenger.android.repository.entitites

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Contact : RealmModel {

    @PrimaryKey
    open var fId: String? = null

    open var conversation: Conversation? = null
}