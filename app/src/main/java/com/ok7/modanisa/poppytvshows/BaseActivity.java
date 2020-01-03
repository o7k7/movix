package com.ok7.modanisa.poppytvshows;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.ok7.modanisa.poppytvshows.di.application.ApplicationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationModule;


public abstract class BaseActivity<D extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity {
    private PresentationComponent mPresentationComponent;

    private D mViewDataBinding;

    private V mViewModel;

    private ProgressDialog mProgressDialog;

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

    public abstract int getBindingVariable();

    @LayoutRes
    public abstract int getLayoutId();

    public abstract V getViewModel();

    public D getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
    }

    public void showLoading() {
        hideLoading();
//        mProgressDialog = Util.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
