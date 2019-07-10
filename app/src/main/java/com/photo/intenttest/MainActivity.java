package com.photo.intenttest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private final int REQUEST_CODE_IMAGE_PICK = 123;
	private final int REQUEST_CODE_PERMISSON = 124;

	private TextView infoText;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		infoText = findViewById(R.id.tv_image_uri);

		infoText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imageUri != null) {
					Intent sendIntent = new Intent(MainActivity.this, PreviewActivity.class);
					sendIntent.setData(imageUri);
					startActivity(sendIntent);
				} else {
					Toast.makeText(MainActivity.this, "no chosen image", Toast.LENGTH_SHORT).show();
				}
			}
		});

		findViewById(R.id.btn_pick_image).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pickerIntent = new Intent(Intent.ACTION_PICK);
				pickerIntent.setType("image/*");
				startActivityForResult(pickerIntent, REQUEST_CODE_IMAGE_PICK);
			}
		});



		findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
					ActivityCompat.requestPermissions(MainActivity.this,
							new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PERMISSON );
					return;
				}

				call();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode,
									int resultCode,
									@Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK
				&& requestCode == REQUEST_CODE_IMAGE_PICK
		  		&& data != null){
			imageUri = data.getData();
			infoText.setText(String.valueOf(imageUri));

		}
	}

	private void call(){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:12312324"));
		startActivity(callIntent);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == REQUEST_CODE_PERMISSON && permissions.length > 0){

			for (int i = 0; i < permissions.length; i++) {
				if (Manifest.permission.CALL_PHONE.equals(permissions[i])
						&& grantResults[i] == PackageManager.PERMISSION_GRANTED){
					call();
				}
			}

		}
	}
}
