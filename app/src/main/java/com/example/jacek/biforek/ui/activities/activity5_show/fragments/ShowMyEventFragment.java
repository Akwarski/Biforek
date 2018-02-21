package com.example.jacek.biforek.ui.activities.activity5_show.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jacek.biforek.models.Post;
import com.example.jacek.biforek.ui.activities.PostActivity;
import com.example.jacek.biforek.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


import com.example.jacek.biforek.R;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;





public class ShowMyEventFragment extends Fragment {

    // DODAJE
    private View mRootVIew;
    private FirebaseRecyclerAdapter<Post, PostHolder> mPostAdapter;//aby post był widoczny
    private RecyclerView mPostRecyclerView;//aby post był widoczny


    public ShowMyEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootVIew = inflater.inflate(R.layout.fragment_show_my_event, container, false);

        init();// Do wyświetlania postów w recycleView

        return  mRootVIew;
    }


    private void init() {
        mPostRecyclerView = mRootVIew.findViewById(R.id.recycler_post2);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUtils.getPostRef();

        setupAdapter();
        mPostRecyclerView.setAdapter(mPostAdapter); //WYŚWIETLANIE
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
                viewHolder.setNumCOmments(String.valueOf(model.getNumComments()));
                viewHolder.setUName(model.getUName());
                viewHolder.setUSurname(model.getUSurname());
                viewHolder.setTIme(DateUtils.getRelativeTimeSpanString(model.getTimeCreated()));
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

    private void onLikeClick(final String postId) {
        FirebaseUtils.getPostLikedRef(postId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
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
        TextView postNumCommentsTextView;


        public PostHolder(View itemView) {
            super(itemView);
            postOwnerUNameTextView = (TextView) itemView.findViewById(R.id.USER_name);
            postOwnerUSurnameTextView = (TextView) itemView.findViewById(R.id.USER_surname);
            postTimeCreatedTextView = (TextView) itemView.findViewById(R.id.TIME);
            postLikeLayout = (LinearLayout) itemView.findViewById(R.id.LIKE_Linear);
            postCommentLayout = (LinearLayout) itemView.findViewById(R.id.COMMENT_Linear);
            postNumLikesTextView = (TextView) itemView.findViewById(R.id.LIKE_counter);
            postNumCommentsTextView = (TextView) itemView.findViewById(R.id.COMMENT_counter);
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

        public void setNumCOmments(String numComments) {
            postNumCommentsTextView.setText(numComments);
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






















/*
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */






    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}*/
