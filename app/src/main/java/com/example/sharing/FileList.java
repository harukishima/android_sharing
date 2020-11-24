package com.example.sharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.net.URI;
import java.net.URLConnection;

public class FileList extends ArrayAdapter<String> {
    Context context;
    String[] items;
    File[] files;
    public FileList(Context context, int layoutToBeInflated, String[] items, File[] files)
    {
        super(context, layoutToBeInflated, items);
        this.context = context;
        this.items = items;
        this.files = files;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.file_list, null);
        TextView name = (TextView) row.findViewById(R.id.name);
        final Button share = (Button) row.findViewById(R.id.btnShare);
        name.setText(files[position].getName());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile(files[position]);
            }
        });
        return (row);
    }
    private void shareFile(File file) {
        Uri uri = FileProvider.getUriForFile(context, "com.example.sharing.fileprovider", file);
        Intent shareIntent = new Intent();

        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("audio/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share..."));
    }
}
