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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.bean.Episode;
import com.isotopeneo.mediaboxbeta.loader.EpisodeInfoLoader;
import com.isotopeneo.mediaboxbeta.util.Constants;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;
import com.isotopeneo.mediaboxbeta.R;

public class EpisodeInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Episode>{

	private View view;
	private IWatchOnlineApplication application;
	private TextView description;
	private final int LOADER_ID = 100;
	private ListView linksList;
	private String episodeInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		episodeInfo = getActivity().getIntent().getStringExtra(Constants.EPISODE_INFO);
		LoggerClass.log("Episode info " + episodeInfo);
		view = inflater.inflate(R.layout.episode_info_details, container);
		application = (IWatchOnlineApplication) getActivity().getApplication();
		init();
		return view;
	}
	
	public void init() {
		description = (TextView) view.findViewById(R.id.description);
		linksList = (ListView) view.findViewById(R.id.linksList);
	}

	@Override
	public Loader<Episode> onCreateLoader(int id, Bundle args) {
		LoggerClass.log("onCreateLoader");
		if (null != episodeInfo && !episodeInfo.equals("")) {
			String[] temp = episodeInfo.split(";");
			return new EpisodeInfoLoader(getActivity(), temp[0], temp[1], temp[2]);
		} 
		application.dismissProgressBar();
		return null;
	}
		

	@Override
	public void onLoadFinished(Loader<Episode> loader, final Episode data) {
		LoggerClass.log("onLoadFinished");
		getLoaderManager().destroyLoader(LOADER_ID);
		application.dismissProgressBar();
		if (null != data) {
			getActivity().getActionBar().setTitle(data.getTitle());
			description.setText(data.getSynopsis());
			if (null != data.getLinks() && data.getLinks().size() > 0) {
				List<String> links = new ArrayList<String>();
				for (int i = 0; i < data.getLinks().size(); i++) {
					links.add("Quality:" + data.getLinks().get(i).getLinkTitle() + "\n" + "Language:" + data.getLinks().get(i).getLanguage());
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
		new AlertDialog.Builder(getActivity()).setMessage("Sorry, could not find any links").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
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
	public void onLoaderReset(Loader<Episode> loader) {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LoggerClass.log("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		application.showProgressBar("Fetching links ", getActivity());
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}

}
