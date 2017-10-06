package com.example.rinkesh.nyaay_srmhack.Authentication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rinkesh.nyaay_srmhack.R;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitError;

/*public class ErrorActivity extends Activity {

    static final String HELLO_TOKEN_ACTIVITY_ERROR_EXTRA =
            "HELLO_TOKEN_ACTIVITY_ERROR_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        final Button signOut = (Button) findViewById(R.id.log_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                finish();
            }
        });
    }

    final AccountKitError error =
            getIntent().getParcelableExtra(HELLO_TOKEN_ACTIVITY_ERROR_EXTRA);

    final TextView errorView = (TextView) findViewById(R.id.error);
        if (errorView != null) {
        if (error != null) {
            errorView.setText(error.toString());
        } else {
            errorView.setText(R.string.na);
        }
    }
}


} */
