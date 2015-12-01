package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.FavoriteBooksAdapter;
import com.ism.author.adapter.SuggestedBookAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.HorizontalListView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 28/10/15.
 */
public class MyDeskBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, AuthorHostActivity.AddToFavouriteListner
{


    private static final String TAG = MyDeskBooksFragment.class.getSimpleName();
    private View view;
    private HorizontalListView lvBooks;
    private AuthorHostActivity activityHost;
    private ResponseHandler responseHandler;
    private ArrayList<BookData> arrayListFavBooks = new ArrayList<>();
    private ArrayList<BookData> arrayListSuggestedBooks = new ArrayList<>();

    private TextView txtFavEmpty;
    private TextView txtSuggestedEmpty;
    private TextView txtSuggestedBooks;
    private TextView txtFavBooks;
    private ImageView imgFavSearch;
    private EditText etFavSearch;
    private ImageView imgSuggestedSearch;
    private EditText etSuggestedSearch;
    private HorizontalListView listViewFavBooks;
    private HorizontalListView listViewSuggestedBooks;
    private FavoriteBooksAdapter favoriteBooksAdapter;
    private SuggestedBookAdapter suggestedBooksAdapter;
    private ArrayList<String> arrayListFav = new ArrayList<>();
    private ArrayList<String> arrayListUnFav = new ArrayList<>();
    private ArrayList<String> arrayListAddBooksToLibrary = new ArrayList<>();
    private ArrayList<String> arrayListRemoveBooksFromLibrary = new ArrayList<>();

    public static MyDeskBooksFragment newInstance() {
        MyDeskBooksFragment fragBooks = new MyDeskBooksFragment();
        return fragBooks;
    }

    @Override
    public void onSearchFav(ArrayList<BookData> arrayList) {
        setUpFavList(arrayList);
    }

    @Override
    public void onSearchSuggested(ArrayList<BookData> arrayList) {
        setUpSuggestedList(arrayList);
    }


    public MyDeskBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk_books, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        txtFavEmpty = (TextView) view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty = (TextView) view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedBooks = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavBooks = (TextView) view.findViewById(R.id.txt_fav_books);
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
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
//        suggestedBooksAdapter = new SuggestedBookAdapter(getActivity(), arrayListSuggestedBooks, this, this);
//        favoriteBooksAdapter = new FavoriteBooksAdapter(getActivity(), arrayListSuggestedBooks, this, this);
        callApiGetBooksForUser();
        imgFavSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFavSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etFavSearch.setVisibility(View.GONE);
                    View view = getActivity().getCurrentFocus();
                    Utility.hideKeyboard(getActivity(), getView());
                    setUpFavList(arrayListFavBooks);
                    etFavSearch.setText("");

                } else {
                    Utility.startSlideAnimation(etFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                    Utility.startSlideAnimation(imgFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                    etFavSearch.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etFavSearch, getActivity());
                }

            }
        });
        imgSuggestedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSuggestedSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etSuggestedSearch.setVisibility(View.GONE);
                    View view = getActivity().getCurrentFocus();
                    Utility.hideKeyboard(getActivity(), getView());
                    setUpSuggestedList(arrayListSuggestedBooks);
                    etSuggestedSearch.setText("");
                } else {
                    Utility.startSlideAnimation(etSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                    Utility.startSlideAnimation(imgSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                    etSuggestedSearch.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSuggestedSearch, getActivity());
                }
            }
        });
        etFavSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            favoriteBooksAdapter.getFilter()
                                    .filter(etFavSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });
        etSuggestedSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            suggestedBooksAdapter.getFilter()
                                    .filter(etSuggestedSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });

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
            activityHost = (AuthorHostActivity) activity;
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
            //callApiGetBooksForUser();
            arrayListFav.add(arrayListSuggestedBooks.get(addToFavItem).getBookId());
            arrayListFavBooks.add(arrayListSuggestedBooks.get(addToFavItem));
            arrayListSuggestedBooks.remove(addToFavItem);
            favoriteBooksAdapter.notifyDataSetChanged();
            suggestedBooksAdapter.notifyDataSetChanged();
            setVisibilityFavItems(arrayListFavBooks.size());
            setVisibilitySuggestedItems(arrayListSuggestedBooks.size());
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
            favoriteBooksAdapter.notifyDataSetChanged();
            suggestedBooksAdapter.notifyDataSetChanged();
            setVisibilityFavItems(arrayListFavBooks.size());
            setVisibilitySuggestedItems(arrayListSuggestedBooks.size());

        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (arrayListFav != null || arrayListUnFav != null)
            callApiAddResourceToFav();
        if (arrayListAddBooksToLibrary != null || arrayListRemoveBooksFromLibrary != null)
            callApiManageLibrary();

    }

    private void callApiAddResourceToFav() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRoleId(Global.role);
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
            Debug.i(TAG, "callApiAddResourceToFav Exception : " + e.getLocalizedMessage());
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

    public void setVisibilityFavItems(int size) {

        if (size == 0) {
            txtFavEmpty.setVisibility(View.VISIBLE);
            listViewFavBooks.setVisibility(View.GONE);
        } else {
            txtFavEmpty.setVisibility(View.GONE);
            listViewFavBooks.setVisibility(View.VISIBLE);
        }
    }

    public void setVisibilitySuggestedItems(int size) {
        if (size == 0) {
            txtSuggestedEmpty.setVisibility(View.VISIBLE);
            listViewSuggestedBooks.setVisibility(View.GONE);
        } else {
            txtSuggestedEmpty.setVisibility(View.GONE);
            listViewSuggestedBooks.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
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

    @Override
    public void onAddToLibrary(String id) {
        arrayListAddBooksToLibrary.add(id);
    }

    @Override
    public void onRemoveFromLibrary(String id) {
        arrayListRemoveBooksFromLibrary.add(id);

    }
}