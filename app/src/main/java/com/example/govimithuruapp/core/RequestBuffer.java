package com.example.govimithuruapp.core;

import android.content.Context;

import com.android.volley.Request;

import java.util.ArrayList;

public class RequestBuffer {

    private ArrayList<Request> buffer;

    public RequestBuffer() {
        this.buffer = new ArrayList<>();
    }

    public <T> void addToBuffer(Request<T> request) {
        synchronized (buffer) {
            this.buffer.add(request);
        }
    }

    public ArrayList<Request> fetchFromBuffer() {
        ArrayList<Request> requests = new ArrayList<>();
        synchronized (buffer) {
            while (!buffer.isEmpty()) requests.add(buffer.remove(0));
        }
        return requests;
    }
}
