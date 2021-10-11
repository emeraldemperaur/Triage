package iot.empiaurhouse.triage.controller

class MultiRecordController {


    fun fetchRecordName(recordID: Int): String{
        var recordName = ""
        when(recordID){
            1 ->{
                recordName = "Patients"
            }
            2 ->{
                recordName = "Diagnoses"
            }
            3 ->{
                recordName = "Prescriptions"
            }
            4 ->{
                recordName = "Visits"
            }
            5 ->{
                recordName = "General Practitioners"
            }
            6 ->{
                recordName = "Doctors"
            }
            7 ->{
                recordName = "Registered Nurses"
            }
            8 ->{
                recordName = "Nurse Practitioners"
            }
            9 ->{
                recordName = "Pharmaceuticals"
            }
        }
        return recordName
    }

}