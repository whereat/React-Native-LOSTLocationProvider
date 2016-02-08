package whereat.io.lostlocationprovider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

// Wrapper class to enable mocking of WritableMap
public class ReactNativeFactory {

    public WritableMap createMap() {
        return Arguments.createMap();
    }
}
