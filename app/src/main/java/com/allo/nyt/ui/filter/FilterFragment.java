package com.allo.nyt.ui.filter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.allo.nyt.R;
import com.allo.nyt.ui.filter.model.Filter;
import com.allo.nyt.ui.utils.DateEditText;
import com.allo.nyt.ui.utils.MultiSpinner;
import com.allo.nyt.utils.Preferences;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends DialogFragment {

    public interface OnFilterFragmentListener {
        void onFilterSaved(Filter filter);
    }

    private OnFilterFragmentListener mListener;

    @BindView(R.id.et_begin_date)
    DateEditText inputBeginDate;

    @BindView(R.id.et_end_date)
    DateEditText inputEndDate;

    @BindView(R.id.sp_sort)
    Spinner spSort;

    @BindView(R.id.sp_news_desk)
    MultiSpinner spNewsDesk;

    Filter mFilter;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        initializeUI();
        initializeData();
        showData();

        // Hide title bar
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFilterFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFilterFragmentListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void initializeUI() {
        inputBeginDate.setOnDateEditTextListener(new DateEditText.OnDateEditTextListener() {
            @Override
            public void onDateChanged(Date date) {
                mFilter.setBeginDate(date);
            }
        });

        inputEndDate.setOnDateEditTextListener(new DateEditText.OnDateEditTextListener() {
            @Override
            public void onDateChanged(Date date) {
                mFilter.setEndDate(date);
            }
        });

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilter.setSort(getResources().getStringArray(R.array.values_sort)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilter.setSort(null);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.values_new_desk));
        spNewsDesk.setAdapter(adapter, false, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                String[] values = getResources().getStringArray(R.array.values_new_desk);
                ArrayList<String> selectedNewsDesk = new ArrayList<>();
                int count = values.length;
                for (int i = 0; i < count; i++) {
                    if (selected[i]) {
                        selectedNewsDesk.add(values[i]);
                    }
                }
                mFilter.setNewsDesk(selectedNewsDesk);
            }
        });
    }

    protected void initializeData() {
        mFilter = Preferences.sharedInstance().getFilter();
    }

    protected void showData() {
        if (mFilter.getBeginDate() != null) inputBeginDate.setDate(mFilter.getBeginDate());
        if (mFilter.getEndDate() != null) inputEndDate.setDate(mFilter.getEndDate());

        if (mFilter.getSort() != null) {
            int spinnerPosition = ((ArrayAdapter<String>) spSort.getAdapter()).getPosition(mFilter.getSort());
            if (spinnerPosition != -1) spSort.setSelection(spinnerPosition);
        }

        if (mFilter.getNewsDesk() != null && mFilter.getNewsDesk().size() > 0) {
            String[] values = getResources().getStringArray(R.array.values_new_desk);
            boolean[] selectedItems = new boolean[values.length];
            int count = values.length;
            for (int i = 0; i < count; i++) {
                if (mFilter.getNewsDesk().contains(values[i])) {
                    selectedItems[i] = true;
                }
            }
            spNewsDesk.setSelected(selectedItems);
        }
    }

    @OnClick(R.id.bt_apply_filter)
    public void applyFilter() {
        Preferences.sharedInstance().saveFilter(mFilter);
        if (mListener != null) mListener.onFilterSaved(mFilter);
        dismiss();
    }
}
