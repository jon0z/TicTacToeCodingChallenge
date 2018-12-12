package com.example.jonas.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private int moveCount = 0;
    private TextView statusTxtView;
    private Board mBoard;
    private TableLayout boardLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create board object
        mBoard = new Board();
        Log.d(TAG, "new board created with #rows = " +mBoard.getNumRows() + " #cols = " +mBoard.getNumCols());

        // create board layout
        boardLayout = findViewById(R.id.board_layout);
        createTableLayout(mBoard.getNumRows(), mBoard.getNumCols(), boardLayout);

        // reset button
        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        // Player turn status
        statusTxtView = findViewById(R.id.player_turn_txt);
        statusTxtView.setText(mBoard.getCurrentPlayerSymbol());

    }

    @Override
    public void onClick(View v) {

        if(!v.getTag().toString().equals("reset")){
            // get the indices for button clicked
            String tag = v.getTag().toString();
            String[] indices = tag.trim().split("");
            int index_i = Integer.valueOf(indices[2]);
            int index_j = Integer.valueOf(indices[3]);

            // set space with current player
            Button btn = v.findViewWithTag(v.getTag());
            if(!btn.getText().equals("")){
                Toast.makeText(this, "Choose another cell", Toast.LENGTH_SHORT).show();
            }else{
                // check if move is possible
                if(mBoard.play(index_i, index_j)){
                    // increment move counter
                    moveCount++;
                    // set current player move
                    btn.setText(mBoard.getCurrentPlayerSymbol());
                } else{
                    Log.e(TAG, "Space on board already taken");
                }

                // check for win
                if(moveCount >= 7){
                    if(mBoard.checkWin()){
                        // display message alert
                        winAlertDialog(false);
                    }

                }

                // check for a tie
                if(moveCount == (mBoard.getNumCols() * mBoard.getNumRows()) - 1 && !mBoard.didGameEnd()){
                    // display message alert
                    winAlertDialog(true);
                    resetBoard();
                }

                // change current player turn
                mBoard.changeCurrentPlayer();
                // update player turn display
                statusTxtView.setText(mBoard.getCurrentPlayerSymbol());
            }

        } else{
            resetBoard();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setting_menu:
                // display alert dialog to change grid size
                settingsAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //=============================== Helper Methods ========================================
    private void clearTableLayout(){
        for(int i=0; i<boardLayout.getChildCount(); i++){
            TableRow childRow = (TableRow) boardLayout.getChildAt(i);
            for(int j=0; j<childRow.getChildCount(); j++){
                Button btn = (Button) childRow.getChildAt(j);
                btn.setText("");
            }
        }
    }

    private void createTableLayout(int rows, int cols, TableLayout tableLayout){
        for(int i=0; i < rows; i++){
            // create each table row
            TableRow tRow = new TableRow(this);
            tRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // create columns (buttons)
            for(int j = 0; j < cols; j++){
                Button btn = new Button(this);
                btn.setTag("b"+i+j);
                btn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tRow.addView(btn);

                btn.setOnClickListener(this);
            }

            tableLayout.addView(tRow);
        }

        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
    }

    private void resetBoard(){
        // clear tableLayout
        clearTableLayout();
        // clear board and start new game
        mBoard.newGame();
        // restart move counter
        moveCount = 0;
        // update player turn display
        statusTxtView.setText(mBoard.getCurrentPlayerSymbol());
    }

    private void winAlertDialog(boolean isTie){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!isTie){
            builder.setMessage("Player " +mBoard.getCurrentPlayerSymbol()+ " Wins!!");
        } else {
            builder.setMessage("Game Ended in Tie!!");
        }

        builder.setPositiveButton("Start New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetBoard();
                    }
                })
                .create()
                .show();
    }

    private void settingsAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.settings_menu, null);
        builder.setView(dialogView)
                .setMessage("Change Board Size")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get new rows and cols entered by user
                        EditText customRows = dialogView.findViewById(R.id.custom_rows);
                        EditText customCols = dialogView.findViewById(R.id.custom_cols);

                        int rows = Integer.valueOf(customRows.getText().toString());
                        int cols = Integer.valueOf(customCols.getText().toString());

                        // update board and table layout with new rows and cols
                        if(rows > 3 && cols > 3 && rows == cols){
                            // destroy board and create new board with new rows and cols
                            mBoard = null;
                            boardLayout.removeAllViewsInLayout();

                            mBoard = new Board(rows, cols);
                            createTableLayout(rows, cols, boardLayout);
                        } else{
                            Toast.makeText(MainActivity.this, "Rows Must Equals Columns and be greater than 3. Try again.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}
