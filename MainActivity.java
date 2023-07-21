package com.example.newtemiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.Robot.*;
//import com.robotemi.sdk.Robot.Companion.getInstance;
//import com.robotemi.sdk.TtsRequest.Companion.create;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamObject;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.constants.*;
import com.robotemi.sdk.exception.OnSdkExceptionListener;
import com.robotemi.sdk.exception.SdkException;
import com.robotemi.sdk.face.ContactModel;
import com.robotemi.sdk.face.OnContinuousFaceRecognizedListener;
import com.robotemi.sdk.face.OnFaceRecognizedListener;
import com.robotemi.sdk.listeners.*;
import com.robotemi.sdk.map.Floor;
import com.robotemi.sdk.map.MapModel;
import com.robotemi.sdk.map.OnLoadFloorStatusChangedListener;
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener;
import com.robotemi.sdk.model.CallEventModel;
import com.robotemi.sdk.model.DetectionData;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToDestinationChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.listener.OnReposeStatusChangedListener;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.sdk.navigation.model.SpeedLevel;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener;
import com.robotemi.sdk.sequence.SequenceModel;
import com.robotemi.sdk.telepresence.CallState;
import com.robotemi.sdk.telepresence.LinkBasedMeeting;
import com.robotemi.sdk.voice.ITtsService;
import com.robotemi.sdk.voice.model.TtsVoice;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Robot.NlpListener, OnRobotReadyListener,
    Robot.ConversationViewAttachesListener,
    Robot.WakeupWordListener,
    Robot.ActivityStreamPublishListener,
    Robot.TtsListener,
    OnBeWithMeStatusChangedListener,
    OnGoToLocationStatusChangedListener,
    OnLocationsUpdatedListener {

    Button turnBy;
    Button tiltAngle;
    Button tiltBy;
    Button speak;
    Button getMapData;
    Button setInteractionState;
    Button stopMovement;
    Button askQuestion;
    Button setDetectionOn;
    Button setDetectionOff;
    Robot robot;
    Toast toast;
    int detectionState;
    OnDetectionStateChangedListener listener;
    WakeupWordListener wakeupWordListener;
    NlpListener nlpListener;

//    OnDetectionStateChangedListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Robot robot = Robot.getInstance();
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_LONG;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnBy = (Button) findViewById(R.id.turnBy);
        tiltAngle = (Button) findViewById(R.id.tiltAngle);
        tiltBy = (Button) findViewById(R.id.tiltBy);
        speak = (Button) findViewById(R.id.speak);
        getMapData = (Button) findViewById(R.id.getMap);
        setInteractionState = (Button) findViewById(R.id.setInteraction);
        stopMovement = (Button) findViewById(R.id.stopMovement);
        askQuestion = (Button) findViewById(R.id.askQuestion);
        setDetectionOn = (Button) findViewById(R.id.setDetectionOn);
        setDetectionOff = (Button) findViewById(R.id.setDetectionOff);
        Robot.getInstance().addOnRobotReadyListener(this);
        //Robot.getInstance().addNlpListener(this);
        Robot.getInstance().addOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().addOnGoToLocationStatusChangedListener(this);
        Robot.getInstance().addConversationViewAttachesListenerListener(this);
        //Robot.getInstance().addWakeupWordListener(this);
        Robot.getInstance().addTtsListener(this);
        Robot.getInstance().addOnLocationsUpdatedListener(this);
        robot.addOnDetectionStateChangedListener(this::onDetectionStateChanged);
//        detectionState = OnDetectionStateChangedListener.IDLE;
        robot.addOnUserInteractionChangedListener(this::onUserInteraction);
        robot.addWakeupWordListener(this::onWakeupWord);
        //robot.addNlpListener(this::onNlpCompleted);
        Robot.getInstance().addNlpListener(this);

        turnBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "turnBy! newCOde", duration);
                toast.show();
                robot.turnBy(20);

            }
        });

        tiltAngle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "tiltAngle!", duration);
                toast.show();
                robot.tiltAngle(20,0.5F);


            }
        });
        tiltBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "tiltBy!", duration);
                toast.show();
                robot.tiltBy(20,.05F);
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "speak!", duration);
                toast.show();
                TtsRequest x = TtsRequest.create("hi");
                robot.speak(x);

            }
        });
        getMapData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "getMapData!", duration);
                toast.show();
                robot.getMapData();

            }
        });

        setInteractionState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "setInteractionState!", duration);
                toast.show();
                robot.setInteractionState(true);

            }
        });

        stopMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here

                robot.requestToBeKioskApp();
                if(robot.isKioskModeOn()){
                    Toast toast = Toast.makeText(context, "KioskMode is on", duration);
                    toast.show();

                }
                robot.stopMovement();

            }
        });

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "askQuestion!", duration);
                toast.show();
                robot.askQuestion("How are you?");

            }
        });

        setDetectionOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "setDetection!", duration);
                toast.show();
                robot.setInteractionState(false);
                robot.setDetectionModeOn(true);


            }
        });


        setDetectionOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the code that should be executed when the button is clicked here
                Toast toast = Toast.makeText(context, "setDetection!", duration);
                toast.show();
                robot.setDetectionModeOn(false);


            }
        });
    }
    protected void onStart() {
        super.onStart();


        //robot.addOnRobotReadyListener((OnRobotReadyListener)this);
        //robot.addOnUserInteractionChangedListener((OnUserInteractionChangedListener)this);
        //robot.addOnDetectionStateChangedListener((OnDetectionStateChangedListener)this);
    }


    @Override
    public void onPublish(@NonNull ActivityStreamPublishMessage activityStreamPublishMessage) {

    }


    public void onUserInteraction(boolean isInteracting) {
        int duration = Toast.LENGTH_LONG;
        Robot robot = Robot.getInstance();
        Context context = getApplicationContext();

        if(isInteracting){
            TtsRequest x = TtsRequest.create("WE ARE INTERACTING");
            //robot.speak(x);
            Log.d("tag", "IN IF STATEMENT FOR USER INTERACTION");
            //Toast toast = Toast.makeText(context, "INTERACTING WITH USER", duration);
            //toast.show();
        }
    }
    public void onDetectionStateChanged(int state) {
        int duration = Toast.LENGTH_LONG;
        Robot robot = Robot.getInstance();
        Context context = getApplicationContext();
        if(state ==2){
            Log.d("tag", "IN IF STATEMENT FOR DETECTION STATE");
            //Toast toast = Toast.makeText(context, "DETECTION DETECTED", duration);
            //robot.askQuestion("Hello, how are you Emmanuel?");
            onUserInteraction(true);
            //toast.show();
        }
        this.detectionState = state;
    }

    @Override
    public void onConversationAttaches(boolean b) {

    }

    @Override
    public void onNlpCompleted(@NonNull NlpResult nlpResult) {
        int duration = Toast.LENGTH_LONG;
        Robot robot = Robot.getInstance();

        TtsRequest w = TtsRequest.create("outside");
        robot.speak(w);        //NLP Q&A Example

        Context context = getApplicationContext();
        switch (nlpResult.action) {
            case "myaction.test":
                Toast toast = Toast.makeText(context, "I HEARD YOU", duration);
                TtsRequest h = TtsRequest.create("I heard you say hello");
                robot.speak(h);        //NLP Q&A Example
                break;
            default:
                TtsRequest x = TtsRequest.create("default");
                robot.speak(x);        //NLP Q&A Example

        }
    }

    @Override
    public void onTtsStatusChanged(@NonNull TtsRequest ttsRequest) {

    }

    @Override
    public void onWakeupWord(@NonNull String s, int i) {
        if(s.equals("Hello")) {
            TtsRequest x = TtsRequest.create("I can hear you");
            robot.speak(x);
        }

    }

    @Override
    public void onBeWithMeStatusChanged(@NonNull String s) {

    }

    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {

    }

    @Override
    public void onLocationsUpdated(@NonNull List<String> list) {

    }
//    public void onDetectionStateChanged(int detectionState) {
//        Context context = getApplicationContext();
//        CharSequence text = "Hello toast!";
//        int duration = Toast.LENGTH_LONG;
//        if(detectionState ==2){
//            Toast toast = Toast.makeText(context, "Hello Person!", duration);
//            toast.show();
//        }
//
//    }

    @Override
    public void onRobotReady(boolean isReady) {
//        if (isReady) {
//            try {
//                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
//                robot.onStart(activityInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
        Robot.getInstance().removeNlpListener(this);
        Robot.getInstance().removeOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().removeOnGoToLocationStatusChangedListener(this);
        Robot.getInstance().removeConversationViewAttachesListenerListener(this);
        Robot.getInstance().removeWakeupWordListener(this);
        Robot.getInstance().removeTtsListener(this);
        Robot.getInstance().removeOnLocationsUpdateListener(this);
    }
}
