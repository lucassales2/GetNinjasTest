package lucassales.com.getninjastest.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.UnknownHostException;

import lucassales.com.getninjastest.BR;
import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.Utility;
import lucassales.com.getninjastest.ui.presenter.ItemDetailsPresenter;
import lucassales.com.getninjastest.ui.presenter.ItemDetailsPresenterListener;
import lucassales.com.getninjastest.ui.viewmodel.ItemDetailsViewModel;

public class DetailsActivity extends AppCompatActivity implements ItemDetailsPresenterListener, OnMapReadyCallback {

    public static final String PATH = "path";
    public static final String IS_LEAD = "isLead";
    private static final int PERMISSION_CALL = 123;
    private static final int PERMISSION_CALL_WAHTS_APP = 124;
    private boolean isLead;

    private ItemDetailsPresenter presenter;
    private ViewDataBinding viewDataBinding;
    private RecyclerView recyclerView;
    private GoogleMap googleMap;
    private ItemDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        isLead = getIntent().getBooleanExtra(IS_LEAD, false);
        presenter = new ItemDetailsPresenter(this, getIntent().getStringExtra(PATH), isLead);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onItemLoaded(final ItemDetailsViewModel viewModel) {
        this.viewModel = viewModel;
        viewDataBinding.setVariable(BR.details, this.viewModel);
        recyclerView.setAdapter(new InfoAdapter(viewModel.getInfos(), isLead));
        findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.footer).setVisibility(View.VISIBLE);
        LatLng latLng = new LatLng(viewModel.getLatitude(), viewModel.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        findViewById(R.id.footer_buttonLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLead) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        ActivityCompat.requestPermissions(DetailsActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_CALL);

                    }

                } else {
                    Intent intentForLink = Utility.createIntentForLink(DetailsActivity.this, DetailsActivity.this.viewModel.getLinkReject());
                    intentForLink.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentForLink);
                }
            }
        });

        findViewById(R.id.footer_buttonRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLead) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        ActivityCompat.requestPermissions(DetailsActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_CALL_WAHTS_APP);

                    }

                } else {
                    Intent intentForLink = Utility.createIntentForLink(DetailsActivity.this, DetailsActivity.this.viewModel.getLinkAccept());
                    intentForLink.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentForLink);
                }
            }
        });

    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof UnknownHostException) {
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + viewModel.getPhone()));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);

                }
                break;
            }
            case PERMISSION_CALL_WAHTS_APP: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PackageManager pm = getPackageManager();


                    try {

                        PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                        Intent mIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + viewModel.getPhone()));
                        mIntent.setPackage("com.whatsapp");
                        mIntent.putExtra("sms_body", "text");
                        mIntent.putExtra("chat", true);

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        startActivity(mIntent);

                    } catch (PackageManager.NameNotFoundException e) {
                        Toast.makeText(DetailsActivity.this, "WhatsApp não está instalado", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                }
            }


        }
    }
}
