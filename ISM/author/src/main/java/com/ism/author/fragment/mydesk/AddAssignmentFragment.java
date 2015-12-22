package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBook;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c162 on 17/12/15.
 */
public class AddAssignmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AddAssignmentFragment.class.getSimpleName();

    private View view;
    private FragmentListener fragListener;
    private TextView txtAssignmentInfo, txtSave;
    private AuthorHostActivity activityHost;
    private TextView txtCancel;
    private RichTextEditor richtexteditor;
    private ArrayList<String> arrayListBooks;
    private Spinner spBooks;
    private ArrayList<AuthorBook> arrListAuthorBooks;
    private String strValidationMsg;
    private EditText etAssignmentName;
    private com.ism.author.Utility.InputValidator inputValidator;
    MyDeskFragment myDeskFragment;

    public static AddAssignmentFragment newInstance() {
        AddAssignmentFragment myDeskFragment = new AddAssignmentFragment();
        return myDeskFragment;
    }

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        txtAssignmentInfo = (TextView) view.findViewById(R.id.txt_assignment_info);
        txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        etAssignmentName = (EditText) view.findViewById(R.id.et_assignment_name);
        spBooks = (Spinner) view.findViewById(R.id.sp_books);
        txtSave = (TextView) view.findViewById(R.id.txt_save);
        richtexteditor = (RichTextEditor) view.findViewById(R.id.richtexteditor);
        richtexteditor.getRichEditor().setEditorFontSize(20);
        richtexteditor.hideMediaControls();
        inputValidator = new InputValidator(getActivity());
//        txtAssignments = (TextView) view.findViewById(R.id.txt_assignments);
        txtAssignmentInfo.setTypeface(Global.myTypeFace.getRalewayRegular());
        etAssignmentName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSave.setTypeface(Global.myTypeFace.getRalewayRegular());

        arrayListBooks = new ArrayList<>();
        arrayListBooks.add(getString(R.string.strSelectBook));
        Adapters.setUpSpinner(getActivity(), spBooks, arrayListBooks, Adapters.ADAPTER_NORMAL);
        callApiGetAuthorBooks();
        spBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (arrayListBooks != null && position > 0) {
////                    callApiGetAuthorBooks();
//                } else {
//                    Adapters.setUpSpinner(getActivity(), spBooks, arrayListBooks, Adapters.ADAPTER_NORMAL);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        txtAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.txt_save: {
                        if (checkInputs())
                            callApiForAddAssignment();
                    }
                    break;
                    case R.id.txt_cancel: {
                        onBackClick();
                    }
                    break;
                }
            }
        };
        txtCancel.setOnClickListener(onClick);
        txtSave.setOnClickListener(onClick);
    }

    private void callApiGetAuthorBooks() {

        if (Utility.isConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSFORAUTHOR);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiForAddAssignment() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
//                attribute.setAssignmentText("Test data");
                attribute.setAssignmentText(richtexteditor.getHtml());
                attribute.setAssignmentName(etAssignmentName.getText().toString());
                attribute.setBookId(arrListAuthorBooks.get(spBooks.getSelectedItemPosition() - 1).getBookId());
                attribute.setClassroomId("0");
                attribute.setSubjectId("0");
                attribute.setTopicId("0");
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.CREATEASSIGNMENT);
            } else {
                Utility.alertOffline(getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "callApiForAddAssignment Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myDeskFragment = MyDeskFragment.newInstance();
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;

            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ADD_ASSIGNMENT);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADD_ASSIGNMENT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void onBackClick() {
        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_ADD_ASSIGNMENT);
       // myDeskFragment.handleBackClick(AppConstant.FRAGMENT_ADD_ASSIGNMENT);
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.CREATEASSIGNMENT:
                    onResponseAddAssignment(object, error);
                    break;
                case WebConstants.GETBOOKSFORAUTHOR:
                    onResponseGetAuthorBooks(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }

    }

    private boolean checkInputs() {

//        & isTextSetInRichTextEditor()
        strValidationMsg = "";
        if (isAssignmentNameSet() & isBookSet()) {
//        if (isAssignmentNameSet() & isBookSet() & isAssignmentTextSet()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }
    }

    private boolean isAssignmentTextSet() {
        try {
            if (richtexteditor == null) {
                Debug.i(TAG, "RichTextEditor is null");
            }
            if ((richtexteditor.getHtml() != null) & (richtexteditor.getHtml().getBytes().length > 0)) {
                return true;
            } else {
                strValidationMsg += Utility.getString(R.string.msg_validation_add_text_rich_editor, getActivity());
                return false;
            }
        } catch (Exception e) {
            Debug.i(TAG, "isAssignmentTextSet Exceptions : " + e.getLocalizedMessage());
            return false;
        }
    }

    private boolean isAssignmentNameSet() {
        return inputValidator.validateStringPresence(etAssignmentName);
    }

    private boolean isBookSet() {
        if (arrListAuthorBooks != null && arrListAuthorBooks.size() == 0 || spBooks.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += "Please select book!";
            return false;
        }
    }

    private void onResponseAddAssignment(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseAddAssignment  : success");
                    etAssignmentName.setText("");
                    richtexteditor.setHtml("");
                    spBooks.setSelection(0);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED))
                    Debug.i(TAG, "onResponseAddAssignment  : FAILED");
            } else if (error != null) {
                Debug.i(TAG, "onResponseAddAssignment error : " + error.getLocalizedMessage());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponseAddAssignment Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetAuthorBooks(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.i(TAG, "onResponseGetAuthorBooks  : success");
                    arrListAuthorBooks = new ArrayList<AuthorBook>();
                    arrListAuthorBooks.addAll(responseHandler.getAuthorBook());
                    List<String> authorBooks = new ArrayList<String>();
                    authorBooks.add(getString(R.string.strSelectBook));
//                    authorBooks.add(getString(R.string.strall));
                    for (AuthorBook authorBook : arrListAuthorBooks) {
                        authorBooks.add(authorBook.getBookName());
                    }
                    Adapters.setUpSpinner(getActivity(), spBooks, authorBooks, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.i(TAG, "onResponseGetAuthorBooks  : failed");
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBooks Exception : " + e.toString());
        }
    }

}
