package it.jaschke.alexandria;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by santhosh on 06/12/15.
 * Dialog which opens the Scanner to Scan the IBSN number from barcode
 */
public class ScannerDialogFragment extends DialogFragment implements ZXingScannerView.ResultHandler {
    private OnDismissListener listener;
    private ZXingScannerView mScannerView;

    public interface OnDismissListener {
        void onDimiss(String isbn);
    }

    public static ScannerDialogFragment newInstance(OnDismissListener listener) {
        ScannerDialogFragment f = new ScannerDialogFragment();
        f.listener = listener;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.scan_dialog_title);
        View rootView = inflater.inflate(R.layout.dialog_scan, container, false);
        mScannerView = (ZXingScannerView) rootView.findViewById(R.id.scanner_view);

        Button cancelBtn = (Button) rootView.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

        return rootView;
    }

    @Override
    public void handleResult(Result result) {
        if (result != null && !TextUtils.isEmpty(result.getText())) {
            listener.onDimiss(result.getText());
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        mScannerView.stopCamera();
        super.dismiss();
    }
}