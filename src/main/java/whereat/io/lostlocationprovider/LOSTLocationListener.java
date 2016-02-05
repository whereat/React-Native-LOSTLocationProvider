package whereat.io.lostlocationprovider;

import android.location.Location;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.mapzen.android.lost.api.LocationListener;

public class LOSTLocationListener implements LocationListener {

    private ReactContext reactContext;

    public LOSTLocationListener(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    @Override
    public void onLocationChanged(final Location location) {
        WritableMap params = new WritableNativeMap();

        params.putDouble("longitude", location.getLongitude());
        params.putDouble("latitude", location.getLatitude());
        params.putDouble("altitude", location.getAltitude());
        params.putDouble("bearing", location.getBearing());
        params.putDouble("speed", location.getSpeed());
        params.putDouble("accuracy", location.getAccuracy());
        params.putDouble("time", location.getTime());

        params.putBoolean("hasAccuracy", location.hasAccuracy());
        params.putBoolean("hasAltitude", location.hasAltitude());
        params.putBoolean("hasBearing", location.hasBearing());
        params.putBoolean("hasSpeed", location.hasSpeed());

        sendEvent("location_changed", params);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        this.reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }
}
