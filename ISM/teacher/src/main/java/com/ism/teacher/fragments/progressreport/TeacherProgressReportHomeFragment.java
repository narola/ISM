package com.ism.teacher.fragments.progressreport;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.customizechart.MyYAxisValueFormatter;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.ClassPerformance;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import model.teachermodel.ROClassPerformance;
import realmhelper.TeacherHelper;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherProgressReportHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, OnChartValueSelectedListener {

    private static final String TAG = TeacherProgressReportHomeFragment.class.getSimpleName();

    //Views
    //Top layout views
    private Spinner spExamType;
    private TextView tvDateRange;
    private EditText etStartdate, etEnddate;
    private ImageView imgCalendarFrom, imgCalendarTo, imgGraphReport, imgListReport;
    private LinearLayout llReportFrom, llReportTo;
    //ArrayList
    private List<String> arrListDefault = new ArrayList<>();

    //Realm
    private TeacherHelper teacherHelper;
    private RealmResults<ROClassPerformance> arrListClassPerformances = null;

    //Chart
    BarChart chartAvgscoreVsStudent, chartAvgscoreVsSubject;
    float sum = 0;
    float average = 0;
    int totalStudents = 0;
    /**
     *     this will add rows based on total students >10
     *     If  total students >10 then increment interval by 10(i.e 11 to 20 and so on)
     */

    int countTotalColumnsOnXaxis =0;


    public static TeacherProgressReportHomeFragment newInstance() {
        TeacherProgressReportHomeFragment teacherQuizHomeFragment = new TeacherProgressReportHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherProgressReportHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_report_average, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        teacherHelper = new TeacherHelper(getActivity());

        //initialize top views
        spExamType = (Spinner) rootview.findViewById(R.id.sp_exam_type);
        arrListDefault.add(Utility.getString(R.string.select, getActivity()));
        Adapters.setUpSpinner(getActivity(), spExamType, arrListDefault, Adapters.ADAPTER_NORMAL);
        tvDateRange = (TextView) rootview.findViewById(R.id.tv_date_range);
        etStartdate = (EditText) rootview.findViewById(R.id.et_startdate);
        etEnddate = (EditText) rootview.findViewById(R.id.et_enddate);
        imgCalendarFrom = (ImageView) rootview.findViewById(R.id.img_calendar_from);
        imgCalendarTo = (ImageView) rootview.findViewById(R.id.img_calendar_to);
        imgGraphReport = (ImageView) rootview.findViewById(R.id.img_graph_report);
        imgListReport = (ImageView) rootview.findViewById(R.id.img_list_report);
        llReportFrom = (LinearLayout) rootview.findViewById(R.id.ll_report_from);
        llReportTo = (LinearLayout) rootview.findViewById(R.id.ll_report_to);

        llReportFrom.setOnClickListener(this);
        llReportTo.setOnClickListener(this);
        imgGraphReport.setOnClickListener(this);
        imgListReport.setOnClickListener(this);


        //charts
        chartAvgscoreVsStudent = (BarChart) rootview.findViewById(R.id.chart_avgscore_vs_student);
        chartAvgscoreVsSubject = (BarChart) rootview.findViewById(R.id.chart_avgscore_vs_subject);

        applyFonts();
        callApiGetReportData();

    }

    private void applyFonts() {
        tvDateRange.setTypeface(Global.myTypeFace.getRalewayRegular());
        etStartdate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEnddate.setTypeface(Global.myTypeFace.getRalewayRegular());
    }

    private void callApiGetReportData() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_580);
                attribute.setRoleId(WebConstants.TEACHER_ROLE_ID);
                attribute.setLastSyncDate("");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_REPORT_DATA);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_REPORT_DATA:
                    onResponseGetReportData(object, error);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG, e.getLocalizedMessage());

        }
    }

    private void onResponseGetReportData(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "onResponseGetReportData success");

                    if (responseHandler.getClassPerformance().size() > 0) {
//                        addClassPerformanceToTable(responseHandler.getClassPerformance());

                        //for storing only the first record from response
                        addClassPerformanceToTable(responseHandler.getClassPerformance().get(0));
                        calculateAverageAndDrawGraph();
                    }

