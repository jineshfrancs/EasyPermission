package test.jinesh.easypermissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    public void onPermissionResult(String permission, boolean isGranded) {
        switch (permission) {
            case Manifest.permission.READ_CONTACTS:
                if (isGranded) {
                    Log.e("readContacts", "granded");
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                } else {
                    Log.e("readContacts", "denied");
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                }
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                if (isGranded) {
                    Log.e("location", "granded");
                } else {
                    Log.e("location", "denied");
                }
                break;
        }
    }
}
