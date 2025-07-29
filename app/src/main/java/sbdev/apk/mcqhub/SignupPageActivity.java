package sbdev.apk.mcqhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SignupPageActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double count = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private double empty_flag = 0;
	private double exist_flag = 0;
	private double filed_ok_count = 0;
	private double all_filled_flag = 0;
	private String alphabet_low = "";
	private String alphabet_high = "";
	private String symbol = "";
	private String all_numbers = "";
	private String all_joined = "";
	private String password_genrated = "";
	private String old_string = "";
	private double random_index = 0;
	private String next_idno = "";
	private double tcount = 0;
	private String msg = "";
	
	private ArrayList<HashMap<String, Object>> all_users = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> subject_maplist = new ArrayList<>();
	private ArrayList<String> subject_name_codelist = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView idno;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear7;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private LinearLayout linear8;
	private Button registration;
	private TextView textview1;
	private LinearLayout linear3;
	private CheckBox checkbox_student;
	private CheckBox checkbox_teacher;
	private TextView textview_name;
	private EditText name;
	private TextView textview_gender;
	private CheckBox checkbox_male;
	private CheckBox checkbox_female;
	private TextView textview_contact;
	private EditText contactno;
	private TextView textview_email;
	private EditText email;
	private TextView textview2;
	private EditText new_password;
	private TextView textview3;
	private EditText re_new_password;
	private TextView textviwe_subject;
	private Spinner spinner_subjectlist;
	
	private Intent int1 = new Intent();
	private AlertDialog.Builder dialog;
	private Calendar cal = Calendar.getInstance();
	private DatabaseReference alluser = _firebase.getReference("all_users");
	private ChildEventListener _alluser_child_listener;
	private TimerTask timer;
	private TimerTask t2;
	private SharedPreferences sp1;
	private TimerTask t;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.signup_page);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		idno = (TextView) findViewById(R.id.idno);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		registration = (Button) findViewById(R.id.registration);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		checkbox_student = (CheckBox) findViewById(R.id.checkbox_student);
		checkbox_teacher = (CheckBox) findViewById(R.id.checkbox_teacher);
		textview_name = (TextView) findViewById(R.id.textview_name);
		name = (EditText) findViewById(R.id.name);
		textview_gender = (TextView) findViewById(R.id.textview_gender);
		checkbox_male = (CheckBox) findViewById(R.id.checkbox_male);
		checkbox_female = (CheckBox) findViewById(R.id.checkbox_female);
		textview_contact = (TextView) findViewById(R.id.textview_contact);
		contactno = (EditText) findViewById(R.id.contactno);
		textview_email = (TextView) findViewById(R.id.textview_email);
		email = (EditText) findViewById(R.id.email);
		textview2 = (TextView) findViewById(R.id.textview2);
		new_password = (EditText) findViewById(R.id.new_password);
		textview3 = (TextView) findViewById(R.id.textview3);
		re_new_password = (EditText) findViewById(R.id.re_new_password);
		textviwe_subject = (TextView) findViewById(R.id.textviwe_subject);
		spinner_subjectlist = (Spinner) findViewById(R.id.spinner_subjectlist);
		dialog = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		rpn = new RequestNetwork(this);
		
		registration.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				empty_flag = 0;
				filed_ok_count = 0;
				map.clear();
				map = new HashMap<>();
				if (checkbox_student.isChecked() || checkbox_teacher.isChecked()) {
					if (checkbox_student.isChecked()) {
						map.put("who_are_you", "Student");
						filed_ok_count++;
					}else {
						map.put("who_are_you", "Teacher");
						filed_ok_count++;
					}
				}else {
					empty_flag++;
				}
				if (name.getText().toString().equals("")) {
					textview_name.setText("Name ⚠️");
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									textview_name.setText("Name");
								}
							});
						}
					};
					_timer.schedule(timer, (int)(3000));
					empty_flag++;
				}else {
					count = 0;
					exist_flag = 0;
					for(int _repeat75 = 0; _repeat75 < (int)(all_users.size()); _repeat75++) {
						if (name.getText().toString().equals(all_users.get((int)count).get("name").toString())) {
							SketchwareUtil.showMessage(getApplicationContext(), "Name already exists contact Administrator or recheck name");
							exist_flag = 1;
							textview_name.setText("Name ⚠️");
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											textview_name.setText("Name");
										}
									});
								}
							};
							_timer.schedule(timer, (int)(3000));
							break;
						}
						count++;
					}
					if (exist_flag == 0) {
						map.put("name", name.getText().toString());
						filed_ok_count++;
					}
				}
				if (contactno.getText().toString().equals("")) {
					textview_contact.setText("Contact No.  ⚠️");
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									textview_contact.setText("Contact No.");
								}
							});
						}
					};
					_timer.schedule(timer, (int)(3000));
					empty_flag++;
				}else {
					count = 0;
					exist_flag = 0;
					for(int _repeat100 = 0; _repeat100 < (int)(all_users.size()); _repeat100++) {
						if (contactno.getText().toString().equals(all_users.get((int)count).get("contactno").toString())) {
							SketchwareUtil.showMessage(getApplicationContext(), "Contact No. already exists contact Administrator or recheck number");
							exist_flag = 1;
							textview_contact.setText("Contact No. ⚠️");
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											textview_contact.setText("Contact No.");
										}
									});
								}
							};
							_timer.schedule(timer, (int)(3000));
							break;
						}
						count++;
					}
					if (contactno.getText().toString().length() == 10) {
						if (exist_flag == 0) {
							map.put("contactno", contactno.getText().toString());
							filed_ok_count++;
						}
					}else {
						textview_contact.setText("Contact No. ⚠️");
						timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										textview_contact.setText("Contact No.");
									}
								});
							}
						};
						_timer.schedule(timer, (int)(3000));
						SketchwareUtil.showMessage(getApplicationContext(), "contact number is invalid⚠️");
					}
				}
				if (email.getText().toString().equals("")) {
					textview_email.setText("Email ⚠️");
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									textview_email.setText("Email");
								}
							});
						}
					};
					_timer.schedule(timer, (int)(3000));
					empty_flag++;
				}else {
					count = 0;
					exist_flag = 0;
					for(int _repeat120 = 0; _repeat120 < (int)(all_users.size()); _repeat120++) {
						if (email.getText().toString().equals(all_users.get((int)count).get("email").toString())) {
							SketchwareUtil.showMessage(getApplicationContext(), "This Email already exists contact Administrator or recheck Email");
							exist_flag = 1;
							textview_email.setText("Email ⚠️");
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											textview_email.setText("Email");
										}
									});
								}
							};
							_timer.schedule(timer, (int)(3000));
							break;
						}
						count++;
					}
					if (exist_flag == 0) {
						map.put("email", email.getText().toString());
						filed_ok_count++;
					}
				}
				if (checkbox_male.isChecked() || checkbox_female.isChecked()) {
					if (checkbox_male.isChecked()) {
						map.put("gender", "Male");
						filed_ok_count++;
					}else {
						map.put("gender", "Female");
						filed_ok_count++;
					}
				}else {
					empty_flag++;
				}
				if ("Select Department".equals(subject_name_codelist.get((int)(spinner_subjectlist.getSelectedItemPosition())))) {
					empty_flag++;
				}else {
					map.put("department_name", subject_name_codelist.get((int)(spinner_subjectlist.getSelectedItemPosition())));
					filed_ok_count++;
				}
				if (!("".equals(new_password.getText().toString()) || "".equals(re_new_password.getText().toString()))) {
					if (new_password.getText().toString().equals(re_new_password.getText().toString())) {
						if ((8 < new_password.getText().toString().length()) || (8 == new_password.getText().toString().length())) {
							if (13 > new_password.getText().toString().length()) {
								map.put("password", new_password.getText().toString());
								new_password.setText("");
								re_new_password.setText("");
								filed_ok_count++;
								filed_ok_count++;
							}else {
								SketchwareUtil.showMessage(getApplicationContext(), "Password must be less than 12 character's⚠️");
							}
						}else {
							SketchwareUtil.showMessage(getApplicationContext(), "Password must be atleast 8 character's⚠️");
						}
					}else {
						SketchwareUtil.showMessage(getApplicationContext(), "New Password & Re-New Password are not matching ‼️");
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Field is empty");
				}
				if (0 < empty_flag) {
					SketchwareUtil.showMessage(getApplicationContext(), String.valueOf(empty_flag).concat(" Fields are Empty or Unchecked"));
				}else {
					if (1 == exist_flag) {
						SketchwareUtil.showMessage(getApplicationContext(), "Name/Contact No/Email is already existing please re-check and try again");
					}else {
						if (filed_ok_count == 8) {
							all_filled_flag = 1;
						}else {
							SketchwareUtil.showMessage(getApplicationContext(), "something is empty or wrongly filled");
						}
					}
				}
				if (1 == all_filled_flag) {
					map.put("status", "new");
					map.put("idno", next_idno);
					count = 0;
					dialog.setTitle("Remember Alert ✅");
					dialog.setMessage("Your Request for New Account has successfully submitted Please Remember Your ".concat(idno.getText().toString().concat(" for⚠️ activation process contact administrator to activate your account ⚠️ and in future to.")));
					alluser.child(next_idno).updateChildren(map);
					all_users.clear();
					alluser.addChildEventListener(_alluser_child_listener);
					SketchwareUtil.showMessage(getApplicationContext(), "Request Submitted Successfully 👍");
					dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
					if (checkbox_student.isChecked()) {
						checkbox_student.setChecked(false);
					}else {
						checkbox_teacher.setChecked(false);
					}
					name.setText("");
					contactno.setText("");
					email.setText("");
					if (checkbox_male.isChecked()) {
						checkbox_male.setChecked(false);
					}else {
						checkbox_female.setChecked(false);
					}
					spinner_subjectlist.setSelection((int)(0));
					checkbox_student.requestFocus();
				}
				map.clear();
				all_filled_flag = 0;
			}
		});
		
		checkbox_student.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (checkbox_teacher.isChecked()) {
					checkbox_teacher.setChecked(false);
				}
			}
		});
		
		checkbox_teacher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (checkbox_student.isChecked()) {
					checkbox_student.setChecked(false);
				}
			}
		});
		
		checkbox_male.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (checkbox_female.isChecked()) {
					checkbox_female.setChecked(false);
				}
				SketchwareUtil.hideKeyboard(getApplicationContext());
			}
		});
		
		checkbox_female.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (checkbox_male.isChecked()) {
					checkbox_male.setChecked(false);
				}
				SketchwareUtil.hideKeyboard(getApplicationContext());
			}
		});
		
		_alluser_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				alluser.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						all_users = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								all_users.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						count = 0;
						for(int _repeat15 = 0; _repeat15 < (int)(all_users.size()); _repeat15++) {
							if (!String.valueOf((long)(count + 1)).equals(all_users.get((int)count).get("idno").toString())) {
								idno.setText("ID No.".concat(String.valueOf((long)(count + 1))));
								next_idno = String.valueOf((long)(count + 1));
								break;
							}else {
								idno.setText("ID No.".concat(String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1))));
								next_idno = String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1));
							}
							count++;
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				if (all_users.size() == 0) {
					idno.setText("ID No. ".concat("1"));
					next_idno = "1";
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				alluser.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						all_users = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								all_users.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						count = 0;
						for(int _repeat56 = 0; _repeat56 < (int)(all_users.size()); _repeat56++) {
							if (!String.valueOf((long)(count + 1)).equals(all_users.get((int)count).get("idno").toString())) {
								idno.setText("ID No.".concat(String.valueOf((long)(count + 1))));
								next_idno = String.valueOf((long)(count + 1));
								break;
							}else {
								idno.setText("ID No.".concat(String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1))));
								next_idno = String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1));
							}
							count++;
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				if (all_users.size() == 0) {
					idno.setText("ID No. ".concat("1"));
					next_idno = "1";
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				alluser.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						all_users = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								all_users.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						count = 0;
						for(int _repeat58 = 0; _repeat58 < (int)(all_users.size()); _repeat58++) {
							if (!String.valueOf((long)(count + 1)).equals(all_users.get((int)count).get("idno").toString())) {
								idno.setText("ID No.".concat(String.valueOf((long)(count + 1))));
								next_idno = String.valueOf((long)(count + 1));
								break;
							}else {
								idno.setText("ID No.".concat(String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1))));
								next_idno = String.valueOf((long)(Double.parseDouble(all_users.get((int)count).get("idno").toString()) + 1));
							}
							count++;
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				if (all_users.size() == 0) {
					idno.setText("ID No. ".concat("1"));
					next_idno = "1";
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		alluser.addChildEventListener(_alluser_child_listener);
		
		_rpn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				t.cancel();
				dialog.setCancelable(false);
				dialog.setTitle("⚠️ Connection Error ⚠️");
				dialog.setMessage("Internet is not Connected. please check connection and try again!!!");
				dialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						int1.setClass(getApplicationContext(), SignupPageActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		all_users.clear();
		tcount = 0;
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tcount++;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(0), (int)(200));
		t2 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (tcount == 10) {
							if (all_users.size() == 0) {
								idno.setText("ID No. ".concat("1"));
								next_idno = "1";
							}
							timer.cancel();
							t2.cancel();
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t2, (int)(0), (int)(205));
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						rpn.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/?sa=X&ved=0ahUKEwisre_hqcjvAhXR4jgGHSmqCjoQOwgC", "A", _rpn_request_listener);
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t, (int)(0), (int)(1000));
		subject_name_codelist.add("Select Department");
		subject_name_codelist.add("Computer Engineering");
		subject_name_codelist.add("Mechanical Engineering");
		subject_name_codelist.add("Automobile Engineering");
		subject_name_codelist.add("E & TC Engineering");
		subject_name_codelist.add("Common Department");
		spinner_subjectlist.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subject_name_codelist));
		((ArrayAdapter)spinner_subjectlist.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		int1.setClass(getApplicationContext(), LoginPageActivity.class);
		startActivity(int1);
		finish();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		t.cancel();
	}
	public void _password_generatorV1 () {
		alphabet_low = "abcdefghijklnopqrstuvwxyz";
		alphabet_high = "ABCDEFGHIJKLNMOPQRSTUVWXYZ";
		all_numbers = "0123456789";
		symbol = "_*?@";
		all_joined = alphabet_low.concat(symbol).concat(all_numbers.concat(alphabet_high));
		password_genrated = "";
		old_string = "";
		for(int _repeat25 = 0; _repeat25 < (int)(8); _repeat25++) {
			random_index = SketchwareUtil.getRandom((int)(0), (int)(65));
			password_genrated = old_string.concat(all_joined.substring((int)(random_index), (int)(random_index + 1)));
			old_string = password_genrated;
		}
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