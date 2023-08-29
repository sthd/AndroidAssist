package com.er.aa.accservices;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.LocaleSpan;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class PalGateAccService extends AccessibilityService {
    //String [] convs = new String [] {"hey, how are you?", "it has been quite a while", "that is the 3rd message", "trying 4"};
    static String[] convs = new String[3];
    static String targetName = "default";
    static final String TAG = "PalGateAccService";
    static final String settingsId = "com.bluegate.app:id/settings_change_language";
    static final String gateOpenerId = "com.bluegate.app:id/gateButtonView";
    //static final String settingsId = "com.bluegate.app:id/settings";

    static int convIndex = 0;
    static int takeAction = 0;
    final int chatTimeLapseInMs = 5000;
    final int stdTimeLApse = 600;
    boolean sentLast = false;
    /**
     * The class name of TaskListView - for simplicity we speak only its items.
     */
    private static final String TASK_LIST_VIEW_CLASS_NAME =
            "com.example.android.apis.accessibility.TaskListView";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        //Toast.makeText(getApplicationContext(), "ready modules, palgate, begin", Toast.LENGTH_SHORT).show();
        //showToast("ready");
        if (event == null) {
            return;
        }

        AccessibilityNodeInfo rootNode = event.getSource();
        if (rootNode == null) {
            return;
        }
        //Toast.makeText(getApplicationContext(), "root not null", Toast.LENGTH_SHORT).show();
        // Grab the parent of the view that fired the event.
        //AccessibilityNodeInfo rowNode = getListItemNodeInfo(rootNode);
        //if (rowNode == null) {
        //    return;
        //}

        //Log.d(TAG, "root node full is:" + rootNode);
        try {
            //AccessibilityNodeInfo settingsButton = getTheNode(rootNode, gateOpenerId);
            //Log.d(TAG, "node full is:" + settingsButton);



            //traverseNodes(rootNode);
            //// Don't forget to recycle the root node to release resources
            //rootNode.recycle();



            //Toast.makeText(getApplicationContext(), "node text" + settingsButton.getText().toString(), Toast.LENGTH_SHORT).show();
            //settingsButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);

            AccessibilityNodeInfo gateOpenerButton = getTheNode(rootNode, gateOpenerId);
            gateOpenerButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Toast.makeText(getApplicationContext(), "opened your gate", Toast.LENGTH_SHORT).show();
            backToReadyModule();
            Thread.sleep(2500);

        } catch (Exception e) {
            Log.d(TAG, "Some exception occured!" + e.getMessage());
        }


    }

    public AccessibilityNodeInfo getTheNode(@NonNull AccessibilityNodeInfo rootNodeInfo, @NonNull String refId) {
        AccessibilityNodeInfo tmpNode = null;
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId(refId);
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            tmpNode = urlNodeInfo.get(0);
            //Toast.makeText(getApplicationContext(), "Node is GOOD" + urlNodeInfo.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Found " + refId);
            Log.d(TAG, "the urlNode info is  " + urlNodeInfo.toString());
            return tmpNode;
        }
        Log.d(TAG, "Not found " + refId);
        Toast tmpToast = Toast.makeText(getApplicationContext(), "The Node isn't here", Toast.LENGTH_SHORT);
        tmpToast.show();
        return tmpNode;
    }



    private void traverseNodes(AccessibilityNodeInfo node) {
        if (node == null) {
            return;
        }

        // Do something with the node, e.g., print its text content
        if (node.getText() != null) {
            Log.d("Accessibility", "Node: " + node.toString() + " Node text: " + node.getText());
        }
        else{
            Log.d("Accessibility", "Node: " + node.toString() + " but no text");
        }

        // Traverse child nodes recursively
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            traverseNodes(childNode);
        }
    }







    public void showToast(String toastText){
        if (toastText != null)
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    }


    public void backToReadyModule(){
        Intent gate_intent = getPackageManager().getLaunchIntentForPackage("com.er.readymodules");
        if (gate_intent != null) {
            Toast.makeText(getApplicationContext(), "back to ready modules", Toast.LENGTH_SHORT).show();
            startActivity(gate_intent);
        } else {
            Toast.makeText(getApplicationContext(), "ready modules is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    public static String getName(@NonNull AccessibilityNodeInfo rootNodeInfo) {
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId("nameRefId");
        //AccessibilityNodeInfo newNod = rootNodeInfo.
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            AccessibilityNodeInfo urlNode = urlNodeInfo.get(0);
            CharSequence charArray = urlNode.getText();
            if (charArray != null && charArray.length() > 0) {
                Log.d(TAG, "Name found: " + charArray.toString());
                return charArray.toString();
            }
        }
        Log.d(TAG, "Name NOT found");
        return null;
    }

    public AccessibilityNodeInfo getNode(@NonNull AccessibilityNodeInfo rootNodeInfo, @NonNull String refId) {
        AccessibilityNodeInfo textBoxNode = null;
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId(refId);
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            textBoxNode = urlNodeInfo.get(0);
            Log.d(TAG, "Found " + refId);
            return textBoxNode;
        }
        Log.d(TAG, "Not found " + refId);
        return textBoxNode;
    }

    public void makeSound() {
        //TextView localeWrappedTextView = findViewById(R.id.my_french_greeting_text);
        //localeWrappedTextView.setText(wrapTextInLocaleSpan("Bonjour!", Locale.FRANCE));

        //private SpannableStringBuilder wrapTextInLocaleSpan(
        //        CharSequence originalText, Locale loc) {
        //    SpannableStringBuilder myLocaleBuilder =
        //            new SpannableStringBuilder(originalText);
        //    myLocaleBuilder.setSpan(new LocaleSpan(loc), 0,
        //            originalText.length() - 1, 0);
        //    return myLocaleBuilder;
        //}
    }

    // Simulates an L-shaped drag path: 200 pixels right, then 200 pixels down.
    private void doRightThenDownDrag() {

        //Path dragRightPath = new Path();
        //dragRightPath.moveTo(200, 200);
        //dragRightPath.lineTo(400, 200);
        //long dragRightDuration = 500L; // 0.5 second

        //// The starting point of the second path must match
        //// the ending point of the first path.
        //Path dragDownPath = new Path();
        //dragDownPath.moveTo(400, 200);
        //dragDownPath.lineTo(400, 400);
        //long dragDownDuration = 500L;
        //GestureDescription.StrokeDescription rightThenDownDrag =
        //        new GestureDescription.StrokeDescription(dragRightPath, 0L,
        //                dragRightDuration, true);
        //rightThenDownDrag.continueStroke(dragDownPath, dragRightDuration,
        //        dragDownDuration, false);
    }


    @Override
    public void onInterrupt() {

    }

    private AccessibilityNodeInfo getListItemNodeInfo(AccessibilityNodeInfo source) {
        AccessibilityNodeInfo current = source;
        while (true) {
            AccessibilityNodeInfo parent = current.getParent();
            if (parent == null) {
                return null;
            }
            if (TASK_LIST_VIEW_CLASS_NAME.equals(parent.getClassName())) {
                return current;
            }
            // NOTE: Recycle the infos.
            AccessibilityNodeInfo oldCurrent = current;
            current = parent;
            oldCurrent.recycle();
        }


    }

}