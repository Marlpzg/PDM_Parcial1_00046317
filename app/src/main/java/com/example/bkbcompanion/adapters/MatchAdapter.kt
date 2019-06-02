package com.example.bkbcompanion.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bkbcompanion.R
import com.example.bkbcompanion.database.entities.Match
import com.example.bkbcompanion.interfaces.RecyclerViewClickListener
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class MatchAdapter(var items: List<Match>, var listener: View.OnClickListener): RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        var holder = MatchHolder(view)
        holder.onClick(listener)

        return holder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bind(items.get(position))
    }

    class MatchHolder(itemView: View): RecyclerView.ViewHolder(itemView), RecyclerViewClickListener{

        override fun onClick(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        fun bind(item: Match) = with(itemView){
            tv_teamA.text = item.homeTeam
            tv_teamB.text = item.guestTeam
            tv_scoreA.text = item.homeScore.toString()
            tv_scoreB.text = item.guestScore.toString()
            if(item.guestScore > item.homeScore){
                tv_scoreB.setTextColor(Color.parseColor("#229922"))
                tv_scoreA.setTextColor(Color.parseColor("#CC2222"))
                if(item.isOver) {
                    tv_result.text = "Ganador: " + item.guestTeam
                }
            }else if(item.guestScore < item.homeScore){
                tv_scoreA.setTextColor(Color.parseColor("#229922"))
                tv_scoreB.setTextColor(Color.parseColor("#CC2222"))
                if(item.isOver) {
                    tv_result.text = "Ganador: " + item.homeTeam
                }
            }else{
                tv_scoreA.setTextColor(Color.parseColor("#000000"))
                tv_scoreB.setTextColor(Color.parseColor("#000000"))
                if(item.isOver) {
                    tv_result.text = "Empate"
                }
            }

            if(item.isOver){
                tv_state.text = "Estado: Finalizado"
                tv_state.setTextColor(Color.parseColor("#CC2222"))
            }else{
                tv_state.text = "Estado: En juego"
                tv_state.setTextColor(Color.parseColor("#229922"))
                tv_result.text = "Resultado por definir"
            }

            val calendar = Calendar.getInstance()
            calendar.time = item.date
            tv_date.text = "Fecha: "+calendar.get(Calendar.DAY_OF_MONTH).toString()+"/"+(calendar.get(Calendar.MONTH)+1).toString()+"/"+calendar.get(Calendar.YEAR).toString()

            var minutos = ""
            if(calendar.get(Calendar.MINUTE).toString().length == 1){
                minutos = "0"+calendar.get(Calendar.MINUTE).toString()
            }else{
                minutos = calendar.get(Calendar.MINUTE).toString()
            }
            tv_time.text = "Hora: "+calendar.get(Calendar.HOUR_OF_DAY).toString()+":"+minutos
        }

    }

    fun setData(newList: List<Match>){
        items = newList
        notifyDataSetChanged()
    }

}