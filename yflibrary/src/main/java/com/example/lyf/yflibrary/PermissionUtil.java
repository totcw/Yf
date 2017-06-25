package com.example.lyf.yflibrary;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.0Dynamic authorization application
 * Created by Administrator on 2016/8/26.
 */
public class PermissionUtil {


    /**
     * Detection authority
     * @param activity
     * @param permissions  //Requested permission group
     */
    public  static void checkPermission(final Activity activity, final String[] permissions, permissionInterface permissionInterface) {
        //Less than 23 do nothing.
        if (Build.VERSION.SDK_INT < 23) {
            permissionInterface.success();
            return;
        }
        final List<String> deniedPermissions = findDeniedPermissions(activity, permissions);

        if (deniedPermissions!=null&&deniedPermissions.size()>0) {
            //Greater than 0, said there is no application for permission

            permissionInterface.fail(deniedPermissions);

        } else {
            //have authority
            permissionInterface.success();

        }



    }



    /**
     * Request permission
     */
    public static void requestContactsPermissions(final Activity activity, final String[] permissions, final int requestCode) {
        //The default is false, but as long as the request once permission will be true, unless the point is no longer asked to be re changed to false
        if (shouldShowPermissions(activity,permissions)) {

            ActivityCompat
                    .requestPermissions(activity, permissions,
                            requestCode);
        } else {

            // No need to prompt the user interface, the direct request permissions, if the user points are no longer asked, even if the request does not have the right to request permissions dialog box
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }

    /**
     * To determine whether the requested permission is successful
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }




    public interface permissionInterface{
        void success();

        void fail(List<String> permissions);
    }

    /**
     * To find no authorization
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedPermissions(Activity activity, String... permission)
    {
        //Storing permissions without authorization
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission)
        {
            if (ContextCompat.checkSelfPermission(activity,value) != PackageManager.PERMISSION_GRANTED)
            {

                //Add without permission
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * To detect if there is no authorization required for these permissions
     * @param activity
     * @param permission
     * @return
     */
    public static boolean shouldShowPermissions(Activity activity, String... permission)
    {

        for (String value : permission)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    value))
            {
               return true;
            }
        }
        return false;
    }



}
