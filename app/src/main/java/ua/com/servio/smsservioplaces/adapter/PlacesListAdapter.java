package ua.com.servio.smsservioplaces.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.adapter.treelistview.Element;
import ua.com.servio.smsservioplaces.model.json.PlaceDTO;


public class PlacesListAdapter extends BaseAdapter {

    private ArrayList<Element> elementsParent;
    private ArrayList<Element> elementsData;
    private int indentionBase;
    private LayoutInflater layoutInflater;
    private Context context;

    public PlacesListAdapter(Context context, ArrayList<Element> elementsParent,
                             ArrayList<Element> elementsData) {
        this.context = context;
        this.elementsParent = elementsParent;
        this.elementsData = elementsData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        indentionBase = 20;
    }


    public ArrayList<Element> getElements() {
        return elementsParent;
    }

    public ArrayList<Element> getElementsData() {
        return elementsData;
    }

    @Override
    public int getCount() {
        return elementsParent.size();
    }

    @Override
    public Object getItem(int position) {
        return elementsParent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Element element = elementsParent.get(position);
        if(element.isHasChildren()) {
            convertView = layoutInflater.inflate(R.layout.fragment_hierarchical_element, null);
        }else {
            convertView = layoutInflater.inflate(R.layout.place_hierarchical_element, null);
        }

        return fillView(convertView, element);
    }

    static class ViewHolder {
        ImageView disclosureImg;
        TextView mTextView;
        RelativeLayout mCardView;
        GridView mPlaces;
    }

    public View fillView(View convertView, final Element element) {

        final ViewHolder holder = new ViewHolder();

        holder.mCardView = (RelativeLayout) convertView.findViewById(R.id.element_rl);

        convertView.setTag(holder);

        if(element.isHasChildren()) {
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.element_imageview);
            holder.mTextView = (TextView) convertView.findViewById(R.id.element_textview);
        }else{
            holder.mTextView = (TextView) convertView.findViewById(R.id.element_textview);
            holder.mPlaces = (GridView) convertView.findViewById(R.id.gvMain);
        }

        int level = element.getLevel();

        holder.mCardView.setPadding(
                indentionBase * level,
                holder.mCardView.getPaddingTop(),
                holder.mCardView.getPaddingRight(),
                holder.mCardView.getPaddingBottom());

        String housingCode = String.valueOf(element.getId());
        String housingName = element.getContentText();

        if(element.isHasChildren()) {
            holder.mTextView.setTag(housingCode);
            holder.mTextView.setText(housingName);
            if (element.isHasChildren() && !element.isExpanded()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_right);
                holder.disclosureImg.setVisibility(View.VISIBLE);
            } else if (element.isHasChildren() && element.isExpanded()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_down);
                holder.disclosureImg.setVisibility(View.VISIBLE);
            } else if (!element.isHasChildren()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_right);
                holder.disclosureImg.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.mTextView.setText(element.getContentText());
            final List <PlaceDTO> listPlaces = element.getPlaces();
            if(listPlaces!= null) {
                holder.mPlaces.setAdapter(new PlacesAdapter(context, element.getPlaces()));
                holder.mPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        PlaceDTO placeDTO = listPlaces.get(position);

                        if (placeDTO.getBills() != null&&placeDTO.getBills().size()>0) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
                            builder.setTitle(placeDTO.getName());

                            final View viewInflated = LayoutInflater.from(context).inflate(R.layout.bill_layout, null);

                            ListView mBills = (ListView) viewInflated.findViewById(R.id.list_bills);
                            mBills.setAdapter(new BillsAdapter(context, placeDTO.getBills()));
                            builder.setView(viewInflated);

                            builder.setPositiveButton(context.getResources().getString(R.string.questions_answer_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();
                        }else{
                            Toast.makeText(context, context.getResources().getString(R.string.bill_not_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        return convertView;

    }



}
