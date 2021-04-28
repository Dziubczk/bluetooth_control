# bluetooth_control

Bluetooth control plugin.

## Getting Started

### Depending
```yaml
dependencies:
  bluetooth_control: ^0.0.1

```

### Importing
```dart
import 'package:bluetooth_control/bluetooth_control.dart';
```

### Usage

#### Check is Bluetooth enable

```dart
Future<bool> get isBluetoothEnable async => await BluetoothControl.isEnable;
```

## Enable Bluetooth

```dart
Future<bool> get turnOnBluetooth async => await BluetoothControl.turnOn;
```
