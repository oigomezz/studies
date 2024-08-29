package com.e.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    private static final int DIALOG_ABOUT_ID = 2;
    private static final int DIALOG_WAIT_ID = 3;
    private static final int DIALOG_JOIN_ID = 4;

    // Represents the internal state of the game
    private TicTacToeGame mGame;
    private boolean mGameOver = false;

    private int mHumanWins = 0;
    private int mComputerWins = 0;
    private int mTies = 0;
    private char mGoFirst = TicTacToeGame.HUMAN_PLAYER;

    private boolean mSoundOn = true;
    private MediaPlayer mTieMediaPlayer;
    private MediaPlayer mWinMediaPlayer;
    private MediaPlayer mLoseMediaPlayer;
    // Buttons making up the board
    private BoardView mBoardView;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mHumanScoreTextView;
    private TextView mComputerScoreTextView;
    private TextView mTieScoreTextView;
    private SharedPreferences mPrefs;

    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;

    // Listen for touches on the board


    private String idPhone;
    private String idGame;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseGame;
    private Dialog mDialog;
    private ChildEventListener mFirebaseListener;
    private ArrayList<String> games;
    private Boolean onlineGame;
    private Boolean isJoin;

    private boolean gameover;
    private boolean humanstart;
    private boolean humanturn;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;
            //Log.i("hola","Entra"+pos+humanturn);
            if (humanturn) {
                if (!gameover && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
                    // If no winner yet, let the computer make a move
                    //setMove(TicTacToeGame.HUMAN_PLAYER, pos);
                    // If no winner yet, let the computer make a move
                    if(mSoundOn){mHumanMediaPlayer.start();}
                    if(onlineGame) {
                        mDatabaseGame.child(String.valueOf(isJoin)).setValue(pos);
                    }
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        humanturn = false;
                        if(onlineGame){
                            mInfoTextView.setText(R.string.turn_computer);
                        }else{
                            mInfoTextView.setText(R.string.turn_computer);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    int move = mGame.getComputerMove();
                                    moveComputer(move);
                                }
                            }, 700);
                        }

                        //Log.d("check", "winner:"+winner);
                    } else if (winner == 1) {
                        if(mSoundOn){mTieMediaPlayer.start();}
                        mTies++;
                        displayScores();
                        updateTextViews(1, R.string.result_tie, Color.rgb(0, 0, 200));
                    } else if (winner == 2) {
                        if(mSoundOn){mWinMediaPlayer.start();}
                        mHumanWins++;
                        displayScores();
                        setwinner(mGame.getWinline(), TicTacToeGame.HUMAN_PLAYER_WIN);
                        updateTextViews(0, R.string.result_human_wins, Color.rgb(68, 191, 135));
                        String defaultMessage = getResources().getString(R.string.result_human_wins);
                        mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                    } else {
                        if(mSoundOn){mLoseMediaPlayer.start();}
                        setwinner(mGame.getWinline(), TicTacToeGame.COMPUTER_PLAYER_WIN);
                        updateTextViews(2, R.string.result_computer_wins, Color.rgb(163, 68, 191));
                    }
                }
            }
