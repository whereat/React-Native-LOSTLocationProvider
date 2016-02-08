#React-Native-LOSTLocationProvider
A simple React Native Android module that wraps Mapzen's LOST [(Location Open Source Tracker)](https://github.com/mapzen/LOST) as an alternative to the standard React Native MapView component.

*Current Version: 0.0.1*

##Installation (Gradle)
    repositories {
        jcenter()
    }
    dependencies {
      ...
      compile 'io.whereat.lib:lostlocationprovider:0.0.+'
    }
####Register Module (MainActivity.java)

    import io.whereat.LOSTLocationProvider.*; 

    public class MainActivity extends ReactActivity {
      ...
      @Override
      protected List<ReactPackage> getPackages() {
        return Arrays.asList(
          ...,
          new LOSTLocationPackage());
        }
      }
      ...
    }
####Import into React Native
    import {NativeModules} from 'react-native';
    const location = NativeModules.LOSTLocationProvider;
##Usage
####Constants (Mapped from LOST LocationRequest Constants)
  - BALANCED_POWER_ACCURACY
  - HIGH_ACCURACY
  - LOW_POWER
  - NO_POWER
  
####Methods
  - `startLocationPolling(Integer interval, Float distance, int priority)`
    - Request location updates from LOST
    - Parameters:
      - interval - Polling frequency in milliseconds
      - distance - Minimum distance for update in meters
      - priority - Poling priority mode (Should use provided constants)
    - NOTE: This method lazy loads the location listener. StartLocationPolling must be called before other methods are called.
  - `stopLocationPolling()`
    - Stop location updates from LOST
  - `mockLocation(Float latitude, Float longitude)`
    - Immediately send a mock location to the location listener.
    - NOTE: This method requires a location listener to have been loaded for it to work. Call `startLocationPolling()` first.
    - NOTE: This method leaves LOST in mock mode. Calling `startLocationPolling()` after this without turning off mock mode will not work.
  - `setMockMode(Boolean value)`
    - Set LOST mock mode. When mock mode is off, location polling will work as expected, using cell networks, wifi, and gps to resolve device location. When mock mode is on, polling will stop and the location listener will immediately broadcast any locations sent through `mockLocation()`.
    
####Events
######location_changed
  - parameters:
    - longitude
    - latitude
    - altitude
    - bearing
    - speed
    - accuracy
    - time
    - hasAccuracy
    - hasAltitude
    - hasBearing
    - hasSpeed
  - example:
  
    `DeviceEventEmitter.addListener('location_changed', this.myEventHandler);`


  

