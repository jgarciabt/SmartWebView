package jgarciabt.smartwebview.utils;

import android.content.Context;
import android.graphics.Color;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import jgarciabt.smartwebview.R;


/**
 * Created by JGarcia on 28/3/15.
 */
public class SnackbarUtils{

    private static Snackbar defaultSnackbarConstructor(Context context, int color, int text, String actionLabel, Snackbar.SnackbarDuration duration)
    {

        Snackbar snackbar = Snackbar.with(context)
                .color(color)
                .text(text)
                .actionLabel(actionLabel)
                .duration(duration);


       return snackbar;
    }

    public static void showNoInternetSnackbar(Context context)
    {
        SnackbarManager.show(defaultSnackbarConstructor(context, Color.RED, R.string.snackbar_offline_message, "OK", Snackbar.SnackbarDuration.LENGTH_INDEFINITE));
    }

    public static void dismissSnackbar()
    {
        SnackbarManager.dismiss();
    }
}
