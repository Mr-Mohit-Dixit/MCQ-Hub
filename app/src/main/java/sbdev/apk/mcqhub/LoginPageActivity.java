package sbdev.apk.mcqhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.annotation.SuppressLint;
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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class LoginPageActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double count = 0;
	private double user_found_pos = 0;
	
	private ArrayList<HashMap<String, Object>> admin_listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allusers_listmap = new ArrayList<>();
	
	private LinearLayout linear3;
	private ImageView imageview1;
	private ImageView imageview2;
	private ImageView imageview3;
	private ImageView imageview4;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private Button login;
	private Button signup;
	private Button about_us;
	private TextView net_stat;
	private TextView textview1;
	private EditText email;
	private TextView textview2;
	private EditText password;
	private CheckBox remember_me;
	private CheckBox show_password;
	
	private Intent int1 = new Intent();
	private AlertDialog.Builder dialog;
	private DatabaseReference admin = _firebase.getReference("admin");
	private ChildEventListener _admin_child_listener;
	private DatabaseReference allusers = _firebase.getReference("all_users");
	private ChildEventListener _allusers_child_listener;
	private SharedPreferences sp1;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private TimerTask t1;
	private TimerTask t;
	private Vibrator v;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.login_page);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		login = (Button) findViewById(R.id.login);
		signup = (Button) findViewById(R.id.signup);
		net_stat = (TextView) findViewById(R.id.net_stat);
		textview1 = (TextView) findViewById(R.id.textview1);
		email = (EditText) findViewById(R.id.email);
		textview2 = (TextView) findViewById(R.id.textview2);
		password = (EditText) findViewById(R.id.password);
		remember_me = (CheckBox) findViewById(R.id.remember_me);
		show_password = (CheckBox) findViewById(R.id.show_password);
		dialog = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		rpn = new RequestNetwork(this);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		login.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("MissingPermission")
			@Override
			public void onClick(View _view) {
				v.vibrate((long)(20));
				if (email.getText().toString().equals("") && password.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Empty field");
				}
else {
					if (0 < admin_listmap.size()) {
						if (email.getText().toString().equals(admin_listmap.get((int)0).get("id").toString())) {
							if (email.getText().toString().equals(admin_listmap.get((int)0).get("id").toString()) && password.getText().toString().equals(admin_listmap.get((int)0).get("password").toString())) {
								if (remember_me.isChecked()) {
									sp1.edit().putString("email", email.getText().toString()).commit();
									sp1.edit().putString("password", password.getText().toString()).commit();
								}
else {
									sp1.edit().remove("email").commit();
									sp1.edit().remove("password").commit();
								}
								int1.putExtra("username", admin_listmap.get((int)0).get("id").toString());
								int1.setClass(getApplicationContext(), AdminHomeActivity.class);
								startActivity(int1);
								finish();
							}
else {
								SketchwareUtil.showMessage(getApplicationContext(), "admin password wrong");
								email.setText("");
								password.setText("");
							}
						}
else {
							if (0 < allusers_listmap.size()) {
								user_found_pos = -1;
								count = 0;
								for(int _repeat53 = 0; _repeat53 < (int)(allusers_listmap.size()); _repeat53++) {
									if (email.getText().toString().toLowerCase().equals(allusers_listmap.get((int)count).get("email").toString().toLowerCase())) {
										user_found_pos = count;
										break;
									}
else {
										count++;
									}
								}
								if (-0.1d > user_found_pos) {
									SketchwareUtil.showMessage(getApplicationContext(), "user not found or email is wrong");
								}
else {
									if (allusers_listmap.get((int)user_found_pos).get("status").toString().equals("new")) {
										dialog.setTitle("Error ⚠️");
										dialog.setMessage("⚠️This Account is new ask Administrator with valid proof for activation of account ⚠️");
										dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface _dialog, int _which) {
												
											}
										});
										dialog.create().show();
										email.setText("");
										password.setText("");
									}
else {
										if (password.getText().toString().equals(allusers_listmap.get((int)user_found_pos).get("password").toString())) {
											if (allusers_listmap.get((int)user_found_pos).get("status").toString().equals("active")) {
												sp1.edit().putString("login_email_temp", email.getText().toString()).commit();
												SketchwareUtil.showMessage(getApplicationContext(), "Login Success");
												if (allusers_listmap.get((int)user_found_pos).get("who_are_you").toString().equals("Student")) {
													SketchwareUtil.showMessage(getApplicationContext(), "Welcome ".concat(allusers_listmap.get((int)user_found_pos).get("name").toString()));
													int1.putExtra("idno", allusers_listmap.get((int)user_found_pos).get("idno").toString());
													int1.putExtra("name", allusers_listmap.get((int)user_found_pos).get("name").toString());
													int1.putExtra("department", allusers_listmap.get((int)user_found_pos).get("department_name").toString());
													int1.setClass(getApplicationContext(), StudentHomeActivity.class);
													startActivity(int1);
													finish();
												}
else {
													SketchwareUtil.showMessage(getApplicationContext(), "Welcome ".concat(allusers_listmap.get((int)user_found_pos).get("name").toString()));
													int1.putExtra("idno", allusers_listmap.get((int)user_found_pos).get("idno").toString());
													int1.putExtra("name", allusers_listmap.get((int)user_found_pos).get("name").toString());
													int1.putExtra("department", allusers_listmap.get((int)user_found_pos).get("department_name").toString());
													int1.setClass(getApplicationContext(), TeacherHomeActivity.class);
													startActivity(int1);
													finish();
												}
												if (remember_me.isChecked()) {
													sp1.edit().putString("email", email.getText().toString()).commit();
													sp1.edit().putString("password", password.getText().toString()).commit();
												}
else {
													sp1.edit().remove("email").commit();
													sp1.edit().remove("password").commit();
												}
											}
else {
												dialog.setTitle("Error ⚠️");
												dialog.setMessage("❌This Account is Deactivated ❌\n⚠️ Contact administrator for reason of Deactivation and also u can ask for re-activation");
												dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface _dialog, int _which) {
														
													}
												});
												dialog.create().show();
												email.setText("");
												password.setText("");
											}
										}
else {
											SketchwareUtil.showMessage(getApplicationContext(), "password is wrong");
										}
									}
								}
							}
							if (allusers_listmap.size() == 0) {
								SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Data Not Available ⚠️");
							}
						}
					}
				}
			}
		});
		
		signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.setClass(getApplicationContext(), SignupPageActivity.class);
				startActivity(int1);
				finish();
			}
		});
		
		show_password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (show_password.isChecked()) {
					password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
				}
