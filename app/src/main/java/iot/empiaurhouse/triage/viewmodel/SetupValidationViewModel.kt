package iot.empiaurhouse.triage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupValidationViewModel: ViewModel() {
    val emailAddress = MutableLiveData<String>()

}