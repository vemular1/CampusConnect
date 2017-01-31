package com.example.campusconnect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.campusconnect.utility.SharedPrefUtility;

public class ShareFiles extends ListActivity {
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;
	private String fileLocation = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_files);
		myPath = (TextView) findViewById(R.id.path);
		root = Environment.getExternalStorageDirectory().toString();
		Log.e("test", root + " " + Environment.getExternalStorageState());
		getDir(root);
	}
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub

			getMenuInflater().inflate(R.menu.main, menu);

			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub

			int id = item.getItemId();
			if (id == R.id.home) {
				Intent home = new Intent(this, HomePage.class);
				startActivity(home);
				return true;
			}else if (id == R.id.logout) {
				
				SharedPrefUtility pref=new SharedPrefUtility(getApplicationContext());
				pref.setLoginId("");
				
				Intent logout = new Intent(this, WelcomePage.class);
				startActivity(logout);
				return true;
			}
			return super.onOptionsItemSelected(item);

		}
	private void getDir(String dirPath) {
		myPath.setText("Location: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);

		if (f.exists()) {
			// Log.e("test", myFiles);

			File[] files = f.listFiles();

			if (!dirPath.equals(root)) {
				item.add(root);
				path.add(root);
				item.add("../");
				path.add(f.getParent());
			}

			for (int i = 0; i < files.length; i++) {
				File file = files[i];

				if (!file.isHidden() && file.canRead()) {
					path.add(file.getPath());
					if (file.isDirectory()) {
						item.add(file.getName() + "/");

					} else {
						item.add(file.getName());
					}
				}
			}

			ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
					R.layout.row, item);
			setListAdapter(fileList);
		} else
			Log.e("test", "doesnot exist");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(path.get(position));

		if (file.isDirectory()) {
			if (file.canRead()) {
				getDir(path.get(position));
			} else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle(
								"[" + file.getName()
										+ "] folder can't be read!")
						.setPositiveButton("OK", null).show();
			}
		} else {
		//	new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
			//		.setTitle("[" + file.getName() + "]")
				//	.setPositiveButton("OK", null).show();

			// share the file here
			fileLocation = file.getAbsolutePath();
			 Intent sharingIntent = new Intent(
			 android.content.Intent.ACTION_SEND);
			Log.i("test", file.getAbsolutePath());			 			 			
			 Uri uri = Uri.fromFile((file));
			 sharingIntent.setType("*/*");		//getMimeType(file.getName())	 			 
			 sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
			 startActivity(Intent.createChooser(sharingIntent, "share via"));
		}
	}
	public static String getMimeType(String url) {
	    String type = null;
	    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
	    if (extension != null) {
	        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
	    }
	    return type;
	}
	
	private Intent getDefaultShareIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("*");

		if (!fileLocation.isEmpty()) {
			Uri uri = Uri.fromFile(getFileStreamPath(fileLocation));
			intent.putExtra(Intent.EXTRA_STREAM, uri);
		}

		Log.e("test", fileLocation);

		//

		return intent;
	}

}
