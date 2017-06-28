package com.example.lyf.yflibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * 权限管理兑换框
 * Created by Administrator on 2016/5/19.
 */
public class PermissionDialog {
    private Context mContext;
    private Dialog mDialog;
    private View mDialogContentView;
    private TextView tv_content;
    private onConfirmListener listener;

    public PermissionDialog(Context context, onConfirmListener listener) {
        this.mContext = context;
        this.listener = listener;
        init();
    }



    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog2);

        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_permission,null);
        Button btn_comfirm = (Button) mDialogContentView.findViewById(R.id.btn_dialog_permission_comfrim);
        tv_content = (TextView) mDialogContentView.findViewById(R.id.tv_dialog_permission_content);


        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.comfirm();
                }
            }
        });

        mDialog.setCancelable(false);//不能点外面取消,也不 能点back取消
        mDialog.setContentView(mDialogContentView);
    }

    public void show(){
        mDialog.show();

    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public interface onConfirmListener{
        void comfirm();


    }

    public void setTvcontent(String content) {
        tv_content.setText(content);
    }
}
