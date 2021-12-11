package com.example.govimithuruapp.core;

import android.content.Context;

public class ConnectivityMonitor extends Thread {
    private static Connected connected;
    private Context context;

    private static final long WAIT_INTERVAL = 10000;

    public ConnectivityMonitor(Context context) {
        this.context = context;
        connected = new Connected();
        connected.setC(false);
    }

    public void setConnected(boolean c) {
        synchronized (connected) {
            connected.setC(c);
        }
    }

    @Override
    public void run() {
        System.out.println("Monitoring Started");

        boolean keepChecking = true;
        while (keepChecking) {
            synchronized (connected) {
                keepChecking = !connected.isC();
            }
            try {
                Thread.sleep(WAIT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BackendManager.getInstance(context).verifyInternetConnectivity(null, BackendManager.ActionCodes.BUFFER_MONITOR);
        }

        System.out.println("Monitoring Completed");
    }

    private static class Connected {
        private boolean c;

        public boolean isC() {
            return c;
        }

        public void setC(boolean c) {
            this.c = c;
        }
    }
}
