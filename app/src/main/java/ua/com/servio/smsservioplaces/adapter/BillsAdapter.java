package ua.com.servio.smsservioplaces.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.model.json.BillDTO;

public class BillsAdapter extends BaseAdapter {
    private Context context;
    private final List<BillDTO> billDTOList;

    public BillsAdapter(Context context, List<BillDTO> billDTOList) {
        this.context = context;
        this.billDTOList = billDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.bill_item, null);

            TextView billNumber = (TextView) convertView
                    .findViewById(R.id.bill_number);
            TextView billOpened = (TextView) convertView
                    .findViewById(R.id.bill_opened);
            billOpened.setText(billDTOList.get(position).getOpened());
            billNumber.setText(String.valueOf(billDTOList.get(position).getNumber()));
            TextView billTotal = (TextView) convertView
                    .findViewById(R.id.bill_total);
            billTotal.setText(String.valueOf(billDTOList.get(position).getTotal()));
            TextView billOpenUser = (TextView) convertView
                    .findViewById(R.id.bill_open_user);
            billOpenUser.setText(billDTOList.get(position).getOpenUser());

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return billDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
