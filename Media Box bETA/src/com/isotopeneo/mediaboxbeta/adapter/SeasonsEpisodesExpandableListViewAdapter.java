package com.isotopeneo.mediaboxbeta.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.isotopeneo.mediaboxbeta.bean.Season;
import com.isotopeneo.mediaboxbeta.R;

public class SeasonsEpisodesExpandableListViewAdapter extends
		BaseExpandableListAdapter {

	private final LayoutInflater inflater;
	private List<Season> allSeasonsEpisodes;
	
	public SeasonsEpisodesExpandableListViewAdapter(Context context, List<Season> data) {
		this.inflater = LayoutInflater.from(context);
		this.allSeasonsEpisodes = data;
	}
	
	@Override
	public int getGroupCount() {
		return allSeasonsEpisodes.size() > 0 ? allSeasonsEpisodes.size() : 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return allSeasonsEpisodes.size() > 0 ? allSeasonsEpisodes.get(groupPosition).getEpisodes().size() : 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return allSeasonsEpisodes.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return allSeasonsEpisodes.get(groupPosition).getEpisodes().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View resultView = convertView;
		ViewHolder holder;

		if (resultView == null) {
			resultView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
			holder = new ViewHolder();
			holder.setTextLabel((TextView) resultView
					.findViewById(android.R.id.text1)); // TODO change view id
			resultView.setTag(holder);
		} else {
			holder = (ViewHolder) resultView.getTag();
		}
		holder.getTextLabel().setText("Season " + allSeasonsEpisodes.get(groupPosition).getNumber());

		return resultView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View resultView = convertView;
		ViewHolder holder;
		if (resultView == null) {
			resultView = inflater
					.inflate(R.layout.simple_expandable_list_item_3, null);
			holder = new ViewHolder();
			holder.setTextLabel((TextView) resultView
					.findViewById(R.id.text3)); // TODO change view id
			resultView.setTag(holder);
		} else {
			holder = (ViewHolder) resultView.getTag();
		}
		String episodeInfo = allSeasonsEpisodes.get(groupPosition).getEpisodes().get(childPosition).getEpisodeNumber() + ". " 
				+ allSeasonsEpisodes.get(groupPosition).getEpisodes().get(childPosition).getTitle();
		holder.getTextLabel().setText(episodeInfo);
		return resultView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private class ViewHolder {
		private TextView textLabel;

		public TextView getTextLabel() {
			return textLabel;
		}

		public void setTextLabel(TextView textLabel) {
			this.textLabel = textLabel;
		}
	}
}
