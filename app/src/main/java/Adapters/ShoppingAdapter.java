package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.expensemanager.personalexpensemanager.R;

import java.util.List;

import Entities.Shopping;

public class ShoppingAdapter extends ArrayAdapter<Shopping> {

    private Context context;
    private List<Shopping> shoppings;

    public ShoppingAdapter(Context context, List<Shopping> shoppings) {
        super(context, R.layout.food_detail_layout, shoppings);
        this.context = context;
        this.shoppings = shoppings;
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.shopping_detail_layout,parent, false);

        TextView TextViewFoodName = view.findViewById(R.id.TextViewShopName);
        TextViewFoodName.setText(shoppings.get(position).getName());

        TextView textViewFoodDateAdded = view.findViewById(R.id.textViewShopDateAdded);
        textViewFoodDateAdded.setText("Added On : " + shoppings.get(position).getDateAdded());

        TextView textViewFoodDate = view.findViewById(R.id.textViewShopDate);
        textViewFoodDate.setText( "Date                  :    " + shoppings.get(position).getDate());

        TextView textViewFoodDesc = view.findViewById(R.id.textViewShopDesc);
        textViewFoodDesc.setText( "Description     :    " + shoppings.get(position).getDescription());

        TextView textViewFoodPrice = view.findViewById(R.id.textViewShopPrice);
        textViewFoodPrice.setText( "Price          :  " +  shoppings.get(position).getPrice());

        return view;
    }
}
