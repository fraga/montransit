package org.montrealtransit.android;

import java.util.Locale;

import org.montrealtransit.android.provider.StmStore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

/**
 * Some useful method for subways.
 * @author Mathieu Méa
 */
public class SubwayUtils {

	/**
	 * The log tag.
	 */
	private static final String TAG = SubwayUtils.class.getSimpleName();

	/**
	 * The STM map (FR).
	 */
	private static final String STM_SUBWAY_MAP_URL_FR = "http://stm.info/metro/images/plan-metro.jpg";
	/**
	 * The STM map (EN).
	 */
	private static final String STM_SUBWAY_MAP_URL_EN = "http://stm.info/English/metro/images/plan-metro.jpg";

	/**
	 * Show the STM subway map.
	 * @param v the view (not used)
	 */
	public static void showSTMSubwayMap(Context context) {
		// TODO store the map on the SD card the first time and then re-open it
		// TODO add a menu to reload the map from the web site in the image viewer?
		String url;
		if (Utils.getUserLanguage().equals(Locale.FRENCH.toString())) {
			url = STM_SUBWAY_MAP_URL_FR;
		} else {
			url = STM_SUBWAY_MAP_URL_EN;
		}
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	/**
	 * Return the subway line name string ID from the subway line number.
	 * @param subwayLineNumber the subway line number
	 * @return the subway line name string ID
	 */
	public static int getSubwayLineName(int number) {
		MyLog.v(TAG, "getSubwayLineName(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.string.green_line;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.string.orange_line;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.string.yellow_line;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.string.blue_line;
		default:
			MyLog.w(TAG, "Unknown subway line number '%s'.", number);
			return R.string.error;
		}
	}

	/**
	 * Return the subway line short name string ID from the subway line number.
	 * @param subwayLineNumber the subway line number
	 * @return the subway line short name string ID
	 */
	public static int getSubwayLineNameShort(int number) {
		MyLog.v(TAG, "getSubwayLineNameShort(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.string.green_line_short;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.string.orange_line_short;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.string.yellow_line_short;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.string.blue_line_short;
		default:
			MyLog.w(TAG, "Unknown subway line number '%s'.", number);
			return R.string.error;
		}
	}

	/**
	 * Return the subway line image ID for the subway line number
	 * @param number the subway line number
	 * @return the subway line image ID
	 */
	public static int getSubwayLineImgId(int number) {
		// MyLog.v(TAG, "getSubwayLineImgId(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.drawable.green;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.drawable.orange;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.drawable.yellow;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.drawable.blue;
		default:
			MyLog.w(TAG, "Unknown image for subway line number '%s'.", number);
			return R.drawable.yellow;
		}
	}

	/**
	 * @param the subway line number
	 * @return the subway line list image
	 */
	public static int getSubwayLineImgListId(int number) {
		// MyLog.v(TAG, "getSubwayLineImgListId(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.drawable.green_list;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.drawable.orange_list;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.drawable.yellow_list;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.drawable.blue_list;
		default:
			MyLog.w(TAG, "Unknown image for subway line number '%s'.", number);
			return R.drawable.yellow;
		}
	}

	/**
	 * @param the subway line number
	 * @return the subway line list top image
	 */
	public static int getSubwayLineImgListTopId(int number) {
		// MyLog.v(TAG, "getSubwayLineImgListTopId(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.drawable.green_list_top;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.drawable.orange_list_top;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.drawable.yellow_list_top;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.drawable.blue_list_top;
		default:
			MyLog.w(TAG, "Unknown image for subway line number '%s'.", number);
			return R.drawable.yellow;
		}
	}

	/**
	 * @param the subway line number
	 * @return the subway line list middle image
	 */
	public static int getSubwayLineImgListMiddleId(int number) {
		// MyLog.v(TAG, "getSubwayLineImgListMiddleId(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.drawable.green_list_middle;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.drawable.orange_list_middle;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.drawable.yellow_list_middle;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.drawable.blue_list_middle;
		default:
			MyLog.w(TAG, "Unknown image for subway line number '%s'.", number);
			return R.drawable.yellow;
		}
	}

	/**
	 * @param the subway line number
	 * @return the subway line list bottom image
	 */
	public static int getSubwayLineImgListBottomId(int number) {
		// MyLog.v(TAG, "getSubwayLineImgListBottomId(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return R.drawable.green_list_bottom;
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return R.drawable.orange_list_bottom;
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return R.drawable.yellow_list_bottom;
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return R.drawable.blue_list_bottom;
		default:
			MyLog.w(TAG, "Unknown image for subway line number '%s'.", number);
			return R.drawable.yellow;
		}
	}

	/**
	 * Return the subway line color ID from the subway line number
	 * @param number the subway line number
	 * @return the subway line color ID
	 */
	public static int getSubwayLineColor(int number) {
		MyLog.v(TAG, "getSubwayLineColor(%s)", number);
		switch (number) {
		case StmStore.SubwayLine.GREEN_LINE_NUMBER:
			return Color.rgb(0, 148, 52);// green
		case StmStore.SubwayLine.ORANGE_LINE_NUMBER:
			return Color.rgb(236, 127, 0); // Orange
		case StmStore.SubwayLine.YELLOW_LINE_NUMBER:
			return Color.rgb(255, 227, 1); // yellow
		case StmStore.SubwayLine.BLUE_LINE_NUMBER:
			return Color.rgb(0, 157, 224); // blue
		default:
			MyLog.w(TAG, "Unknown color for subway line number '%s'.", number);
			return Color.TRANSPARENT;
		}
	}

}
