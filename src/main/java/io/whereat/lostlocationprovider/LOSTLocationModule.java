package io.whereat.lostlocationprovider;

import android.location.Location;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class LOSTLocationModule extends ReactContextBaseJavaModule {

    private static final String BALANCED_POWER_KEY = "BALANCED_POWER_ACCURACY";
    private static final String HIGH_ACCURACY_KEY = "HIGH_ACCURACY";
    private static final String LOW_POWER_KEY = "LOW_POWER";
    private static final String NO_POWER_KEY = "NO_POWER";

    private final LocationListener locationListener;
    private final LostApiClient lostApiClient;

    public LOSTLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        locationListener = new LOSTLocationListener(this.getReactApplicationContext(), new ReactNativeFactory());

        lostApiClient = new LostApiClient.Builder(this.getReactApplicationContext()).build();
        lostApiClient.connect();
    }

    @Override
    public String getName() {
        return "LOSTLocationProvider";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(BALANCED_POWER_KEY, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        constants.put(HIGH_ACCURACY_KEY, LocationRequest.PRIORITY_HIGH_ACCURACY);
        constants.put(LOW_POWER_KEY, LocationRequest.PRIORITY_LOW_POWER);
        constants.put(NO_POWER_KEY, LocationRequest.PRIORITY_NO_POWER);
        return constants;
    }

    @ReactMethod
    public void startLocationPolling(Integer interval, Float distance, int priority) {
        LocationRequest request = LocationRequest.create()
                .setInterval(interval) // milliseconds
                .setSmallestDisplacement(distance) // meters
                .setPriority(priority);

        LocationServices.FusedLocationApi.requestLocationUpdates(request, this.locationListener);
    }

    @ReactMethod
    public void stopLocationPolling() {
        LocationServices.FusedLocationApi.removeLocationUpdates(this.locationListener);
    }

    @ReactMethod
    public void mockLocation(Float latitude, Float longitude) {
        Location mockLocation = new Location("mock");
        mockLocation.setLatitude(latitude);
        mockLocation.setLongitude(longitude);
        LocationServices.FusedLocationApi.setMockMode(true);
        LocationServices.FusedLocationApi.setMockLocation(mockLocation);
    }

    @ReactMethod
    public void setMockMode(Boolean value) {
        LocationServices.FusedLocationApi.setMockMode(value);
    }
}