//                    setUpData();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetReportData Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetReportData api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetReportData Exception : " + e.toString());
        }
    }


    /**
     * using this method to store just first record from response
     *
     * @param classPerformance
     */

    private void addClassPerformanceToTable(ClassPerformance classPerformance) {
        teacherHelper.addClassPerformance(Global.getRealmDataModel.getRealmClassPerformance(classPerformance));
    }

    /**
     * use below method when response from api gets corrected
     *
     * @param arrListClassPerformance
     */

    private void addClassPerformanceToTable(ArrayList<ClassPerformance> arrListClassPerformance) {

        for (ClassPerformance classPerformance : arrListClassPerformance) {
            teacherHelper.addClassPerformance(Global.getRealmDataModel.getRealmClassPerformance(classPerformance));
        }
    }

    /**
     * Calculating average percentage based on number of exam records in class performance
     */


    private void calculateAverageAndDrawGraph() {
        try {

            arrListClassPerformances = teacherHelper.getAllClassPerformances();

            if (arrListClassPerformances.size() > 0) {
                for (int i = 0; i < arrListClassPerformances.size(); i++) {
                    sum += Double.valueOf(arrListClassPerformances.get(i).getStudentsScore().get(i).getPercentage());
                    totalStudents += arrListClassPerformances.get(i).getStudentsScore().size();
                }

                average = sum / arrListClassPerformances.size();
                Debug.e(TAG, "sum is:" + sum + " and avg is:" + average + " and total students are:" + totalStudents);
                chartAvgscoreVsStudent.setDrawBarShadow(false);
                chartAvgscoreVsStudent.setDrawValueAboveBar(true);

                chartAvgscoreVsStudent.setDescription("Avg.Score V/S No of Student");
                // if more than 60 entries are displayed in the chart, no values will be
                // drawn
                chartAvgscoreVsStudent.setMaxVisibleValueCount(100);
                // scaling can now only be done on x- and y-axis separately
                chartAvgscoreVsStudent.setPinchZoom(false);

                chartAvgscoreVsStudent.setDrawGridBackground(false);
                XAxis xAxis = chartAvgscoreVsStudent.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(Global.myTypeFace.getRalewayRegular());
                xAxis.setDrawGridLines(false);
                xAxis.setSpaceBetweenLabels(2);

                YAxisValueFormatter custom = new MyYAxisValueFormatter();

                YAxis leftAxis = chartAvgscoreVsStudent.getAxisLeft();
                leftAxis.setTypeface(Global.myTypeFace.getRalewayRegular());
                leftAxis.setDrawGridLines(false);
                leftAxis.setLabelCount(8, false);
                leftAxis.setValueFormatter(custom);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setSpaceTop(15f);

                YAxis rightAxis = chartAvgscoreVsStudent.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setTypeface(Global.myTypeFace.getRalewayRegular());
                rightAxis.setLabelCount(0, true);
                rightAxis.setValueFormatter(custom);
                rightAxis.setSpaceTop(15f);


                /**
                 * countTotalColumnsOnXaxis
                 */


                for (int i = 0; i < totalStudents; i+=10) {
                    countTotalColumnsOnXaxis++;
                }
                setDataOnGraph(countTotalColumnsOnXaxis);
                chartAvgscoreVsStudent.animateY(1000);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setDataOnGraph(int count) {

        ArrayList<String> xVals = new ArrayList<String>();

        int startValue = 10;

        for (int i = 0; i < count; i++) {
            xVals.add(String.valueOf(startValue));
             startValue += 10;
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(average, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(0.0f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(Global.myTypeFace.getRalewayRegular());

        chartAvgscoreVsStudent.setData(data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_report_from:
                break;
            case R.id.ll_report_to:
                break;
            case R.id.img_graph_report:
                break;
            case R.id.img_list_report:
                break;
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
