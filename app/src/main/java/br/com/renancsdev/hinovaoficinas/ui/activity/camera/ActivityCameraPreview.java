package br.com.renancsdev.hinovaoficinas.ui.activity.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityCameraPreviewBinding;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityDetalhesOficinaBinding;
import br.com.renancsdev.hinovaoficinas.ui.activity.oficina.ActivityDetalhesOficina;

public class ActivityCameraPreview extends AppCompatActivity {

    private ActivityCameraPreviewBinding binding;

    private static final  Integer LAYOUT = R.layout.activity_camera_preview;


    CameraDevice cameraDevice;
    private String cameraId;
    private Size imageDimension;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    protected CaptureRequest.Builder captureRequestBuilder;
    protected CameraCaptureSession cameraCaptureSessions;
    Integer CountResumedTimesCamera = 0;


    String iFoto = null;
    String iNome = "";
    String iTelefone = "";
    Integer iAvaliacao = 0;
    String iEndereco = "";
    String iDescricao = "";
    Double iLatitude = 0.0;
    Double iLongitude = 0.0;


    private String avaliacao = "avaliacao";
    private String telefone = "telefone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
        setarDadosIntent();
    }

    /* Configuração Inicial */
    private void setarConfig(){
        setarConfigLayout();
        setarConfigDataBinding();
        binding.camTexture.setSurfaceTextureListener(mSurfaceTexture);
    }

    private void setarConfigLayout(){
        setContentView(LAYOUT);
    }

    private void setarConfigDataBinding(){
        binding = DataBindingUtil.setContentView(this , LAYOUT);
    }

    //Prepara o Textureview
    TextureView.SurfaceTextureListener mSurfaceTexture = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
            CountResumedTimesCamera++;
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    // TODO Cria o preview da camera
    // Abre a câmera
    private void openCamera(){
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                manager.openCamera(cameraId, cameraStateCallback, null);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    // Fecha a câmera
    private void closeCamera(){
        if(cameraDevice != null){
            cameraDevice.close(); //fecha a camera
            cameraDevice = null; // variavel fica nula
        }
    }

    // Verifica o estado da câmera ,se está aberta, fechada ou se deu erro
    private CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            //Toast.makeText(CameraFotografar.this, " Camera Fechada ", Toast.LENGTH_SHORT).show();
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
            Toast.makeText(ActivityCameraPreview.this, "Erro :"+error, Toast.LENGTH_SHORT).show();
        }
    };

    // TODO Preview da Imagem
    // Cria o preview da foto
    protected void createCameraPreview() {
        try {
            SurfaceTexture surfaceTexture = binding.camTexture.getSurfaceTexture();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int heightDisply = displayMetrics.heightPixels + getNavigationBarHeight();
            int WidthDisply  = displayMetrics.widthPixels + getNavigationBarHeight();
            surfaceTexture.setDefaultBufferSize(heightDisply,WidthDisply);

            Surface surface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    cameraCaptureSessions = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {}
            },null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //Atualiza o preview
    protected void updatePreview() {
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {e.printStackTrace();
        } catch (NullPointerException null_e){
            //mensagem.ErrorMsgRedirect("Fatal Error \n(CreateCameraPreview(Preview Class)) \n\n"+null_e,CameraFotografar.this, MainActivity.class);
        }
    }

    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else return 0;
        }
        return 0;
    }

    // TODO BackroundThread()
    protected void startBackroundThread(){
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackroundThread(){
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(binding.camTexture.isAvailable()){openCamera();}
        else{binding.camTexture.setSurfaceTextureListener(mSurfaceTexture); }

        binding.fabCameraVoltar.setOnClickListener(v ->{
            Intent intent = new Intent(ActivityCameraPreview.this, ActivityDetalhesOficina.class);
            intent.putExtra("foto" , "");
            intent.putExtra("nome" , "");
            intent.putExtra("telefone"  , "");
            intent.putExtra("avaliacao" , "");
            intent.putExtra("endereco"  , "");
            intent.putExtra("descricao" , "");
            intent.putExtra("latitude"  , 0.0);
            intent.putExtra("longitude" , 0.0);
            startActivity(intent);
        });
    }
    @Override
    protected void onPause(){
        stopBackroundThread();
        closeCamera();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        closeCamera();
        super.onDestroy();
    }

    private void setarDadosIntent(){
        this.iFoto = getIntent().getStringExtra("foto");
        this.iNome = getIntent().getStringExtra("nome");
        this.iTelefone = getIntent().getStringExtra(telefone);
        this.iAvaliacao = getIntent().getIntExtra(avaliacao , 0);
        this.iEndereco = getIntent().getStringExtra("endereco");
        this.iDescricao = getIntent().getStringExtra("descricao");
        this.iLatitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        this.iLongitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
    }

}