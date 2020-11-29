package com.example.noci.lists

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.noci.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.custom_dialog.view.*

class CustomDialog : DialogFragment() {
    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): CustomDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = CustomDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        //view.username.setText("title")
    }

    private fun setupClickListeners(view: View) {
        view.cancel.setOnClickListener {
            dismiss()
        }

        view.add.setOnClickListener {
            val title = view.dialog_list_title.text.toString()
            if (title != null || title != "") {
                Hawk.put(LIST_TITLE, title)
//                val title: String = view.dialog_list_title.text.toString()
//                if(title != "") {
//                    fromDialogToDB()
//                }
                dismiss()
            } else {
                //view.dialog_list_title.text.
            }
            //}
        }
    }

    interface DialogListener {
        fun sendText(title: String)
    }

    // Activate for a premade AlertDialog
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(activity)
//        builder.setTitle("Alert Dialog")
//        builder.setMessage("Hello! I am Alert Dialog")
//        builder.setPositiveButton("Cool", object: DialogInterface.OnClickListener {
//            override fun onClick(dialog: DialogInterface, which:Int) {
//                //dismiss()
//            }
//        })
//        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
//            override fun onClick(dialog: DialogInterface, which:Int) {
//                //dismiss()
//            }
//        })
//        return builder.create()
//    }
}

