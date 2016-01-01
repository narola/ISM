package com.ism.author.fragment.gotrending;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.HtmlImageGetter;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.gotrending.QuestionsMostFollowAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.AddQuestionTextDialog;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.MediaUploadAttribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.TrendingQuestion;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


/**
 * Created by c166 on 21/10/15.
 */
public class GoTrendingFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, AuthorHostActivity.HostListenerTrending, AddQuestionTextDialog.SelectMediaListener, AddQuestionTextDialog.AddTextListener {

    private static final String TAG = GoTrendingFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private TextView txtYourAnswer, txtSave, txt_Creator_Name, txtQuestion, txtDate, txtFollowers,
            txtQuestionMostFollower, txtEmpty, txtEmptyAnswer;
    private EditText etAnswer;
    private RecyclerView rvTrendingQuestionsList;
    private QuestionsMostFollowAdapter questionsMostFollowAdapter;
    private AuthorHostActivity activityHost;
    private String trendingId;
    private ImageView imgProfilePic;
    private ArrayList<TrendingQuestion> arrayListTrendingQuestions = new ArrayList<>();
    private InputValidator inputValidator;
    private int visibleQuestionPosition = -1;
    ScrollView scrollView;

    private ImageView imgAttachMedia;

    public static GoTrendingFragment newInstance() {
        GoTrendingFragment fragGoTrending = new GoTrendingFragment();
        return fragGoTrending;
    }

    public GoTrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gotrending, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        inputValidator = new InputValidator(getActivity());
        txtYourAnswer = (TextView) view.findViewById(R.id.txt_your_answer);
        txtYourAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtDate = (TextView) view.findViewById(R.id.txt_date);
        txtDate.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtFollowers = (TextView) view.findViewById(R.id.txt_total_followers);
        txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtQuestion = (TextView) view.findViewById(R.id.txt_question);
        txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

        txt_Creator_Name = (TextView) view.findViewById(R.id.txt_creator_name);
        txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtSave = (TextView) view.findViewById(R.id.txt_save);
        txtSave.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtQuestionMostFollower = (TextView) view.findViewById(R.id.txt_questions_most_follower);
        txtQuestionMostFollower.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgProfilePic = (ImageView) view.findViewById(R.id.img_user_dp);

        etAnswer = (EditText) view.findViewById(R.id.et_answer);
        etAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvTrendingQuestionsList = (RecyclerView) view.findViewById(R.id.rv_trending_questions_list);
        rvTrendingQuestionsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtEmptyAnswer = (TextView) view.findViewById(R.id.txt_empty_answer);
        txtEmptyAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgAttachMedia = (ImageView) view.findViewById(R.id.img_attach_media);
        imgAttachMedia.setOnClickListener(this);


        setEmptyView(true);
        onClicks();

