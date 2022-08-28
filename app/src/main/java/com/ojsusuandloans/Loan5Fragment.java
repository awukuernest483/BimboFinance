package com.ojsusuandloans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;


public class Loan5Fragment extends Fragment {

    private Loan1Fragment.OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (Loan1Fragment.OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }




    private final Uri mImageUri = null;


    private static final int REQUEST_IMAGE_CAPTURE = 111;

    ImageView imageView;
    TextView image ;


    public static Fragment newInstance() {
        return new Loan5Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.loan5, container, false);

        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        view.findViewById(R.id.imageset);


        imageView = view.findViewById(R.id.imageview);

        view.findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);



            }
        });

        image = view.findViewById(R.id.imageset);


        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please take a selfie with your id to continue",Toast.LENGTH_SHORT).show();

                }else{

                mOnButtonClickListener.onButtonClicked(v);}


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
            image.setText("y");
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Loans")
                .child("unapproved")
                .child(requireActivity().getIntent().getStringExtra("username"))
                .child("pictureurl");
        ref.setValue(imageEncoded);

        DatabaseReference ref1 = FirebaseDatabase.getInstance()
                .getReference("Loans")
                .child("unapproved")
                .child(requireActivity().getIntent().getStringExtra("username"))
                .child("username");
        ref1.setValue(requireActivity().getIntent().getStringExtra("username"));

        DatabaseReference ref2 = FirebaseDatabase.getInstance()
                .getReference("Loans")
                .child("unapproved")
                .child(requireActivity().getIntent().getStringExtra("username"))
                .child("amount");
        ref2.setValue("50");

        DatabaseReference ref3 = FirebaseDatabase.getInstance()
                .getReference("Loans")
                .child("unapproved")
                .child(requireActivity().getIntent().getStringExtra("username"))
                .child("timestamp");
        ref3.setValue(ServerValue.TIMESTAMP);


    }

}