package iot.empiaurhouse.triage.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.core.content.res.ResourcesCompat
import iot.empiaurhouse.triage.R
import kotlinx.android.synthetic.main.buffer_dialog_view.view.*

class ChironBufferDialog {

    lateinit var dialog: CustomDialog
    lateinit var view : View

    fun show(context: Context): Dialog {
        return show(context, null)
    }


    fun show(context: Context, title: CharSequence?): Dialog {
        val inflater = (context as Activity).layoutInflater
        view = inflater.inflate(R.layout.buffer_dialog_view, null)
        if (title != null) {
            view.buffer_title.text = title
        }

        // Card Color
        view.buffer_cardview.setCardBackgroundColor(Color.parseColor("#600c204f"))

        // Progress Bar Color
        setColorFilter(view.buffer_pbar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.white, null))

        // Text Color
        view.buffer_title.setTextColor(Color.WHITE)

        dialog = CustomDialog(context)
        dialog.setContentView(view)
        dialog.show()
        return dialog
    }

    fun setTitle(bufferTitle: String){
        view.buffer_title.text = bufferTitle
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.BufferDialogTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.bufferBackground)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }

}