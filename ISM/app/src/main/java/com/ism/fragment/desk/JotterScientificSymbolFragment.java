package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.GridAdaptor;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c162 on 23/12/15.
 */
public class JotterScientificSymbolFragment extends Fragment {
    private static final String TAG = JotterScientificSymbolFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;
    private GridView gridSymbols;
    private GridAdaptor gridAdaptor;
    private HostActivity activityHost;

    public static JotterScientificSymbolFragment newInstance() {
        JotterScientificSymbolFragment fragment = new JotterScientificSymbolFragment();
        return fragment;
    }

    public JotterScientificSymbolFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jotter_scientific_symbol, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        gridSymbols = (GridView) view.findViewById(R.id.gridview_scientific_symbol);


        String[] formulas = {
               getResources() .getString(R.string.alpha),
                getResources().getString(R.string.beta),
                getResources().getString(R.string.gamma),
                getResources().getString(R.string.delta),
                getResources().getString(R.string.epsilon),
                getResources().getString(R.string.zeta),
                getResources().getString(R.string.eta),
                getResources().getString(R.string.theta),
                getResources().getString(R.string.iota),
                getResources().getString(R.string.kappa),
                getResources().getString(R.string.lamda),
                getResources().getString(R.string.mu),
                getResources().getString(R.string.nu),
                getResources().getString(R.string.xi),
                getResources().getString(R.string.omicron),
                getResources().getString(R.string.pi),
                getResources().getString(R.string.greeksmallrho),
                getResources().getString(R.string.greeksmallsigma),
                getResources().getString(R.string.greeksmallsigmafinal),
                getResources().getString(R.string.greeksmalltau),
                getResources().getString(R.string.greeksmallupsilon),
                getResources().getString(R.string.greeksmallphi),
                getResources().getString(R.string.chi),
                getResources().getString(R.string.psi),
                getResources().getString(R.string.greekomega),
                getResources().getString(R.string.greektheta),
                getResources().getString(R.string.greekupsilon),
                getResources().getString(R.string.phi),
                getResources().getString(R.string.greekpi)

        };


        gridAdaptor = new GridAdaptor(getActivity(), formulas);
        gridSymbols.setAdapter(gridAdaptor);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost=(HostActivity)activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


}
