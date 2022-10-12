package com.kevin.netgemtest.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kevin.netgemtest.R;

public abstract class BaseBottomSheet extends BottomSheetDialogFragment {
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFullScreen()) {
            final Dialog dialog = getDialog();
            if (dialog != null) {
                final View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                bottomSheet.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                final Window window = dialog.getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    window.setGravity(Gravity.CENTER);
                }
            }
            view.post(() -> {
                final View parent = (View) view.getParent();
                parent.setBackgroundColor(Color.TRANSPARENT);
                final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
                final BottomSheetBehavior<View> bottomSheetBehavior = (BottomSheetBehavior<View>) params.getBehavior();
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetBehavior.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    bottomSheetBehavior.setMaxHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                }
            });
        } else {
            view.post(() -> {
                final View parent = (View) view.getParent();
                parent.setBackgroundColor(Color.TRANSPARENT);
                final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
                final BottomSheetBehavior<View> bottomSheetBehavior = (BottomSheetBehavior<View>) params.getBehavior();
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                    bottomSheetBehavior.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    protected void setDraggable(final boolean isDraggable) {
        final View view = getView();
        if (view != null) {
            view.post(() -> {
                final View parent = (View) view.getParent();
                final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
                final BottomSheetBehavior<View> bottomSheetBehavior = (BottomSheetBehavior<View>) params.getBehavior();
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.setDraggable(isDraggable);
                }
            });
        }
    }

    protected abstract boolean isFullScreen();
}

