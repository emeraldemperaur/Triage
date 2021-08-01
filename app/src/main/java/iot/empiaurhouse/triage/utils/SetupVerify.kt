package iot.empiaurhouse.triage.utils

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.widget.EditText
import android.widget.TextView
import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.network.RetrofitClientFactory
import iot.empiaurhouse.triage.view.ChironBufferDialog


class SetupVerify {

    private var isValidField: Boolean = false
    private lateinit var iDValidator: Validator
    private lateinit var serverValidator: Validator
    private lateinit var inputString: String
    private lateinit var userPUID: String
    private lateinit var serverUrl: String
    private val fail : String = "#800020"
    private val success : String = "#0c204f"
    private var serverFound = true
    private var serverNotFound = false
    private val bufferDialog = ChironBufferDialog()
    lateinit var userPreferences: UserPreferenceManager
    private lateinit var retrofitClientFactory: RetrofitClientFactory
    private lateinit var retrofitContext: Context





    fun verifyID(IDEditText: EditText, IDTextView: TextView): Boolean {
        isValidField = IDEditText.nonEmpty()
        inputString = IDEditText.text.toString()
        iDValidator = Validator(inputString)
        return if (isValidField && iDValidator.validEmail().check()){
            IDTextView.setTextColor(Color.parseColor(success))
            userPUID = inputString
            isValidField
        }
        else if (isValidField && iDValidator.minLength(5).maxLength(11).check()){
            IDTextView.setTextColor(Color.parseColor(success))
            userPUID = inputString
            isValidField
        }
        else{
            IDTextView.setTextColor(Color.parseColor(fail))
            false
        }
    }

    fun verifyIP(IPEditText: EditText, IPTextView: TextView): Boolean{
        isValidField = IPEditText.nonEmpty()
        inputString = IPEditText.text.toString()
        serverValidator = Validator(inputString)
        return if (isValidField && serverValidator.startWithNumber().contains(".").check()) {
            IPTextView.setTextColor(Color.parseColor(success))
            serverUrl = inputString
            isValidField
        } else{
            IPTextView.setTextColor(Color.parseColor(fail))
            false
        }
    }

    fun verifyURL(URLEditText: EditText, IPTextView: TextView): Boolean{
        isValidField = URLEditText.nonEmpty()
        inputString = URLEditText.text.toString()
        serverValidator = Validator(inputString)
        return if (isValidField && serverValidator.validUrl().check()) {
            IPTextView.setTextColor(Color.parseColor(success))
            serverUrl = inputString
            isValidField
        } else{
            IPTextView.setTextColor(Color.parseColor(fail))
            false
        }

    }

    fun chironConnect(context: Context, chironUrl: String): Boolean{
        // connect to server then return YES or NO
        retrofitContext = context
        bufferDialog.show(context, "Please Wait...")
        userPreferences = UserPreferenceManager(context)
        RetrofitClientFactory.initServerUrl(retrofitContext)
        serverFound = false
        return if(serverFound){
            Handler().postDelayed(
                    {
                        saveChironUser()
                        bufferDialog.setTitleSize(21)
                        bufferDialog.setErrorIcon()
                        bufferDialog.setTitle(context.getString(R.string.buffer_text_pass))
                        bufferDialog.initCloser()

                    },
                    3000 // value in milliseconds
            )
            serverFound
        }
        else{
            Handler().postDelayed(
                    {
                        bufferDialog.setTitle(context.getString(R.string.buffer_text_fail))
                        // before closing
                        bufferDialog.initCloser()
                    },
                    3000 // value in milliseconds
            )
            serverNotFound
        }


    }

    private fun saveChironUser(){
        // get device phone number or IMEI
        userPreferences.storeUserData(userPUID, serverUrl, "","$userPUID::$serverUrl")

    }

    private fun clearChironUser(){
        userPreferences.clearUserData()
    }





}