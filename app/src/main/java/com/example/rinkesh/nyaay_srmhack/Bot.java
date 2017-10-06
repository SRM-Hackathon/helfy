 package com.example.rinkesh.nyaay_srmhack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.configuration.Html;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

 public class Bot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);

        final ConversationService myCoversationServices = new ConversationService(
                "2017-10-10",
                getString(R.string.username),
                getString(R.string.password)
        );

        final TextView conversation = (TextView) findViewById(R.id.coversation);
        final EditText userInput = (EditText) findViewById(R.id.user_input);

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView tv, int action, KeyEvent keyEvent) {
                if (action == EditorInfo.IME_ACTION_DONE){

                    final String inputText = userInput.getText().toString();
                    conversation.append(
                            android.text.Html.fromHtml("<p><b>You:<b> " + inputText + "</p>")
                    );

                    userInput.setText("");

                    MessageRequest request = new MessageRequest.Builder()
                            .inputText(inputText)
                            .build();

                    myCoversationServices
                            .message(getString(R.string.workspace),request)
                            .enqueue(new ServiceCallback<MessageResponse>() {
                                @Override
                                public void onResponse(MessageResponse response) {

                                    final String outputText = response.getText().get(1);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            conversation.append(
                                                    android.text.Html.fromHtml("<p><b>Bot:</b> " + outputText + "</p")
                                            );
                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Exception e) {

                                }

                            });
                }
                return false;
            }
        });

    }
}