else {
					password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		
		_admin_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				admin_listmap.clear();
				admin.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						admin_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								admin_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				admin_listmap.clear();
				admin.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						admin_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								admin_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				admin_listmap.clear();
				admin.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						admin_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								admin_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		admin.addChildEventListener(_admin_child_listener);
		
		_allusers_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				allusers_listmap.clear();
				allusers.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allusers_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allusers_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				allusers_listmap.clear();
				allusers.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allusers_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allusers_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				allusers_listmap.clear();
				allusers.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allusers_listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allusers_listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allusers.addChildEventListener(_allusers_child_listener);
		
		_rpn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				net_stat.setText("Connected");
				t1.cancel();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				t.cancel();
				net_stat.setText("connection error");
				
dialog.setCancelable(false);
				dialog.setTitle("⚠️ Connection Error ⚠️");
				dialog.setMessage("Internet is not Connected. please check connection and try again!!!");
				dialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						int1.setClass(getApplicationContext(), LoginPageActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		if (!"".equals(sp1.getString("email", ""))) {
			remember_me.setChecked(true);
			email.setText(sp1.getString("email", ""));
			password.setText(sp1.getString("password", ""));
		}
		count = 1;
		t1 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (count == 1) {
							net_stat.setText("Connecting");
						}
						if (count == 2) {
							net_stat.setText("Connecting.");
						}
						if (count == 3) {
							net_stat.setText("Connecting..");
						}
						if (count == 4) {
							net_stat.setText("Connecting...");
						}
						if (count == 5) {
							net_stat.setText("Connecting....");
							count = 0;
						}
						count++;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t1, (int)(0), (int)(100));
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
		dialog.setTitle("⚠️ Alert ⚠️");
		dialog.setMessage("Are you sure ?");
		dialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finish();
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		t.cancel();
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
