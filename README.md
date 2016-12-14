# EasyPermission
Simplyfied utility class to ask marshmallow permissions

![Output sample](https://github.com/jineshfrancs/EasyPermission/blob/master/screens/easy.gif)

Ask any permission by one line of code
```
  easyPermission.requestPermission(this, Manifest.permission.READ_CONTACTS);
```
Receive the result of all permissions at one place.

```
    @Override
    public void onPermissionResult(String permission, boolean isGranted) {
        switch (permission) {
            case Manifest.permission.READ_CONTACTS:
                if (isGranted) {
                    //granted read contacts permission
                    //asking for one more permission after contacts permission is granted
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                } else {
                    //denied read contacts permission
                    //asking for one more permission even after contacts permission is denied
                    easyPermission.requestPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
                }
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                if (isGranted) {
                    //granted access location permission
                } else {
                    //denied access location permission
                }
                break;
        }
    }
```
