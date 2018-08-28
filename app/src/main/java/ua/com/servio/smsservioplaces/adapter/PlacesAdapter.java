package ua.com.servio.smsservioplaces.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.model.json.PlaceDTO;

public class PlacesAdapter extends BaseAdapter {
    private Context context;
    private final List<PlaceDTO> placeDTOList;

    public PlacesAdapter(Context context, List<PlaceDTO> placeDTOList) {
        this.context = context;
        this.placeDTOList = placeDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.place_element, null);

            TextView placeNumber = (TextView) convertView
                    .findViewById(R.id.place_number);
            placeNumber.setText(placeDTOList.get(position).getName());
            TextView placeTop = (TextView) convertView
                    .findViewById(R.id.place_top);
            placeTop.setText(String.valueOf(placeDTOList.get(position).getTop()));
            TextView placeLeft = (TextView) convertView
                    .findViewById(R.id.place_left);
            placeLeft.setText(String.valueOf(placeDTOList.get(position).getLeft()));
            TextView placeBills = (TextView) convertView
                    .findViewById(R.id.place_bills);
            placeBills.setText(
                    placeDTOList.get(position).getBills()!=null && placeDTOList.get(position).getBills().size()>0 ?
                    context.getResources().getString(R.string.have_bills):
                    context.getResources().getString(R.string.have_not_bills));

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return placeDTOList.size();
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
