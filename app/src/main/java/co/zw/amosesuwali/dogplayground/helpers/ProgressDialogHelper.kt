package co.zw.amosesuwali.dogplayground.helpers

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity

class ProgressDialogHelper(activity : FragmentActivity, context: Context) {

    var dialog : Dialog? = context?.let { Dialog(it) }
    public val progressDialogBuilder: AlertDialog.Builder? = activity?.let {
        AlertDialog.Builder(it)
    }
}