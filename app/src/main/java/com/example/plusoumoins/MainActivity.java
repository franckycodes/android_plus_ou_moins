package com.example.plusoumoins;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plusoumoins.MyDb;

import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    protected int hiddenNumber;
    protected int totalGuess;
    final int MAX_GUESS=10;
    protected int totalPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hiddenNumber=getRandom(0,100);
        totalGuess=1;
        totalPoints=0;
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        } else */
        if(id==R.id.about_id)
        {
            Intent intent=new Intent(this, AboutAppDialog.class);

            startActivity(intent);
            return true;
        }else if(id==R.id.close_app_id)
        {
            this.finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static int getRandom(int min, int max)
    {
        return (int) Math.ceil(Math.random()*100);
    }
    public void OnClickedGuessBt(View view) throws IOException {
        EditText userGuess;
        TextView resultLbl;


        userGuess= (EditText) findViewById(R.id.user_guess_input_id) ;
        resultLbl=(TextView) findViewById(R.id.result_label_id);


        int userGuessVal= Integer.valueOf(userGuess.getText().toString());

        if(totalGuess<10)
        {
            if(userGuessVal<hiddenNumber)
            {
                resultLbl.setText("PLUS, vie: "+String.valueOf(totalGuess)+"/"+String.valueOf(MAX_GUESS));
            }else if(userGuessVal>hiddenNumber)
            {
                resultLbl.setText("MOINS, vie:"+String.valueOf(totalGuess)+"/"+String.valueOf(MAX_GUESS));
            }else if(userGuessVal==hiddenNumber)
            {

                userGuess.setText("");
                hiddenNumber=getRandom(0,100);
                totalPoints+=MAX_GUESS-totalGuess;
                resultLbl.setText("BRAVO, hitanao ilay isa miafina, vie: "+String.valueOf(totalGuess)+"/"+String.valueOf(MAX_GUESS)+", total points: "+totalPoints);
                //MyDb savePoints=new MyDb("savepoints");

                //savePoints.saveFile(String.valueOf(totalPoints));
                this.save();

                totalGuess=1;
            }
            totalGuess++;
        }else{
            totalPoints+=MAX_GUESS-totalGuess;
            resultLbl.setText("Game OVER, ito ilay isa miafina "+String.valueOf(hiddenNumber)+", mazotoa milalao ihany, total points: "+totalPoints);
            totalGuess=1;
            userGuess.setText("");
            hiddenNumber=getRandom(0,100);
        }


        //resultLbl.setText("You entered "+userGuessVal+" but the hidden number is "+  String.valueOf(hiddenNumber));

   }

    public void save( )
    {
        String myText=String.valueOf(totalPoints);

        FileOutputStream fos=null;
        try {
            fos = openFileOutput("mydatas.rov", MODE_PRIVATE);

            fos.write(myText.getBytes());
            Toast.makeText(this, "Saved points", Toast.LENGTH_LONG).show();
        }catch(IOException e)
        {
            e.printStackTrace();
        }finally{
            try{
                if(fos!=null)
                {
                    fos.close();
                }
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}