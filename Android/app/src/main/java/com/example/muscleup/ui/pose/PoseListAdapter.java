package com.example.muscleup.ui.pose;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muscleup.R;
import com.example.muscleup.model.callback.OnImageAnalyzeSuccessListener;
import com.example.muscleup.model.data.KeyPoint;
import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.data.PoseItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PoseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PoseItem> imageList = new ArrayList<>();
    private boolean isUploadViewExist = false;
    private View.OnClickListener onUploadClickListener;
    private OnImageAnalyzeSuccessListener onImageAnalyzeSuccessListener;

    public PoseListAdapter(View.OnClickListener onUploadClickListener,
                           OnImageAnalyzeSuccessListener onImageAnalyzeSuccessListener) {
        this.onUploadClickListener = onUploadClickListener;
        this.onImageAnalyzeSuccessListener = onImageAnalyzeSuccessListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PoseItem.ITEM_POSE_IMAGE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pose, parent, false);
            return new PoseImageViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload, parent, false);
            return new UploadViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imageList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PoseImageViewHolder) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageList.get(position).getImage(),
                    0, imageList.get(position).getImage().length);
            ((PoseImageViewHolder) holder).item_upload_iv.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void addImage(byte[] image) {
        if (getItemCount() < 2) imageList.add(
                new PoseItem(PoseItem.ITEM_POSE_IMAGE, image));
    }

    public void addUploadView() {
        if (isUploadViewExist) return;

        isUploadViewExist = true;
        imageList.add(null);
    }

    public void setPose(List<Pose> poseList, int position) {

        for (int i = 0; i < poseList.size(); i++) {
            float[] keyPointArray = poseList.get(i).getKeypoints();
            List<KeyPoint> keyPointList = new ArrayList<>();

            for (int l = 0; l < poseList.get(i).getKeypoints().length / 3; l++) {
                keyPointList.add(
                        new KeyPoint(keyPointArray[l * 3], keyPointArray[(l * 3) + 1], keyPointArray[(l * 3) + 2]));
            }
            byte[] byteImage = imageList.get(position).getImage();
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            Canvas canvas = new Canvas(bitmapImage);

            drawLine(canvas, keyPointList.get(0), keyPointList.get(1));
            drawLine(canvas, keyPointList.get(0), keyPointList.get(2));
            drawLine(canvas, keyPointList.get(1), keyPointList.get(2));
            drawLine(canvas, keyPointList.get(1), keyPointList.get(3));
            drawLine(canvas, keyPointList.get(2), keyPointList.get(4));
            drawLine(canvas, keyPointList.get(3), keyPointList.get(5));
            drawLine(canvas, keyPointList.get(4), keyPointList.get(6));
            drawLine(canvas, keyPointList.get(5), keyPointList.get(6));
            drawLine(canvas, keyPointList.get(5), keyPointList.get(7));
            drawLine(canvas, keyPointList.get(5), keyPointList.get(11));
            drawLine(canvas, keyPointList.get(6), keyPointList.get(8));
            drawLine(canvas, keyPointList.get(6), keyPointList.get(12));
            drawLine(canvas, keyPointList.get(7), keyPointList.get(9));
            drawLine(canvas, keyPointList.get(8), keyPointList.get(10));
            drawLine(canvas, keyPointList.get(11), keyPointList.get(12));
            drawLine(canvas, keyPointList.get(13), keyPointList.get(11));
            drawLine(canvas, keyPointList.get(14), keyPointList.get(12));
            drawLine(canvas, keyPointList.get(15), keyPointList.get(13));
            drawLine(canvas, keyPointList.get(16), keyPointList.get(14));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteImage = stream.toByteArray();

            imageList.set(position, new PoseItem(PoseItem.ITEM_POSE_IMAGE, byteImage));
        }
        if (position == 1) onImageAnalyzeSuccessListener.onSuccess();
    }

    public byte[] getPose(int position) {
        return imageList.get(position).getImage();
    }

    private void drawLine(Canvas canvas, KeyPoint start, KeyPoint end) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (start.getScore() < 0.2 || end.getScore() < 0.2) return;
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
    }

    static class PoseImageViewHolder extends RecyclerView.ViewHolder {

        ImageView item_upload_iv;

        public PoseImageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_upload_iv = itemView.findViewById(R.id.item_upload_iv);
        }
    }

    class UploadViewHolder extends RecyclerView.ViewHolder {

        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> onUploadClickListener.onClick(itemView));
        }
    }
}
