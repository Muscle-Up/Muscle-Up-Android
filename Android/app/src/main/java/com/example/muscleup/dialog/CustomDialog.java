package com.example.muscleup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.muscleup.R;
import com.example.muscleup.model.callback.CustomDialogListener;

public class CustomDialog {

    private String text;
    private Context context;
    private CustomDialogListener customDialogListener;

    public CustomDialog(String text, Context context, CustomDialogListener customDialogListener) {
        this.text = text;
        this.context = context;
        this.customDialogListener = customDialogListener;
    }

    public void callFunction() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.show();

        TextView dialog_tv_message = dialog.findViewById(R.id.dialog_tv_message);
        Button dialog_btn_ok = dialog.findViewById(R.id.dialog_btn_ok);
        Button dialog_btn_no = dialog.findViewById(R.id.dialog_btn_no);

        dialog_tv_message.setText(text);
        dialog_btn_ok.setOnClickListener(view -> {
            customDialogListener.onClickOk();
            dialog.dismiss();
        });
        dialog_btn_no.setOnClickListener(view -> {
            customDialogListener.onClickNo();
            dialog.dismiss();
        });
    }
}
