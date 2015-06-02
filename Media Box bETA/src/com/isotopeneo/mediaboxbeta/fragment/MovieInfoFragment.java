package com.isotopeneo.mediaboxbeta.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.bean.Movie;
import com.isotopeneo.mediaboxbeta.loader.MovieInfoLoader;
import com.isotopeneo.mediaboxbeta.request.BitmapRequestExecutor;
import com.isotopeneo.mediaboxbeta.util.Constants;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;
import com.isotopeneo.mediaboxbeta.R;

public class MovieInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Movie>{

	private View view;
	private IWatchOnlineApplication application;
	private ImageView poster;
	private TextView description, tv_more_info;
	private final int LOADER_ID = 100;
	private ListView linksList;
	private String imdbID;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imdbID = getActivity().getIntent().getStringExtra(Constants.IMDB_ID);
		LoggerClass.log("Imdb id is " + imdbID);
		view = inflater.inflate(R.layout.movie_info_details, container);
		application = (IWatchOnlineApplication) getActivity().getApplication();
		init();
		return view;
	}
	
	public void init() {
		poster = (ImageView) view.findViewById(R.id.poster);
		description = (TextView) view.findViewById(R.id.description);
		linksList = (ListView) view.findViewById(R.id.linksList);
		tv_more_info = (TextView) view.findViewById(R.id.tv_more_info);
		tv_more_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openWebPage(Constants.IMDB_TITLE_URL + imdbID);
			}
		});
	}

	@Override
	public Loader<Movie> onCreateLoader(int id, Bundle args) {
		LoggerClass.log("onCreateLoader");
		return new MovieInfoLoader(getActivity(), imdbID);
	}

	@Override
	public void onLoadFinished(Loader<Movie> loader, final Movie data) {
		LoggerClass.log("onLoadFinished");
		getLoaderManager().destroyLoader(LOADER_ID);
		application.dismissProgressBar();
		if (null != data) {
			new BitmapRequestExecutor().downloadAsync(poster, data.getPoster(), application, "test");
			getActivity().getActionBar().setTitle(data.getTitle() + " (" + data.getYear() + ") ");
			description.setText(data.getDescription());
			if (null != data.getLinks() && data.getLinks().size() > 0) {
				List<String> links = new ArrayList<String>();
				for (int i = 0; i < data.getLinks().size(); i++) {
					links.add("Quality:" + data.getLinks().get(i).getLt_title() + "\n" + "Age:" + data.getLinks().get(i).getAge());
					
				}
				if (links.size() > 0) {
					ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),  R.layout.simple_list_item_1, links);
					linksList.setAdapter(adapter);
					linksList.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							openWebPage(data.getLinks().get(position).getLink());
						}
					});
				} else {
					showAlertDialog();
				}
				
			}
		} else {
			showAlertDialog();
		}
	}

	public void showAlertDialog() {
		new AlertDialog.Builder(getActivity()).setMessage("Sorry, could not find any info").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish();
			}
		}).show();
	}
	
	public void openWebPage(String url) {
		if (null != url) {
			LoggerClass.log("opening url :" + url);
			Uri webpage = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
			if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
				startActivity(intent);
			}
		}
	}
	
	@Override
	public void onLoaderReset(Loader<Movie> loader) {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LoggerClass.log("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		application.showProgressBar("Fetching links for " + application.getQueryString(), getActivity());
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}

}
