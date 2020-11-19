package in.falconfour.sahpathi.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import in.falconfour.sahpathi.LoginActivity;
import in.falconfour.sahpathi.MainActivity;
import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.User;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ImageView dpchange;
    private ImageView logoutIv;
    private TextView logoutTv;
    private View myView;
    private TextView userNameTv;
    private TextView branchTv;
    private TextView collegeTv;
    private TextView groupTv;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mDb;
    private User objectUser;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          myView = inflater.inflate(R.layout.fragment_profile, container, false);
         logoutTv = myView.findViewById(R.id.logoutTv);
         logoutIv = myView.findViewById(R.id.logout_iv);
         userNameTv = myView.findViewById(R.id.user_name_tv);
         collegeTv = myView.findViewById(R.id.college_name_tv);
         branchTv = myView.findViewById(R.id.branch_tv);
         groupTv = myView.findViewById(R.id.group_tv);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
        userNameTv.setText(mUser.getEmail());
        mDb.collection("users").document(mUser.getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        objectUser = documentSnapshot.toObject(User.class);
                        collegeTv.setText(objectUser.getCOLLEGE());
                        branchTv.setText(objectUser.getBRANCH());
                    }
                });


         //picking up the dp
        dpchange = (ImageView) myView.findViewById(R.id.dpchange);
        dpchange.setOnClickListener(this);

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging Out
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        logoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging Out
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return myView;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        select_image();
    }

    private void select_image() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageState(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ImageView viewImage= (ImageView)myView.findViewById(R.id.circlepic);
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageState());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }

                try {
                    String[] projection = new String[0];
                    final Cursor cursor = this.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
                    if (Build.VERSION.SDK_INT >= 29) {

                        Uri imageUri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));

                        Uri mediaUri = null;
                        try (ParcelFileDescriptor pfd = this.getContext().getContentResolver().openFileDescriptor(mediaUri, "r")) {
                            if (pfd != null) {
                                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                            }
                        } catch (IOException ex) {

                        }
                    }

                    else{


                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        viewImage.setImageBitmap(bitmap);
                        String path = android.os.Environment
                                .getExternalStorageState()
                                + File.separator
                                + "Phoenix" + File.separator + "default";
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }} catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }



}
