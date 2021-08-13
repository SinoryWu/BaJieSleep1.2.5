package com.example.bajiesleep;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class PushReceiverViewModel extends ViewModel {
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();


}
