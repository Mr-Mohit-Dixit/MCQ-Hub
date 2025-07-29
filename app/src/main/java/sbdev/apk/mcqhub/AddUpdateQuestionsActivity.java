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
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddUpdateQuestionsActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String update_uqno = "";
	private HashMap<String, Object> map = new HashMap<>();
	private double tcount = 0;
	private String next_uqno = "";
	private double count = 0;
	private double exist_flag = 0;
	private HashMap<String, Object> update_data_map = new HashMap<>();
	private HashMap<String, Object> teacher_info_map = new HashMap<>();
	private double all_field_ok = 0;
	private double count1 = 0;
	private double count2 = 0;
	
	private ArrayList<HashMap<String, Object>> allmcq_listmap = new ArrayList<>();
	private ArrayList<String> spinner_op_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allusers_listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_maplist = new ArrayList<>();
	private ArrayList<String> subject_liststr = new ArrayList<>();
	private ArrayList<String> subject_code_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> subjectlist_map = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView uqno;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear13;
	private LinearLayout linear12;
	private TextView textview_name;
	private EditText question;
	private CheckBox checkbox1;
	private TextView textview_department;
	private TextView textview_semester;
	private TextView textview7;
	private Spinner spinner_subjectlist;
	private TextView textview1;
	private EditText op1;
	private TextView textview2;
	private EditText op2;
	private TextView textview3;
	private EditText op3;
	private TextView textview4;
	private EditText op4;
	private TextView textview5;
	private Spinner spinner_op_ans;
	private Button action;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference allmcq = _firebase.getReference("All MCQ");
	private ChildEventListener _allmcq_child_listener;
	private DatabaseReference allusers = _firebase.getReference("all_users");
	private ChildEventListener _allusers_child_listener;
	private TimerTask timer;
	private TimerTask t2;
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_update_questions);
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
		uqno = (TextView) findViewById(R.id.uqno);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		textview_name = (TextView) findViewById(R.id.textview_name);
		question = (EditText) findViewById(R.id.question);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		textview_department = (TextView) findViewById(R.id.textview_department);
		textview_semester = (TextView) findViewById(R.id.textview_semester);
		textview7 = (TextView) findViewById(R.id.textview7);
		spinner_subjectlist = (Spinner) findViewById(R.id.spinner_subjectlist);
		textview1 = (TextView) findViewById(R.id.textview1);
		op1 = (EditText) findViewById(R.id.op1);
		textview2 = (TextView) findViewById(R.id.textview2);
		op2 = (EditText) findViewById(R.id.op2);
		textview3 = (TextView) findViewById(R.id.textview3);
		op3 = (EditText) findViewById(R.id.op3);
		textview4 = (TextView) findViewById(R.id.textview4);
		op4 = (EditText) findViewById(R.id.op4);
		textview5 = (TextView) findViewById(R.id.textview5);
		spinner_op_ans = (Spinner) findViewById(R.id.spinner_op_ans);
		action = (Button) findViewById(R.id.action);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		checkbox1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				spinner_op_list.clear();
				if (checkbox1.isChecked()) {
					op1.setText("True");
					op2.setText("False");
					op1.setEnabled(false);
					op2.setEnabled(false);
					op3.setEnabled(false);
					op4.setEnabled(false);
					spinner_op_list.add("Select Answer");
					spinner_op_list.add("Option 1");
					spinner_op_list.add("Option 2");
					spinner_op_ans.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinner_op_list));
					((ArrayAdapter)spinner_op_ans.getAdapter()).notifyDataSetChanged();
				}else {
					op1.setText("");
					op2.setText("");
					op1.setEnabled(true);
					op2.setEnabled(true);
					op3.setEnabled(true);
					op4.setEnabled(true);
					spinner_op_list.add("Select Answer");
					spinner_op_list.add("Option 1");
					spinner_op_list.add("Option 2");
					spinner_op_list.add("Option 3");
					spinner_op_list.add("Option 4");
					spinner_op_ans.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinner_op_list));
					((ArrayAdapter)spinner_op_ans.getAdapter()).notifyDataSetChanged();
				}
			}
		});
		
		spinner_subjectlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (!"Select Subject".equals(subject_liststr.get((int)(_position)))) {
					textview_semester.setText("Semester : ".concat(subjectlist_map.get((int)_position - 1).get("semester").toString()));
				}else {
					textview_semester.setText("Semester : ".concat(""));
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		action.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				all_field_ok = 0;
				map.clear();
				map = new HashMap<>();
				if (!"".equals(question.getText().toString())) {
					map.put("question", question.getText().toString());
					all_field_ok++;
					if (map.containsKey("question")) {
						count = 0;
						if (checkbox1.isChecked()) {
							all_field_ok = 5;
							map.put("op1", op1.getText().toString());
							map.put("op2", op2.getText().toString());
							map.put("op3", "");
							map.put("op4", "");
						}else {
							if (!"".equals(op1.getText().toString())) {
								map.put("op1", op1.getText().toString());
								all_field_ok++;
							}
							if (!"".equals(op2.getText().toString())) {
								map.put("op2", op2.getText().toString());
								all_field_ok++;
							}
							if (!"".equals(op3.getText().toString())) {
								map.put("op3", op3.getText().toString());
								all_field_ok++;
							}
							if (!"".equals(op4.getText().toString())) {
								map.put("op4", op4.getText().toString());
								all_field_ok++;
							}
						}
						if ("Select Answer".equals(spinner_op_list.get((int)(spinner_op_ans.getSelectedItemPosition())))) {
							SketchwareUtil.showMessage(getApplicationContext(), "Please Select Answer");
						}else {
							map.put("ans", String.valueOf((long)(spinner_op_ans.getSelectedItemPosition())));
							all_field_ok++;
						}
					}
				}else {
					textview_name.setText("Questions ⚠️");
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									textview_name.setText("Question");
								}
							});
						}
					};
					_timer.schedule(timer, (int)(3000));
				}
				if (!"Select Subject".equals(subject_liststr.get((int)(spinner_subjectlist.getSelectedItemPosition())))) {
					all_field_ok++;
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Select Subject ⚠️");
				}
				if (all_field_ok == 7) {
					if ("update".equals(getIntent().getStringExtra("action"))) {
						map.put("uqno", update_uqno);
						map.put("subject_code", subject_code_list.get((int)(spinner_subjectlist.getSelectedItemPosition())));
						map.put("added_by", teacher_info_map.get("name").toString());
						map.put("semester", subjectlist_map.get((int)spinner_subjectlist.getSelectedItemPosition() - 1).get("semester").toString());
						map.put("department_name", subjectlist_map.get((int)spinner_subjectlist.getSelectedItemPosition() - 1).get("department_name").toString());
						map.put("wrong_flag", "off");
						if (getIntent().getStringExtra("wrong").equals("on")) {
							map.remove("reported_by");
							map.remove("remark");
							allmcq.child(update_uqno).removeValue();
						}
					}else {
						map.put("uqno", next_uqno);
						map.put("subject_code", subject_code_list.get((int)(spinner_subjectlist.getSelectedItemPosition())));
						map.put("added_by", teacher_info_map.get("name").toString());
						map.put("semester", subjectlist_map.get((int)spinner_subjectlist.getSelectedItemPosition() - 1).get("semester").toString());
						map.put("department_name", subjectlist_map.get((int)spinner_subjectlist.getSelectedItemPosition() - 1).get("department_name").toString());
						map.put("wrong_flag", "off");
					}
					allmcq.child(map.get("uqno").toString()).updateChildren(map);
					if ("update".equals(getIntent().getStringExtra("action"))) {
						SketchwareUtil.showMessage(getApplicationContext(), "MCQ Updated Successfully");
						int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
						int1.putExtra("semester", getIntent().getStringExtra("semester"));
						int1.putExtra("subject", getIntent().getStringExtra("subject"));
						int1.putExtra("idno", getIntent().getStringExtra("idno"));
						int1.putExtra("name", getIntent().getStringExtra("name"));
						int1.putExtra("department", getIntent().getStringExtra("department"));
						int1.setClass(getApplicationContext(), AllQuestionsListActivity.class);
						startActivity(int1);
						finish();
					}else {
						SketchwareUtil.showMessage(getApplicationContext(), "MCQ Added Successfully");
						question.setText("");
						op1.setText("");
						op2.setText("");
						op3.setText("");
						op4.setText("");
						spinner_op_ans.setSelection((int)(0));
						if (checkbox1.isChecked()) {
							checkbox1.performClick();
						}
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), String.valueOf((long)(6 - all_field_ok)).concat("Field are Empty"));
				}
			}
		});
		
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
						if ("update".equals(getIntent().getStringExtra("action"))) {
							int1.putExtra("uqno", getIntent().getStringExtra("uqno"));
							int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
						}
						int1.putExtra("semester", getIntent().getStringExtra("semester"));
						int1.putExtra("subject", getIntent().getStringExtra("subject"));
						int1.putExtra("idno", getIntent().getStringExtra("idno"));
						int1.putExtra("name", getIntent().getStringExtra("name"));
						int1.putExtra("department", getIntent().getStringExtra("department"));
						int1.setClass(getApplicationContext(), AddUpdateQuestionsActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
		
		_allmcq_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ("update".equals(getIntent().getStringExtra("action"))) {
					if (update_uqno.equals(_childValue.get("uqno").toString())) {
						temp_maplist.clear();
						temp_maplist.add(_childValue);
						map = temp_maplist.get((int)0);
						textview_semester.setText("Semester : ".concat(_childValue.get("semester").toString()));
						uqno.setText("Unique Question No. ".concat(_childValue.get("uqno").toString()));
						question.setText(_childValue.get("question").toString());
						op1.setText(_childValue.get("op1").toString());
						op2.setText(_childValue.get("op2").toString());
						op3.setText(_childValue.get("op3").toString());
						op4.setText(_childValue.get("op4").toString());
						if ("True".equals(_childValue.get("op1").toString()) && ("".equals(_childValue.get("op3").toString()) || "".equals(_childValue.get("op4").toString()))) {
							checkbox1.setChecked(true);
						}
						spinner_op_ans.setSelection((int)(Double.parseDouble(_childValue.get("ans").toString())));
						count1 = 0;
						for(int _repeat117 = 0; _repeat117 < (int)(subject_code_list.size()); _repeat117++) {
							if (_childValue.get("subject_code").toString().equals(subject_code_list.get((int)(count1)))) {
								spinner_subjectlist.setSelection((int)(count1));
								break;
							}
							count1++;
						}
					}
				}else {
					allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
							allmcq_listmap = new ArrayList<>();
							try {
								GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
								for (DataSnapshot _data : _dataSnapshot.getChildren()) {
									HashMap<String, Object> _map = _data.getValue(_ind);
									allmcq_listmap.add(_map);
								}
							}
							catch (Exception _e) {
								_e.printStackTrace();
							}
							count = 0;
							if (!(0 == allmcq_listmap.size())) {
								for(int _repeat53 = 0; _repeat53 < (int)(allmcq_listmap.size()); _repeat53++) {
									if (!String.valueOf((long)(count + 1)).equals(allmcq_listmap.get((int)count).get("uqno").toString())) {
										uqno.setText("Unique Question No. ".concat(String.valueOf((long)(count + 1))));
										next_uqno = String.valueOf((long)(count + 1));
										break;
									}else {
										uqno.setText("Unique Question No. ".concat(String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1))));
										next_uqno = String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1));
									}
									count++;
								}
							}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					if (allmcq_listmap.size() == 0) {
						uqno.setText("Unique Question No. ".concat("1"));
						next_uqno = "1";
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				temp_maplist.clear();
				if ("update".equals(getIntent().getStringExtra("action"))) {
					if (update_uqno.equals(_childValue.get("uqno").toString())) {
						temp_maplist.add(_childValue);
						map = temp_maplist.get((int)0);
						uqno.setText("Unique Question No. ".concat(_childValue.get("uqno").toString()));
						question.setText(_childValue.get("question").toString());
						op1.setText(_childValue.get("op1").toString());
						op2.setText(_childValue.get("op2").toString());
						op3.setText(_childValue.get("op3").toString());
						op4.setText(_childValue.get("op4").toString());
						spinner_op_ans.setSelection((int)(Double.parseDouble(_childValue.get("ans").toString())));
						if ("True".equals(_childValue.get("op1").toString()) && ("".equals(_childValue.get("op3").toString()) || "".equals(_childValue.get("op4").toString()))) {
							checkbox1.setChecked(true);
						}
						count1 = 0;
						for(int _repeat181 = 0; _repeat181 < (int)(subject_code_list.size()); _repeat181++) {
							if (_childValue.get("subject_code").toString().equals(subject_code_list.get((int)(count1)))) {
								spinner_subjectlist.setSelection((int)(count1));
								break;
							}
							count1++;
						}
					}
				}else {
					allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
							allmcq_listmap = new ArrayList<>();
							try {
								GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
								for (DataSnapshot _data : _dataSnapshot.getChildren()) {
									HashMap<String, Object> _map = _data.getValue(_ind);
									allmcq_listmap.add(_map);
								}
							}
							catch (Exception _e) {
								_e.printStackTrace();
							}
							count = 0;
							for(int _repeat122 = 0; _repeat122 < (int)(allmcq_listmap.size()); _repeat122++) {
								if (!String.valueOf((long)(count + 1)).equals(allmcq_listmap.get((int)count).get("uqno").toString())) {
									uqno.setText("Unique Question No. ".concat(String.valueOf((long)(count + 1))));
									next_uqno = String.valueOf((long)(count + 1));
									break;
								}else {
									uqno.setText("Unique Question No. ".concat(String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1))));
									next_uqno = String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1));
								}
								count++;
							}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					if (allmcq_listmap.size() == 0) {
						uqno.setText("Unique Question No. ".concat("1"));
						next_uqno = "1";
					}
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
				temp_maplist.clear();
				if ("update".equals(getIntent().getStringExtra("action"))) {
					if (update_uqno.equals(_childValue.get("uqno").toString())) {
						temp_maplist.add(_childValue);
						map = temp_maplist.get((int)0);
						uqno.setText("Unique Question No. ".concat(_childValue.get("uqno").toString()));
						question.setText(_childValue.get("question").toString());
						op1.setText(_childValue.get("op1").toString());
						op2.setText(_childValue.get("op2").toString());
						op3.setText(_childValue.get("op3").toString());
						op4.setText(_childValue.get("op4").toString());
						spinner_op_ans.setSelection((int)(Double.parseDouble(_childValue.get("ans").toString())));
						if ("True".equals(_childValue.get("op1").toString()) && ("".equals(_childValue.get("op3").toString()) || "".equals(_childValue.get("op4").toString()))) {
							checkbox1.setChecked(true);
						}
						count1 = 0;
						for(int _repeat180 = 0; _repeat180 < (int)(subject_code_list.size()); _repeat180++) {
							if (_childValue.get("subject_code").toString().equals(subject_code_list.get((int)(count1)))) {
								spinner_subjectlist.setSelection((int)(count1));
								break;
							}
							count1++;
						}
					}
				}else {
					allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
							allmcq_listmap = new ArrayList<>();
							try {
								GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
								for (DataSnapshot _data : _dataSnapshot.getChildren()) {
									HashMap<String, Object> _map = _data.getValue(_ind);
									allmcq_listmap.add(_map);
								}
							}
							catch (Exception _e) {
								_e.printStackTrace();
							}
							count = 0;
							for(int _repeat121 = 0; _repeat121 < (int)(allmcq_listmap.size()); _repeat121++) {
								if (!String.valueOf((long)(count + 1)).equals(allmcq_listmap.get((int)count).get("uqno").toString())) {
									uqno.setText("Unique Question No. ".concat(String.valueOf((long)(count + 1))));
									next_uqno = String.valueOf((long)(count + 1));
									break;
								}else {
									uqno.setText("Unique Question No. ".concat(String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1))));
									next_uqno = String.valueOf((long)(Double.parseDouble(allmcq_listmap.get((int)count).get("uqno").toString()) + 1));
								}
								count++;
							}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					if (allmcq_listmap.size() == 0) {
						uqno.setText("Unique Question No. ".concat("1"));
						next_uqno = "1";
					}
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allmcq.addChildEventListener(_allmcq_child_listener);
		
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
						count = 0;
						for(int _repeat12 = 0; _repeat12 < (int)(allusers_listmap.size()); _repeat12++) {
							if (allusers_listmap.get((int)count).get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
								teacher_info_map = allusers_listmap.get((int)count);
								break;
							}
							count++;
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
						count = 0;
						for(int _repeat15 = 0; _repeat15 < (int)(allusers_listmap.size()); _repeat15++) {
							if (allusers_listmap.get((int)count).get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
								teacher_info_map = allusers_listmap.get((int)count);
								break;
							}
							count++;
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
						count = 0;
						for(int _repeat15 = 0; _repeat15 < (int)(allusers_listmap.size()); _repeat15++) {
							if (allusers_listmap.get((int)count).get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
								teacher_info_map = allusers_listmap.get((int)count);
								break;
							}
							count++;
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
		
		_subjectlist_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subject_liststr.add(_childValue.get("subject_name").toString().concat(" (".concat(_childValue.get("subject_code").toString().concat(")"))));
					subject_code_list.add(_childValue.get("subject_code").toString());
					subjectlist_map.add(_childValue);
				}
				spinner_subjectlist.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subject_liststr));
				((ArrayAdapter)spinner_subjectlist.getAdapter()).notifyDataSetChanged();
				if (getIntent().getStringExtra("action").equals("update")) {
					spinner_subjectlist.setEnabled(false);
					count2 = 0;
					for(int _repeat55 = 0; _repeat55 < (int)(subject_code_list.size()); _repeat55++) {
						if (subject_code_list.get((int)(count2)).equals(getIntent().getStringExtra("subject"))) {
							spinner_subjectlist.setSelection((int)(count2));
						}
						count2++;
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subject_liststr.add(_childValue.get("subject_name").toString().concat(" (".concat(_childValue.get("subject_code").toString().concat(")"))));
					subject_code_list.add(_childValue.get("subject_code").toString());
					subjectlist_map.add(_childValue);
				}
				spinner_subjectlist.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subject_liststr));
				((ArrayAdapter)spinner_subjectlist.getAdapter()).notifyDataSetChanged();
				if (getIntent().getStringExtra("action").equals("update")) {
					spinner_subjectlist.setEnabled(false);
					count2 = 0;
					for(int _repeat56 = 0; _repeat56 < (int)(subject_code_list.size()); _repeat56++) {
						if (subject_code_list.get((int)(count2)).equals(getIntent().getStringExtra("subject"))) {
							spinner_subjectlist.setSelection((int)(count2));
						}
						count2++;
					}
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
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subject_liststr.add(_childValue.get("subject_name").toString().concat(" (".concat(_childValue.get("subject_code").toString().concat(")"))));
					subject_code_list.add(_childValue.get("subject_code").toString());
					subjectlist_map.add(_childValue);
				}
				spinner_subjectlist.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subject_liststr));
				((ArrayAdapter)spinner_subjectlist.getAdapter()).notifyDataSetChanged();
				if (getIntent().getStringExtra("action").equals("update")) {
					spinner_subjectlist.setEnabled(false);
					count2 = 0;
					for(int _repeat56 = 0; _repeat56 < (int)(subject_code_list.size()); _repeat56++) {
						if (subject_code_list.get((int)(count2)).equals(getIntent().getStringExtra("subject"))) {
							spinner_subjectlist.setSelection((int)(count2));
						}
						count2++;
					}
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		subjectlist.addChildEventListener(_subjectlist_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("ADD MCQ");
		allmcq_listmap.clear();
		spinner_op_list.clear();
		if (getIntent().getStringExtra("action").equals("update")) {
			setTitle("UPDATE MCQ");
			update_uqno = getIntent().getStringExtra("uqno");
			action.setText("UPDATE");
		}
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
							if (allmcq_listmap.size() == 0) {
								uqno.setText("Unique Question No. ".concat("1"));
								next_uqno = "1";
							}
							timer.cancel();
							t2.cancel();
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t2, (int)(0), (int)(205));
		textview_department.setText("Department :\n".concat(getIntent().getStringExtra("department")));
		spinner_op_list.add("Select Answer");
		spinner_op_list.add("Option 1");
		spinner_op_list.add("Option 2");
		spinner_op_list.add("Option 3");
		spinner_op_list.add("Option 4");
		spinner_op_ans.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinner_op_list));
		((ArrayAdapter)spinner_op_ans.getAdapter()).notifyDataSetChanged();
		count = 0;
		subject_code_list.clear();
		subject_liststr.clear();
		subjectlist_map.clear();
		subject_liststr.add("Select Subject");
		subject_code_list.add("Select");
		spinner_subjectlist.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subject_liststr));
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
	public void onStop() {
		super.onStop();
		t.cancel();
	}
	
	@Override
	public void onBackPressed() {
		if (getIntent().getStringExtra("action").equals("update")) {
			int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
			int1.putExtra("semester", getIntent().getStringExtra("semester"));
			int1.putExtra("subject", getIntent().getStringExtra("subject"));
			int1.putExtra("idno", getIntent().getStringExtra("idno"));
			int1.putExtra("name", getIntent().getStringExtra("name"));
			int1.putExtra("department", getIntent().getStringExtra("department"));
			int1.setClass(getApplicationContext(), AllQuestionsListActivity.class);
			startActivity(int1);
			finish();
		}else {
			finish();
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