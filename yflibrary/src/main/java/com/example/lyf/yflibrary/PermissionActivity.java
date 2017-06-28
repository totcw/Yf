package com.example.lyf.yflibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lyf on 2017/6/25.
 */

public class PermissionActivity extends Activity {

    private String[] REQUEST_PERMISSIONS;
    private HashMap<String, String> map;//管理权限的map
    private static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 9; //权限的请求码
    private static final int REQUEST_PERMISSION_SEETING = 8; //去设置界面的请求码



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            REQUEST_PERMISSIONS = intent.getStringArrayExtra("permission");
        }

        if (REQUEST_PERMISSIONS != null) {
            checkPermiss();
        } else {
            finish();
            if (Permission.mPermissionResult != null) {

                Permission.mPermissionResult.fail();
            }

        }
    }




    /**
     * 请求权限
     *
     * @param permissions
     */
    private void requestPermission(final String[] permissions) {


            //请求权限
        PermissionDialog  permissionDialog = new PermissionDialog(PermissionActivity.this, new PermissionDialog.onConfirmListener() {
                @Override
                public void comfirm() {
                    //请求权限  将已经开启的权限 在点拒绝 会杀死当前进程和在设置界面关闭一样
                    PermissionUtil.requestContactsPermissions(PermissionActivity.this, permissions, REQUEST_PERMISSION_CODE_TAKE_PIC);
                }


            });


        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                System.out.println("sss:"+s);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }

        permissionDialog.setTvcontent(sb.toString());

        permissionDialog.show();

    }

    /**
     * 请求权限2
     *
     * @param permissions
     */
    private void requestPermission2(final String[] permissions) {
        DeleteDialog deleteDialog = new DeleteDialog(PermissionActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //去掉已经请求过的权限
                List<String> deniedPermissions = PermissionUtil.findDeniedPermissions(PermissionActivity.this, permissions);
                //请求权限
                PermissionUtil.requestContactsPermissions(PermissionActivity.this, deniedPermissions.toArray(new String[deniedPermissions.size()]), REQUEST_PERMISSION_CODE_TAKE_PIC);
            }

        });

        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }
        deleteDialog.setTvcontent("请允许" + sb + "权限请求");
        deleteDialog.show();
    }

    private void startToSetting() {
        DeleteDialog deleteDialog = new DeleteDialog(PermissionActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
            }


        });


        deleteDialog.setTvcontent("去设置界面开启权限?");
        deleteDialog.show();
    }


    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE_TAKE_PIC) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                //TODO 有权限
                finish();
                if (Permission.mPermissionResult != null) {

                    Permission.mPermissionResult.success();
                }

            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this, permissions)) {//这个返回false 表示勾选了不再提示

                    startToSetting();

                } else {

                    //表示没有权限 ,但是没勾选不再提示
                    for (String s : permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this,
                                s)) {
                            //去掉已经允许的
                            if (map != null) {
                                map.remove(s);
                            }
                        }
                    }
                    requestPermission2(permissions);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            finish();
            if (Permission.mPermissionResult != null) {

                Permission.mPermissionResult.fail();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == REQUEST_PERMISSION_SEETING) {
                checkPermiss();
        } else {
            finish();
            if (Permission.mPermissionResult != null) {

                Permission.mPermissionResult.fail();
            }
        }

    }


    /**
     * 请求权限
     */
    private void checkPermiss() {
        System.out.println("size:"+REQUEST_PERMISSIONS.length);
        PermissionUtil.checkPermission(this,  REQUEST_PERMISSIONS, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                //请求成功
                finish();
                if (Permission.mPermissionResult != null) {

                    Permission.mPermissionResult.success();
                }
            }

            @Override
            public void fail(final List<String> permissions) {
                for (String s : permissions) {
                    System.out.println(s);
                }
                getPermissionName();
                requestPermission(permissions.toArray(new String[permissions.size()]));


            }
        });
    }



    public void getPermissionName() {

            map = new HashMap<>();
            map.put("android.permission.WRITE_CONTACTS", "修改联系人");
            map.put("android.permission.GET_ACCOUNTS", "访问账户Gmail列表");
            map.put("android.permission.READ_CONTACTS", "读取联系人");
            map.put("android.permission.READ_CALL_LOG", "读取通话记录");
            map.put("android.permission.READ_PHONE_STATE", "读取电话状态");
            map.put("android.permission.CALL_PHONE", "拨打电话");
            map.put("android.permission.WRITE_CALL_LOG", "修改通话记录");
            map.put("android.permission.USE_SIP", "使用SIP视频");
            map.put("android.permission.PROCESS_OUTGOING_CALLS", "PROCESS_OUTGOING_CALLS");
            map.put("com.android.voicemail.permission.ADD_VOICEMAIL", "ADD_VOICEMAIL");
            map.put("android.permission.READ_CALENDAR", "读取日历");
            map.put("android.permission.WRITE_CALENDAR", "修改日历");
            map.put("android.permission.CAMERA", "拍照");
            map.put("android.permission.BODY_SENSORS", "传感器");
            map.put("android.permission.ACCESS_FINE_LOCATION", "获取精确位置");
            map.put("android.permission.ACCESS_COARSE_LOCATION", "获取粗略位置");
            map.put("android.permission.READ_EXTERNAL_STORAGE", "读存储卡");
            map.put("android.permission.WRITE_EXTERNAL_STORAGE", "修改存储卡");
            map.put("android.permission.RECORD_AUDIO", "录音");
            map.put("android.permission.READ_SMS", "读取短信内容");
            map.put("android.permission.RECEIVE_WAP_PUSH", "接收Wap Push");
            map.put("android.permission.RECEIVE_MMS", "接收短信");
            map.put("android.permission.SEND_SMS", "发送短信");
            map.put("android.permission.READ_CELL_BROADCASTS", "READ_CELL_BROADCASTS");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null) {
            map.clear();
            map = null;
        }

        REQUEST_PERMISSIONS = null;
        Permission.mPermissionResult = null;
    }
}
