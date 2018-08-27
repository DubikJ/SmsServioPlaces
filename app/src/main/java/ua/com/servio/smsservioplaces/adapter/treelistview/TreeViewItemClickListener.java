package ua.com.servio.smsservioplaces.adapter.treelistview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


import java.util.ArrayList;

import ua.com.servio.smsservioplaces.adapter.PlacesListAdapter;

public class TreeViewItemClickListener implements OnItemClickListener {
    private PlacesListAdapter treeViewAdapter;
    private ListItemClick listItemClick;

    public TreeViewItemClickListener(PlacesListAdapter treeViewAdapter, final ListItemClick listItemClick) {
        this.treeViewAdapter = treeViewAdapter;
        this.listItemClick = listItemClick;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        position -= 1;
        Element element = (Element) treeViewAdapter.getItem(position);
        ArrayList<Element> elements = treeViewAdapter.getElements();
        ArrayList<Element> elementsData = treeViewAdapter.getElementsData();
        if (!element.isHasChildren()) {
            listItemClick.onItemClik(element);
            return;
        }

        if (element.isExpanded()) {
            element.setExpanded(false);
            ArrayList<Element> elementsToDel = new ArrayList<Element>();
            for (int i = position + 1; i < elements.size(); i++) {
                if (element.getLevel() >= elements.get(i).getLevel())
                    break;
                elementsToDel.add(elements.get(i));
            }
            elements.removeAll(elementsToDel);
            treeViewAdapter.notifyDataSetChanged();
        } else {
            element.setExpanded(true);
            int i = 1;
            for (Element e : elementsData) {
                if (e.getParendId() == element.getId()) {
                    e.setExpanded(false);
                    elements.add(position + i, e);
                    i ++;
                }
            }
            treeViewAdapter.notifyDataSetChanged();
        }
    }

    public interface ListItemClick {

        void onItemClik(Element element);

    }



}