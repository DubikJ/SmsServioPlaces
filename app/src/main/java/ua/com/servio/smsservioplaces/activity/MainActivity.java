package ua.com.servio.smsservioplaces.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.smsservioplaces.R;
import ua.com.servio.smsservioplaces.adapter.PlacesListAdapter;
import ua.com.servio.smsservioplaces.adapter.treelistview.Element;
import ua.com.servio.smsservioplaces.adapter.treelistview.TreeViewItemClickListener;
import ua.com.servio.smsservioplaces.model.json.DownloadResponse;
import ua.com.servio.smsservioplaces.model.json.PlaceGroupDTO;
import ua.com.servio.smsservioplaces.model.json.PlaceGroupSchemaDTO;
import ua.com.servio.smsservioplaces.model.json.PlaceUnionDTO;
import ua.com.servio.smsservioplaces.service.sync.SyncService;
import ua.com.servio.smsservioplaces.service.sync.SyncServiceFactory;
import ua.com.servio.smsservioplaces.ui.widget.PullToRefreshListView;
import ua.com.servio.smsservioplaces.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity{


    private PullToRefreshListView listView;
    private NetworkUtils networkUtils;
    private PlacesListAdapter adapter;
    private Observable observable;
    private ArrayList listParent, listChild;


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

        networkUtils = new NetworkUtils(this);

        listParent = new ArrayList<Element>();
        listChild = new ArrayList<Element>();

        initAdapter();
    }

    private void startSync(){

        if (!networkUtils.checkEthernet()) {
            showMessage(getString(R.string.error_internet_connecting));
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            listView.onRefreshComplete();
            return;
        }

        getSupportActionBar().setTitle(getResources().getString(R.string.download));

        SyncService syncService = SyncServiceFactory.createService(
                SyncService.class, MainActivity.this);

        observable = syncService.getPlaces();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadResponse>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(DownloadResponse downloadResponse) {
                        if(downloadResponse == null) {
                            showMessage(getString(R.string.error_no_data));
                            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                            listView.onRefreshComplete();
                            return;
                        }
                        if(downloadResponse.getPlaceUnions().size()==0) {
                            showMessage(getString(R.string.error_no_data));
                            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                            listView.onRefreshComplete();
                            return;
                        }
                        initDataToListView(downloadResponse.getPlaceUnions());
                        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                        listView.onRefreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!networkUtils.checkEthernet()) {
                            showMessage(getString(R.string.error_internet_connecting));
                            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                            listView.onRefreshComplete();
                            return;
                        }
                        showMessage(e.getMessage());
                        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                        listView.onRefreshComplete();
                    }

                    @Override
                    public void onComplete() {
                    }
                });



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

        listParent = new ArrayList<Element>();
        listChild = new ArrayList<Element>();

        if(placeUnionDTOList!=null) {

            if (placeUnionDTOList.size() == 0) return true;

            int u = 0;
            for (PlaceUnionDTO placeUnionDTO : placeUnionDTOList) {


                Element elementU = new Element(placeUnionDTO.getName(), 0, u, 0,
                        placeUnionDTO.getPlaceGroups().size() > 0, false);

                listParent.add(elementU);

                int g = 1;
                for (PlaceGroupDTO placeGroupDTO : placeUnionDTO.getPlaceGroups()) {

                    Element elementG = new Element(placeGroupDTO.getName(), 1, g, u,
                            placeGroupDTO.getPlaceGroupSchemas().size() > 0, true);


                    listChild.add(elementG);

                    int gs = 0;
                    for (PlaceGroupSchemaDTO placeGroupSchemaDTO : placeGroupDTO.getPlaceGroupSchemas()) {

                        Element elementGS = new Element(placeGroupSchemaDTO.getName(), 2, gs, g,
                                false, true, placeGroupSchemaDTO.getPlaces());

                        listChild.add(elementGS);

                        g++;
                    }
                    g++;
                }
                u++;
            }

        }

        initAdapter();

        return true;

    }

    private void initAdapter(){

        adapter = new PlacesListAdapter(this, listParent, listChild);

        listView.setAdapter(adapter);

        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(adapter, new TreeViewItemClickListener.ListItemClick() {

            @Override
            public void onItemClik(Element element) {

            }
        });
        listView.setOnItemClickListener(treeViewItemClickListener);

    }

}
