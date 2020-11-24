package com.example.sharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import java.io.File;

/*
* add FileProvider to AndroidManifest.xml
* <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.example.sharing.fileprovider"
            android:grantUriPermissions="true"
            android:exported="false">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths" />
        </provider>
* with filepaths is shared directory
* Example:
* <paths>
    <files-path path="Records/" name="myrecords" /> //file-path: /data/data/<package-name>/files
    <external-path name="myimages" path="Records/"/> //external-path : /storage/emulated/0
 </paths>
*/

public class MainActivity extends Activity {
    ListView lstFile;
    String[] name = {"Item 1", "Item 2", " "};
    File[] files;
    private File privateRootDir;
    private File folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstFile = (ListView) findViewById(R.id.lstFile);
        askWritingPermission(); //not necessary
        privateRootDir = Environment.getExternalStorageDirectory();
        folder = new File(privateRootDir,"Records");
        boolean success = true;

        if(!folder.exists()) {
            success = folder.mkdirs();
        }
        if(success) {
            name[2] = "success";
        } else {
            name[2] = "failed";
        }
        name[2] = folder.getAbsolutePath();
        name = folder.list();
        FileList adapter = new FileList(this, R.layout.file_list, name, folder.listFiles());
        lstFile.setAdapter(adapter);

    }
    //below is not necessary
    private void askWritingPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Log.d("TAG", "askLocationPermission: alertbox here");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            }
        }
    }
}