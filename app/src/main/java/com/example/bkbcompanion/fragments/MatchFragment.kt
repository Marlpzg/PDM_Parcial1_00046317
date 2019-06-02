package com.example.bkbcompanion.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.bkbcompanion.R
import com.example.bkbcompanion.interfaces.FragmentComunication
import com.example.bkbcompanion.models.BKBViewModel
import kotlinx.android.synthetic.main.fragment_match.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "pos"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MatchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MatchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MatchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: BKBViewModel

    private lateinit var comunicacion: FragmentComunication
    private lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }

        viewModel = ViewModelProviders.of(this).get(BKBViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Activity){
            activity = context
            if (activity is FragmentComunication){
                comunicacion = activity as FragmentComunication
            }
        }
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onStart() {

        super.onStart()
        viewModel.getAllMatches.observe(this, Observer {
            var match = it[param1!!]
            viewModel.currentMatch.value = match
            if (match.isOver){
                comunicacion.viewMatches()
            }
        })

        viewModel.currentMatch.observe(this, Observer {
            tv_homeTeam.text = it.homeTeam
            tv_guestTeam.text = it.guestTeam
            tv_scoreHomeTeam.text = it.homeScore.toString()
            tv_scoreGuestTeam.text = it.guestScore.toString()
        })

        bt_team_a_3_p.setOnClickListener{
            viewModel.currentMatch.value!!.homeScore = (viewModel.currentMatch.value!!.homeScore)+3
            updateMatch()

        }

        bt_team_b_3_p.setOnClickListener {
            viewModel.currentMatch.value!!.guestScore = (viewModel.currentMatch.value!!.guestScore)+3
            updateMatch()
        }

        bt_team_a_2_p.setOnClickListener{
            viewModel.currentMatch.value!!.homeScore = (viewModel.currentMatch.value!!.homeScore)+2
            updateMatch()

        }

        bt_team_b_2_p.setOnClickListener {
            viewModel.currentMatch.value!!.guestScore = (viewModel.currentMatch.value!!.guestScore)+2
            updateMatch()
        }

        bt_team_a_free_throw.setOnClickListener{
            viewModel.currentMatch.value!!.homeScore = (viewModel.currentMatch.value!!.homeScore)+1
            updateMatch()

        }

        bt_team_b_free_throw.setOnClickListener {
            viewModel.currentMatch.value!!.guestScore = (viewModel.currentMatch.value!!.guestScore)+1
            updateMatch()
        }

        btn_endMatch.setOnClickListener {
            viewModel.currentMatch.value!!.isOver = true
            viewModel.updateMatch(viewModel.currentMatch.value!!)
            Toast.makeText(context, "El partido ha finalizado.", Toast.LENGTH_LONG).show()
            comunicacion.viewMatches()
        }

        btn_suspendMatch.setOnClickListener {
            comunicacion.viewMatches()
        }

        btn_resetMatch.setOnClickListener {
            viewModel.currentMatch.value!!.guestScore = 0
            viewModel.currentMatch.value!!.homeScore = 0
            updateMatch()
        }
    }

    fun updateMatch(){
        viewModel.currentMatch.value = viewModel.currentMatch.value
    }

    override fun onPause() {
        viewModel.updateMatch(viewModel.currentMatch.value!!)
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment MatchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MatchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }

}
