package whereat.io.lostlocationprovider;

import android.location.Location;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LOSTLocationListenerTest {

    private LOSTLocationListener lostLocationListener;

    @Mock
    private ReactContext context;
    @Mock
    private DeviceEventManagerModule.RCTDeviceEventEmitter emitter;
    @Mock
    private ReactNativeFactory reactNativeFactory;
    @Mock
    private WritableMap writableMap;


    @Before
    public void setUp() {
        initMocks(this);
        when(context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).thenReturn(emitter);
        when(reactNativeFactory.createMap()).thenReturn(writableMap);
        lostLocationListener = new LOSTLocationListener(context, reactNativeFactory);
    }

    @Test
    public void ShouldEmitALocationChangedEventToJSWithLocationParamsWhenOnLocationChanged() {
        lostLocationListener.onLocationChanged(new Location("SOME PROVIDER"));

        verify(emitter).emit("location_changed", writableMap);
    }

}