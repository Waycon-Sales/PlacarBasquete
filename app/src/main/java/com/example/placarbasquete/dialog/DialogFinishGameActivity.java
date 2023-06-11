package com.example.placarbasquete.dialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.placarbasquete.R;
import com.example.placarbasquete.databinding.ActivityDialogFinishGameBinding;
import com.example.placarbasquete.interfaces.PlacarInterface;

public class DialogFinishGameActivity extends BaseBlurDialog {

    private ActivityDialogFinishGameBinding binding;
    private String message;
    private boolean addTime = false;
    private Context context;

    private  PlacarInterface listener;

    private boolean finishNotSave = false;

    public DialogFinishGameActivity(String message, Context context, boolean addTime, boolean finishNotSave) {
        super("dialog finish");
        this.message = message;
        this.addTime = addTime;
        this.context = context;
        this.listener = ((PlacarInterface) context);
        this.finishNotSave = finishNotSave;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityDialogFinishGameBinding.inflate(inflater, container, false);

        binding.tvMessage.setText(message);
        if(addTime){
            binding.btnAddTimeDialog.setVisibility(View.GONE);
        }

        if(finishNotSave == false){
            binding.btnFinishNotSave.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancelDialog.setOnClickListener(v -> dismiss());
        binding.btnFinishGameDialog.setOnClickListener(v -> {
            dismiss();
            listener.finishAndSave();
        });
        binding.btnAddTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
                listener.addTime();
            }
        });

        binding.btnFinishNotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.finishNotSave();
            }
        });

    }
}