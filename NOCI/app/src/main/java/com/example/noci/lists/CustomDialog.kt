package com.example.noci.lists

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.noci.R
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*


class CustomDialog() : DialogFragment()  {

    private var listener: ExampleDialogListener? = null

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
        //setupView(view)
        //setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
           //listener = activity as ExampleDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString().toString() +
                        "must implement ExampleDialogListener"
            )
        }
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            // Get the layout inflater
//            val inflater = requireActivity().layoutInflater;
//
//            // Inflate and set the layout for the dialog
//            // Pass null as the parent view because its going in the dialog layout
//            builder.setView(inflater.inflate(R.layout.custom_dialog, null))
//                // Add action buttons
//                .setPositiveButton(R.string.cancel,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        // sign in the user ...
//                        Toast.makeText(context, "FIRST GOOD", Toast.LENGTH_LONG).show()
//                        if (listener != null) {
//                            listener!!.applyTexts("title")
//                            Toast.makeText(context, "SECOND GOOD", Toast.LENGTH_LONG).show()
//                        }
//                    })
//                .setNegativeButton(R.string.cancel,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        Toast.makeText(context, "FIRST BAD", Toast.LENGTH_LONG).show()
//                        getDialog()?.cancel()
//                    })
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
//
//    private fun setupView(view: View) {
//        //view.username.setText("title")
//    }

//    private fun setupClickListeners(view: View) {
//
//        view.cancel.setOnClickListener {
//            Toast.makeText(context, "CANCEL", Toast.LENGTH_LONG).show()
//            dismiss()
//        }
//
//        view.add.setOnClickListener {
//            val title = view.dialog_list_title.text.toString()
//            if (title != "") {
//                Toast.makeText(context, "Title is $title", Toast.LENGTH_LONG).show()
//                //Hawk.put(LIST_TITLE, title)
//                listener.applyTexts(title)
//                dismiss()
//            } else {
//                Toast.makeText(context, "Title field is empty!", Toast.LENGTH_SHORT).show()
//            }
////                val title: String = view.dialog_list_title.text.toString()
////                if(title != "") {
////                    fromDialogToDB()
////                }
////                dismiss()
////            } else {
////                //view.dialog_list_title.text.
////            }
//            //}
//        }
//    }

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

interface ExampleDialogListener {
    fun applyTexts(title: String)
}

