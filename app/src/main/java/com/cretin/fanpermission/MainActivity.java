package com.cretin.fanpermission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.tools.fanpermission.FanPermissionListener;
import com.cretin.tools.fanpermission.FanPermissionUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch aSwitch = findViewById(R.id.switch_view);

                FanPermissionUtils.with(MainActivity.this)
                        .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .addPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .addPermissions(Manifest.permission.CALL_PHONE)
                        .addPermissions(Manifest.permission.ACCESS_WIFI_STATE)
                        .addPermissions(Manifest.permission.CAMERA)
                        .setPermissionsCheckListener(new FanPermissionListener() {
                            @Override
                            public void permissionRequestSuccess() {
                                ((TextView) findViewById(R.id.tv_result)).setText("授权结果\n\n所有权限都授权成功\n");
                                Toast.makeText(MainActivity.this, "所有权限都授权成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                                StringBuilder result = new StringBuilder("授权结果\n\n授权失败\n\n");
                                result.append("授权通过的权限：\n");
                                for (String grantedPermission : grantedPermissions) {
                                    result.append(grantedPermission + "\n");
                                }
                                result.append("\n临时拒绝的权限：\n");
                                for (String deniedPermission : deniedPermissions) {
                                    result.append(deniedPermission + "\n");
                                }
                                result.append("\n永久拒绝的权限：\n");
                                for (String forceDeniedPermission : forceDeniedPermissions) {
                                    result.append(forceDeniedPermission + "\n");
                                }
                                ((TextView) findViewById(R.id.tv_result)).setText(result);
                                Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .createConfig()
                        .setForceAllPermissionsGranted(aSwitch.isChecked())
                        .setForceDeniedPermissionTips("请前往设置->应用->【" + FanPermissionUtils.getAppName(MainActivity.this) + "】->权限中打开相关权限，否则功能无法正常运行！")
                        .buildConfig()
                        .startCheckPermission();
            }
        });


        findViewById(R.id.tv_to_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));
            }
        });
    }
}
