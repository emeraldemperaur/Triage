package iot.empiaurhouse.triage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import iot.empiaurhouse.triage.model.Prescription
import iot.empiaurhouse.triage.model.Visit
import iot.empiaurhouse.triage.view.PatientDiagnosesRxFragment
import iot.empiaurhouse.triage.view.PatientDiagnosesVisitFragment

class DiagnosesPagerAdapter(fa: FragmentActivity, private var prescriptions: ArrayList<Prescription>?,
                            private var visits: ArrayList<Visit>?): FragmentStateAdapter(fa) {

    private var fragment: Fragment? = null
    private var prescriptionsObject: ArrayList<Prescription>? = null
    private var visitsObject: ArrayList<Visit>? = null




    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        fragment = null
        if (position == 0) {
            prescriptionsObject = prescriptions
            fragment = PatientDiagnosesRxFragment.newInstance("", "", prescriptions!!)



        } else if (position == 1) {
            visitsObject = visits
            fragment = PatientDiagnosesVisitFragment.newInstance("","", visits!!)

        }
        return fragment!!
    }












}