package com.photo.intenttest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		ImageView imageView = findViewById(R.id.image_view);

		Intent intent = getIntent();

		Uri imageUri = intent.getData();

		if (imageUri != null){
			imageView.setImageURI(imageUri);
		}
	}
}
