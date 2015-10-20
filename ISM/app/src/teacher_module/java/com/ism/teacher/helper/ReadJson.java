package com.ism.teacher.helper;

import android.content.Context;

import com.zota.davaindia.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadJson {

	private Context _context;

	public ReadJson(Context context){
		this._context = context;
	}

    public String ReadEventInfo()
    {
        InputStream inputStream = _context.getResources().openRawResource(R.raw.store_detail_response);
        System.out.println(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }
}
