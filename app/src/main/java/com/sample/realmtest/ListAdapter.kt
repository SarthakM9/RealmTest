package com.sample.realmtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ityx.messenger.android.repository.entitites.TextMessage
import io.realm.RealmList
import kotlinx.android.synthetic.main.list_row.view.*

class ListAdapter(
        val list: RealmList<TextMessage>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?,
            viewType: Int): ListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_row, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder?, position: Int) {
        if (holder == null)
            return
        holder.message.text = "<${position + 1}> " + list[position].message
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.message
    }
}