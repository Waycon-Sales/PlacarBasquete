package com.example.placarbasquete.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.placarbasquete.utils.BitmapUtil;


public abstract class BaseBlurDialog extends DialogFragment {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 6.5f;
    private static final int ALPHA = 120;
    private Drawable dialogBackground;
    private final String tag;


    public BaseBlurDialog(String tag) {
        this.tag = tag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            buildDialogBackground(activity);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        buildDialogBackground(activity);
    }

    private void buildDialogBackground(Activity activity) {
        final View viewToBlur = activity.findViewById(android.R.id.content).getRootView();
        viewToBlur.post(() -> {
            if (isAdded()) {
                Resources resources = getResources();
                Object BitmapUtil = new BitmapUtil();
                Bitmap bitmap = ((com.example.placarbasquete.utils.BitmapUtil) BitmapUtil).getViewVisual(viewToBlur);
                if (resources != null && bitmap != null) {
                    Bitmap bitmapBlurred = ((com.example.placarbasquete.utils.BitmapUtil) BitmapUtil).blurBitmap(getActivity(), bitmap, BITMAP_SCALE, BLUR_RADIUS);

                    Drawable[] drawables = new Drawable[2];
                    drawables[0] = new BitmapDrawable(getResources(), bitmapBlurred);
                    drawables[1] = new ColorDrawable(Color.argb(ALPHA, 255, 255, 255));

                    dialogBackground = new LayerDrawable(drawables);
                    setBackground();
                }
            }
        });
    }

    private void setBackground() {
        if (dialogBackground != null) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    window.setBackgroundDrawable(dialogBackground);

                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }
            }
        }
    }

    public void onShow(FragmentManager manager) {
        show(manager, getTagDialog());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        this.setCancelable(false);
    }

    public String getTagDialog() {
        return tag;
    }
}

