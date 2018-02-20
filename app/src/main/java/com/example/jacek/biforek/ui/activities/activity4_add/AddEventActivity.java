package com.example.jacek.biforek.ui.activities.activity4_add;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.activities.PostActivity;
import com.example.jacek.biforek.ui.dialogs.PostCreateDialog;
import com.example.jacek.biforek.models.Post;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.jacek.biforek.utils.Constants;

public class AddEventActivity extends Fragment {


//Zmienne prywatne
    protected EditText where, when, which, alko, club, addition;
    protected ProgressBar progressBar;
    protected StorageReference storageReference;
    protected Button ShareButton;

    //DODANE
    private View mRootVIew;
    private FirebaseRecyclerAdapter<Post, PostHolder> mPostAdapter;//aby post był widoczny
    private RecyclerView mPostRecyclerView;//aby post był widoczny

    public AddEventActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootVIew = inflater.inflate(R.layout.fragment_blank, container, false);


        //aby post był widoczny
        //init();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button ShareButton = (Button) mRootVIew.findViewById(R.id.Button_Share);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCreateDialog dialog = new PostCreateDialog();
                dialog.show(getFragmentManager(), null);
            }
        });


        return mRootVIew;
    }

    public View ret(){
        return mRootVIew;
    }

    private void init() {
        mPostRecyclerView = (RecyclerView) mRootVIew.findViewById(R.id.recyclerview_post);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Dodaje aby post był widoczny
        FirebaseUtils.getPostRef();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        setupAdapter();
        mPostRecyclerView.setAdapter(mPostAdapter);
    }

    //aby post był widoczny
    private void setupAdapter() {
        mPostAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(
                Post.class,
                R.layout.row_post,
                PostHolder.class,

                //Dodaje aby post był widoczny
                FirebaseUtils.getPostRef()
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ) {
            @Override
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Połączone z PostHolder (na dole)
            protected void populateViewHolder(PostHolder viewHolder, final Post model, int position) {
                viewHolder.setNumCOmments(String.valueOf(model.getNumComments()));
                viewHolder.setNumLikes(String.valueOf(model.getNumLikes()));  //Do wyświetlenia lików
                viewHolder.setTIme(DateUtils.getRelativeTimeSpanString(model.getTimeCreated()));
                viewHolder.setPostText(model.getPostText());
                viewHolder.setUName(model.getUName().getUName());
                viewHolder.setUSurname(model.getUSurname().getUSurname());
                viewHolder.setPostText(model.getPostText());
                viewHolder.setWhereText(model.getWhereText());
                viewHolder.setWhenText(model.getWhenText());
                viewHolder.setWhichText(model.getWhichText());
                viewHolder.setAlkoText(model.getAlkoText());
                viewHolder.setClubText(model.getClubText());


                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Do aktualizacji ilości likeów
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //During the tutorial I think I messed up this code. Make sure your's aligns to this, or just
    //check out the github code
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Do dodawania lub odejmowania likeów
    private void onLikeClick(final String postId) {
        FirebaseUtils.getPostLikedRef(postId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        // Odejmowanie likeów
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
                        }

                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //Z neta dodawanie lików
                        else {
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

    //aby post był widoczny
    public static class PostHolder extends RecyclerView.ViewHolder {
        ImageView postOwnerDisplayImageView;
        TextView postOwnerUNameTextView;
        TextView postOwnerUSurnameTextView;
        TextView postTimeCreatedTextView;
        ImageView postDisplayImageVIew; //Do wyświetlania Postów z likeami
        TextView postTextTextView;
        LinearLayout postLikeLayout;
        LinearLayout postCommentLayout;
        TextView postNumLikesTextView;  //Do zloczania lików
        TextView postNumCommentsTextView;
        TextView postAdditionTextView;
        TextView postWhereTextView;
        TextView postWhenTextView;
        TextView postWhichTextView;
        TextView postAlkoTextView;
        TextView postClubTextView;

        //Dodaje like w celu działania:
        //ImageView postLikeIV;


        public PostHolder(View itemView) {
            super(itemView);
            //postOwnerDisplayImageView = (ImageView) itemView.findViewById(R.id.iv_post_owner_display); //Zdjęcie w kółeczku
            postOwnerUNameTextView = (TextView) itemView.findViewById(R.id.USER_name);
            postOwnerUSurnameTextView = (TextView) itemView.findViewById(R.id.USER_surname);
            postTimeCreatedTextView = (TextView) itemView.findViewById(R.id.TIME);
            //postDisplayImageVIew = (ImageView) itemView.findViewById(R.id.iv_post_display); //Do wyświetlania postów z likeami //aktualizacja ZDJĘCIE
            postLikeLayout = (LinearLayout) itemView.findViewById(R.id.LIKE_Linear); // Do wyświetlenia likeów zamiast postLikeIV
            postCommentLayout = (LinearLayout) itemView.findViewById(R.id.COMMENT_counter);
            postNumLikesTextView = (TextView) itemView.findViewById(R.id.LIKE_counter); // Do zliczania lików
            postNumCommentsTextView = (TextView) itemView.findViewById(R.id.COMMENT_counter);
            //postTextTextView = (TextView) itemView.findViewById(R.id.tv_post_text); // Tylko jeden tekst główny... OPIS
            postAdditionTextView = (TextView) itemView.findViewById(R.id.POST);
            postWhereTextView = (TextView) itemView.findViewById(R.id.GdzieAdd);
            postWhenTextView = (TextView) itemView.findViewById(R.id.KiedyAdd);
            postWhichTextView = (TextView) itemView.findViewById(R.id.KtóraAdd);
            postAlkoTextView = (TextView) itemView.findViewById(R.id.ProwiantAdd);
            postClubTextView = (TextView) itemView.findViewById(R.id.PotemAdd);
        }

            //Dodaje like w celu działania:
            //postLikeIV = (ImageView) itemView.findViewById(R.id.iv_like);


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

        //Dodaje do wyświetlenia postów:
        //public void setPostImage(String url){

        //}

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
















    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        where = (EditText) findViewById(R.id.Where);
        when = (EditText) findViewById(R.id.When);
        which = (EditText) findViewById(R.id.Which);
        alko = (EditText) findViewById(R.id.Alko);
        club = (EditText) findViewById(R.id.Club);
        addition = (EditText) findViewById(R.id.Addition);

        progressBar = (ProgressBar) findViewById(R.id.Progres);
        storageReference = FirebaseStorage.getInstance().getReference();



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Dodaje
        mRootVIew = inflater.inflate(R.layout.fragment_home, container, false);

        ShareButton = (Button) mRootVIew.findViewById(R.id.Button_Share);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostCreateDialog dialog = new PostCreateDialog();
                dialog.show(getFragmentManager(), null);
            }
        });

    }


    //public void ShareButton(View view){
    //    String Where = where.getText().toString().trim();
    //    String When = when.getText().toString().trim();
    //    String Which = which.getText().toString().trim();
    //    String Alko = alko.getText().toString().trim();
    //   String Club = club.getText().toString().trim();
    //}


    //wprowadzanie info na serwer

    */

}
