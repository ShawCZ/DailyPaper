package com.shaw.daily.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;

/**
 * Created by
 */

public class HolderCreator implements CBViewHolderCreator<ImageHolder> {
	private final ArrayList<String> bannerTitles;

	public HolderCreator(ArrayList<String> bannerTitles) {
		this.bannerTitles = bannerTitles;
	}

	@Override
	public ImageHolder createHolder() {
		return new ImageHolder(bannerTitles);
	}
}
