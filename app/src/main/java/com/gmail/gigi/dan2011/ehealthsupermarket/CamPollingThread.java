package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Context;

/**
 * Javadoc comment.
 */
public class CamPollingThread extends Thread {

  private AndroidCameraApi androidCameraApi;
  private Context context; // change this
  // it needs to be public, we need to access it from the AndroidCameraApi class
  boolean stopThread = true;

  CamPollingThread(Context context) {
    this.context = context;
    androidCameraApi = (AndroidCameraApi) context;
  }

  @Override
  public void run() {
    while (true) {
      if (stopThread == true) {
        try {
          androidCameraApi.takePicture();
        } catch (IllegalArgumentException e) {
          System.out.println("Camera API exception, probably the AndroidCameraApi was closed" + e);
          return;
        }
      } else {
        androidCameraApi.finish();
        return;
      }

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
