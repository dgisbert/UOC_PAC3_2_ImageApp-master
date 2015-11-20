package com.booreg.android.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Offers convenience methods to create and show simple alert dialogs.
 */

public class DialogUtil
{
    private static final class DefaultCancelListener implements DialogInterface.OnClickListener
    {
        @Override public void onClick(DialogInterface dialog, int which) {  /* Nothing to do */ }
    }

    /**
     * Shows a dialog with Ok / Cancel Options. Cancel option does nothing
     */

    public static void showOKCancelDialog(Context context, int idTitle, int idMessage, int idTextOk, int idTextCancel, DialogInterface.OnClickListener okListener)
    {
        showOKCancelDialog(context, idTitle, idMessage, idTextOk, idTextCancel, okListener, new DefaultCancelListener());
    }

    /**
     * Shows a dialog with Ok / Cancel Options
     */

    public static void showOKCancelDialog(Context context, int idTitle, int idMessage, int idTextOk, int idTextCancel, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
    {
        new AlertDialog.Builder(context).setTitle(idTitle).setMessage(idMessage).setPositiveButton(idTextOk, okListener).setNegativeButton(idTextCancel, cancelListener).create().show();
    }
}
