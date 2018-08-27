package ua.com.servio.smsservioplaces.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.adapter.PlacesListAdapter;
import ua.com.servio.smsservioplaces.adapter.treelistview.Element;
import ua.com.servio.smsservioplaces.adapter.treelistview.TreeViewItemClickListener;
import ua.com.servio.smsservioplaces.model.APIError;
import ua.com.servio.smsservioplaces.model.json.BillDTO;
import ua.com.servio.smsservioplaces.model.json.DownloadResponse;
import ua.com.servio.smsservioplaces.model.json.PlaceDTO;
import ua.com.servio.smsservioplaces.model.json.PlaceGroupDTO;
import ua.com.servio.smsservioplaces.model.json.PlaceGroupSchemaDTO;
import ua.com.servio.smsservioplaces.model.json.PlaceUnionDTO;
import ua.com.servio.smsservioplaces.service.sync.SyncService;
import ua.com.servio.smsservioplaces.service.sync.SyncServiceFactory;
import ua.com.servio.smsservioplaces.ui.widget.PullToRefreshListView;
import ua.com.servio.smsservioplaces.utils.ErrorUtils;
import ua.com.servio.smsservioplaces.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity{


    private SyncService syncService;
    private PullToRefreshListView listView;
    private ErrorUtils errorUtils;
    private NetworkUtils networkUtils;
    private PlacesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (PullToRefreshListView) findViewById(R.id.list_places);
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startSync();
            }
        });

        errorUtils = new ErrorUtils(this);
        networkUtils = new NetworkUtils(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startSync();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void startSync(){

        if (!networkUtils.checkEthernet()) {
            showMessage(getString(R.string.error_internet_connecting));
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            listView.onRefreshComplete();
            return;
        }

        syncService = SyncServiceFactory.createService(
                SyncService.class,
                this.getBaseContext());
        getSupportActionBar().setTitle(getResources().getString(R.string.download));
        try {
            Response<DownloadResponse> downloadResponse = syncService.search().execute();
            if (downloadResponse.isSuccessful()) {
                DownloadResponse body = downloadResponse.body();
                if(body == null) {
                    showMessage(getString(R.string.error_no_data));
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                    listView.onRefreshComplete();
                    return;
                }
                if(body.getPlaceUnions().size()==0) {
                    showMessage(getString(R.string.error_no_data));
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                    listView.onRefreshComplete();
                    return;
                }
                if(initDataToListView(body.getPlaceUnions())) {
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                    listView.onRefreshComplete();
                }
            } else {
                APIError error = errorUtils.parseErrorCode(downloadResponse.code());
                showMessage(error.getMessage());
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                listView.onRefreshComplete();

            }
        } catch (Exception exception) {
            APIError error = errorUtils.parseErrorMessage(exception);
            showMessage(error.getMessage());
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            listView.onRefreshComplete();
        }
    }

    public void showMessage(String textMessage) {
        if (textMessage == null || textMessage.isEmpty()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.questions_title_info));
        builder.setMessage(textMessage);

        builder.setNeutralButton(getString(R.string.questions_answer_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean initDataToListView(List<PlaceUnionDTO> placeUnionDTOList){

        ArrayList listParent = new ArrayList<Element>();
        ArrayList listChild = new ArrayList<Element>();

        if(placeUnionDTOList!=null) {

            if (placeUnionDTOList.size() == 0) return true;

            int u = 0;
            for (PlaceUnionDTO placeUnionDTO : placeUnionDTOList) {


                Element elementU = new Element(placeUnionDTO.getName(), 0, u, 0,
                        placeUnionDTO.getPlaceGroups().size() > 0, false);

                listParent.add(elementU);
                u++;

                int g = 0;
                for (PlaceGroupDTO placeGroupDTO : placeUnionDTO.getPlaceGroups()) {

                    Element elementG = new Element(placeUnionDTO.getName(), 1, g, u,
                            placeGroupDTO.getPlaceGroupSchemas().size() > 0, false);


                    listChild.add(elementG);
                    g++;

                    int gs = 0;
                    for (PlaceGroupSchemaDTO placeGroupSchemaDTO : placeGroupDTO.getPlaceGroupSchemas()) {

                        Element elementGS = new Element(placeUnionDTO.getName(), 2, gs, g,
                                placeGroupSchemaDTO.getPlaces().size() > 0, false);

                        listChild.add(elementGS);
                        g++;

                        int p = 0;
                        for (PlaceDTO placeDTO : placeGroupSchemaDTO.getPlaces()) {

                            Element elementP = new Element(placeUnionDTO.getName(), 3, p, gs,
                                    placeDTO.getBills().size() > 0, false);

                            listChild.add(elementP);
                            p++;

                            int b = 0;
                            for (BillDTO billDTO : placeDTO.getBills()) {

                                Element elementB = new Element(placeUnionDTO.getName(), 3, p, gs,
                                        placeDTO.getBills().size() > 0, false);

                                listChild.add(elementB);
                                p++;
                            }
                        }
                    }

                }
            }

        }

        adapter = new PlacesListAdapter(this, listParent, listChild);

        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(adapter, new TreeViewItemClickListener.ListItemClick() {

            @Override
            public void onItemClik(Element element) {

            }
        });
        listView.setOnItemClickListener(treeViewItemClickListener);

        return true;

    }


}
