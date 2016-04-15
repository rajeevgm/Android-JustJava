package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 2;
    int cost = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        CheckBox whippedCream = (CheckBox) findViewById(
                R.id.whippedCream_check_box);
        boolean hasWhippedCream = whippedCream.isChecked();
        /**
         *  Log.v("MainActivity","SubmitOrder - hasWhippedCreame : " + hasWhippedCream);
         */

        CheckBox chocolate = (CheckBox) findViewById(
                R.id.chocolate_check_box);
        boolean hasChocolate = chocolate.isChecked();
        /**
         *  Log.v("MainActivity","SubmitOrder - hasChocolate : " + hasChocolate);
         */

        EditText nameEditText = (EditText) findViewById(
                R.id.name_edit_text);
        String name = nameEditText.getText().toString();
        /**
         * Log.v("MainActivity","Name : " + name);
        */

        /** display(quantity);
         displayPrice(quantity * 5);
         */
        int price = calculatePrice(hasChocolate,hasWhippedCream);
        /**
         * Log.v("MainActivity","SubmitOrder - Total Price : " + price);
         */



        String priceMessage = createOrderSummary(price,hasWhippedCream,hasChocolate,name);
//        displayMessage(priceMessage);
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "test@test.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }

//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setType("*/*");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, "test@test.com");
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//        emailIntent.putExtra(Intent.EXTRA_STREAM, attachment);
//        if (emailIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(emailIntent);
//        }

    }

    /**
     * Order Summary
     */
    public String createOrderSummary(int price,boolean hasWhippedCream,boolean hasChocolate,String name) {
        String summaryMessage = "Name : " + name;
        summaryMessage = summaryMessage + "\n" + "Add " + getString(R.string.whipped_cream) +"? " + hasWhippedCream;
        summaryMessage = summaryMessage + "\n" + "Add " + getString(R.string.chocolate) + "? " + hasChocolate;
        summaryMessage = summaryMessage + "\n" + "Quantity: " + quantity;
        summaryMessage = summaryMessage + "\n" + "Total: $" + price;
        summaryMessage = summaryMessage + "\n" + getString(R.string.thank_you);
        return summaryMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @param
     */
    private int calculatePrice(Boolean hasChocolate,Boolean hasWhippedCream) {
        int unitPrice = cost;
        if (hasChocolate){
            unitPrice = unitPrice + 2;
        }
        if (hasWhippedCream){
            unitPrice = unitPrice + 1;
        }
        return quantity * unitPrice;
    }

    /**
     * This method increments the quantity and displays it
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
        } else {
            showToast("You can order max 100 coffees");
        }
        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity and displays it
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        }
        else {
            showToast("You should order atleast 1 coffee");
        }

        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int level) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + level);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        CharSequence text = (CharSequence) message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}