// So we aren&#39;t notified of continued events when finger is moved
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();//yolo
        setContentView(R.layout.activity_android_tic_tac_toe);
        idPhone = UUID.randomUUID().toString();//yolo

        mGame = new TicTacToeGame();
        // Restore the scores from the persistent preference data source
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level",
                getResources().getString(R.string.difficulty_harder));
        if (difficultyLevel.equals(getResources().getString(
                R.string.difficulty_easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(
                R.string.difficulty_harder)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

        mHumanWins = mPrefs.getInt("mHumanWins", 0);
        mComputerWins = mPrefs.getInt("mComputerWins", 0);
        mTies = mPrefs.getInt("mTies", 0);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanScoreTextView = (TextView) findViewById(R.id.player_score);
        mComputerScoreTextView = (TextView) findViewById(R.id.computer_score);
        mTieScoreTextView = (TextView) findViewById(R.id.tie_score);

        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setColor(mPrefs.getInt("board_color", 0xFFCCCCCC));
        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);
        mBoardView.setGame(mGame);

        if (savedInstanceState == null) {
            startNewGame();
        } else {
            // Restore the game's state
            mGame.setBoardState(savedInstanceState.getCharArray("board"));
            mGameOver = savedInstanceState.getBoolean("mGameOver");
            mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
            mHumanWins = savedInstanceState.getInt("mHumanWins");
            mComputerWins = savedInstanceState.getInt("mComputerWins");
            mTies = savedInstanceState.getInt("mTies");
            mGoFirst = savedInstanceState.getChar("mGoFirst");

            if (!mGameOver) {
                mInfoTextView
                        .setText(mGoFirst == TicTacToeGame.COMPUTER_PLAYER ? R.string.turn_computer
                                : R.string.turn_human);
                mBoardView.invalidate();
            }

        }
        onlineGame =false;
        displayScores();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putInt("mHumanWins", Integer.valueOf(mHumanWins));
        outState.putInt("mComputerWins", Integer.valueOf(mComputerWins));
        outState.putInt("mTies", Integer.valueOf(mTies));
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putChar("mGoFirst", mGoFirst);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
        mHumanWins = savedInstanceState.getInt("mHumanWins");
        mComputerWins = savedInstanceState.getInt("mComputerWins");
        mTies = savedInstanceState.getInt("mTies");
        mGoFirst = savedInstanceState.getChar("mGoFirst");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(),
                R.raw.human);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(),
                R.raw.pc);
        mTieMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.tie);
        mWinMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.win);
        mLoseMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.lose);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("mHumanWins", mHumanWins);
        ed.putInt("mComputerWins", mComputerWins);
        ed.putInt("mTies", mTies);
        ed.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CANCELED) {
            mSoundOn = mPrefs.getBoolean("sound", true);
            String difficultyLevel = mPrefs.getString("difficulty_level",
                    getResources().getString(R.string.difficulty_harder));
            if (difficultyLevel.equals(getResources().getString(
                    R.string.difficulty_easy))) {
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);

            } else if (difficultyLevel.equals(getResources().getString(
                    R.string.difficulty_harder))) {
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            } else {
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
            }
            mBoardView.setColor(mPrefs.getInt("board_color", 0xFFCCCCCC));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_tic_tac_toe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.action_new_game:
                onlineGame = false;
                startNewGame();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;
            case R.id.action_creategame:
                this.createonlinegame();
                return true;
            case R.id.action_joingame:
                this.joinonlinegame();
                return true;
            case R.id.about_menu:
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View aboutView;
                aboutView = inflater.inflate(R.layout.about_tic_tac_toe, null);
                builder.setView(aboutView);
                builder.setPositiveButton("OK", null);
                builder.create().show();
                return true;
            case R.id.reset:
                mHumanWins = 0;
                mComputerWins = 0;
                mTies = 0;
                displayScores();
                return true;
        }
        return false;
    }
    public void createonlinegame(){
        idGame = idPhone;
        mDatabase.child("games").child(idGame).removeValue();
        mDatabase.child("games").child(idGame).child("wait").setValue(true);

        mDatabaseGame = FirebaseDatabase.getInstance().getReference().child("games").child(idGame);
        mFirebaseListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value =String.valueOf(dataSnapshot.getValue());
                Log.i("change0", String.valueOf(dataSnapshot));
                Log.i("change",key+"  " +value);

                if(key.equals("wait")){
                    if (value.equals("false")){
                        newOnlineGame(false);

                    }
                }else if(key.equals(String.valueOf(!isJoin))){
                    moveComputer(Integer.parseInt(value));
                    humanturn = true;
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        mDatabaseGame.addChildEventListener(mFirebaseListener);

        mDialog=createdDialog(DIALOG_WAIT_ID);
        mDialog.show();
    }
    // Set up the game board.
    private void startNewGame() {
        gameover = false;
        humanstart = !humanstart;


        mGame.clearBoard();
        mBoardView.invalidate();

        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
        if (!humanstart) {
            mInfoTextView.setTypeface(null, Typeface.NORMAL);
            mInfoTextView.setText(R.string.turn_computer);
            humanturn = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    if(mSoundOn){mComputerMediaPlayer.start();}
                    mInfoTextView.setText(R.string.turn_human);
                    mBoardView.invalidate();
                    humanturn = true;

                }
            }, 700);
        }else {
            humanturn = true;
            // Human goes first
            mInfoTextView.setText("Tu primero.");
            mInfoTextView.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void displayScores() {
        mHumanScoreTextView.setText(Integer.toString(mHumanWins));
        mComputerScoreTextView.setText(Integer.toString(mComputerWins));
        mTieScoreTextView.setText(Integer.toString(mTies));

    }

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate(); // Redraw the board
            return true;
        }
        return false;
    }




    protected Dialog createdDialog(int id) {
        Dialog dialog = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_WAIT_ID:
                builder.setMessage("Esperando a un Jugador "+"\nID: "+idGame)
                        .setCancelable(false)
                        .setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mDatabase.child("games").child(idGame).removeValue();
                            }
                        });
                dialog = builder.create();
                break;
            case DIALOG_JOIN_ID:


                builder.setTitle("Escoje una partida");
                final CharSequence[] nGames = games.toArray(new String[games.size()]);
                builder.setItems(nGames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idGame = games.get(which);
                        Log.i("Game",idGame);
                        newOnlineGame(true);
                    }
                });
                dialog = builder.create();

                break;
        }
        return dialog;
    }
    private void newOnlineGame(boolean join){
        onlineGame = true;
        if (join) {
            mDatabaseGame = FirebaseDatabase.getInstance().getReference().child("games").child(idGame);
            mDatabaseGame.child("wait").setValue(false);
            mDatabaseGame.child(String.valueOf(true)).setValue(-1);
            mDatabaseGame.child(String.valueOf(false)).setValue(-1);
            mFirebaseListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String key = dataSnapshot.getKey();
                    String value =String.valueOf(dataSnapshot.getValue());
                    Log.i("change0", String.valueOf(dataSnapshot));
                    Log.i("change",key+"  " +value);

                    if(key.equals("wait")){
                        if (value.equals("false")){
                            newOnlineGame(false);

                        }
                    }else if(key.equals(String.valueOf(!isJoin))){
                        moveComputer(Integer.parseInt(value));
                        humanturn = true;
                    }

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            };

            mDatabaseGame.addChildEventListener(mFirebaseListener);
        }else{
            mDialog.dismiss();
        }

        gameover = false;
        humanstart = !join;
        isJoin = join;


        mGame.clearBoard();
        mBoardView.invalidate();


        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
        if (!humanstart) {
            mInfoTextView.setTypeface(null, Typeface.NORMAL);
            mInfoTextView.setText("Turno de jugador 2");
            humanturn = false;
        }else {
            humanturn = true;
            // Human goes first
            mInfoTextView.setText("Tu primero");
            mInfoTextView.setTypeface(null, Typeface.NORMAL);
        }
    }
    private void moveComputer(int move){
        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        if(mSoundOn){mComputerMediaPlayer.start();}
        mInfoTextView.setText(R.string.turn_human);
        mBoardView.invalidate();
        int winner = mGame.checkForWinner();
        if (winner == 0) {
            mInfoTextView.setText(R.string.turn_human);
            mInfoTextView.setTypeface(null, Typeface.NORMAL);
            humanturn = true;
        } else if (winner == 1) {
            if(mSoundOn){mTieMediaPlayer.start();}
            updateTextViews(1, R.string.result_tie, Color.rgb(0, 0, 200));
        } else if (winner == 2) {
            if(mSoundOn){mWinMediaPlayer.start();}
            setwinner(mGame.getWinline(), TicTacToeGame.HUMAN_PLAYER_WIN);
            updateTextViews(0, R.string.result_human_wins, Color.rgb(68, 191, 135));
            String defaultMessage = getResources().getString(R.string.result_human_wins);
            mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
        } else {
            if(mSoundOn){mLoseMediaPlayer.start();}
            mComputerWins++;
            displayScores();
            setwinner(mGame.getWinline(), TicTacToeGame.COMPUTER_PLAYER_WIN);
            updateTextViews(2, R.string.result_computer_wins, Color.rgb(163, 68, 191));
        }
    }
    private void setwinner(int line,char winner) {
        switch (line) {
            case 1:
                setline(0, 1, 2, winner);
                break;
            case 2:
                setline(3, 4, 5, winner);
                break;
            case 3:
                setline(6, 7, 8, winner);
                break;
            case 4:
                setline(0, 3, 6, winner);
                break;
            case 5:
                setline(1, 4, 7, winner);
                break;
            case 6:
                setline(2, 5, 8, winner);
                break;
            case 7:
                setline(0, 4, 8, winner);
                break;
            case 8:
                setline(2, 4, 6, winner);
                break;
        }
    }
    private void setline(int a,int b,int c,char winner){
        setMove(winner,a);
        setMove(winner,b);
        setMove(winner,c);
    }
    private void updateTextViews(int winner,int result,int color){


        mInfoTextView.setText(result);
        mInfoTextView.setTypeface(null, Typeface.BOLD);
        mInfoTextView.setTextColor(color);

        gameover = true;
        if (mDatabaseGame != null) {
            mDatabaseGame.removeEventListener(mFirebaseListener);
        }

    }
    public void joinonlinegame(){
        FirebaseDatabase.getInstance().getReference().child("games").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        games = new ArrayList<String>();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                            if (String.valueOf(dsp.child("wait").getValue()).equals("true")){
                                games.add(String.valueOf(dsp.getKey())); //add result into array list
                            }
                        }
                        createdDialog(DIALOG_JOIN_ID).show();

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
