package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Javadoc comment.
 */
public class CustomAdapter extends BaseAdapter implements Serializable {

  private ArrayList<RowItem> singleRow;
  private LayoutInflater thisInflater;


  /**
   * Javadoc comment.
   */
  public CustomAdapter(Context context, ArrayList<RowItem> aaRow) {

    this.singleRow = aaRow;
    thisInflater = (LayoutInflater.from(context));

  }

  @Override
  public int getCount() {
    return singleRow.size();
  }

  @Override
  public RowItem getItem(int position) {
    return singleRow.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = thisInflater.inflate(R.layout.fragment_mylist_row, parent, false);

      TextView theHeading = (TextView) convertView.findViewById(R.id.textHeading);
      TextView theSubHeading = (TextView) convertView.findViewById(R.id.textSubHeading);
      ImageView theImage = (ImageView) convertView.findViewById(R.id.imageView_product);
      RowItem currentRow = (RowItem) getItem(position);

      theHeading.setText(currentRow.getListName());
      theSubHeading.setText(currentRow.getSubHeading());
      theImage.setImageResource(currentRow.getSmallImageName());
    }
    return convertView;
  }

}
