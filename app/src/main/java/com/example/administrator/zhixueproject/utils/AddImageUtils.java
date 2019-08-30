package com.example.administrator.zhixueproject.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import java.io.File;

/**
 *
 * 上传图片
 */

public class AddImageUtils {
    public static Uri imageUri;//原图保存地址
    public static String outputUri= FileUtils.getSdcardPath()+"crop.png";//裁剪后地址
    public static Uri outputUriSmall;// 缩略图展示
    public static final int REQUEST_PICK_IMAGE = 1; //相册选取
    public static final int REQUEST_CAPTURE = 2;  //拍照
    public static final int REQUEST_PICTURE_CUT = 3;  //剪裁图片
    public static final int REQUEST_PICTURE_CUT_SMALL = 4; // 发布帖子使用

    /**
     * 从相册选择
     */
    public static void selectFromAlbum(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) context).startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    /**
     * 打开系统相机
     */
    public static void openCamera(Context context) {
        File file = new FileStorage().createIconFile();
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        ((Activity) context).startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 裁剪
     */
    public static String cropPhoto(Context context) {
        File file = new File(outputUri);
        if(file.isFile()){
            file.delete();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 480);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outputUri)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        ((Activity) context).startActivityForResult(intent, REQUEST_PICTURE_CUT);
        return outputUri;
    }

    /**
     * 裁剪
     */
    public static Uri cropPhotoSmall(Context context) {
            File file = new FileStorage().createCropFile();
            //缩略图保存地址
            outputUriSmall = Uri.fromFile(file);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(imageUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0.5);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 600);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUriSmall);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            ((Activity)context).startActivityForResult(intent, REQUEST_PICTURE_CUT_SMALL);
            return outputUriSmall;
    }
    /**
     * 从相册选择后的操作
     *
     * @param data
     */
    @TargetApi(19)
    public static void handleImageOnKitKat(Intent data, Context context) {
        imageUri = data.getData();

        if (DocumentsContract.isDocumentUri(context, imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
        }

    }

    public static void handleImageBeforeKitKat(Intent intent, Context context) {
        imageUri = intent.getData();
    }

    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
