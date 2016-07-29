package com.allo.nyt.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.allo.nyt.R;
import com.allo.nyt.utils.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends DialogFragment {

    @BindView(R.id.cb_fullscreen_detail)
    CheckBox mCbFullscreenDetail;

    private Unbinder unbinder;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mCbFullscreenDetail.setChecked(Preferences.sharedInstance().getBoolean(Preferences.SHOW_ARTICLE_FULLSCREEN, false));
        mCbFullscreenDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Preferences.sharedInstance().putBoolean(Preferences.SHOW_ARTICLE_FULLSCREEN, isChecked);
            }
        });

        getDialog().setTitle(R.string.action_settings);

        // Hide title bar
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
