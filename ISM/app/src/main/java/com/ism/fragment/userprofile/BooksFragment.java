package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.FavoriteBooksAdapter;
import com.ism.adapter.SuggestedBookAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class BooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.BooksListner {

    private static final String TAG = BooksFragment.class.getSimpleName();
    private View view;
    private HostActivity activityHost;
    public static HorizontalListView listViewFavBooks;
    FavoriteBooksAdapter favoriteBooksAdapter;
    private ArrayList<BookData> arrayListFavBooks;
    public static HorizontalListView listViewSuggestedBooks;
    SuggestedBookAdapter suggestedBooksAdapter;
    private ArrayList<BookData> arrayListSuggestedBooks;
    public static TextView txtSuggestedEmpty;
    public static TextView txtFavEmpty;
    private TextView txtSuggestedBooks;
    private TextView txtFavBooks;
    private int addToFavItem;
    private ResponseHandler responseHandler;
    private ImageView imgFavSearch;
    private EditText etFavSearch;
    private ImageView imgSuggestedSearch;
    private EditText etSuggestedSearch;
    private int resourceId;
    private boolean favourite;
    ArrayList<String> arrayListFav = new ArrayList<String>();
    ArrayList<String> arrayListUnFav = new ArrayList<String>();
    ArrayList<String> arrayListAddBooksToLibrary = new ArrayList<String>();
    ArrayList<String> arrayListRemoveBooksFromLibrary = new ArrayList<String>();
    private HostActivity.BooksListner booksListner;
    private String strSearch = "";
    private ImageView imgNextSuggested, imgNextFav, imgPrevFav, imgPrevSuggested;
    private Handler mHandler;
    private long mInitialDelay = 300;
    private long mRepeatDelay = 100;
    private int prevItemFav;
    private int nxtItemFav;
    private int favNavigation = 0;
    private int suggestedNavigation=0;

    public static BooksFragment newInstance() {
        BooksFragment fragment = new BooksFragment();
        return fragment;
    }

    @Override
    public void onAddToLibrary(String id) {
        Debug.i(TAG, "onAddToLibrary" + id);
        try {
            //callApiGetBooksForUser();
            arrayListAddBooksToLibrary.add(id);

        } catch (Exception e) {
            Debug.e(TAG, "onAddToLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromLibrary(String id) {
        Debug.i(TAG, "onRemoveFromLibrary" + id);
        try {
            //callApiGetBooksForUser();
            arrayListRemoveBooksFromLibrary.add(id);

        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onSearchFav(ArrayList<BookData> arrayList) {
        setUpFavList(arrayList);
    }

    @Override
    public void onSearchSuggested(ArrayList<BookData> arrayList) {
        setUpSuggestedList(arrayList);
    }


    public BooksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile_books, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {

        txtFavEmpty = (TextView) view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty = (TextView) view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedBooks = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavBooks = (TextView) view.findViewById(R.id.txt_fav_books);
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        imgNextFav = (ImageView) view.findViewById(R.id.img_next_fav);
        imgPrevFav = (ImageView) view.findViewById(R.id.img_prev_fav);
        imgNextSuggested = (ImageView) view.findViewById(R.id.img_next_suggested);
        imgPrevSuggested = (ImageView) view.findViewById(R.id.img_prev_suggested);
        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);
        //set typeface
        txtFavEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewFavBooks = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggestedBooks = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);
        prevItemFav = 0;
        nxtItemFav = 0;
        listViewFavBooks.setSelection(0);
        callApiGetBooksForUser();
        onClicks();

    }

    private void onClicks() {
        try {

//            imgNextFav.setOnTouchListener(new View.OnTouchListener() {
//
//
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    Utility.showToast(getActivity(),"Next");
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            if (mHandler != null)
//                                return true;
//                            mHandler = new Handler();
//                            mHandler.postDelayed(mAction, mInitialDelay);
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            if (mHandler == null)
//                                return true;
//                            mHandler.removeCallbacks(mAction);
//                            mHandler = null;
//                            break;
//                    }
//                    return false;
//                }
//
//                Runnable mAction = new Runnable() {
//                    @Override
//                    public void run() {
//                        listViewFavBooks.scrollTo((int) listViewFavBooks.getScrollX() + 1, (int) listViewFavBooks.getScrollY());
//                        mHandler.postDelayed(mAction, mRepeatDelay);
//                    }
//                };
//            });
            imgNextFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    Debug.i(TAG, "Next-> favNavigation : " + favNavigation);
//                    if (favNavigation > 0) {
//                        --favNavigation;
//                        listViewFavBooks.scrollTo((int) listViewFavBooks.getScrollX() + 150, (int) listViewFavBooks.getScrollY());
//                    } else {
//                        Debug.i(TAG, "else item : " + listViewFavBooks.getSelectedItemPosition());
//                    }
                    listViewFavBooks.setScrollX(80);

                }
            });
            imgPrevFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    Debug.i(TAG, "Prev-> favNavigation : " + favNavigation);
//                    if (favNavigation <-1) {
//                        ++favNavigation;
//                        listViewFavBooks.scrollTo((int) listViewFavBooks.getScrollX() - 150, (int) listViewFavBooks.getScrollY());
//                    } else {
//                        Debug.i(TAG, "else item : " + listViewFavBooks.getSelectedItemPosition());
//                    }
                    listViewFavBooks.setScrollX(-80);
                }
            });

            imgNextSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    Debug.i(TAG, "Next-> favNavigation : " + favNavigation);
                    if (suggestedNavigation > 0) {
                        --suggestedNavigation;
                        listViewSuggestedBooks.scrollTo((int) listViewSuggestedBooks.getScrollX() + 150, (int) listViewSuggestedBooks.getScrollY());
                    } else {
                        Debug.i(TAG, "else item : " + listViewFavBooks.getSelectedItemPosition());
                    }

                }
            });
            imgPrevSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    if (suggestedNavigation <-1) {
                        ++suggestedNavigation;
                        listViewSuggestedBooks.scrollTo((int) listViewSuggestedBooks.getScrollX() - 150, (int) listViewSuggestedBooks.getScrollY());
                    } else {
                        Debug.i(TAG, "else item : " + listViewFavBooks.getSelectedItemPosition());
                    }
                }
            });
            imgFavSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickImgFavSearch();
                }
            });
            imgSuggestedSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickImgSuggetedSearch();

                }
            });
