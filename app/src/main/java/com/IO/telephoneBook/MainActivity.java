package com.IO.telephoneBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.sayuti.lib.WaveSideBar;
import com.sayuti.lib.WaveSideBar.OnLetterSelectedListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.ClipData;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;

public class MainActivity extends AppCompatActivity {
	public final int REQ_CD_FP = 101;
	
	private LinearLayout toolbar_lin;
	private ScrollView main_scroll;
	private ImageView toolbarMenu;
	private TextView toolbarTitle_text;
	private ImageView toolbarNewContact;
	private LinearLayout body;
	private LinearLayout contacts_lin;
	private ScrollView contact_scroll;
	private LinearLayout settings_lin;
	private RecyclerView contacts_recycler;
	private WaveSideBar sort_wavesidebar;
	private LinearLayout contact_lin;
	private TextView contact_text;
	private EditText phoneNumber_edittext;
	private LinearLayout linear1;
	private EditText job_edittext;
	private EditText email_edittext;
	private EditText address_edittext;
	private EditText notes_edittext;
	private Button addContact_button;
	private EditText name_edittext;
	private EditText surname_edittext;
	private TextView settings_text;
	private LinearLayout sortParam_lin;
	private EditText transferPath_edit;
	private LinearLayout dataTransfer_lin;
	private Button deleteAll_button;
	private TextView sortParam_text;
	private Spinner sortBy_spinner;
	private Button import_button;
	private Button export_button;
	
