package test.jinesh.easypermissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class for asking permission
 *
 * @author Jinesh Francis
 */
public class EasyPermission {
    /**
     * Request code for all permissions
     */
    private static final int MY_PERMISSIONS_REQUEST = 100;
    /**
     * Activity reference for reflection
     */
    private Activity runningActivity;

    /**
     * method to request permission
     *
     * @param runningActivity current activity reference
     * @param permission      permission to ask
     */
    public void requestPermission(Activity runningActivity, String permission) {
        this.runningActivity = runningActivity;

        if (ContextCompat.checkSelfPermission(runningActivity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(runningActivity,
                    new String[]{permission},
                    MY_PERMISSIONS_REQUEST);

        } else {
            callInterface(runningActivity, permission, true);
        }
    }

    /**
     * This method is called in onRequestPermissionsResult of the runningActivity
     *
     * @param requestCode  The request code of runningActivity.
     * @param permissions  The requested permissions of runningActivity.
     * @param grantResults The grant results for the corresponding permissions from runningActivity
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (runningActivity != null) {
                        callInterface(runningActivity, permissions[0], true);
                    }

                } else {
                    if (runningActivity != null) {
                        callInterface(runningActivity, permissions[0], false);
                    }

                }
                return;
            }
        }
    }

    /**
     * Method to call OnPermissionResult interface
     *
     * @param activity   activity reference
     * @param permission current asked permission
     * @param isGranted  true if permission granted false otherwise
     * @throws InterfaceNotImplementedException throws when OnPermissionResult is not implemented
     */
    private void callInterface(Activity activity, String permission, boolean isGranted) throws InterfaceNotImplementedException {
        Method method = null;
        try {
            method = activity.getClass().getMethod("onPermissionResult", String.class, boolean.class);
        } catch (NoSuchMethodException e) {
            throw new InterfaceNotImplementedException("please implement EasyPermission.OnPermissionResult interface in your activity to get the permissions result");
        }
        if (method != null) {
            try {
                method.invoke(activity, permission, isGranted);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Interface to notify permission result
     */
    public interface OnPermissionResult {
        /**
         * Method will get called after permission request
         *
         * @param permission asked permission
         * @param isGranted  true if permission granted false otherwise
         */
        void onPermissionResult(String permission, boolean isGranted);
    }

    /**
     * Exception throws when OnPermissionResult interface not implemented
     */
    public class InterfaceNotImplementedException extends RuntimeException {
        public InterfaceNotImplementedException(String message) {
            super(message);
        }
    }
}
