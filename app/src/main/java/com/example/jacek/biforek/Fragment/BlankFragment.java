package com.example.jacek.biforek.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.jacek.biforek.FirebaseUtils;
import com.example.jacek.biforek.Constants;
import com.example.jacek.biforek.Post;
import com.example.jacek.biforek.PostCreateDialog;
import com.example.jacek.biforek.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    private View mRootView;
    private FirebaseRecyclerAdapter<Post, PostHolder> mPostAdapter;
    private RecyclerView mPostRecyclerView;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_blank, container, false);
        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCreateDialog dialog = new PostCreateDialog();
                dialog.show(getFragmentManager(), null);
            }
        });
        init();
        return mRootView;
    }

    private void init() {
        mPostRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_post);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setupAdapter();
        mPostRecyclerView.setAdapter(mPostAdapter);
    }

    private void setupAdapter() {
        mPostAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(
                Post.class,
                R.layout.row_post,
                PostHolder.class,
                FirebaseUtils.getPostRef()
        ) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, final Post model, int position) {
                viewHolder.setNumLikes(String.valueOf(model.getNumLikes()));
                viewHolder.setTIme(DateUtils.getRelativeTimeSpanString(model.getTimeCreated()));
                viewHolder.setUName(model.getUName().getUName());
                viewHolder.setUSurname(model.getUSurname().getUSurname());
                viewHolder.setPostText(model.getPostText());
                viewHolder.setWhereText(model.getWhereText());
                viewHolder.setWhenText(model.getWhenText());
                viewHolder.setWhichText(model.getWhichText());
                viewHolder.setAlkoText(model.getAlkoText());
                viewHolder.setClubText(model.getClubText());

                viewHolder.postLikeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLikeClick(model.getPostId());
                    }
                });

                viewHolder.postCommentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PostActivity.class);
                        intent.putExtra(Constants.EXTRA_POST, model);
                        startActivity(intent);
                    }
                });

            }
        };
    }

    //During the tutorial I think I messed up this code. Make sure your's aligns to this, or just
    //check out the github code
    private void onLikeClick(final String postId) {
        FirebaseUtils.getPostLikedRef(postId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            //User liked
                            FirebaseUtils.getPostRef()
                                    .child(postId)
                                    .child(Constants.NUM_LIKES_KEY)
                                    .runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            long num = (long) mutableData.getValue();
                                            mutableData.setValue(num - 1);
                                            return Transaction.success(mutableData);
                                        }

                                        @Override
                                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                            FirebaseUtils.getPostLikedRef(postId)
                                                    .setValue(null);
                                        }
                                    });
                        } else {
                            FirebaseUtils.getPostRef()
                                    .child(postId)
                                    .child(Constants.NUM_LIKES_KEY)
                                    .runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            long num = (long) mutableData.getValue();
                                            mutableData.setValue(num + 1);
                                            return Transaction.success(mutableData);
                                        }

                                        @Override
                                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                            FirebaseUtils.getPostLikedRef(postId)
                                                    .setValue(true);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        TextView postOwnerUNameTextView;
        TextView postOwnerUSurnameTextView;
        TextView postTimeCreatedTextView;
        TextView postAdditionTextView;
        TextView postWhereTextView;
        TextView postWhenTextView;
        TextView postWhichTextView;
        TextView postAlkoTextView;
        TextView postClubTextView;
        LinearLayout postLikeLayout;
        LinearLayout postCommentLayout;
        TextView postNumLikesTextView;


        public PostHolder(View itemView) {
            super(itemView);
            postOwnerUNameTextView = (TextView) itemView.findViewById(R.id.USER_name);
            postOwnerUSurnameTextView = (TextView) itemView.findViewById(R.id.USER_surname);
            postTimeCreatedTextView = (TextView) itemView.findViewById(R.id.TIME);
            postLikeLayout = (LinearLayout) itemView.findViewById(R.id.LIKE_Linear);
            postCommentLayout = (LinearLayout) itemView.findViewById(R.id.COMMENT_counter);
            postNumLikesTextView = (TextView) itemView.findViewById(R.id.LIKE_counter);
            postAdditionTextView = (TextView) itemView.findViewById(R.id.POST);
            postWhereTextView = (TextView) itemView.findViewById(R.id.GdzieAdd);
            postWhenTextView = (TextView) itemView.findViewById(R.id.KiedyAdd);
            postWhichTextView = (TextView) itemView.findViewById(R.id.KtóraAdd);
            postAlkoTextView = (TextView) itemView.findViewById(R.id.ProwiantAdd);
            postClubTextView = (TextView) itemView.findViewById(R.id.PotemAdd);
        }

        public void setUName(String UName) {
            postOwnerUNameTextView.setText(UName);
        }

        public void setUSurname(String USurname) {
            postOwnerUSurnameTextView.setText(USurname);
        }

        public void setTIme(CharSequence time) {
            postTimeCreatedTextView.setText(time);
        }

        public void setNumLikes(String numLikes) {
            postNumLikesTextView.setText(numLikes);
        }

        public void setPostText(String Addition) {
            postAdditionTextView.setText(Addition);
        }

        public void setWhereText(String Where) {
            postWhereTextView.setText(Where);
        }

        public void setWhenText(String When) {
            postWhenTextView.setText(When);
        }

        public void setWhichText(String Which) {
            postWhichTextView.setText(Which);
        }

        public void setAlkoText(String Alko) {
            postAlkoTextView.setText(Alko);
        }

        public void setClubText(String Club) {
            postClubTextView.setText(Club);
        }

    }
}