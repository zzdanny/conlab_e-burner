package tw.ntust.e_burner.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tw.ntust.e_burner.R;

public class AmountListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<AmountItem> amountItems;

    private TextView txtTitle;

    public AmountListAdapter(Context context, ArrayList<AmountItem> _amountItems) {
        layoutInflater = LayoutInflater.from(context);
        amountItems = _amountItems;
    }

    @Override
    public int getCount() {
        return amountItems.size();
    }

    @Override
    public Object getItem(int position) {
        return amountItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return amountItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_amount_item, null);

        txtTitle = (TextView) convertView.findViewById(R.id.amountListItem_txtTitle);

        txtTitle.setText(amountItems.get(position).getTitle());

        return convertView;
    }

}
