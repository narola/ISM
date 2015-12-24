package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.GridAdaptor;
import com.ism.adapter.JotterNotesAdapter;
import com.ism.dialog.AddNewNoteDialog;
import com.ism.dialog.ShareNoteDialog;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;

/**
 * Created by c161 on --/10/15.
 */
public class DeskFragment extends Fragment implements HostActivity.InsertSymbolListener, ShareNoteDialog.AlertDismissListener {

    private static final String TAG = DeskFragment.class.getSimpleName();
    public static final int DIALOG_SHARE = 100;
    public static final int DIALOG_ADD_NOTES = 200;

    private View view;

    private FragmentListener fragListener;
    private EditText etNotes;
    private HostActivity activityHost;
    GridAdaptor gridAdaptor;
    private RecyclerView listView;
    JotterNotesAdapter jotterNotesAdapter;
    private TextView txtAddNew;
    private ImageView imgShare;
    private ShareNoteDialog shareNoteDialog;

    public static DeskFragment newInstance() {
        DeskFragment fragDesk = new DeskFragment();
        return fragDesk;
    }

    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        shareNoteDialog = new ShareNoteDialog(getActivity(),this);

        etNotes = (EditText) view.findViewById(R.id.et_notes);
        etNotes.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtAddNew = (TextView) view.findViewById(R.id.txt_add);
        txtAddNew.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgShare = (ImageView) view.findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShare.setActivated(true);

                shareNoteDialog.show();
            }
        });
        listView = (RecyclerView) view.findViewById(R.id.recyclerview_notes);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jotterNotesAdapter = new JotterNotesAdapter(getActivity(), null);
        listView.setAdapter(jotterNotesAdapter);

        txtAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNoteDialog newNoteDialog = new AddNewNoteDialog(getActivity());
                newNoteDialog.show();
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            activityHost.setInsertSymbolListener(this);
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_DESK);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void insertSymbol(String symbol) {
        etNotes.setText(etNotes.getText().toString() + symbol);
        etNotes.setSelection(etNotes.getText().toString().length());
    }

    @Override
    public void onDismiss(int alertDialog) {
        if (alertDialog == DIALOG_SHARE) {
            imgShare.setActivated(false);
        }
    }
}
