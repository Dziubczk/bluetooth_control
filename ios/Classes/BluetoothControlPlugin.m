#import "BluetoothControlPlugin.h"
#if __has_include(<bluetooth_control/bluetooth_control-Swift.h>)
#import <bluetooth_control/bluetooth_control-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "bluetooth_control-Swift.h"
#endif

@implementation BluetoothControlPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftBluetoothControlPlugin registerWithRegistrar:registrar];
}
@end
