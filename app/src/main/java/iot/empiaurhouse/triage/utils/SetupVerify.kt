package iot.empiaurhouse.triage.utils

import android.widget.EditText
import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty


class SetupVerify {

    private var isValidField: Boolean = false
    private lateinit var iDValidator: Validator
    private lateinit var serverValidator: Validator
    private lateinit var inputString: String

    public fun verifyID(IDEditText: EditText): Boolean {
        isValidField = IDEditText.nonEmpty()
        inputString = IDEditText.text.toString()
        iDValidator = Validator(inputString)
        return if (isValidField && iDValidator.validEmail().check()){
            isValidField
        }
        else if (isValidField && iDValidator.minLength(5).maxLength(11).check()){
            isValidField
        }
        else{
            false
        }
    }

    public fun verifyIP(IPEditText: EditText): Boolean{
        isValidField = IPEditText.nonEmpty()
        inputString = IPEditText.text.toString()
        serverValidator = Validator(inputString)
        return if (isValidField && serverValidator.startWithNumber().contains(".").check()) {
            isValidField
        } else{
            false
        }
    }

    public fun verifyURL(URLEditText: EditText): Boolean{
        isValidField = URLEditText.nonEmpty()
        inputString = URLEditText.text.toString()
        serverValidator = Validator(inputString)
        return if (isValidField && serverValidator.validUrl().check()) {
            isValidField
        } else{
            false
        }

    }


}