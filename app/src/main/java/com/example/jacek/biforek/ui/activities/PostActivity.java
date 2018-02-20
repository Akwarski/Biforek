package com.example.jacek.biforek.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jacek.biforek.R;
import com.example.jacek.biforek.models.Comment;
import com.example.jacek.biforek.models.Post;
import com.example.jacek.biforek.models.User;
import com.example.jacek.biforek.utils.Constants;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BUNDLE_COMMENT = "comment";
    private Post mPost;
    private EditText mCommentEditTextView;
    private Comment mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if (savedInstanceState != null) {
            mComment = (Comment) savedInstanceState.getSerializable(BUNDLE_COMMENT);
        }

        Intent intent = getIntent();
        mPost = (Post) intent.getSerializableExtra(Constants.EXTRA_POST);

        init();
        initPost();
        initCommentSection();
    }

    private void initCommentSection() {
        RecyclerView commentRecyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(PostActivity.this));

        FirebaseRecyclerAdapter<Comment, CommentHolder> commentAdapter = new FirebaseRecyclerAdapter<Comment, CommentHolder>(
                Comment.class,
                R.layout.row_comment,
                CommentHolder.class,
                FirebaseUtils.getCommentRef(mPost.getPostId())
        ) {
            @Override
            protected void populateViewHolder(CommentHolder viewHolder, Comment model, int position) {
                viewHolder.setUserNAME(model.getUName().getUName());
                viewHolder.setUserSURNAME(model.getUSurname().getUSurname());
                viewHolder.setComment(model.getComment());
                viewHolder.setTime(DateUtils.getRelativeTimeSpanString(model.getTimeCreated()));

                /*Glide.with(PostActivity.this)
                        .load(model.getUser().getPhotoUrl())
                        .into(viewHolder.commentOwnerDisplay);
                */
            }
        };

        commentRecyclerView.setAdapter(commentAdapter);
    }

    private void initPost() {
        //ImageView postOwnerDisplayImageView = (ImageView) findViewById(R.id.iv_post_owner_display);
        TextView postOwnerUserNameTextView = (TextView) findViewById(R.id.USER_name);
        TextView postOwnerUserSurnameTextView = (TextView) findViewById(R.id.USER_surname);
        TextView postTimeCreatedTextView = (TextView) findViewById(R.id.TIME);
        //ImageView postDisplayImageView = (ImageView) findViewById(R.id.iv_post_display);
        LinearLayout postLikeLayout = (LinearLayout) findViewById(R.id.LIKE_Linear);
        LinearLayout postCommentLayout = (LinearLayout) findViewById(R.id.COMMENT_Linear);
        TextView postNumLikesTextView = (TextView) findViewById(R.id.LIKE_counter);
        TextView postNumCommentsTextView = (TextView) findViewById(R.id.COMMENT_counter);
        TextView postTextTextView = (TextView) findViewById(R.id.POST);

        postOwnerUserNameTextView.setText(mPost.getUName().getUName());
        postOwnerUserSurnameTextView.setText(mPost.getUSurname().getUSurname());
        postTimeCreatedTextView.setText(DateUtils.getRelativeTimeSpanString(mPost.getTimeCreated()));
        postTextTextView.setText(mPost.getPostText());
        postNumLikesTextView.setText(String.valueOf(mPost.getNumLikes()));
        postNumCommentsTextView.setText(String.valueOf(mPost.getNumComments()));

        /*Glide.with(PostActivity.this)
                .load(mPost.getUser().getPhotoUrl())
                .into(postOwnerDisplayImageView);

        if (mPost.getPostImageUrl() != null) {
            postDisplayImageView.setVisibility(View.VISIBLE);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(mPost.getPostImageUrl());

            Glide.with(PostActivity.this)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(postDisplayImageView);
        } else {
            postDisplayImageView.setImageBitmap(null);
            postDisplayImageView.setVisibility(View.GONE);
        }*/
    }

    private void init() {
        mCommentEditTextView = (EditText) findViewById(R.id.add_comment); // activity_post dodawanie komentarza
        findViewById(R.id.comment_send).setOnClickListener(this);         // activity_post wysłanie komentarza
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_send:
                sendComment();
        }
    }

    private void sendComment() {
        final ProgressDialog progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setMessage("Sending comment..");
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        mComment = new Comment();
        final String uid = FirebaseUtils.getUid();
        final String strComment = mCommentEditTextView.getText().toString();

        mComment.setTimeCreated(System.currentTimeMillis());
        FirebaseUtils.getUserRef(FirebaseUtils.getCurrentUser().getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        mComment.setUser(user);
                        mComment.setCommentId(uid);
                        mComment.setComment(strComment);
                        mComment.setTimeCreated(System.currentTimeMillis());



                        FirebaseUtils.getCommentRef(mPost.getPostId())
                                .child(uid)
                                .setValue(mComment);

                        FirebaseUtils.getPostRef().child(mPost.getPostId())
                                .child(Constants.NUM_COMMENTS_KEY) // constants.java to jest stała
                                .runTransaction(new Transaction.Handler() {
                                    @Override
                                    public Transaction.Result doTransaction(MutableData mutableData) {
                                        long num = (long) mutableData.getValue();
                                        mutableData.setValue(num + 1);
                                        return Transaction.success(mutableData);
                                    }

                                    @Override
                                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                        progressDialog.dismiss();
                                        FirebaseUtils.addToMyRecord(Constants.COMMENTS_KEY, uid);
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
    }


    public static class CommentHolder extends RecyclerView.ViewHolder {
        //ImageView commentOwnerDisplay;
        TextView usernameTextView;
        TextView usersurnameTextView;
        TextView timeTextView;
        TextView commentTextView;

        public CommentHolder(View itemView) {
            super(itemView);
            //commentOwnerDisplay = (ImageView) itemView.findViewById(R.id.iv_comment_owner_display);
            usernameTextView = (TextView) itemView.findViewById(R.id.USER_name_comment);
            usersurnameTextView = (TextView) itemView.findViewById(R.id.USER_surname_comment);
            timeTextView = (TextView) itemView.findViewById(R.id.TIME_comment);
            commentTextView = (TextView) itemView.findViewById(R.id.text_comment);
        }

        public void setUserNAME(String username) {
            usernameTextView.setText(username);
        }

        public void setUserSURNAME(String usersurname) {
            usersurnameTextView.setText(usersurname);
        }

        public void setTime(CharSequence time) {
            timeTextView.setText(time);
        }

        public void setComment(String comment) {
            commentTextView.setText(comment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_COMMENT, mComment);
        super.onSaveInstanceState(outState);
    }
}
