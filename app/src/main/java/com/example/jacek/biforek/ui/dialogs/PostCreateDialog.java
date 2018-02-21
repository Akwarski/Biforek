package com.example.jacek.biforek.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jacek on 2018-02-07.
 */

public class PostCreateDialog extends DialogFragment implements View.OnClickListener {
    private Post mPost;
    private ProgressDialog mProgressDialog;
    private View mRootView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mPost = new Post();
        mProgressDialog = new ProgressDialog(getContext());

        mRootView = getActivity().getLayoutInflater().inflate(R.layout.create_post_dialog, null);
        mRootView.findViewById(R.id.Button_Share).setOnClickListener(this);
        builder.setView(mRootView);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_Share:
                sendPost();
                break;
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

                        String NAME = null;
                        NAME = mPost.getUName();

                        String SURNNAME = null;
                        SURNNAME = mPost.getUSurname();

                        mPost.setUser(user);
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
                        mPost.setUName(NAME);
                        mPost.setUSurname(SURNNAME);


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
}
