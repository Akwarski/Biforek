package com.example.jacek.biforek.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.jacek.biforek.utils.Constants;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.example.jacek.biforek.R;
import com.example.jacek.biforek.models.Post;
import com.example.jacek.biforek.models.User;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Jacek on 2018-02-07.
 */

public class PostCreateDialog extends DialogFragment implements View.OnClickListener {
    private static final int RC_PHOTO_PICKER = 1;
    private Post mPost;
    private ProgressDialog mProgressDialog;
    //private Uri mSelectedUri;
    //private ImageView mPostDisplay;
    private View mRootView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mPost = new Post();
        mProgressDialog = new ProgressDialog(getContext());

        mRootView = getActivity().getLayoutInflater().inflate(R.layout.create_post_dialog, null);
        //mPostDisplay = (ImageView) mRootView.findViewById(R.id.post_dialog_display);
        mRootView.findViewById(R.id.Button_Share).setOnClickListener(this);
        //mRootView.findViewById(R.id.post_dialog_select_imageview).setOnClickListener(this);
        builder.setView(mRootView);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_Share:
                sendPost();
                break;
            /*case R.id.post_dialog_select_imageview:
                selectImage();
                break;*/
        }
    }

    private void sendPost() {
        mProgressDialog.setMessage("Sending post...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        FirebaseUtils.getUserRef(FirebaseUtils.getCurrentUser().getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        final String postId = FirebaseUtils.getUid();
                        TextView postDialogTextView = mRootView.findViewById(R.id.Addition);
                        TextView postWhere = mRootView.findViewById(R.id.Where);
                        TextView postWhen = mRootView.findViewById(R.id.When);
                        TextView postWhich = mRootView.findViewById(R.id.Which);
                        TextView postAlko = mRootView.findViewById(R.id.Alko);
                        TextView postClub = mRootView.findViewById(R.id.Club);

                        String text = postDialogTextView.getText().toString();
                        String Where = postWhere.getText().toString();
                        String When = postWhen.getText().toString();
                        String Which = postWhich.getText().toString();
                        String Alko = postAlko.getText().toString();
                        String Club = postClub.getText().toString();

                        mPost.setUser(user);
                        //mPost.setUName();
                        //mPost.setUSurname();
                        mPost.setNumComments(0);
                        mPost.setNumLikes(0);
                        mPost.setTimeCreated(System.currentTimeMillis());
                        mPost.setPostId(postId);
                        mPost.setPostText(text);
                        mPost.setWhereText(Where);
                        mPost.setWhenText(When);
                        mPost.setWhichText(Which);
                        mPost.setAlkoText(Alko);
                        mPost.setClubText(Club);

                        /*if (mSelectedUri != null) {
                            FirebaseUtils.getImageSRef()
                                    .child(mSelectedUri.getLastPathSegment())
                                    .putFile(mSelectedUri)
                                    .addOnSuccessListener(getActivity(),
                                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    String url = Constants.POST_IMAGES + "/" + mSelectedUri.getLastPathSegment();
                                                    mPost.setPostImageUrl(url);
                                                    addToMyPostList(postId);
                                                }
                                            });
                        } else {
                            addToMyPostList(postId);
                        }*/

                        addToMyPostList(postId);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mProgressDialog.dismiss();
                    }
                });
    }

    private void addToMyPostList(String postId) {
        FirebaseUtils.getPostRef().child(postId)
                .setValue(mPost);
        FirebaseUtils.getMyPostRef().child(postId).setValue(true)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressDialog.dismiss();
                        dismiss();
                    }
                });

        FirebaseUtils.addToMyRecord(Constants.POST_KEY, postId);
    }

    /*private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }*/

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                //mSelectedUri = data.getData();
                //mPostDisplay.setImageURI(mSelectedUri);
            }
        }
    }*/
}
