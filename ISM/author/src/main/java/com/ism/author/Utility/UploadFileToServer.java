package com.ism.author.Utility;

/**
 * Created by c162 on 30/10/15.
 */
public class UploadFileToServer
{


//    String TAG = "UploadFileToServer";
//
//    long totalSize = 0;
//
//    public Context context;
//
//
//    public UploadFileToServer(Context context) {
//
//        this.context = context;
//
//        holder.img_status_back.setImageDrawable(null);
//
//        holder.img_status_back.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
//
//        lyfData.mediaStatus = AppConstant.STATUS_UPLOADING;
//
//
//        Log.e(TAG, "UploadFileToServer = " + lyfData.mediaStatus);
//
//    }
//
//
//    @Override
//
//    protected String doInBackground(String... params) {
//
//        Log.e(TAG, "doInBackground");
//
//        if (isCancelled()) return "";
//
//        else return uploadFile();
//
//
//    }
//
//
//    private String uploadFile() {
//
//
//        String responseString = null;
//        URLConnection connection = new URL(url).openConnection();
//        connection.setDoOutput(true);
////        HttpClient httpclient = new DefaultHttpClient();
////
////        HttpPost httppost = new HttpPost(WSConstants.SERVER_URL + WSConstants.URL_UploadLyf);
//
//        try {
//
//            CustomMultiPartEntity entity = new CustomMultiPartEntity(new RecoverySystem.ProgressListener() {
//
//                @Override
//
//                public void transferred(long num) {
//
//                    if (isCancelled()) return;
//
//                    int p = (int) ((float) ((float) num / (float) totalSize) * 100);
//
//                    publishProgress(p);
//
//                }
//
//            });
//
//
//            String file_path = null;
//
//            String thfile_path = null;
//
//
//            if (lyfData.lyf_type == LyfMessageData.Item.CELL_LYF_IMAGE) {
//
//                file_path = AppGlobal.getWisperAppImageDirectory() + "/" + lyfData.media_name;
//
//                // 	thfile_path = AppGlobal.getWisperAppImageDirectory() + "/th_" +lyfData.media_name;
//
//                thfile_path = getImageThumb(file_path, lyfData.media_name).getAbsolutePath();
//
//            } else if (lyfData.lyf_type == LyfMessageData.Item.CELL_LYF_VIDEO) {
//
//                file_path = AppGlobal.getWisperAppVedioDirectory() + "/" + lyfData.media_name;
//
//                thfile_path = AppGlobal.getWisperAppImageDirectory() + "/th_" + lyfData.media_name.replace("mp4", "png");
//
//            }
//
//
//            Log.e(TAG, file_path);
//
//            File sourceFile = new File(file_path);
//
//            File thumb_file = new File(thfile_path);
//
//
//            if (((Activity) context) instanceof MainActivity) {
//
//                entity.addPart(WSConstants.PARAM_USERID, new StringBody(((MainActivity) context).user.getId()));
//
//            } else {
//
//                entity.addPart(WSConstants.PARAM_USERID, new StringBody(((ProfileLyfActivity) context).user.getId()));
//
//            }
//
//
//            entity.addPart(WSConstants.PARAM_THUMBDATA, new FileBody(thumb_file, "image/*"));
//
//            entity.addPart(WSConstants.PARAM_MEDIANAME, new StringBody(lyfData.media_name));
//
//            if (lyfData.lyf_type == LyfMessageData.Item.CELL_LYF_IMAGE) {
//
//                entity.addPart(WSConstants.PARAM_MEDIATYPE, new StringBody("" + LyfMessageData.Item.CELL_LYF_IMAGE));
//
//                entity.addPart(WSConstants.PARAM_MEDIADATA, new FileBody(sourceFile, "image/*"));
//
//            } else if (lyfData.lyf_type == LyfMessageData.Item.CELL_LYF_VIDEO) {
//
//                entity.addPart(WSConstants.PARAM_MEDIATYPE, new StringBody("" + LyfMessageData.Item.CELL_LYF_VIDEO));
//
//                entity.addPart(WSConstants.PARAM_MEDIADATA, new FileBody(sourceFile, "video/*"));
//
//            }
//
//
//            httppost.setEntity(entity);
//
//            totalSize = entity.getContentLength();
//
//
//            HttpResponse response = httpclient.execute(httppost);
//
//
//            HttpEntity r_entity = response.getEntity();
//
//
//            int statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode == 200) {
//
//                responseString = EntityUtils.toString(r_entity);
//
//            } else {
//
//                responseString = "Error occurred! Http Status Code: " + statusCode;
//
//            }
//
//        } catch (ClientProtocolException e) {
//
//            responseString = e.toString();
//
//        } catch (Exception e) {
//
//            responseString = e.toString();
//
//        }
//
//        return responseString;
//
//    }
//
//
//    private File getImageThumb(String file_path, String fileName) {
//
//        try {
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//
//            options.inSampleSize = 2;
//
//            Bitmap preview_bitmap = BitmapFactory.decodeFile(file_path, options);
//
//
//            Log.e("getImageThumb", "drawBitmap Thmb image loaded...");
//
//
//            Bitmap dstBmp;
//
//            if (preview_bitmap.getWidth() >= preview_bitmap.getHeight()) {
//
//                dstBmp = Bitmap.createBitmap(preview_bitmap,
//
//                        preview_bitmap.getWidth() / 2 - preview_bitmap.getHeight() / 2, 0,
//
//                        preview_bitmap.getWidth(), preview_bitmap.getWidth() / 2);
//
//            } else {
//
//                dstBmp = Bitmap.createBitmap(preview_bitmap, 0,
//
//                        preview_bitmap.getHeight() / 2 - preview_bitmap.getWidth() / 2, preview_bitmap.getWidth(),
//
//                        preview_bitmap.getWidth() / 2);
//
//            }
//
//
//            Log.e("getImageThumb", "drawBitmap Thmb image created...");
//
//
//            dstBmp = AppGlobal.fastblur(dstBmp, 5);
//
//
//            Log.e("getImageThumb", "drawBitmap Thmb image blured...");
//
//
//            File th_outFile = new File(AppGlobal.getWisperAppImageDirectory(), "th_" + fileName);
//
//            FileOutputStream out = new FileOutputStream(th_outFile);
//
//            dstBmp.compress(Bitmap.CompressFormat.PNG, 80, out);
//
//            out.close();
//
//            return th_outFile;
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//        return null;
//
//    }
//
//
//    @Override
//
//    protected void onProgressUpdate(final Integer... values) {
//
//        super.onProgressUpdate(values);
//
//        if (values[0] <= 100) {
//
//            holder.progressbar.setProgress(values[0]);
//
//        }
//
//    }
//
//
//    @Override
//
//    protected void onPostExecute(String result) {
//
//        super.onPostExecute(result);
//
//        Log.e("responseString", result);
//
//
//        try {
//
//            ResponseObject response = getMapper().readValue(result, ResponseObject.class);
//
//            if (response.getStatus() == 1) {
//
//                UploadData questionData = response.getQuestionData();
//
//                if (questionData != null) {
//
//                    if (questionData.getMedia_name().length() > 0) {
//
//                        try {
//
//                            lyfData.mediaStatus = AppConstants.MediaStatus.Status_Uploaded;
//
//                            lyfData.media_name = questionData.getMedia_name();
//
//                            sendMediaNotification();
//
//                            updateLyfStatus(context);
//
//                            RefreshStatusforCell(context);
//
//                        } catch (Exception e1) {
//
//                            e1.printStackTrace();
//
//                        }
//
//                    }
//
//                }
//
//            } else {
//
//                try {
//
//                    lyfData.mediaStatus = AppConstants.MediaStatus.Status_DownloadFail;
//
//                    updateLyfStatus(context);
//
//                    RefreshStatusforCell(context);
//
//                } catch (Exception e1) {
//
//                    e1.printStackTrace();
//
//                }
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            //	Toast.makeText(context, "File Not Found !", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

}