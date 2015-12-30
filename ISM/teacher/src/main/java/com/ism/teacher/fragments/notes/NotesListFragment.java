package com.ism.teacher.fragments.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.notes.AllNotesAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.listview.DragNDropAdapter;
import com.ism.teacher.listview.DragNDropListView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.LessonNotes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by c75 on 25/12/15.
 */
public class NotesListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = NotesListFragment.class.getSimpleName();
    Fragment mFragment;
    DragNDropListView listViewNotes;

    ArrayList<String> listDataHeader = new ArrayList<>();
    HashMap<String, ArrayList<String>> listDataChild;
    ArrayList<LessonNotes>arrListLessonNotes=new ArrayList<>();

    public NotesListFragment() {
        // Required empty public constructor
    }

    public NotesListFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_blue);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {
        listViewNotes = (DragNDropListView) rootview.findViewById(R.id.list_view_notes);
       // listViewNotes.setDragOnLongPress(true);

        if (getNotesContainer().getBundleArguments().getString(AllNotesAdapter.ARG_NOTES_SUBJECT_ID) != null) {
            if (Utility.isInternetConnected(getActivity())) {

                callApiLessonNotesWithDetails(getNotesContainer().getBundleArguments().getString(AllNotesAdapter.ARG_NOTES_SUBJECT_ID));
            }
        }

        listViewNotes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                getNotesContainer().callNotesAddEditService(arrListLessonNotes.get(groupPosition).getLectureId(),
                        arrListLessonNotes.get(groupPosition).getNotes().get(childPosition));
                return false;
            }
        });

    }

    public void callApiLessonNotesWithDetails(String subject_id) {
        try {
            ((TeacherHostActivity) getActivity()).showProgress();
            Attribute attribute = new Attribute();
            attribute.setSubjectId(subject_id);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.LESSON_NOTES_WITH_DETAILS);
        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }

    }

    private NotesContainer getNotesContainer() {
        return (NotesContainer) mFragment;
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {

            switch (apiCode) {
                case WebConstants.LESSON_NOTES_WITH_DETAILS:
                    if (getActivity() != null && isAdded()) {
                        ((TeacherHostActivity) getActivity()).hideProgress();
                        onResponseGetLessonNotesWithDetail(object, error);
                    }
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetLessonNotesWithDetail(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListLessonNotes=responseHandler.getLessonNotes();
                    if (responseHandler.getLessonNotes().size() > 0) {
                        listDataChild = new HashMap<>();
                        for (int i = 0; i < responseHandler.getLessonNotes().size(); i++) {

                            ArrayList<String> arrListNotesTitle = new ArrayList<>();
                            for (int j = 0; j < responseHandler.getLessonNotes().get(i).getNotes().size(); j++) {
                                arrListNotesTitle.add(responseHandler.getLessonNotes().get(i).getNotes().get(j).getNoteTitle());
                            }
                            listDataHeader.add(responseHandler.getLessonNotes().get(i).getLectureName());
                            listDataChild.put(responseHandler.getLessonNotes().get(i).getLectureName(), arrListNotesTitle);
                        }

                        listViewNotes.setAdapter(new DragNDropAdapter(getActivity(), listDataHeader, listDataChild));
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseLessonNotes api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseLessonNotes Exception : " + e.toString());
        }
    }
}
