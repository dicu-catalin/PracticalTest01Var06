package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PracticalTest01Var06Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int scor  = intent.getIntExtra("scor", 0);

        ProcessingThread processingThread = new ProcessingThread(this, scor);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }
}
