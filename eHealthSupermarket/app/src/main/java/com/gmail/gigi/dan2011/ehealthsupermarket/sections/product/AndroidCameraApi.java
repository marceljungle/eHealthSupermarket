package com.gmail.gigi.dan2011.ehealthsupermarket.sections.product;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.scan.VolleyMultipartRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Camera API which use Camera2API.
 */
public class AndroidCameraApi extends AppCompatActivity {

  private static final String TAG = "AndroidCameraApi";
  private TextureView textureView;
  private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
  private static final String PRODUCT_FIND_ERROR = "Ha ocurrido un error al buscar el producto";
  static {
    ORIENTATIONS.append(Surface.ROTATION_0, 90);
    ORIENTATIONS.append(Surface.ROTATION_90, 0);
    ORIENTATIONS.append(Surface.ROTATION_180, 270);
    ORIENTATIONS.append(Surface.ROTATION_270, 180);
  }

  private String cameraId;
  protected CameraDevice cameraDevice;
  protected CameraCaptureSession cameraCaptureSessions;
  protected CaptureRequest.Builder captureRequestBuilder;
  private Size imageDimension;
  private static final int REQUEST_CAMERA_PERMISSION = 200;
  private Handler mmBackgroundHandler;
  private HandlerThread mmBackgroundThread;
  private Button takePictureButton;
  private Context context;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_android_camera_api);
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{
          Manifest.permission.CAMERA
      }, 100);
    }
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{
          Manifest.permission.WRITE_EXTERNAL_STORAGE
      }, 100);
    }
    textureView = (TextureView) findViewById(R.id.texture);
    assert textureView != null;
    textureView.setSurfaceTextureListener(textureListener);
    takePictureButton = findViewById(R.id.takePicture);
    context = this;
    takePictureButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        takePicture();
      }
    });
  }

  @Override
  public void onBackPressed() {
    finish();
  }


  TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
      //open your camera here
      openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
      // Transform you image captured size according to the surface width and height
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
      return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
  };
  private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
    @Override
    public void onOpened(CameraDevice camera) {
      //This is called when the camera is open
      Log.e(TAG, "onOpened");
      cameraDevice = camera;
      createCameraPreview();
    }

    @Override
    public void onDisconnected(CameraDevice camera) {
      cameraDevice.close();
    }

    @Override
    public void onError(CameraDevice camera, int error) {
      cameraDevice.close();
      cameraDevice = null;
    }
  };
  final CameraCaptureSession.CaptureCallback captureCallbackListener =
      new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request,
            TotalCaptureResult result) {
          super.onCaptureCompleted(session, request, result);
          createCameraPreview();
        }
      };

  protected void startBackgroundThread() {
    mmBackgroundThread = new HandlerThread("Camera Background");
    mmBackgroundThread.start();
    mmBackgroundHandler = new Handler(mmBackgroundThread.getLooper());
  }

  protected void stopBackgroundThread() {
    mmBackgroundThread.quitSafely();
    try {
      mmBackgroundThread.join();
      mmBackgroundThread = null;
      mmBackgroundHandler = null;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  private void uploadBitmap(final byte[] buffer) {

    final String tags = "image";
    String url = "http://ehealtsupermarket.duckdns.org/api/predict2";
    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
        url,
        new Response.Listener<NetworkResponse>() {
          @Override
          public void onResponse(NetworkResponse response) {
            String dataRes = "";
            try {
              dataRes = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
            }
            //FIND PRODUCT IN THE FIREBASE DATABASE
            Intent intent = new Intent(context, ActivityProductView.class);
            db.collection("PRODUCTS").document(dataRes).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Product product = documentSnapshot.toObject(Product.class);
                    intent.putExtra("product", product);
                    startActivity(intent);
                  }
                }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(Exception error) {

                Toast.makeText(getApplicationContext(), PRODUCT_FIND_ERROR, Toast.LENGTH_LONG)
                    .show();
              }
            });
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
          }
        }) {


      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("myImage", Base64.encodeToString(buffer, Base64.NO_WRAP));
        return params;
      }
    };

    Volley.newRequestQueue(this).add(volleyMultipartRequest);
  }

  protected void takePicture() {
    if (null == cameraDevice) {
      Log.e(TAG, "cameraDevice is null");
      return;
    }
    CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    try {
      CameraCharacteristics characteristics = manager
          .getCameraCharacteristics(cameraDevice.getId());
      Size[] jpegSizes = null;
      if (characteristics != null) {
        jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            .getOutputSizes(ImageFormat.JPEG);
      }
      int width = 640;
      int height = 480;
      if (jpegSizes != null && 0 < jpegSizes.length) {
        width = jpegSizes[0].getWidth();
        height = jpegSizes[0].getHeight();
      }
      ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
      List<Surface> outputSurfaces = new ArrayList<Surface>(2);
      outputSurfaces.add(reader.getSurface());
      outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
      final CaptureRequest.Builder captureBuilder = cameraDevice
          .createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
      captureBuilder.addTarget(reader.getSurface());
      captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
      // Orientation
      int rotation = getWindowManager().getDefaultDisplay().getRotation();
      captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
      ImageReader.OnImageAvailableListener readerListener =
          new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
              Image image;
              image = reader.acquireLatestImage();
              ByteBuffer buffer = image.getPlanes()[0].getBuffer();
              byte[] bytes = new byte[buffer.capacity()];
              buffer.get(bytes);
              Toast.makeText(AndroidCameraApi.this, "Procesando imagen...", Toast.LENGTH_SHORT)
                  .show();
              uploadBitmap(bytes);
              image.close();
            }
          };
      reader.setOnImageAvailableListener(readerListener, mmBackgroundHandler);
      final CameraCaptureSession.CaptureCallback captureListener =
          new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request,
                TotalCaptureResult result) {
              super.onCaptureCompleted(session, request, result);
              createCameraPreview();
            }
          };
      cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
          try {
            session.capture(captureBuilder.build(), captureListener, mmBackgroundHandler);
          } catch (CameraAccessException e) {
            e.printStackTrace();
          }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
        }
      }, mmBackgroundHandler);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  protected void createCameraPreview() {
    try {
      SurfaceTexture texture = textureView.getSurfaceTexture();
      assert texture != null;
      texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
      Surface surface = new Surface(texture);
      captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
      captureRequestBuilder.addTarget(surface);
      cameraDevice
          .createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
              //The camera is already closed
              if (null == cameraDevice) {
                return;
              }
              // When the session is ready, we start displaying the preview.
              cameraCaptureSessions = cameraCaptureSession;
              updatePreview();
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
              Toast.makeText(AndroidCameraApi.this, "Configuration change", Toast.LENGTH_SHORT)
                  .show();
            }
          }, null);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  private void openCamera() {
    CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    Log.e(TAG, "is camera open");
    try {
      cameraId = manager.getCameraIdList()[0];
      CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
      StreamConfigurationMap map = characteristics
          .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
      assert map != null;
      imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
      // Add permission for camera and let user grant the permission
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(AndroidCameraApi.this,
            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
            REQUEST_CAMERA_PERMISSION);
        return;
      }
      manager.openCamera(cameraId, stateCallback, null);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
    Log.e(TAG, "openCamera X");
  }

  protected void updatePreview() {
    if (null == cameraDevice) {
      Log.e(TAG, "updatePreview error, return");
    }
    captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    try {
      cameraCaptureSessions
          .setRepeatingRequest(captureRequestBuilder.build(), null, mmBackgroundHandler);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
      if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
        // close the app
        Toast.makeText(AndroidCameraApi.this,
            "Perdona!, no puedes usar la app sin aceptar los permisos solicitados", Toast.LENGTH_LONG)
            .show();
        finish();
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e(TAG, "onResume");
    startBackgroundThread();
    if (textureView.isAvailable()) {
      openCamera();
    } else {
      textureView.setSurfaceTextureListener(textureListener);
    }
  }

  @Override
  protected void onPause() {
    Log.e(TAG, "onPause");
    //closeCamera();
    stopBackgroundThread();
    super.onPause();
  }
}