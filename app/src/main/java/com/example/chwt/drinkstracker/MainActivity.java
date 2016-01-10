package com.example.chwt.drinkstracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateDrinksLog(context);
        addSaveCupListenerToButton(context);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Instructions to save a drink:

    1) find the button
    2) create an onClick method
    3) create a Drink object
    4) Save the drink object

     */

    private void displayShortToast(Context context, String message){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void saveNewDrink(Context context){
        Drink newDrink = new Drink();
        long newDrinkID = newDrink.save(context);
        Long id = Long.valueOf(newDrinkID);
        String successMessage = "your cup was added. ID: " + id.toString();
        displayShortToast(context, successMessage);
    }

    private void updateDrinksLog(Context context){
        final TextView displayDrinksTextBox = (TextView) findViewById(R.id.displayDrinksTextBox);
        displayDrinksTextBox.setText(Drink.getLastDrink(context).toString());
    }

    private void addSaveCupListenerToButton(final Context context){
        final Button addCupButton = (Button) findViewById(R.id.AddCupButton);
        addCupButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v){
                                                saveNewDrink(context);
                                                updateDrinksLog(context);
                                            }
                                        }
        );
    }
}