        callApiForGetTrending();
    }


    private void onClicks() {
        try {
            txtSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSave();
                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "onClicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onClickSave() {
        try {
            if (inputValidator.validateStringPresence(etAnswer)) {
                callApiForSubmitTrendingAnswer();
            }
        } catch (Exception e) {
            Debug.i(TAG, "onClickSave Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void callApiForGetTrending() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(Global.strUserId);
                attribute.setRole(Global.role);
                attribute.setCheckSlot(Global.checkSlotNo);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTIONS);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetTrending Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    private void callApiForSubmitTrendingAnswer() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();

                Attribute attribute = new Attribute();

                MediaUploadAttribute authorIdParam = new MediaUploadAttribute();
                authorIdParam.setParamName("author_id");
                authorIdParam.setParamValue(Global.strUserId);


                MediaUploadAttribute questionIdParam = new MediaUploadAttribute();
                questionIdParam.setParamName("question_id");
                questionIdParam.setParamValue(trendingId);


                MediaUploadAttribute answerTextParam = new MediaUploadAttribute();
                answerTextParam.setParamName("answer_text");
                answerTextParam.setParamValue(getHtmlQuestionText());

                addImage();

                if (imageSources.size() > 0) {
                    for (int i = 0; i < imageSources.size(); i++) {
                        MediaUploadAttribute mediaFileParam = new MediaUploadAttribute();
                        mediaFileParam.setParamName("" + i);
                        mediaFileParam.setFileName(imageSources.get(i));
                        attribute.getArrListFile().add(mediaFileParam);
                    }
                }

                MediaUploadAttribute numberOfImagesParam = new MediaUploadAttribute();
                numberOfImagesParam.setParamName("number_of_images");
                numberOfImagesParam.setParamValue(String.valueOf(imageSources.size()));


                attribute.getArrListParam().add(authorIdParam);
                attribute.getArrListParam().add(questionIdParam);
                attribute.getArrListParam().add(answerTextParam);
                attribute.getArrListParam().add(numberOfImagesParam);


//                attribute.setAuthorId(Global.strUserId);
//                attribute.setQuestionid(trendingId);
//                attribute.setAnswerText(etAnswer.getText().toString().trim());

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.SUBMIT_TRENDING_ANSWER);


            } catch (Exception e) {
                Log.e(TAG, "callApiForSubmitTrendingAnswer Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    private void setEmptyView(boolean isEnable) {

        txtEmpty.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvTrendingQuestionsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
        txtEmptyAnswer.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_GOTRENDING);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_GOTRENDING);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void onBackClick() {
        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_GOTRENDING);


    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            activityHost.hideProgress();
            switch (apiCode) {
                case WebConstants.GET_TRENDING_QUESTIONS:
                    onResponseGetTrending(object, error);
                    break;
                case WebConstants.SUBMIT_TRENDING_ANSWER:
                    onResponseSubmitTrendingAnswer(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseSubmitTrendingAnswer(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    etAnswer.setText("");
                    arrayListTrendingQuestions.remove(visibleQuestionPosition);
                    setQuestion(arrayListTrendingQuestions.size() - 1 > visibleQuestionPosition ? visibleQuestionPosition - 1 : visibleQuestionPosition + 1);
                    questionsMostFollowAdapter.notifyDataSetChanged();


                    if (arrayListTrendingQuestions.size() == 0) setEmptyView(true);
                    else setEmptyView(false);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseSubmitTrendingAnswer : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseSubmitTrendingAnswer error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseSubmitTrendingAnswer Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetTrending(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

                    arrayListTrendingQuestions = responseHandler.getTrendingQuestions();
                    setUpData(arrayListTrendingQuestions);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseGetTrending : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetTrending error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetTrending Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpData(ArrayList<TrendingQuestion> trendingQuestions) {
        try {
            if (trendingQuestions != null && trendingQuestions.size() > 0) {
                setEmptyView(false);

                questionsMostFollowAdapter = new QuestionsMostFollowAdapter(getActivity(), trendingQuestions, this);
                rvTrendingQuestionsList.setAdapter(questionsMostFollowAdapter);

            } else {
                setEmptyView(true);
            }

        } catch (Exception e) {
            Debug.i(TAG, "setUpData Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onViewSelect(int position) {
        setQuestion(position);
    }

    private void setQuestion(int position) {
        try {
            visibleQuestionPosition = position;
            if (arrayListTrendingQuestions != null && arrayListTrendingQuestions.size() > 0) {
                txtDate.setText(com.ism.commonsource.utility.Utility.DateFormat(arrayListTrendingQuestions.get(position).getPostedOn()));
                txt_Creator_Name.setText(arrayListTrendingQuestions.get(position).getPostedByUsername());
                txtFollowers.setText(arrayListTrendingQuestions.get(position).getFollowerCount() + " followers");
                txtQuestion.setText(arrayListTrendingQuestions.get(position).getQuestionText());
                trendingId = arrayListTrendingQuestions.get(position).getTrendingId();
                Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListTrendingQuestions.get(position).getPostedByPic(), imgProfilePic, ISMAuthor.options);
            }
        } catch (Exception e) {
            Debug.i(TAG, "setQuestion Exceptions : " + e.getLocalizedMessage());
        }

    }

    /**
     * richeditor text.
     */

    AddQuestionTextDialog addRichTextDialog;
    String htmlText;
    String richEditText;
    ArrayList<String> imageSources = new ArrayList<String>();
    private final int SELECT_PHOTO = 1, SELECT_VIDEO = 2;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_attach_media:

                addRichTextDialog = new AddQuestionTextDialog(getActivity(), (AddQuestionTextDialog.SelectMediaListener) this,
                        (AddQuestionTextDialog.AddTextListener) this, getHtmlQuestionText());
                addRichTextDialog.show();
                break;
        }
    }


    @Override
    public void SetText(String text) {
        htmlText = text;
        richEditText = text;
        etAnswer.setText(Html.fromHtml(text, new HtmlImageGetter(50, 50, getActivity(), null), null));

        Debug.e(TAG, "RichEditor text is:::::::::::" + text);
        Debug.e(TAG, "Text Of Edittext is:::::::::::" + Html.toHtml(etAnswer.getText()));
    }

    @Override
    public void ImagePicker() {

        openImage();

    }

    @Override
    public void VideoPicker() {
        openVideo();
    }


    private String getHtmlQuestionText() {


        Debug.e(TAG, "Original text of edittext is:::::" + Html.toHtml(etAnswer.getText()));

        htmlText = Html.toHtml(etAnswer.getText());
        htmlText = htmlText.replaceAll("<p dir=\"ltr\"><img", "<img");
        htmlText = htmlText.replaceAll("<p dir=\"ltr\"", "<p");
        htmlText = htmlText.replaceAll(".png\"></p>", ".png\">");
        htmlText = htmlText.replaceAll(".jpeg\"></p>", ".jpeg\">");
        htmlText = htmlText.replaceAll(".jpg\"></p>", ".jpg\">");
        htmlText = htmlText.replaceAll(".png\"> </p>", ".png\">");
        htmlText = htmlText.replaceAll(".jpeg\"> </p>", ".jpeg\">");
        htmlText = htmlText.replaceAll(".jpg\"> </p>", ".jpg\">");

        Debug.e(TAG, "Formatted text of edittext is:::::" + htmlText);

        return htmlText;
    }

    public void addImage() {

        Debug.e(TAG, "The RichEditText Is:::" + richEditText);

        if (richEditText != null) {
            String richEditText;
            imageSources.clear();
            XmlPullParserFactory factory = null;
            try {
                factory = XmlPullParserFactory.newInstance();

                XmlPullParser xpp = factory.newPullParser();
                richEditText = getHtmlQuestionText().replace("&nbsp;", " ");
                xpp.setInput(new StringReader(richEditText));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG && "img".equals(xpp.getName())) {
                        //found an image start tag, extract the attribute 'src' from here..

                        if (xpp.getAttributeValue(0).contains("file://")) {
                            String path = xpp.getAttributeValue(0).replace("file://", "");
                            imageSources.add(path);
                        }
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void openImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void openVideo() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/*");
        startActivityForResult(photoPickerIntent, SELECT_VIDEO);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        if (returnedIntent != null) {

            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            if (addRichTextDialog != null && addRichTextDialog.isShowing()) {
                                final Uri imageUri = returnedIntent.getData();
                                String imgPath = Utility.getRealPathFromURI(imageUri, getActivity());
                                addRichTextDialog.insertImage(imgPath);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case SELECT_VIDEO:

                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            if (addRichTextDialog != null && addRichTextDialog.isShowing()) {

                                final Uri videoUri = returnedIntent.getData();
                                String videoPath = Utility.getRealPathFromURI(videoUri, getActivity());
                                addRichTextDialog.insertVideo(videoPath);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    }

}
