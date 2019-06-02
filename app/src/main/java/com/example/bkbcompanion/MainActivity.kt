package com.example.bkbcompanion

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bkbcompanion.fragments.ListFragment
import com.example.bkbcompanion.fragments.MatchFragment
import com.example.bkbcompanion.fragments.NewMatchFragment
import com.example.bkbcompanion.interfaces.FragmentComunication
import com.example.bkbcompanion.models.BKBViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity(),
        FragmentComunication,
        ListFragment.OnFragmentInteractionListener,
        NewMatchFragment.OnFragmentInteractionListener,
        MatchFragment.OnFragmentInteractionListener {

    private lateinit var viewModel: BKBViewModel

    override fun sendData(pos: Int) {
        viewModel.getAllMatches.observe(this, Observer {
            if(!it[pos].isOver){

                var partido = MatchFragment()
                var datos = Bundle()
                datos.putInt("pos", pos)
                partido.arguments = datos
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, partido)
                        .addToBackStack("prev")
                        .commit()


            }else{
                Toast.makeText(this, "Este partido ya ha finalizado", Toast.LENGTH_SHORT).show()
            }
            viewModel.getAllMatches.removeObservers(this)
        })

    }

    override fun viewMatches() {
        var lista = ListFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, lista)
                .commit()
    }

    override fun addMatch() {
        var crear = NewMatchFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, crear)
                .addToBackStack("prev")
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(BKBViewModel::class.java)

        if (savedInstanceState == null) {
            var lista = ListFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, lista).commit()
        }

    }
}
