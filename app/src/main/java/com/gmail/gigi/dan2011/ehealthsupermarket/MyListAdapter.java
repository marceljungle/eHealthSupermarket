package com.gmail.gigi.dan2011.ehealthsupermarket;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListAdapter extends
    RecyclerView.Adapter<MyListAdapter.Holderview> {

  private final List<Product> productListUntouchable;
  private List<Product> productList;
  private FirebaseUser user;
  private FirebaseFirestore db;
  private Context context;
  private ClickListener clickListener;


  public MyListAdapter(
      List<Product> productList,
      Context context,
      FirebaseUser user,
      FirebaseFirestore db) {
    this.productList = productList;
    this.context = context;
    this.productListUntouchable = productList;
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
    holder.bindData(productList.get(position).getProduct_name());
  }

  public String getItem(int position) {
    return (productList != null) ? (String) productList.get(position).getProduct_name()
        : null;
  }

  public void setOnItemClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }


  @Override
  public int getItemCount() {
    return productList.size();
  }


  public void setfilter(List<Product> listOfProds) {
    productList = new ArrayList<>();
    productList.addAll(listOfProds);
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