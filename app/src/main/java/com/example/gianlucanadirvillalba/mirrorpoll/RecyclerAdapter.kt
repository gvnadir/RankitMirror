package com.example.gianlucanadirvillalba.mirrorpoll

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

/**
 * Created by gianlucanadirvillalba on 06/10/2017.
 */
class RecyclerAdapter(context: Context) : RecyclerView.Adapter<RecyclerAdapter.PollsHolder>(), PollListener
{
    private lateinit var mContext: Context
    private var mLayoutInflater = LayoutInflater.from(context)

    companion object
    {
        var data: ArrayList<Poll> = ArrayList()

        fun addData(instance: RecyclerAdapter, data: ArrayList<Poll>)
        {
            this.data = data
            instance.notifyItemRangeChanged(0, data.size)
        }
    }

    override fun onBindViewHolder(holder: PollsHolder?, position: Int)
    {
        val poll = data[position]
        holder?.textName?.text = poll.name
        holder?.textVotes?.text = "voti ${poll.votes}"
        if (poll.candidates.size == 0) holder?.textCandidates?.text = "nessun candidato"
        else holder?.textCandidates?.text = poll.candidates.toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PollsHolder
    {
        val view = mLayoutInflater.inflate(R.layout.custom_list_poll, parent, false)
        return PollsHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onAddPoll(pollArray: ArrayList<Poll>)
    {
        addData(this, pollArray)
    }

    class PollsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        var textName = itemView?.findViewById(R.id.pollname) as TextView
        var textCandidates = itemView?.findViewById(R.id.candidates) as TextView
        var textVotes = itemView?.findViewById(R.id.votes) as TextView

        init
        {
            itemView?.setOnClickListener(this)
        }

        override fun onClick(p0: View?)
        {
            Toast.makeText(MyApplication.appContext, "Sending poll ${data[adapterPosition].name}", Toast.LENGTH_SHORT).show()
            MyApplication.sendToMirror(data[adapterPosition])
        }
    }

}