//        etFavSearch
//                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId,
//                                                  KeyEvent event) {
//                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                            favoriteBooksAdapter.getFilter()
//                                    .filter(etFavSearch.getText().toString()
//                                            .trim());
//                            Utility.hideKeyboard(getActivity(), getView());
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//        etSuggestedSearch
//                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId,
//                                                  KeyEvent event) {
//                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                            suggestedBooksAdapter.getFilter()
//                                    .filter(etSuggestedSearch.getText().toString()
//                                            .trim());
//                            Utility.hideKeyboard(getActivity(), getView());
//                            return true;
//                        }
//                        return false;
//                    }
//                });

            etSuggestedSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    strSearch = "";
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    strSearch = strSearch + s;
                    setUpSuggestedList(onSearch(arrayListSuggestedBooks, strSearch));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etFavSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    strSearch = "";
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    strSearch = strSearch + s;
                    setUpFavList(onSearch(arrayListFavBooks, strSearch));
//                favoriteBooksAdapter.getFilter()
//                        .filter(etFavSearch.getText().toString()
//                                .trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "onClicks : " + e.getLocalizedMessage());
        }
    }

    private void onClickImgSuggetedSearch() {
        try {
            if (etSuggestedSearch.getVisibility() == View.VISIBLE) {
                etSuggestedSearch.setVisibility(View.GONE);
                Utility.hideKeyboard(getActivity(), getView());
                // setUpSuggestedList(arrayListSuggestedBooks);
                etSuggestedSearch.setText("");
            } else {
                Utility.startSlideAnimation(etSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                Utility.startSlideAnimation(imgSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                etSuggestedSearch.setVisibility(View.VISIBLE);
                Utility.showSoftKeyboard(etSuggestedSearch, getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onClickImgSuggetedSearch : " + e.getLocalizedMessage());
        }
    }

    private void onClickImgFavSearch() {
        try {
            if (etFavSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                etFavSearch.setVisibility(View.GONE);
                Utility.hideKeyboard(getActivity(), getView());
                //setUpFavList(arrayListFavBooks);
                etFavSearch.setText("");

            } else {
                Utility.startSlideAnimation(etFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                Utility.startSlideAnimation(imgFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                etFavSearch.setVisibility(View.VISIBLE);
                Utility.showSoftKeyboard(etFavSearch, getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onClickImgFavSearch : " + e.getLocalizedMessage());
        }
    }

    public ArrayList<BookData> onSearch(ArrayList<BookData> arrayList, String charSequence) {
        ArrayList<BookData> bookDatas = new ArrayList<>();

        try {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getBookName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
//            if (arrayList.get(i).getAuthorName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())|| arrayList.get(i).getBookName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    bookDatas.add(arrayList.get(i));
                    // Debug.i(TAG, "i :" + i + " String : " + charSequence);
                }
                // Debug.i(TAG, "i :" + i + " String : " + charSequence);
            }
        } catch (Exception e) {
            Debug.i(TAG, "onSearch: " + e.getLocalizedMessage());
        }
        return bookDatas;
    }

    public void callApiGetBooksForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_BOOKS_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetBooksForUser Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_BOOKS_FOR_USER:
                    onResponseUserBooks(object, error);
                    break;
                case WebConstants.MANAGE_FAVOURITES:
                    onResponseAddResourceToFavorite(object, error);
                    break;
                case WebConstants.MANAGE_BOOK_LIBRARY:
                    onResponseManageLibrary(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseManageLibrary(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseManageLibrary success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseManageLibrary Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseManageLibrary api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseManageLibrary Exception : " + e.toString());
        }
    }


    private void onResponseUserBooks(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListFavBooks = responseHandler.getBooks().get(0).getFavorite();
                    arrayListSuggestedBooks = responseHandler.getBooks().get(0).getSuggested();
                    setUpSuggestedList(arrayListSuggestedBooks);
                    setUpFavList(arrayListFavBooks);
                    Debug.i(TAG, "onResponseUserBooks success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseUserBooks Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseUserBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserBooks Exception : " + e.toString());
        }
    }

    public void setVisibilityFavItems(int size) {

        if (size == 0) {
            txtFavEmpty.setVisibility(View.VISIBLE);
            listViewFavBooks.setVisibility(View.GONE);
        } else {
            txtFavEmpty.setVisibility(View.GONE);
            listViewFavBooks.setVisibility(View.VISIBLE);
            favNavigation = size % 4;
            Debug.i(TAG, "favNavigation : " + favNavigation);
            Debug.i(TAG, "favNavigation : " + Math.round(size/4));
            Debug.i(TAG, "favNavigation : " + size/4);

        }
    }

    public void setVisibilitySuggestedItems(int size) {
        if (size == 0) {
            txtSuggestedEmpty.setVisibility(View.VISIBLE);
            listViewSuggestedBooks.setVisibility(View.GONE);
        } else {
            txtSuggestedEmpty.setVisibility(View.GONE);
            listViewSuggestedBooks.setVisibility(View.VISIBLE);
            suggestedNavigation = size % 4;
        }
    }

    public void setUpFavList(ArrayList<BookData> arrayListFavBooks) {
        try {

            favoriteBooksAdapter = new FavoriteBooksAdapter(getActivity(), arrayListFavBooks, this);
            listViewFavBooks.setAdapter(favoriteBooksAdapter);
            favoriteBooksAdapter.notifyDataSetChanged();
            setVisibilityFavItems(arrayListFavBooks.size());

        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }

    public void setUpSuggestedList(ArrayList<BookData> arrayListSuggestedBooks) {
        try {
            suggestedBooksAdapter = new SuggestedBookAdapter(getActivity(), arrayListSuggestedBooks, this);
            listViewSuggestedBooks.setAdapter(suggestedBooksAdapter);
            suggestedBooksAdapter.notifyDataSetChanged();
            setVisibilitySuggestedItems(arrayListSuggestedBooks.size());
        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            //   activityHost.setListenerHostAboutMe(this);
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
    }


    @Override
    public void onAddToFav(int addToFavItem) {
        Debug.i(TAG, "OnAddToFav" + addToFavItem);
        try {
            arrayListFav.add(arrayListSuggestedBooks.get(addToFavItem).getBookId());
            arrayListFavBooks.add(arrayListSuggestedBooks.get(addToFavItem));
            arrayListSuggestedBooks.remove(addToFavItem);
            setVisibilityFavItems(arrayListFavBooks.size());
            setVisibilitySuggestedItems(arrayListSuggestedBooks.size());
            favoriteBooksAdapter.notifyDataSetChanged();
            suggestedBooksAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromFav(int position) {
        Debug.i(TAG, "onRemoveFromFav" + position);
        try {
            arrayListUnFav.add(arrayListFavBooks.get(position).getBookId());
            arrayListSuggestedBooks.add(arrayListFavBooks.get(position));
            arrayListFavBooks.remove(position);
            setVisibilityFavItems(arrayListFavBooks.size());
            setVisibilitySuggestedItems(arrayListSuggestedBooks.size());
            favoriteBooksAdapter.notifyDataSetChanged();
            suggestedBooksAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (arrayListFav.size() != 0 || arrayListUnFav.size() != 0)
                callApiAddResourceToFav();
            if (arrayListAddBooksToLibrary.size() != 0 || arrayListRemoveBooksFromLibrary.size() != 0)
                callApiManageLibrary();
        } catch (ClassCastException e) {
            Log.e(TAG, "onPause Exception : " + e.toString());
        }


    }

    private void callApiAddResourceToFav() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                if (arrayListFav == null)
                    attribute.setFavResourceId(new ArrayList<String>());
                else {
                    attribute.setFavResourceId(arrayListFav);// get all resource ids from suggested list to add resource ids in user favourites
                }
                if (arrayListUnFav == null)
                    attribute.setUnfavoriteResourceId(new ArrayList<String>());// get All the resource ids from favourite list to add resource id in user unfavourites
                else {
                    attribute.setUnfavoriteResourceId(arrayListUnFav);// get All the resource ids from favourite list to add resource id in user unfavourites
                }
                attribute.setResourceName(AppConstant.RESOURCE_BOOKS);
                Debug.i(TAG, "Attributes object :" + attribute);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_FAVOURITES);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiAddResourceToFav Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiManageLibrary() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setAddBookId(arrayListAddBooksToLibrary);
                attribute.setRemoveBookId(arrayListRemoveBooksFromLibrary);
                Debug.i(TAG, "Attributes object :" + attribute);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_BOOK_LIBRARY);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiManageLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    private void onResponseAddResourceToFavorite(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                Debug.i(TAG, "Response object :" + object);
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite Failed");
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseAddResourceToFavorite api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddResourceToFavorite Exception : " + e.toString());
        }
    }


}