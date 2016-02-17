package in.helpchat.assignment.contacts;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by admin on 15/02/16.
 */
public class DetailView extends Fragment{
    int GALLERY_PICTURE=1;
    int CAMERA_REQUEST=0;
    String imagePath;
    ImageView image;
    int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState){
        final View view=inflater.inflate(R.layout.detail_view, container, false);
        Bundle bundle = this.getArguments();
        id = Integer.parseInt(bundle.getString("id"));
        final Context context=getActivity();
        final DatabaseHandler db= new DatabaseHandler(context);
        final Contacts cn=db.getContact(id);
        String name=cn.getName();
        final String phoneNumber=cn.getPhoneNumber();
        imagePath=cn.getImagePath();

        TextView t1=(TextView) view.findViewById(R.id.name);
        TextView t2=(TextView) view.findViewById(R.id.phoneNumber);
        image=(ImageView) view.findViewById(R.id.imageView);
        ImageButton editImage=(ImageButton) view.findViewById(R.id.editImage);
        Button deleteButton=(Button) view.findViewById(R.id.deleteButton);
        Button updateButton=(Button) view.findViewById(R.id.updateButton);
        t1.setText(name);
        t2.setText(phoneNumber);
        if(imagePath!=null){
            image.setImageURI(Uri.parse(cn.getImagePath()));
        }
        else{
            image.setImageResource(R.mipmap.ic_launcher);
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = ((EditText) view.findViewById(R.id.name)).getText().toString();
                String newPhoneNumber = ((EditText) view.findViewById(R.id.phoneNumber)).getText().toString();
                String newImagePath = imagePath;
                cn.setName(newName);
                cn.setPhoneNumber(newPhoneNumber);
                cn.setImagePath(newImagePath);
                db.updateContact(cn);
                Fragment fr = new ListView();
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.switchContent(R.id.list_view, fr);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteContact(cn);
                Fragment fr = new ListView();
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.switchContent(R.id.list_view, fr);
            }
        });

        editImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        return view;
    }

    private void dialog(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent pictureActionIntent = new Intent(
                        Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent,GALLERY_PICTURE);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "Pictures/"+"contactsApp"+id+".png");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });

        myAlertDialog.show();

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        String filePath = Environment.getExternalStorageDirectory()+ File.separator + "Pictures/"+"contactsApp"+id+".png";
        if(requestCode==GALLERY_PICTURE&&null!=data){
            Uri selectedImage=data.getData();
            imagePath=selectedImage.toString();
            image.setImageURI(Uri.parse(imagePath));
            image.buildDrawingCache();
            Bitmap bm=image.getDrawingCache();
            OutputStream fOut = null;
            try {
                File savePath = new File(filePath);
                imagePath=(Uri.fromFile(savePath)).toString();
                fOut = new FileOutputStream(savePath);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error occured. Please try again later."+e, Toast.LENGTH_LONG).show();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
            }
        }
        if(requestCode==CAMERA_REQUEST&&null!=data){
            File savePath = new File(filePath);
            imagePath=(Uri.fromFile(savePath)).toString();
            image.setImageURI(Uri.parse(imagePath));
        }
    }

}
