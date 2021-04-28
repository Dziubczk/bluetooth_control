import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:bluetooth_control/bluetooth_control.dart';

void main() {
  const MethodChannel channel = MethodChannel('bluetooth_control');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await BluetoothControl.isAvailable, '42');
    expect(await BluetoothControl.isEnabled, '42');
    expect(await BluetoothControl.enable, '42');
    expect(await BluetoothControl.turnOn, '42');
  });
}
