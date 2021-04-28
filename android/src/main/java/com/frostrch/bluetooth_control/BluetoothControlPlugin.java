package com.frostrch.bluetooth_control;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener;


public class BluetoothControlPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, RequestPermissionsResultListener {
    private static final String TAG = "BluetoothControlPlugin";
    private Activity activity;
    private MethodChannel channel;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    private static final int REQUEST_ENABLE_BLUETOOTH = 1337;
    private static final int REQUEST_FINE_LOCATION_PERMISSIONS = 1452;

    private final Object initializationLock = new Object();

    private FlutterPluginBinding pluginBinding;
    private ActivityPluginBinding activityBinding;

    public BluetoothControlPlugin() {
    }

    private void setup(
            final BinaryMessenger messenger,
            final Application application,
            final Activity activity,
            final ActivityPluginBinding activityBinding) {
        synchronized (initializationLock) {
            Log.i(TAG, "setup");
            this.activity = activity;
            channel = new MethodChannel(messenger, "bluetooth_control/methods");
            channel.setMethodCallHandler(this);
            bluetoothManager = (BluetoothManager) application.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
            activityBinding.addRequestPermissionsResultListener(this);
        }
    }

    private void tearDown() {
        Log.i(TAG, "teardown");
        channel.setMethodCallHandler(null);
        channel = null;
        bluetoothAdapter = null;
        bluetoothManager = null;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        pluginBinding = binding;
        channel = new MethodChannel(binding.getBinaryMessenger(), "bluetooth_control");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        pluginBinding = null;
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activityBinding = binding;
        setup(
                pluginBinding.getBinaryMessenger(),
                (Application) pluginBinding.getApplicationContext(),
                activityBinding.getActivity(),
                activityBinding);
    }

    @Override
    public void onDetachedFromActivity() {
        tearDown();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (bluetoothAdapter == null) {
            if ("isAvailable".equals(call.method)) {
                result.success(false);
            } else {
                result.error("bluetooth_unavailable", "bluetooth is not available", null);
            }
            return;
        }

        switch (call.method) {
            case "isAvailable": {
                result.success(true);
                break;
            }
            case "isEnabled": {
                result.success(bluetoothAdapter.isEnabled());
                break;
            }
            case "enable":
            case "turnOn": {
                if (bluetoothAdapter.isEnabled()) {
                    result.success(true);
                    break;
                }
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
                break;
            }
            default: {
                result.notImplemented();
                break;
            }
        }
    }

    @Override
    public boolean onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSIONS) {
            return grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
