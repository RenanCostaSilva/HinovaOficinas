package br.com.renancsdev.hinovaoficinas.util.permissao;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import br.com.renancsdev.hinovaoficinas.R;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Permissao extends AppCompatActivity implements EasyPermissions.PermissionCallbacks , EasyPermissions.RationaleCallbacks{

    private String TAG = "Permissao";
    private Integer RC_CAMERA_PERM = 123;
    private Integer RC_LOCATION_PERM = 124;
    private Integer RC_CAMERA_AND_LOCATION = 125;
    private Context context;

    public Permissao(Context context) {
        this.context = context;
    }

    private Boolean hasCameraPermission(){
        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA , Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void verificarPermissao() {
        if (hasCameraPermission()){
            Log.e("App123" , "OK");
        }
        else
        {
            EasyPermissions.requestPermissions(
                    (Activity) context,
                    context.getString(R.string.rationale_camera_location),
                    RC_CAMERA_AND_LOCATION,
              Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied((Activity) context, perms))
        {
            new AppSettingsDialog.Builder((Activity) context).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(context , getString(R.string.returned_from_app_settings_to_activity),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleDenied:" + requestCode);
    }

}
