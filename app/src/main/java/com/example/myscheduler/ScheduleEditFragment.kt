package com.example.myscheduler

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myscheduler.databinding.FragmentScheduleEditBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleEditFragment : Fragment() {
    private var _binding: FragmentScheduleEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScheduleEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setFabVisible(View.INVISIBLE)
        binding.save.setOnClickListener { saveSchedule(it) }
    }

    private fun saveSchedule(view: View){
        realm.executeTransaction {db: Realm ->
            val maxId = db.where<Schedule>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1L
            val schedule = db.createObject<Schedule>(nextId)
            val date = "${binding.dateEdit.text} ${binding.timeEdit.text}".toDate()

            if (date != null) schedule.date = date
            schedule.title = binding.titleEdit.text.toString()
            schedule.detail = binding.detailEdit.text.toString()

        }

        Snackbar.make(view, "追加しました。", Snackbar.LENGTH_SHORT)
                .setAction("戻る") {findNavController().popBackStack()}
                .setActionTextColor(Color.YELLOW)
                .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date? {
        return try {
            SimpleDateFormat(pattern).parse(this)
        } catch (e: IllegalArgumentException) {
            return null
        } catch (e: ParseException) {
            return null
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}