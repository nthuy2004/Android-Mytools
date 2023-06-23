package vn.nth.mytools.UI.Fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import vn.nth.mytools.App
import vn.nth.mytools.R
import vn.nth.mytools.UI.Activities.ProgramsActivity
import vn.nth.mytools.databinding.FragmentHomeBinding

class Home : Fragment(){
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_home_opt_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            R.id.restart_menu -> {
                App.toast("Restart menu")
            }
            R.id.setting_menu -> {
                App.toast("Setting menu")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initEvent() {
        binding.btnPrograms.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.setClass(requireContext(), ProgramsActivity::class.java)
            startActivity(i)
        }
    }
}