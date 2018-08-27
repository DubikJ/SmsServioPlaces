package ua.com.servio.smsservioplaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.adapter.treelistview.Element;


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
          //  convertView = layoutInflater.inflate(R.layout.animal_item, null);
        }

        return fillView(convertView, element);
    }

    static class ViewHolder {
        ImageView disclosureImg;
        TextView mTextView;
        RelativeLayout mCardView;
        TextView mCodeNameTV;
        TextView mCodeTV;
        TextView mRfidTV;
    }

    public View fillView(View convertView, final Element element) {

        ViewHolder holder = new ViewHolder();

        holder.mCardView = (RelativeLayout) convertView.findViewById(R.id.element_rl);

        convertView.setTag(holder);

        if(element.isHasChildren()) {
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.element_imageview);
            holder.mTextView = (TextView) convertView.findViewById(R.id.element_textview);
        }else{
//            holder.mTextView = (TextView) convertView.findViewById(R.id.list_animal_type);
//            holder.mCodeNameTV = (TextView) convertView.findViewById(R.id.list_animal_code_name);
//            holder.mCodeTV = (TextView) convertView.findViewById(R.id.list_animal_code);
//            holder.mRfidTV = (TextView) convertView.findViewById(R.id.list_animal_rfid);
        }

        int level = element.getLevel();

        holder.mCardView.setPadding(
                indentionBase * level,
                holder.mCardView.getPaddingTop(),
                holder.mCardView.getPaddingRight(),
                holder.mCardView.getPaddingBottom());

        String housingCode = element.getExternalId();
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
//            Animal animalItem = (Animal) element.getAnimal();
//            if(animalItem!=null) {
//                holder.mTextView.setText(element.getContentText());
//                holder.mRfidTV.setText(animalItem.getRfid());
//                if(animalItem.isGroupAnimal()){
//                    holder.mCodeNameTV.setText(context.getResources().getString(R.string.animal_group_number_short));
//                    holder.mCodeTV.setText(String.valueOf(animalItem.getNumber()));
//                }else{
//                    holder.mCodeNameTV.setText(context.getResources().getString(R.string.service_item_number_symbol));
//                    holder.mCodeTV.setText(animalItem.getCode());
//                }
//            }
//            Glide.with(holder.mAvatar.getContext())
//                    .load(element.getPhotoFile() == null ? R.drawable.ic_pig_photo : element.getPhotoFile())
//                    .fitCenter()
//                    .into(holder.mAvatar);
        }

        return convertView;

    }



}
