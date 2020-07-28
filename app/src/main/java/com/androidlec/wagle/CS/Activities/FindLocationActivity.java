package com.androidlec.wagle.CS.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.Locale;

public class FindLocationActivity extends AppCompatActivity {

    MapView mapView;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapView.clearMapTilePersistentCache();
    }

    @Override
    protected void onStart() {
        MapView.setMapTilePersistentCacheEnabled(true);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        init();

        mapView.setMapViewEventListener(mapViewEventListener);
        mapView.setPOIItemEventListener(poiItemEventListener);

    }

    private void init() {
        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        getCurrentLocation();
    }

    private void getCurrentLocation() {
        // GPS체크
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            Toast.makeText(this, "GPS를 켜주세요!", Toast.LENGTH_LONG).show();
        }

        mapView.setCurrentLocationEventListener(currentLocationEventListener);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    private void setNewMarker(MapPoint mapPoint) {
        MapPOIItem marker = new MapPOIItem();
        marker.setMapPoint(mapPoint);
        marker.setItemName(getCompleteAddressString(mapPoint.getMapPointGeoCoord().latitude, mapPoint.getMapPointGeoCoord().longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        marker.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven);
        marker.setDraggable(true);
        mapView.addPOIItem(marker);
    }

    private String getCompleteAddressString(double latitude, double longitude) {
        String strAdd;
        Geocoder geocoder = new Geocoder(FindLocationActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                strReturnedAddress.append(returnedAddress.getAddressLine(0));

                strAdd = strReturnedAddress.toString();
                // "대한민국 " 글자 지워버림
                strAdd = strAdd.substring(5);

            } else {
                strAdd = "위치를 찾을 수 없습니다.";
            }
        } catch (Exception e) {
            strAdd = "위치를 찾을 수 없습니다.";
        }

        return strAdd;
    }

    MapView.MapViewEventListener mapViewEventListener = new MapView.MapViewEventListener() {
        @Override
        public void onMapViewInitialized(MapView mapView) {

        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {

        }

        @Override
        public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
            mapView.removeAllPOIItems();
            setNewMarker(mapPoint);
        }

        @Override
        public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

        }
    };

    MapView.POIItemEventListener poiItemEventListener = new MapView.POIItemEventListener() {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
            EditText editText = new EditText(getApplicationContext());

            AlertDialog.Builder builder = new AlertDialog.Builder(FindLocationActivity.this);
            builder.setTitle("주소 이름 지정");
            builder.setMessage("이름을 지정하시고 확인을 누르면 장소지정이 완료됩니다.");
            builder.setView(editText);
            builder.setPositiveButton("확인", (dialog, which) -> {
                String name = editText.getText().toString().trim();
                if (name.length() == 0) {
                    name = mapPOIItem.getItemName();
                }
                Intent intent = new Intent();
                intent.putExtra("locationName", name);
                intent.putExtra("locationAddress", mapPOIItem.getItemName());
                setResult(RESULT_OK, intent);
                finish();
            });
            builder.setNegativeButton("취소", null);
            builder.show();

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
            mapPOIItem.setItemName(getCompleteAddressString(mapPoint.getMapPointGeoCoord().latitude, mapPoint.getMapPointGeoCoord().longitude));
        }
    };

    private MapView.CurrentLocationEventListener currentLocationEventListener = new MapView.CurrentLocationEventListener() {
        @Override
        public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
            MapPoint currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
            //이 좌표로 지도 중심 이동
            mapView.setMapCenterPoint(currentMapPoint, true);
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
            mapView.setZoomLevel(2, true);
        }

        @Override
        public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

        }

        @Override
        public void onCurrentLocationUpdateFailed(MapView mapView) {
            Toast.makeText(FindLocationActivity.this, "현재 위치를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCurrentLocationUpdateCancelled(MapView mapView) {
        }
    };

}