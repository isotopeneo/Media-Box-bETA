package com.isotopeneo.mediaboxbeta.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.isotopeneo.mediaboxbeta.activity.MovieInfoActivity;
import com.isotopeneo.mediaboxbeta.activity.TVShowInfoActivity;
import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.loader.GeneralSearchLoader;
import com.isotopeneo.mediaboxbeta.util.Constants;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;
import com.isotopeneo.mediaboxbeta.R;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>{

	private View view;
	private EditText searchField;
	private IWatchOnlineApplication application;
	private final int REQUEST_CODE = 200;
	private final int LOADER_ID = 100;
	private Spinner selector;
	private ListView searchResults;
	private TextView tv_searchResults;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_details, container);
		application = (IWatchOnlineApplication) getActivity().getApplication();
		init();
		return view;
	}

	public void init() {
		searchField = (EditText) view.findViewById(R.id.searchData);
		tv_searchResults = (TextView) view.findViewById(R.id.tv_searchResults);
		searchResults = (ListView) view.findViewById(R.id.searchResults);
		selector = (Spinner) view.findViewById(R.id.selector);
		ArrayAdapter<String> selectorAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, new String[] {
						"Movies", "TV Show" });
		selector.setAdapter(selectorAdapter);
		searchField.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// final int DRAWABLE_LEFT = 0;
				// final int DRAWABLE_TOP = 1;
				final int DRAWABLE_RIGHT = 2;
				// final int DRAWABLE_BOTTOM = 3;

				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (event.getRawX() >= (searchField.getRight() - searchField
							.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
							.width())) {
						initiateSearch();
						return true;
					}
				}
				return false;
			}
		});
	}

	public void initiateSearch() {
		String searchQuery = searchField.getText().toString();
		if (application.isDeviceConnectedToNetwork()) {
			if (searchQuery.length() > 0) {
				application.setQueryString(searchQuery);
				inititateLoader();
			} else {
				application.showAlertDialog("Please enter a search query",
						getActivity());
			}
		} else {
			application.showAlertDialog(
					"Please check your internet connection", getActivity());
		}

	}

	public void inititateLoader() {
		if (selector.getSelectedItemPosition() == 1) {
			application.setSearchMovie(false);
		} else {
			application.setSearchMovie(true);
		}
		application.showProgressBar("Fetching results for " + application.getQueryString(), getActivity());
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}
	
	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		LoggerClass.log("onCreateLoader");
		return new GeneralSearchLoader(application);
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String data) {
		LoggerClass.log("onLoadFinished");
		application.dismissProgressBar();
		getLoaderManager().destroyLoader(LOADER_ID);
		updateSearchResultsListView(data);
	}

	private void updateSearchResultsListView(String type) {
		if (type.equals(Constants.TV_SHOWS)) {
			if (null != application.getTvShows() && application.getTvShows().size() > 0) {
				List<String> tvShows = new ArrayList<String>();
				for (int i = 0; i < application.getTvShows().size(); i++) {
					String started = application.getTvShows().get(i).getStartedYear();
					String ended = application.getTvShows().get(i).getEndedYear();
					if (null == ended || ended.equals("") || ended.equals("0")) {
						tvShows.add(application.getTvShows().get(i).getName() + "\n" + started);
					} else {
						tvShows.add(application.getTvShows().get(i).getName() + "\n" + started + "-" + ended);
					}
				}
				if (tvShows.size() > 0) {
					tv_searchResults.setVisibility(TextView.VISIBLE);
					ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, tvShows);
					searchResults.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					searchResults.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							LoggerClass.log("onItemClick");
							application.setQueryString(application.getTvShows().get(position).getName());
							Intent tvShowIntent = new Intent(getActivity(),
									TVShowInfoActivity.class);
							tvShowIntent.putExtra(Constants.TVRAGE_ID, application.getTvShows().get(position).getId());
							startActivityForResult(tvShowIntent, REQUEST_CODE);
						}
					});
				} else {
					application.showAlertDialog("Sorry, could not find any results for this query", getActivity());
				}
				
			} else {
				application.showAlertDialog("Sorry, could not find any results for this query", getActivity());
			}
		} else {
			if (null != application.getMovies() && application.getMovies().size() > 0) {
				List<String> allMovies = new ArrayList<String>();
				for (int i = 0; i < application.getMovies().size(); i++) {
					allMovies.add(application.getMovies().get(i).getTitle() + "\n" + application.getMovies().get(i).getYear());
				}
				if (allMovies.size() > 0) {
					tv_searchResults.setVisibility(TextView.VISIBLE);
					ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, allMovies);
					searchResults.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					searchResults.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							LoggerClass.log("onItemClick");
							application.setQueryString(application.getMovies().get(position).getTitle());
							Intent movieIntent = new Intent(getActivity(),
									MovieInfoActivity.class);
							movieIntent.putExtra(Constants.IMDB_ID, application.getMovies().get(position).getImdbID());
							startActivityForResult(movieIntent, REQUEST_CODE);
						}
					});
				} else {
					application.showAlertDialog("Sorry, could not find any results for this query", getActivity());
				}
			} else {
				application.showAlertDialog("Sorry, could not find any results for this query", getActivity());
			}
		}

	}

	@Override
	public void onLoaderReset(Loader<String> loader) {
	}
}
