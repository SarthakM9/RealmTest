package com.sample.realmtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.ityx.messenger.android.repository.entitites.Contact
import com.ityx.messenger.android.repository.entitites.TextMessage
import com.sample.realmtest.realm.RealmManager
import io.realm.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        OrderedRealmCollectionChangeListener<RealmResults<TextMessage>> {
    private lateinit var contact: Contact
    private lateinit var adapter: ListAdapter
    private lateinit var realm: Realm
    private lateinit var list: RealmResults<TextMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RealmManager.initRealm(this)
        realm = Realm.getDefaultInstance()
        contact = RealmManager.createTempObject(realm)

        list = realm.where(TextMessage::class.java).findAllSorted("date", Sort.DESCENDING)
        list.addChangeListener(this)

        initRecyclerView()
        addMsgBtn.setOnClickListener { RealmManager.addRandomMessage(contact) }
        delMsgBtn.setOnClickListener {
            RealmManager.deleteLastMessage(list)
        }
    }

    private fun initRecyclerView() {
        adapter = ListAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onChange(collection: RealmResults<TextMessage>,
            changeSet: OrderedCollectionChangeSet?) {

        if (changeSet == null) {
            adapter.notifyDataSetChanged()
            return
        }

        //         For deletions, the adapter has to be notified in reverse order.
        val deletions = changeSet.deletionRanges
        for (i in deletions.indices.reversed()) {
            val range = deletions[i]
            adapter.notifyItemRangeRemoved(range.startIndex, range.length)
        }

        val insertions = changeSet.insertionRanges
        for (range in insertions) {
            Log.i("Test", "insertions")
            adapter.notifyItemRangeInserted(range.startIndex, range.length)
        }

        val modifications = changeSet.changeRanges
        for (range in modifications) {
            adapter.notifyItemRangeChanged(range.startIndex, range.length)
        }
    }

    override fun onDestroy() {
        list.removeChangeListener(this)
        realm.close()
        super.onDestroy()
    }
}