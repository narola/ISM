package com.ism.fragment.myAuthor.authorDesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ism.adapter.myAuthor.AuthorBooksAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 1/1/2016.
 */
public class AuthorDeskBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.BooksListner {


    private static final String TAG = AuthorDeskBooksFragment.class.getSimpleName();
    private View view;
    public RecyclerView listViewAuthorBooks;
    AuthorBooksAdapter authorBooksAdapter;
    private ArrayList<BookData> arrayListAuthorBook;
    public static TextView txtEmpty;
    private TextView txtAuthorBooks;
    private ResponseHandler responseHandler;
    private ImageView imgSearch;
    private EditText etSearch;

    ArrayList<String> arrayListFav = new ArrayList<String>();
    ArrayList<String> arrayListUnFav = new ArrayList<String>();
    ArrayList<String> arrayListAddBooksToLibrary = new ArrayList<String>();
    ArrayList<String> arrayListRemoveBooksFromLibrary = new ArrayList<String>();
    private String strSearch = "";
    private ImageView  imgNextFav, imgPrevFav;
    private LinearLayoutManager layoutManagerFav;
    private HostActivity activityHost;
    private FragmentListener fragmentListener;

    public static AuthorDeskBooksFragment newInstance() {
        AuthorDeskBooksFragment fragment = new AuthorDeskBooksFragment();
        return fragment;
    }

    @Override
    public void onAddToLibrary(String id) {
        Log.e(TAG, "onAddToLibrary" + id);
        try {
            arrayListAddBooksToLibrary.add(id);
        } catch (Exception e) {
            Debug.e(TAG, "onAddToLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromLibrary(String id) {
        Log.e(TAG, "onRemoveFromLibrary" + id);
        try {
            arrayListRemoveBooksFromLibrary.add(id);
        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    public AuthorDeskBooksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authors_books, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        txtEmpty = (TextView) view.findViewById(R.id.txt_fav_empty);
        txtAuthorBooks = (TextView) view.findViewById(R.id.txt_fav_books);
        imgSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        imgNextFav = (ImageView) view.findViewById(R.id.img_next_fav);
        imgPrevFav = (ImageView) view.findViewById(R.id.img_prev_fav);
        etSearch = (EditText) view.findViewById(R.id.et_search_fav);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAuthorBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmpty.setText(R.string.no_books_available);
        listViewAuthorBooks = (RecyclerView) view.findViewById(R.id.lv_fav_books);
        layoutManagerFav = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listViewAuthorBooks.setLayoutManager(layoutManagerFav);

        callApiGetBooksOfAuthor();
        onClicks();

    }


    private void onClicks() {
        try {
            imgNextFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listViewAuthorBooks.getLayoutManager().smoothScrollToPosition(listViewAuthorBooks, null, layoutManagerFav.findLastCompletelyVisibleItemPosition() + 1);
                }

            });

            imgPrevFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listViewAuthorBooks.getLayoutManager().smoothScrollToPosition(listViewAuthorBooks, null, layoutManagerFav.findFirstCompletelyVisibleItemPosition() > 0 ? layoutManagerFav.findFirstCompletelyVisibleItemPosition() - 1 : 0);
                }
            });

            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickImgSearch();
                }
            });

            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    strSearch = "";
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    strSearch = strSearch + s;
                    setUpList(onSearch(arrayListAuthorBook, strSearch));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onClicks : " + e.getLocalizedMessage());
        }
    }

    private void onClickImgSearch() {
        try {
            if (etSearch.getVisibility() == View.VISIBLE) {
                etSearch.setVisibility(View.GONE);
                Utility.hideKeyboard(getActivity(), getView());
                etSearch.setText("");

            } else {
                Utility.startSlideAnimation(etSearch, etSearch.getWidth(), 0, 0, 0);
                Utility.startSlideAnimation(imgSearch, etSearch.getWidth(), 0, 0, 0);
                etSearch.setVisibility(View.VISIBLE);
                Utility.showSoftKeyboard(etSearch, getActivity());
            }

        } catch (Exception e) {
            Log.e(TAG, "onClickImgSearch : " + e.getLocalizedMessage());
        }
    }

    public ArrayList<BookData> onSearch(ArrayList<BookData> arrayList, String charSequence) {
        ArrayList<BookData> bookDatas = new ArrayList<>();
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getBookName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    bookDatas.add(arrayList.get(i));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onSearch: " + e.getLocalizedMessage());
        }
        return bookDatas;
    }

    public void callApiGetBooksOfAuthor() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(activityHost.getBundle().getString(AppConstant.AUTHOR_ID));// change it with author id
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_BOOKS_BY_AUTHOR);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Log.e(TAG, "callApiGetBooksForUser Exception : " + e.toString());
        }
    }

    private void onResponseManageLibrary(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseManageLibrary success");
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


    private void onResponseBooksByAuthor(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListAuthorBook = responseHandler.getAuthorBooks();
                    setUpList(arrayListAuthorBook);
                    Log.e(TAG, "onResponseBooksByAuthor success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseBooksByAuthor Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseBooksByAuthor api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseBooksByAuthor Exception : " + e.toString());
        }
    }

    public void setVisibilityItems(int size) {

        if (size == 0) {
            txtEmpty.setVisibility(View.VISIBLE);
            listViewAuthorBooks.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            listViewAuthorBooks.setVisibility(View.VISIBLE);
        }
    }

    public void setUpList(ArrayList<BookData> arrayList) {
        try {
            authorBooksAdapter = new AuthorBooksAdapter(getActivity(), arrayList, this);
            listViewAuthorBooks.setAdapter(authorBooksAdapter);
            authorBooksAdapter.notifyDataSetChanged();
            setVisibilityItems(arrayList.size());
        } catch (Exception e) {
            Debug.e(TAG, "setUpList Exceptions :" + e.getLocalizedMessage());
        }
    }


    @Override
    public void onAddToFav(int addToFavItem) {
        Log.e(TAG, "OnAddToFav" + addToFavItem);
        try {
            setVisibilityItems(arrayListAuthorBook.size());
            authorBooksAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromFav(int position) {
        Log.e(TAG, "onRemoveFromFav" + position);
        try {
            arrayListUnFav.add(arrayListAuthorBook.get(position).getBookId());
//            arrayListAuthorBook.remove(position);
            setVisibilityItems(arrayListAuthorBook.size());
            authorBooksAdapter.notifyDataSetChanged();
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
                Log.e(TAG, "Attributes object :" + attribute);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_FAVOURITES);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Log.e(TAG, "callApiAddResourceToFav Exception : " + e.getLocalizedMessage());
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
                Log.e(TAG, "Attributes object :" + attribute);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_BOOK_LIBRARY);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Log.e(TAG, "callApiManageLibrary Exception : " + e.getLocalizedMessage());
        }
    }

    private void onResponseAddResourceToFavorite(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                Log.e(TAG, "Response object :" + object);
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseAddResourceToFavorite success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseAddResourceToFavorite Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseAddResourceToFavorite api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddResourceToFavorite Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_BOOKS_BY_AUTHOR:
                    onResponseBooksByAuthor(object, error);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost=(HostActivity)activity;
            fragmentListener = (FragmentListener) activity;
            if (fragmentListener != null) {
                fragmentListener.onFragmentAttached(AuthorDeskFragment.FRAGMENT_BOOKS);
            }

        } catch (Exception e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach fragment ");
            if (fragmentListener != null) {
                fragmentListener.onFragmentDetached(AuthorDeskFragment.FRAGMENT_BOOKS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragmentListener = null;
    }
}
