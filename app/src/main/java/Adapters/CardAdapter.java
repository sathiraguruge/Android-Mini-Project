package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.*;
import Entities.*;
import java.util.*;
import android.view.*;

import com.expensemanager.personalexpensemanager.R;

public class CardAdapter extends  ArrayAdapter<Card>{

    private Context context;
    private List<Card> cards;

    public CardAdapter(Context context, List<Card> cards) {
        super(context, R.layout.credit_detail_layout, cards);
        this.context = context;
        this.cards = cards;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.credit_detail_layout,parent, false);

        TextView TextViewCardName = view.findViewById(R.id.TextViewCardName);
        TextViewCardName.setText(cards.get(position).getName());

        TextView textViewCardDesc = view.findViewById(R.id.textViewCardDesc);
        textViewCardDesc.setText( cards.get(position).getDescription());

        TextView textViewCardNo = view.findViewById(R.id.textViewCardNo);
        textViewCardNo.setText( "Card No          :   " + cards.get(position).getCardNo());

        TextView textViewCardType = view.findViewById(R.id.textViewCardType);
        textViewCardType.setText( "Card Type       :   " + cards.get(position).getType());

        TextView textViewCardExp = view.findViewById(R.id.textViewCardExp);
        textViewCardExp.setText( "Exp Date         :   " +  cards.get(position).getExpDate());

        TextView textViewCardDate = view.findViewById(R.id.textViewCardDate);
        textViewCardDate.setText("Added On : " + cards.get(position).getDate());
        return view;
    }
}

