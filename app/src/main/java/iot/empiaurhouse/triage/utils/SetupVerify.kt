package iot.empiaurhouse.triage.utils

import android.graphics.Color
import android.widget.EditText
import android.widget.TextView
import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty


class SetupVerify {

    private var isValidField: Boolean = false
    private lateinit var iDValidator: Validator
    private lateinit var serverValidator: Validator
    private lateinit var inputString: String
    private val fail : String = "#800020"
    private val success : String = "#0c204f"

    fun verifyID(IDEditText: EditText, IDTextView: TextView): Boolean {
        isValidField = IDEditText.nonEmpty()
        inputString = IDEditText.text.toString()
        iDValidator = Validator(inputString)
        return if (isValidField && iDValidator.validEmail().check()){
            IDTextView.setTextColor(Color.parseColor(success))
            isValidField
        }
        else if (isValidField && iDValidator.minLength(5).maxLength(11).check()){
            IDTextView.setTextColor(Color.parseColor(success))
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
            isValidField
        } else{
            IPTextView.setTextColor(Color.parseColor(fail))
            false
        }

    }


}