package com.example.luisfelix.gym;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReciver extends BroadcastReceiver {

    public MyReciver() {
    }
    /*Este disparate es para la notificacion*/

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);
    }
}