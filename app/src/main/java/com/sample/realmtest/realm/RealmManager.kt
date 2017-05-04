package com.sample.realmtest.realm

import android.content.Context
import com.ityx.messenger.android.repository.entitites.Contact
import com.ityx.messenger.android.repository.entitites.Conversation
import com.ityx.messenger.android.repository.entitites.TextMessage
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import java.util.*

object RealmManager {

    private val USER_ID = "REALM_TEST_USER"

    fun initRealm(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }

    fun createTempObject(realm: Realm): Contact {
        var contact = realm.where(Contact::class.java).equalTo("fId", USER_ID).findFirst()
        if (contact == null) {
            val newContact = Contact()
            newContact.fId = USER_ID
            newContact.conversation = Conversation()
            newContact.conversation?.fId = UUID.randomUUID().toString()
            newContact.conversation?.textMessages = RealmList<TextMessage>()
            realm.executeTransaction { contact = it.copyToRealm(newContact) }
        }
        return contact
    }

    fun addRandomMessage(contact: Contact) {
        val realm = Realm.getDefaultInstance()
        val message = TextMessage()
        message.fId = UUID.randomUUID().toString()
        message.message = "message: ${Calendar.getInstance().timeInMillis}"
        realm.executeTransaction { contact.conversation?.textMessages?.add(message) }
        realm.close()
    }
}