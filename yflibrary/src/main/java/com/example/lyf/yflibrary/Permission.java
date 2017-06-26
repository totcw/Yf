package com.example.lyf.yflibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.ObjectStreamException;
import java.util.List;

/**
 *
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/26
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class Permission {

    public static  PermissionResult mPermissionResult;


    public static void checkPermisson(Context context,String[] permissions, PermissionResult permissionResult) {
        mPermissionResult = permissionResult;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra("permission", permissions);
        context.startActivity(intent);
    }
}
