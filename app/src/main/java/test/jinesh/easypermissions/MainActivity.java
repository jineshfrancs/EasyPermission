package test.jinesh.easypermissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import test.jinesh.easypermissions.utils.EasyPermission;

public class MainActivity extends AppCompatActivity implements EasyPermission.OnPermissionResult {
    EasyPermission easyPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easyPermission = new EasyPermission();
        easyPermission.requestPermission(this, Manifest.permission.READ_CONTACTS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        easyPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionResult(String permission, boolean isGranted) {
        switch (permission) {
            case Manifest.permission.READ_CONTACTS:
                if (isGranted) {
                    Log.e("readContacts", "granted");
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                } else {
                    Log.e("readContacts", "denied");
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                }
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                if (isGranted) {
                    Log.e("location", "granted");
                } else {
                    Log.e("location", "denied");
                }
                break;
        }
    }
}
