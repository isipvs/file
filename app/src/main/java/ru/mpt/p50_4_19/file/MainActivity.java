package ru.mpt.p50_4_19.file;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final  int REQUEST_CODE_READ_FILES= 1;
    private static boolean READ_FILE_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int hasReadContactPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_FILE_GRANTED = true;
            getFiles();
        }

        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_FILES ); // Изменить READ_CONTACNTS на FILE
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_READ_FILES)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                READ_FILE_GRANTED = true;
            }
        }

        if(READ_FILE_GRANTED)
        {
            getFiles();
        }
        else{
            Toast.makeText(this, "Требутся установить разарешение", Toast.LENGTH_LONG).show();
        }
    }

    private  void getFiles()
    {
        File dataDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);


        if (dataDirectory.exists() && dataDirectory.isDirectory() ) {

            ArrayAdapter<File> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataDirectory.listFiles());

            ListView listView = findViewById(R.id.listView);

            listView.setAdapter(adapter);
        }

        else{
            Toast.makeText(this, "Папка" + dataDirectory + " отсутствует", Toast.LENGTH_LONG).show();
        }


    }
}