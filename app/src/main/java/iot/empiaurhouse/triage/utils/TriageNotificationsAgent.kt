package iot.empiaurhouse.triage.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.ChironRecords
import iot.empiaurhouse.triage.view.HubActivity
import kotlin.properties.Delegates

class TriageNotificationsAgent(val context: Context, val chironRecords: ArrayList<ChironRecords>, private val notificationsEnabled: Boolean) {

    private var totalRecordsResult by Delegates.notNull<Int>()
    private var refreshRecordsResult by Delegates.notNull<Int>()
    private var userManager: UserPreferenceManager = UserPreferenceManager(context)
    private var patientsCount by Delegates.notNull<Int>()
    private var diagnosesCount by Delegates.notNull<Int>()
    private var prescriptionsCount by Delegates.notNull<Int>()
    private var visitsCount by Delegates.notNull<Int>()
    private var practitionersCount by Delegates.notNull<Int>()
    private var doctorsCount by Delegates.notNull<Int>()
    private var registeredNursesCount by Delegates.notNull<Int>()
    private var nursePractitionersCount by Delegates.notNull<Int>()
    private var pharmaceuticalsCount by Delegates.notNull<Int>()
    private var freshRecordCount by Delegates.notNull<Int>()
    private lateinit var notificationContext: String
    private lateinit var serverUrl: String
    private val channelID = "TriageAlert"
    private val notificationID = 69


    fun inspectRecords(){
        freshRecordCount = 0
        val extantRecordCount = extantHeadCount()
        var focusRecordCount = 0
        val urlID = userManager.getServerUrl()
        serverUrl = sliceURL(urlID!!)
        for (record in chironRecords) {
            freshRecordCount += record.recordCount!!
        }
        if (freshRecordCount > extantRecordCount){
            // new records
            focusRecordCount = freshRecordCount - extantRecordCount
            notificationContext = "\n$focusRecordCount records were added to the database.\n"
            if (focusRecordCount == 1){
                notificationContext = "\n$focusRecordCount record was added to the database.\n"
            }
            println(notificationContext)
        }
        if (freshRecordCount == extantRecordCount){
            // no new or deleted records
            notificationContext = "\n$freshRecordCount records successfully found on database.\n"
            if (freshRecordCount == 1){
                notificationContext = "\n$freshRecordCount record successfully found on database.\n"
            }
            println(notificationContext)
        }
        else if (freshRecordCount < extantRecordCount){
            // deleted records
            focusRecordCount = extantRecordCount - freshRecordCount
            notificationContext = "\n$focusRecordCount records were deleted from the database.\n"
            if (focusRecordCount == 1){
                notificationContext = "\n$focusRecordCount record has been deleted from the database.\n"
            }
            println(notificationContext)
        }
        for (recordsFound in chironRecords){
            when(recordsFound.recordName){
                "Patient"->{
                    patientsCount = recordsFound.recordCount!!
                    println("\nPatients: $patientsCount")
                }
                "Diagnosis"->{
                    diagnosesCount = recordsFound.recordCount!!
                    println("Diagnoses: $diagnosesCount")
                }
                "Prescription"->{
                    prescriptionsCount = recordsFound.recordCount!!
                    println("Prescriptions: $prescriptionsCount")
                }
                "Visit"->{
                    visitsCount = recordsFound.recordCount!!
                    println("Visits: $visitsCount")
                }
                "Practitioner"->{
                    practitionersCount = recordsFound.recordCount!!
                    println("Practitioners: $practitionersCount")
                }
                "Doctor"->{
                    doctorsCount = recordsFound.recordCount!!
                    println("Doctors: $doctorsCount")
                }
                "Registered Nurse"->{
                    registeredNursesCount = recordsFound.recordCount!!
                    println("Registered Nurses: $registeredNursesCount")

                }
                "Nurse Practitioner"->{
                    nursePractitionersCount = recordsFound.recordCount!!
                    println("Nurse Practitioners: $nursePractitionersCount")
                }
                "Pharmaceutical"->{
                    pharmaceuticalsCount = recordsFound.recordCount!!
                    println("Pharmaceuticals: $pharmaceuticalsCount\n")
                }
            }
        }
        userManager.storeChironRecordsCount(patientsCount, diagnosesCount, prescriptionsCount,
            visitsCount, practitionersCount, doctorsCount, nursePractitionersCount,
            registeredNursesCount, pharmaceuticalsCount)
        refreshRecordsResult = (patientsCount + diagnosesCount + prescriptionsCount + visitsCount
        + practitionersCount + doctorsCount + nursePractitionersCount + registeredNursesCount + pharmaceuticalsCount)
        println("\n\n\t Records cache refresh successful\t")
        println("\t\t - Updated Total Records: $refreshRecordsResult")
        if (notificationsEnabled) {
            createTN()
        }
    }

    private fun extantHeadCount(): Int {
        totalRecordsResult = 0
        patientsCount = userManager.getPatientsCount()
        diagnosesCount = userManager.getDiagnosesCount()
        prescriptionsCount = userManager.getPrescriptionsCount()
        visitsCount = userManager.getVisitsCount()
        practitionersCount = userManager.getPractitionersCount()
        doctorsCount = userManager.getDoctorsCount()
        registeredNursesCount = userManager.getRegisteredNursesCount()
        nursePractitionersCount = userManager.getNursePractitionersCount()
        pharmaceuticalsCount = userManager.getPharmaceuticalsCount()
        totalRecordsResult = (patientsCount + diagnosesCount + prescriptionsCount + visitsCount
        + practitionersCount + doctorsCount + nursePractitionersCount + registeredNursesCount + pharmaceuticalsCount)
        println("\n\n\t Extant records cache inspected\t")
        println("\t\t - Extant Total Records: $totalRecordsResult")
        return totalRecordsResult
    }

    private fun createTN(){
        createTNChannel()
        val intent = Intent(context, HubActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.chironlogo500)
        val iconLg = BitmapFactory.decodeResource(context.resources, R.drawable.triage_tinted_background)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_chiron_logo_blue)
            .setLargeIcon(icon)
            .setContentTitle("Chiron API connected")
            .setContentText(notificationContext)
            .setSubText(serverUrl)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(iconLg)
                .bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(notificationID, notification)

    }

    private fun createTNChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = channelID
            val description = channelID
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val vibrationEffect = longArrayOf(50, 100, 50, 100)
            val channel = NotificationChannel(channelID, name, importance).apply {
                setDescription(description)
                vibrationPattern = vibrationEffect
                lightColor = Color.parseColor("#0c204f")
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sliceURL(urlString: String): String{
        var slicedUrl = ""
        val strLength = 24
        slicedUrl = urlString.substringAfter("https://")
        slicedUrl = slicedUrl.substringAfter("http://")
        slicedUrl = slicedUrl.substringAfter("www.")
        slicedUrl = slicedUrl.substringBefore(".")
        if (slicedUrl.length > strLength) {
            slicedUrl = slicedUrl.substring(0, strLength)
        }
        return slicedUrl
    }

}