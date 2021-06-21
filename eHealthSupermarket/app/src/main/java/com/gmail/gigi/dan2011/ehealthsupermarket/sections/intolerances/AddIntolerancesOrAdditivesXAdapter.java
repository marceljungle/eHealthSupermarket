package com.gmail.gigi.dan2011.ehealthsupermarket.sections.intolerances;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddIntolerancesOrAdditivesXAdapter extends
    RecyclerView.Adapter<AddIntolerancesOrAdditivesXAdapter.Holderview> {

  private final Map<String, Object> itemsListUntouchable;
  private Map<String, Object> itemsList = new HashMap<>();
  private FirebaseUser user;
  private FirebaseFirestore db;
  private Context context;
  private ClickListener clickListener;


  public AddIntolerancesOrAdditivesXAdapter(
      Map<String,
          Object> itemsList,
      Context context,
      FirebaseUser user,
      FirebaseFirestore db) {
    this.itemsList = itemsList;
    this.context = context;
    this.itemsListUntouchable = itemsList;
    this.user = user;
    this.db = db;
  }

  @Override
  public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
    View layout = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.custom_intolerance_item_x, parent, false);
    return new Holderview(layout);
  }

  @Override
  public void onBindViewHolder(Holderview holder, final int position) {
    holder.bindData((String) itemsList.keySet().toArray()[position]);
  }

  public String getItem(int position) {
    return (itemsList.keySet().toArray() != null) ? (String) itemsList.keySet().toArray()[position]
        : null;
  }

  public void setOnItemClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }


  @Override
  public int getItemCount() {
    return itemsList.size();
  }

  public void setfilter(List<String> listitem) {
    itemsList = new HashMap<>();
    for (String str : listitem) {
      itemsList.put(str, itemsListUntouchable.get(str));
    }
    notifyDataSetChanged();
  }

  public class Holderview extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView v_image;
    public TextView v_name;
    public ImageButton v_delete;

    Holderview(View itemview) {
      super(itemview);
      v_image = (ImageView) itemview.findViewById(R.id.product_image);
      v_name = (TextView) itemview.findViewById(R.id.product_title);
      v_delete = itemview.findViewById(R.id.delete_item);
      if (clickListener != null) {
        v_delete.setOnClickListener(this);
        //itemView.setOnClickListener(this);
      }
    }

    public void bindData(final String name) {
      v_image.setImageResource(R.drawable.ic_intolerances);
      v_name.setText(name);
    }

    @Override
    public void onClick(View v) {
      if (clickListener != null) {
        clickListener.onItemClick(getAdapterPosition(), v);
      }
    }
  }

  public interface ClickListener {

    void onItemClick(int position, View v);
  }

}