package iot.empiaurhouse.triage.view

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.utils.subscribeOnBackground
import iot.empiaurhouse.triage.viewmodel.DataPivotViewModel
import iot.empiaurhouse.triage.viewmodel.InsightModelViewModel
import kotlinx.coroutines.InternalCoroutinesApi


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class SettingsFragment : PreferenceFragmentCompat() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private var themedContext: Context? = null
    private lateinit var navController: NavController
    private lateinit var userManager: UserPreferenceManager
    private lateinit var exitButton: Preference
    private lateinit var bulkDeleteInsight: Preference
    private lateinit var bulkDeletePivots: Preference
    private lateinit var insightViewModel: InsightModelViewModel
    private lateinit var pivotViewModel: DataPivotViewModel
    private lateinit var app: Application





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @InternalCoroutinesApi
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        navController = findNavController()
        userManager = UserPreferenceManager(requireContext())
        insightViewModel = ViewModelProvider(this)[InsightModelViewModel::class.java]
        pivotViewModel = ViewModelProvider(this)[DataPivotViewModel::class.java]
        app = requireActivity().application
        setPreferencesFromResource(R.xml.triage_preferences, rootKey)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        toolbarView.visibility = View.GONE
        bulkDeleteInsight = findPreference("bulkDeleteInsights")!!
        bulkDeleteInsight.setOnPreferenceClickListener {
            insightViewModel.processPivot(app)
            errorNoteSnackBar(requireView(), "Insight Models")
            subscribeOnBackground {
                insightViewModel.killInsightModelsDB()
            }
            true
        }
        bulkDeletePivots = findPreference("bulkDeletePivots")!!
        bulkDeletePivots.setOnPreferenceClickListener {
            pivotViewModel.processPivot(app)
            errorNoteSnackBar(requireView(), "Data Pivots")
            subscribeOnBackground {
                pivotViewModel.killDataPivotDB()
            }
            true
        }
        exitButton = findPreference("logoutTriage")!!
        exitButton.setOnPreferenceClickListener {
            userManager.clearUserData()
            val signOutIntent = Intent(activity, SetupActivity::class.java)
            startActivity(signOutIntent)
            true
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun errorNoteSnackBar(view: View, title: String){
        val connectErrorNote = Snackbar.make(view,"Bulk deleted $title database successfully!", Snackbar.LENGTH_SHORT)
        val errorNoteView = connectErrorNote.view
        errorNoteView.layoutParams
        val errorNoteText = errorNoteView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        errorNoteView.setBackgroundColor(requireActivity().getColor(R.color.chiron_blue))
        errorNoteText.setTextColor(requireActivity().getColor(R.color.white))
        val fontFace = resources.getFont(R.font.montserratlight)
        errorNoteText.typeface = fontFace
        errorNoteText.maxLines = 4
        connectErrorNote.anchorView = view.rootView.findViewById(R.id.hub_foot_nav)
        connectErrorNote.show()

    }



    override fun onResume() {
        super.onResume()
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        toolbarView.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context).also {
            themedContext = ContextThemeWrapper(context, R.style.PreferencesTheme)

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