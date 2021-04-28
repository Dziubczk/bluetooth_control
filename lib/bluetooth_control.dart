
import 'dart:async';

import 'package:flutter/services.dart';

class BluetoothControl {
  static const MethodChannel _channel =
      const MethodChannel('bluetooth_control');

  static Future<bool> get isAvailable async => await _channel.invokeMethod('isAvailable');

  static Future<bool> get isEnabled async => await _channel.invokeMethod('isEnabled');

  static Future<bool> get turnOn async => await _channel.invokeMethod('turnOn');

  static Future<bool> get enable async => await _channel.invokeMethod('enable');
}
