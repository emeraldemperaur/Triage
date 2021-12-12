package iot.empiaurhouse.triage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Patients(): ArrayList<Patient>(), Parcelable
