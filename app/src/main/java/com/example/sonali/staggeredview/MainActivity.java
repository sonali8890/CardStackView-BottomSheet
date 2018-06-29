package com.example.sonali.staggeredview;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.sonali.staggeredview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "TAG";

    ActivityMainBinding binder;
    BottomSheetBehavior bottomSheetBehavior;
    boolean isClicked;
    ViewGroup view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binder.card1.setOnClickListener(this);
        binder.card2.setOnClickListener(this);
        binder.card3.setOnClickListener(this);
        binder.card4.setOnClickListener(this);
        bottomSheetBehavior = BottomSheetBehavior.from(binder.bottomSheet1);
        bottomSheetBehavior.setPeekHeight(getResources().getDimensionPixelSize(R.dimen.margine_200dp));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED && isClicked) {
                    isClicked = false;
//                    binder.scrollView.smoothScrollTo(0, view.getTop() - 40);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                revalidateViews(slideOffset);
            }
        });
        revalidateViews(0);
    }

    private void revalidateViews(float diff) {
        int preBottom = 0;
        for (int i = 0; i < binder.container.getChildCount(); i++) {
            final LinearLayout ll = (LinearLayout) binder.container.getChildAt(i);
            final RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) ll.getLayoutParams();
            if (i != 0) {
                int accelaration = (int) (preBottom * diff);
                p.topMargin = (accelaration + getResources().getDimensionPixelSize(R.dimen.margine_40dp)) * i;
                ll.setLayoutParams(p);
            }
            preBottom = ll.getHeight();
        }

    }

    @Override
    public void onClick(View v) {
        view = (ViewGroup) v;
        isClicked = true;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
