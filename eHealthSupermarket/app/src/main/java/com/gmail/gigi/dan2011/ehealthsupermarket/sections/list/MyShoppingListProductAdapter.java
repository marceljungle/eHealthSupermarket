package com.gmail.gigi.dan2011.ehealthsupermarket.sections.list;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MyShoppingListProductAdapter extends
    RecyclerView.Adapter<MyShoppingListProductAdapter.Holderview> {


  private List<Product> productList;
  private FirebaseFirestore db;
  private Context context;
  private ClickListener clickListener;


  public MyShoppingListProductAdapter(
      List<Product> productList,
      Context context,
      FirebaseFirestore db) {
    this.productList = productList;
    this.context = context;
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
    holder.bindData(productList.get(position).getProduct_name(),
        productList.get(position).getImage());
  }

  public String getItem(int position) {
    return (productList != null) ? (String) productList.get(position).getProduct_name()
        : null;
  }

  public Product getProduct(int position) {
    return productList.get(position);
  }

  public void setOnItemClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }


  @Override
  public int getItemCount() {
    return productList.size();
  }


  public class Holderview extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView v_image;
    public TextView v_name;
    public ImageButton v_delete;

    Holderview(View itemview) {
      super(itemview);
      v_image = itemview.findViewById(R.id.product_image);
      v_name = itemview.findViewById(R.id.product_title);
      v_delete = itemview.findViewById(R.id.delete_item);
      if (clickListener != null) {
        v_delete.setOnClickListener(this);
        //itemView.setOnClickListener(this);
      }
    }

    public void bindData(final String name, String image) {
      if(image == null) {
        v_image.setImageResource(R.drawable.baseline_fastfood_black_24dp);
      }else{
        Picasso.get().load(image).fit().centerCrop().into(v_image);
      }
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