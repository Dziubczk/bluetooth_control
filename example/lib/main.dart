import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:bluetooth_control/bluetooth_control.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _ble;

  @override
  void initState() {
    super.initState();
    checkBle();
  }

  Future<void> checkBle() async {
    bool ble = await BluetoothControl.isEnabled;

    setState(() {
      _ble = ble;
    });
  }

  Future<void> enable() async {
    await BluetoothControl.turnOn;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Text(_ble ? 'BLE ENABLE' : 'BLE DISABLE'),
              FlatButton(
                onPressed: () => checkBle(),
                child: Text('check ble'),
              ),
              FlatButton(
                onPressed: () => enable(),
                child: Text('turn on ble'),
              ),
              FlatButton(
                onPressed: () => enable(),
                child: Text('force enable ble'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
