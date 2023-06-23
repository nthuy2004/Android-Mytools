package vn.nth.mytools.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.nth.mytools.App
import vn.nth.mytools.databinding.FragmentMonitorBinding

class Monitor : Fragment(){
    private lateinit var binding : FragmentMonitorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonitorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hello.root.setOnClickListener {
            App.toast("htrgbfvdcgrfd")
        }
    }
}