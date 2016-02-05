package whereat.io.lostlocationprovider;

import android.location.Location;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LOSTLocationListenerTest {

    private final String PROVIDER = "some provider";
    private final int LATITUDE = 4;
    private final int LONGITUDE = 2;
    private final int ALTITUDE = 80;
    private final float BEARING = 1.5f;
    private final float SPEED = 1.7f;
    private final float ACCURACY = 0.8f;
    private final long TIME = 1000l;

    private LOSTLocationListener lostLocationListener;
    private ReactContext context;
    private Location location;

    @Before
    public void setUp() {
        context = mock(ReactContext.class);
        lostLocationListener = new LOSTLocationListener(context);

        location = new Location(PROVIDER);
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);
        location.setAltitude(ALTITUDE);
        location.setBearing(BEARING);
        location.setSpeed(SPEED);
        location.setAccuracy(ACCURACY);
        location.setTime(TIME);
    }

    @Test
    public void ShouldEmitALocationChangedEventToJSWithLocationParamsWhenOnLocationChanged() {
        lostLocationListener.onLocationChanged(location);

        ArgumentCaptor<WritableMap> paramCaptor = ArgumentCaptor.forClass(WritableMap.class);
        WritableMap expectedParams = new WritableNativeMap();
        expectedParams.putDouble("longitude", LONGITUDE);
        expectedParams.putDouble("latitude", LATITUDE);
        expectedParams.putDouble("altitude", ALTITUDE);
        expectedParams.putDouble("bearing", BEARING);
        expectedParams.putDouble("speed", SPEED);
        expectedParams.putDouble("accuracy", ACCURACY);
        expectedParams.putDouble("time", TIME);

        expectedParams.putBoolean("hasAccuracy", true);
        expectedParams.putBoolean("hasAltitude", true);
        expectedParams.putBoolean("hasBearing", true);
        expectedParams.putBoolean("hasSpeed", true);

        verify(context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("location_changed", paramCaptor.capture());
        assertEquals(paramCaptor.getValue(), expectedParams);

    }

}