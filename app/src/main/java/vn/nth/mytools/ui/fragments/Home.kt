package vn.nth.mytools.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import vn.nth.mytools.App
import vn.nth.mytools.R
import vn.nth.mytools.Utils.ShellUtils
import vn.nth.mytools.ui.activities.ProgramsActivity
import vn.nth.mytools.databinding.FragmentHomeBinding

class Home : Fragment(){
    private val handle : Handler = Handler(Looper.getMainLooper())
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
    private var checkRootState =
        "if [[ \$(id -u 2>&1) == '0' ]] || [[ \$(\$UID) == '0' ]] || [[ \$(whoami 2>&1) == 'root' ]] || [[ \$(set | grep 'USER_ID=0') == 'USER_ID=0' ]]; then\n" +
                "  echo 'success'\n" +
                "else\n" +
                "if [[ -d /cache ]]; then\n" +
                "  echo 1 > /cache/vtools_root\n" +
                "  if [[ -f /cache/vtools_root ]] && [[ \$(cat /cache/vtools_root) == '1' ]]; then\n" +
                "    echo 'success'\n" +
                "    rm -rf /cache/vtools_root\n" +
                "    return\n" +
                "  fi\n" +
                "fi\n" +
                "exit 1\n" +
                "exit 1\n" +
                "fi\n"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        binding.statusSummary.text = "Checking root state..."
        Thread {
            val str = ShellUtils.exec(checkRootState, false)
            App.toast("output: ${str.output} | exec time: ${str.execTime}")
            handle.post {
                binding.statusSummary.text = "DOMEEEEEEEEEEEEEEEEEEEEEEE..."
            }
        }.start()
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