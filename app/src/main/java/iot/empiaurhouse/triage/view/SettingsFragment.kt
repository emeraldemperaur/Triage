package iot.empiaurhouse.triage.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentSettingsBinding


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class SettingsFragment : PreferenceFragmentCompat() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private var themedContext: Context? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.triage_preferences, rootKey)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        toolbarView.visibility = View.GONE


    }

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)
        container!!.context.setTheme(R.style.PreferencesTheme);

        return null
    }*/

    override fun onResume() {
        super.onResume()
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        toolbarView.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context).also {
            themedContext = ContextThemeWrapper(context, R.style.PreferencesTheme)
            // if you want to apply a theme overlay:
            // themedContext.theme.applyStyle(R.style.MyThemeOverlay, true)
        }
    }

    override fun onDetach() {
        super.onDetach()
        themedContext = null
    }

    override fun getContext(): Context? {
        return themedContext ?: super.getContext()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding = FragmentSettingsBinding.bind(view)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}