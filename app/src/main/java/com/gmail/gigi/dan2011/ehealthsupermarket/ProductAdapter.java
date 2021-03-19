package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Javadoc comment.
 */
public class ProductAdapter extends BaseAdapter {

  private ArrayList<Product> product;
  private Context context;
  private String productId;

  public ProductAdapter(Context context, ArrayList<Product> product) {
    this.product = product;
    this.context = context;
  }

  @Override
  public int getCount() {
    return product.size();
  }

  @Override
  public Product getItem(int position) {
    return product.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.grid_item_product, viewGroup, false);
    }

    // LinearLayout square = (LinearLayout) view.findViewById(R.id.square);
    ImageView imageProduct = (ImageView) view.findViewById(R.id.imageProduct_grid);
    TextView nameProduct = (TextView) view.findViewById(R.id.product_name_grid);
    TextView quantityProduct = (TextView) view.findViewById(R.id.product_quantity_grid);

    Product item = (Product) getItem(position);

    this.productId = item.getProduct_id();
    Picasso.get().load(item.getImage()).into(imageProduct);
    nameProduct.setText(item.getGeneric_name());
    quantityProduct.setText(item.getPackaging() + " " + item.getQuantity());

    return view;
  }

  public String productGetId() {
    return this.productId;
  }
}
