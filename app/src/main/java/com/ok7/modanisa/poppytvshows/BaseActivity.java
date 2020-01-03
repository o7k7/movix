package com.ok7.modanisa.poppytvshows;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;

import com.ok7.modanisa.poppytvshows.di.application.ApplicationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationModule;


public class BaseActivity extends FragmentActivity {

    private PresentationComponent mPresentationComponent;

    protected PresentationComponent getPresentationComponent() {
        if (mPresentationComponent == null) {
            mPresentationComponent = getApplicationComponent()
                    .newPresentationComponent(new PresentationModule(this));
        }
        return mPresentationComponent;
    }

    private ApplicationComponent getApplicationComponent() {
        return ((PoppyApp) getApplication())
                .getApplicationComponent();
    }

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
