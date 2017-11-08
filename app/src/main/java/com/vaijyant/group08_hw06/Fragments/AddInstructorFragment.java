package com.vaijyant.group08_hw06.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.vaijyant.group08_hw06.Models.Instructor;
import com.vaijyant.group08_hw06.R;

import java.io.ByteArrayOutputStream;


public class AddInstructorFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    Bitmap photo;

    private OnFragmentInteractionListener mListener;

    public AddInstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_instructor, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.btnRegister_ai).setOnClickListener(this);
        getView().findViewById(R.id.imgBtnRegister_ai).setOnClickListener(this);
        getView().findViewById(R.id.btnReset_ai).setOnClickListener(this);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister_ai:
                String firstName = ((EditText) getView().findViewById(R.id.editRegFirstName_ai)).getText().toString();
                String lastName = ((EditText) getView().findViewById(R.id.editRegLastName_ai)).getText().toString();
                String email = ((EditText) getView().findViewById(R.id.editEmail_ai)).getText().toString();
                String personalWebsite = ((EditText) getView().findViewById(R.id.editRegPersonalWebsite_ai)).getText().toString();

                byte[] userImage = null;
                if (photo != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    userImage = stream.toByteArray();
                }

                Instructor instructor = new Instructor();
                instructor.setFirstName(firstName);
                instructor.setLastName(lastName);
                instructor.setEmail(email);
                instructor.setPersonalWebsite(personalWebsite);
                instructor.setInstructorImage(userImage);

                mListener.addInstructor(instructor);

                break;

            case R.id.btnReset_ai:
                ((EditText) getView().findViewById(R.id.editRegFirstName_ai)).setText("");
                ((EditText) getView().findViewById(R.id.editRegLastName_ai)).setText("");
                ((EditText) getView().findViewById(R.id.editEmail_ai)).setText("");
                ((EditText) getView().findViewById(R.id.editRegPersonalWebsite_ai)).setText("");
                ((ImageButton) getView().findViewById(R.id.imgBtnRegister_ai)).setImageResource(android.R.drawable.ic_menu_camera);
                photo = null;

                break;

            case R.id.imgBtnRegister_ai:

                imageSelectorAlert();

                break;
        }
    }

    public void imageSelectorAlert() {
        final CharSequence[] items = {  "Take Photo",
                                        "Choose from Library",
                                        "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Intent intent;
                switch (item){
                    case 0:
                        intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST);
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_REQUEST);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;

                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        photo = null;
        if ((requestCode == CAMERA_REQUEST || requestCode == GALLERY_REQUEST) && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            ((ImageButton) getView().findViewById(R.id.imgBtnRegister_ai)).setImageBitmap(photo);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addInstructor(Instructor instructor);
    }
}
