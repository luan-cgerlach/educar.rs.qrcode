package com.example.schoolaccesscode.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startScanner();
        }
    }

    private void startScanner() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("qr_code_result", result.getText());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanner();
            } else {
                Toast.makeText(this, "Camera permission is required to scan QR code", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
