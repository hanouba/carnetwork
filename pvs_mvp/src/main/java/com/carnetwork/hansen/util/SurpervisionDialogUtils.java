package com.carnetwork.hansen.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carnetwork.hansen.R;


/**
 * @author HanN on 2020/6/6 16:17
 * @email: 1356548475@qq.com
 * @project trunk
 * @description: 督办界面的弹窗
 * @updateuser:
 * @updatedata: 2020/6/6 16:17
 * @updateremark:
 * @version: 2.1.67
 */
public class SurpervisionDialogUtils {
    public static SurpervisionDialogUtils getInstance() {
        return new SurpervisionDialogUtils();
    }


    /**
     * 弹出自定义样式的督办处置AlertDialog
     *
     * @param context 上下文
     * @param name      点击弹出框选择条目后，督办人的名称

     */
    public  void showAlertDialog(Context context, final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        View view = LayoutInflater.from(context).inflate(R.layout.view_create_user, null);
        final EditText etProjectName = (EditText) view.findViewById(R.id.et_project_name);
        final EditText etPhone = (EditText) view.findViewById(R.id.et_phone);
        final EditText etName = (EditText) view.findViewById(R.id.et_name);
        final EditText etVerification = (EditText) view.findViewById(R.id.et_verification);
        TextView btSubmit = (TextView) view.findViewById(R.id.tv_get_ver);

        builder.setView(view);
        //赋值给其父类以获取dismiss方法
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //检查手机号
                // 获取验证码
                onConfirmDialogInterface.onConfirmClickListener("");
                //业务逻辑处理完毕使dialog消失
                alertDialog.dismiss();
            }
        });
    }

    public interface ConfirmDialogInterface {
        //监听确认按钮点击事件
        void onConfirmClickListener(String msg);

    }

    private ConfirmDialogInterface onConfirmDialogInterface;

    public void setOnConfirmDialogInterface(ConfirmDialogInterface onConfirmDialogInterface) {
        this.onConfirmDialogInterface = onConfirmDialogInterface;
    }





    /**
     * 文字颜色修改
     * @param reply
     * @param content
     * @param colorID
     * @return
     */
    private  SpannableStringBuilder getSpannable(String reply, String content, int colorID) {
        SpannableStringBuilder builder = new SpannableStringBuilder(reply).append(content);
        CharacterStyle characterStyle = new ForegroundColorSpan(colorID);
        if (content.length() == 0) {
            builder.setSpan(characterStyle, reply.length(), reply.length()+content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else {
            builder.setSpan(characterStyle, reply.length(), reply.length()+content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }
    private EnsureDialogInterface onEnsureDialogInterface;
    public void setOnEnsureDialogInterface(EnsureDialogInterface onEnsureDialogInterface) {
        this.onEnsureDialogInterface = onEnsureDialogInterface;
    }
    public interface EnsureDialogInterface {
        //监听确认按钮点击事件
        void onEnsuerClickListener();

    }












}