	private Intent intent = new Intent();
	private SharedPreferences sp;
	private AlertDialog.Builder dial;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		toolbar_lin = (LinearLayout) findViewById(R.id.toolbar_lin);
		main_scroll = (ScrollView) findViewById(R.id.main_scroll);
		toolbarMenu = (ImageView) findViewById(R.id.toolbarMenu);
		toolbarTitle_text = (TextView) findViewById(R.id.toolbarTitle_text);
		toolbarNewContact = (ImageView) findViewById(R.id.toolbarNewContact);
		body = (LinearLayout) findViewById(R.id.body);
		contacts_lin = (LinearLayout) findViewById(R.id.contacts_lin);
		contact_scroll = (ScrollView) findViewById(R.id.contact_scroll);
		settings_lin = (LinearLayout) findViewById(R.id.settings_lin);
		contacts_recycler = (RecyclerView) findViewById(R.id.contacts_recycler);
		sort_wavesidebar = (WaveSideBar) findViewById(R.id.sort_wavesidebar);
		contact_lin = (LinearLayout) findViewById(R.id.contact_lin);
		contact_text = (TextView) findViewById(R.id.contact_text);
		phoneNumber_edittext = (EditText) findViewById(R.id.phoneNumber_edittext);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		job_edittext = (EditText) findViewById(R.id.job_edittext);
		email_edittext = (EditText) findViewById(R.id.email_edittext);
		address_edittext = (EditText) findViewById(R.id.address_edittext);
		notes_edittext = (EditText) findViewById(R.id.notes_edittext);
		addContact_button = (Button) findViewById(R.id.addContact_button);
		name_edittext = (EditText) findViewById(R.id.name_edittext);
		surname_edittext = (EditText) findViewById(R.id.surname_edittext);
		settings_text = (TextView) findViewById(R.id.settings_text);
		sortParam_lin = (LinearLayout) findViewById(R.id.sortParam_lin);
		transferPath_edit = (EditText) findViewById(R.id.transferPath_edit);
		dataTransfer_lin = (LinearLayout) findViewById(R.id.dataTransfer_lin);
		deleteAll_button = (Button) findViewById(R.id.deleteAll_button);
		sortParam_text = (TextView) findViewById(R.id.sortParam_text);
		sortBy_spinner = (Spinner) findViewById(R.id.sortBy_spinner);
		import_button = (Button) findViewById(R.id.import_button);
		export_button = (Button) findViewById(R.id.export_button);
		sp = getSharedPreferences("data.io", Activity.MODE_PRIVATE);
		dial = new AlertDialog.Builder(this);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		toolbarMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (settings_lin.getVisibility() == View.GONE) {
					_animation(15);
				}
				else {
					_animation(18);
				}
				_animation(11);
			}
		});
		
		toolbarNewContact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (contact_scroll.getVisibility() == View.GONE) {
					_animation(5);
				}
				else {
					_animation(8);
				}
				_animation(10);
			}
		});
		
		sort_wavesidebar.setOnLetterSelectedListener(new WaveSideBar.OnLetterSelectedListener() {
			@Override
			public void onLetterSelected(String _index) {
				final ContactsAdapter adapter = (ContactsAdapter)contacts_recycler.getAdapter();
				contacts_recycler.smoothScrollToPosition((int)adapter.getFirstLetterPosition(_index));
			}
		});
		
		addContact_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try{
					if (phoneNumber_edittext.getText().toString().length() > 0) {
						final ContactsAdapter adapter = (ContactsAdapter)contacts_recycler.getAdapter();
						
						String phoneNumber = phoneNumber_edittext.getText().toString();
						String name = name_edittext.getText().toString();
						String surname = surname_edittext.getText().toString();
						String job = job_edittext.getText().toString();
						String email = email_edittext.getText().toString();
						String address = address_edittext.getText().toString();
						String notes = notes_edittext.getText().toString();
						if (contact_text.getText().toString().equals("New contact")) {
							Contact contact = new Contact(phoneNumber, name, surname, job, email, address, notes);
							
							adapter.addItem(contact);
							_sort();
							_save();
						}
						if (contact_text.getText().toString().equals("Edit contact")) {
							int position = sp.getInt("lastPosition", -1);
							Contact contact = new Contact(phoneNumber, name, surname, job, email, address, notes);
							
							if(position != -1){
										adapter.setItem(position, contact);
								_sort();
								_save();
							}
						}
						_animation(8);
						SketchwareUtil.hideKeyboard(getApplicationContext());
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Contact must contain a phone number at least");
					}
				}catch(Exception e){
					final String exc = e.toString() + " >>> " + e.getStackTrace()[0].getLineNumber();
					dial.setTitle("Error");
					dial.setMessage(exc);
					dial.create().show();
				}
			}
		});
		
		transferPath_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				String path = transferPath_edit.getText().toString();
				if (path.length() == 0) {
					sp.edit().remove("transferPath").commit();
				}
				else {
					if(!path.startsWith("/")) path = "/" + path;
					if(!path.endsWith("/")) path += "/";
					
					sp.edit().putString("transferPath", path).commit();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		deleteAll_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
				dlg.setTitle("Delete all contacts?");
				dlg.setMessage("This operation cannot be undone");
				dlg.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						sp.edit().remove("contacts").commit();
						_update();
					}
				});
				dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dlg.setNeutralButton("Close", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dlg.create().show();
			}
		});
		
		sortBy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				sp.edit().putInt("sortParam", _position).commit();
				_sort();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		import_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				fp.setType("*/*");
				fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
				fp = Intent.createChooser(fp, "Choose a file to import");
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		export_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try{
					Date date = new Date();
					String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(date.getTime());
					
					String path = FileUtil.getExternalStorageDir() + sp.getString("transferPath", "/Download/TelephoneBook/");
					String filename = "contacts_" + timestamp + ".dat";
					
					String data = sp.getString("contacts", "");
					data = Encryption.encrypt("test_key-2023", data);
					if (!FileUtil.isExistFile(path)) {
						FileUtil.makeDir(path);
					}
					FileUtil.writeFile(path + filename, data);
					SketchwareUtil.showMessage(getApplicationContext(), "Exported to " + path + filename);
				}catch(Exception e){
					final String exc = e.toString() + " >>> " + e.getStackTrace()[0].getLineNumber();
					dial.setTitle("Error occurred when exporting contacts");
					dial.setMessage(exc);
					dial.create().show();
				}
			}
		});
	}
	
	private void initializeLogic() {
		_style();
		_setup();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				try{
					String path = _filePath.get(0);
					String data = FileUtil.readFile(path);
					data = Encryption.decrypt("test_key-2023", data);
					
					sp.edit().putString("contacts", data).commit();
					_update();
					SketchwareUtil.showMessage(getApplicationContext(), "Imported from " + path);
				}catch(Exception e){
					final String exc = e.toString() + " >>> " + e.getStackTrace()[0].getLineNumber();
					dial.setTitle("Error occurred when importing contacts");
					dial.setMessage(exc);
					dial.create().show();
				}
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	public void _setup () {
		try{
			final AlertDialog.Builder dlg = new AlertDialog.Builder(this);
			toolbar_lin.setVisibility(View.VISIBLE);
			contacts_lin.setVisibility(View.VISIBLE);
			contact_scroll.setVisibility(View.GONE);
			settings_lin.setVisibility(View.GONE);
			contacts_recycler.setLayoutManager(new LinearLayoutManager(this));
			final ContactsAdapter adapter = new ContactsAdapter(getBaseContext(), new ArrayList<Contact>());
			ContactsAdapter.ItemClickListener icl = new ContactsAdapter.ItemClickListener(){
				    @Override
				    public void onItemClicked(final View view, final int position){
					final Contact contact = adapter.getItem(position);
					dlg.setTitle("Contact " + String.valueOf(position+1));
					dlg.setMessage(contact.toString(true));
					dlg.setPositiveButton("Call", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							String number = contact.getPhoneNumber().getFullNumber().trim();
							intent.setAction(Intent.ACTION_CALL);
							intent.setData(Uri.parse("tel:" + number));
							startActivity(intent);
						}
					});
					dlg.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							adapter.removeItem(position);
							_save();
						}
					});
					dlg.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							phoneNumber_edittext.setText(contact.getPhoneNumber().getFullNumber());
							name_edittext.setText(contact.getName());
							surname_edittext.setText(contact.getSurname());
							job_edittext.setText(contact.getJob());
							email_edittext.setText(contact.getEmail());
							address_edittext.setText(contact.getAddress());
							notes_edittext.setText(contact.getNotes());
							
							sp.edit().putInt("lastPosition", position).commit();
							_animation(6);
						}
					});
					dlg.create().show();
					    }
			};
			adapter.setClickListener(icl);
			
			contacts_recycler.setAdapter(adapter);
			ArrayList<String> sortParams = new ArrayList<String>(){
				    {
					    add("Default");
					    add("Name");
					    add("Surname");
					    add("Job");
					    add("Email");
					    add("Address");
					    add("Notes");
					    }
			};
			sortBy_spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, sortParams));
			sortBy_spinner.setSelection((int)(sp.getInt("sortParam", 0)));
			transferPath_edit.setText(sp.getString("transferPath", "/Download/TelephoneBook/"));
			_update();
			_sort();
		}catch(Exception e){
			final String exc = e.toString() + " >>> " + e.getStackTrace()[0].getLineNumber();
			dial.setTitle("Error");
			dial.setMessage(exc);
			dial.create().show();
		}
	}
	
	
	public void _style () {
		//Layout params
		
		int height = SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - 28;
		int contacts = (int)(height  * 0.88);
		int toolbar = height - contacts;
		toolbar_lin.setLayoutParams(new LinearLayout.LayoutParams((int)(SketchwareUtil.getDisplayWidthPixels(getApplicationContext())), (int)(toolbar)));
		contacts_lin.setLayoutParams(new LinearLayout.LayoutParams((int)(SketchwareUtil.getDisplayWidthPixels(getApplicationContext())), (int)(contacts)));
		//Corners
		sort_wavesidebar.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)1, 0xFFFFF2CC, 0xFFF4B183));
		contact_lin.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)16, (int)5, 0xFFD7CCC8, 0xFFDFA67B));
		addContact_button.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)6, (int)3, 0xFF6D4C41, 0xFF5D4037));
		import_button.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)2, 0xFF795548, 0xFF5D4037));
		export_button.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)22, (int)2, 0xFF8D6E63, 0xFF6D4C41));
		deleteAll_button.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)8, (int)4, 0xFFB71C1C, 0xFF5D4037));
		//Fonts
		toolbarTitle_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/iconic_font.ttf"), 1);
		contact_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/iconic_font.ttf"), 1);
	}
	
	
	public void _update () {
		ArrayList<Contact> contacts = Contact.Serializer.fromString(sp.getString("contacts", ""));
		
		final ContactsAdapter adapter = (ContactsAdapter)contacts_recycler.getAdapter();
		adapter.setData(contacts);
	}
	
	
	public void _save () {
		final ContactsAdapter adapter = (ContactsAdapter)contacts_recycler.getAdapter();
		
		String contacts = Contact.Serializer.toString(adapter.getData());
		sp.edit().putString("contacts", contacts).commit();
	}
	
	
	public void _animation (final double _id) {
	}
	
	public void _animation(int id){
		ObjectAnimator oa1 = new ObjectAnimator();
		ObjectAnimator oa2 = new ObjectAnimator();
		ObjectAnimator oa3 = new ObjectAnimator();
		ObjectAnimator oa4 = new ObjectAnimator();
		oa1.setPropertyName("alpha");
		oa1.setInterpolator(new AccelerateInterpolator());
		oa2.setPropertyName("translationY");
		oa2.setInterpolator(new DecelerateInterpolator());
		oa3.setPropertyName("scaleY");
		oa3.setInterpolator(new LinearInterpolator());
		switch(id){
			case 5 :
			contact_scroll.setVisibility(View.VISIBLE);
			contact_text.setText("New contact");
			phoneNumber_edittext.setText("");
			name_edittext.setText("");
			surname_edittext.setText("");
			job_edittext.setText("");
			email_edittext.setText("");
			address_edittext.setText("");
			notes_edittext.setText("");
			addContact_button.setText("Create");
			oa1.setTarget(contacts_lin);
			oa1.setFloatValues((float)(0.9d), (float)(0.2d));
			oa1.setDuration((int)(500));
			oa1.addListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator _param1){}
						
						@Override
						public void onAnimationCancel(Animator _param1){}
						
						@Override
						public void onAnimationRepeat(Animator _param1){}
						
						@Override
						public void onAnimationEnd(Animator _param1){
					contacts_lin.setAlpha((float)(1));
					contacts_lin.setVisibility(View.GONE);
				}
			});
			settings_lin.setVisibility(View.GONE);
			oa1.start();
			oa2.setTarget(contact_scroll);
			oa2.setFloatValues((float)(280), (float)(-4));
			oa2.setDuration((int)(800));
			oa2.start();
			break;
			case 6 :
			contact_scroll.setVisibility(View.VISIBLE);
			contact_text.setText("Edit contact");
			addContact_button.setText("Apply");
			oa1.setTarget(contacts_lin);
			oa1.setFloatValues((float)(0.9d), (float)(0.2d));
			oa1.setDuration((int)(500));
			oa1.addListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator _param1){}
						
						@Override
						public void onAnimationCancel(Animator _param1){}
						
						@Override
						public void onAnimationRepeat(Animator _param1){}
						
						@Override
						public void onAnimationEnd(Animator _param1){
					contacts_lin.setAlpha((float)(1));
					contacts_lin.setVisibility(View.GONE);
				}
			});
			settings_lin.setVisibility(View.GONE);
			oa1.start();
			oa2.setTarget(contact_scroll);
			oa2.setFloatValues((float)(280), (float)(-4));
			oa2.setDuration((int)(800));
			oa2.start();
			break;
			case 8 :
			contacts_lin.setVisibility(View.VISIBLE);
			oa1.setTarget(contact_scroll);
			oa1.setFloatValues((float)(0.9d), (float)(0.2d));
			oa1.setDuration((int)(500));
			oa1.addListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator _param1){}
						
						@Override
						public void onAnimationCancel(Animator _param1){}
						
						@Override
						public void onAnimationRepeat(Animator _param1){}
						
						@Override
						public void onAnimationEnd(Animator _param1){
					contact_scroll.setAlpha((float)(1));
					contact_scroll.setVisibility(View.GONE);
				}
			});
			settings_lin.setVisibility(View.GONE);
			oa1.start();
			oa2.setTarget(contacts_lin);
			oa2.setFloatValues((float)(-180), (float)(0));
			oa2.setDuration((int)(800));
			oa2.start();
			break;
			case 10 :
			oa1.setTarget(toolbarNewContact);
			oa1.setFloatValues((float)(0.8d), (float)(1));
			oa1.setDuration((int)(900));
			oa1.start();
			oa3.setTarget(toolbarNewContact);
			oa3.setFloatValues((float)(1.2d), (float)(1));
			oa3.setDuration((int)(750));
			oa3.start();
			break;
			case 11 :
			oa1.setTarget(toolbarMenu);
			oa1.setFloatValues((float)(0.8d), (float)(1));
			oa1.setDuration((int)(700));
			oa1.start();
			oa3.setTarget(toolbarMenu);
			oa3.setFloatValues((float)(1.2d), (float)(1));
			oa3.setDuration((int)(600));
			oa3.start();
			oa4.setTarget(toolbarMenu);
			oa4.setPropertyName("rotation");
			oa4.setInterpolator(new AccelerateInterpolator());
			float rotationCurrent = toolbarMenu.getRotation();
			float rotationTarget = rotationCurrent == 0 ? 90 : 0;
			oa4.setFloatValues((float)(rotationCurrent), (float)(rotationTarget));
			oa4.setDuration((int)(650));
			oa4.start();
			break;
			case 15 :
			settings_lin.setVisibility(View.VISIBLE);
			oa1.setTarget(contacts_lin);
			oa1.setFloatValues((float)(0.9d), (float)(0.2d));
			oa1.setDuration((int)(500));
			oa1.addListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator _param1){}
						
						@Override
						public void onAnimationCancel(Animator _param1){}
						
						@Override
						public void onAnimationRepeat(Animator _param1){}
						
						@Override
						public void onAnimationEnd(Animator _param1){
					contacts_lin.setAlpha((float)(1));
					contacts_lin.setVisibility(View.GONE);
					toolbarNewContact.setVisibility(View.INVISIBLE);
				}
			});
			contact_scroll.setVisibility(View.GONE);
			oa1.start();
			oa2.setTarget(settings_lin);
			oa2.setFloatValues((float)(280), (float)(-4));
			oa2.setDuration((int)(800));
			oa2.start();
			break;
			case 18 :
			contacts_lin.setVisibility(View.VISIBLE);
			oa1.setTarget(settings_lin);
			oa1.setFloatValues((float)(0.9d), (float)(0.2d));
			oa1.setDuration((int)(350));
			oa1.setRepeatCount((int)(1));
			oa1.addListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator _param1){}
						
						@Override
						public void onAnimationCancel(Animator _param1){}
						
						@Override
						public void onAnimationRepeat(Animator _param1){}
						
						@Override
						public void onAnimationEnd(Animator _param1){
					settings_lin.setAlpha((float)(1));
					settings_lin.setVisibility(View.GONE);
					toolbarNewContact.setVisibility(View.VISIBLE);
				}
			});
			contact_scroll.setVisibility(View.GONE);
			oa1.start();
			oa2.setTarget(contacts_lin);
			oa2.setFloatValues((float)(-180), (float)(0));
			oa2.setDuration((int)(800));
			oa2.start();
			break;
		}
	}
	
	
	public void _sort () {
		final ContactsAdapter adapter = (ContactsAdapter)contacts_recycler.getAdapter();
		
		ContactsAdapter.Sort.SortParam sortParam = ContactsAdapter.Sort.SortParam.values()[sp.getInt("sortParam", 0)];
		adapter.sort(sortParam);
		
		sort_wavesidebar.setCustomLetter(adapter.getFirstLetters());
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}