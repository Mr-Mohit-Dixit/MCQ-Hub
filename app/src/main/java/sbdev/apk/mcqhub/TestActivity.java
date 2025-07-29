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
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class TestActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double countdown = 0;
	private double mm = 0;
	private double ss = 0;
	private String mmt = "";
	private String sst = "";
	private double total_question = 0;
	private double current_question = 0;
	private double total_points = 0;
	private String r_msg = "";
	private HashMap<String, Object> map_r = new HashMap<>();
	private String alphabet_low = "";
	private String alphabet_high = "";
	private String all_numbers = "";
	private String symbol = "";
	private String all_joined = "";
	private String password_genrated = "";
	private String old_string = "";
	private double random_index = 0;
	private String key = "";
	private String tp_key = "";
	private HashMap<String, Object> temp_map = new HashMap<>();
	private double count1 = 0;
	private String temp_ans = "";
	private String current_uqno = "";
	private double count2 = 0;
	private String current_selected = "";
	private double count3 = 0;
	private String report_remark = "";
	private double count4 = 0;
	private double n = 0;
	private double report_dbFlag = 0;
	private String reported_uqno = "";
	
	private ArrayList<HashMap<String, Object>> allmcq_maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> results_maplist = new ArrayList<>();
	private ArrayList<String> uqno_list = new ArrayList<>();
	private ArrayList<String> done_uqno = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_maplist = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear6;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView textview1;
	private RadioButton radiobutton_op1;
	private RadioButton radiobutton_op2;
	private RadioButton radiobutton_op3;
	private RadioButton radiobutton_op4;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private ImageView imageview1;
	private TextView textview_subjectname;
	private TextView questions_count_view;
	private TextView time_view;
	private TextView question_view;
	private Button submit;
	private TextView textview_added_by;
	private ImageView report_question;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private TimerTask t1;
	private TimerTask t2;
	private DatabaseReference allmcq = _firebase.getReference("All MCQ");
	private ChildEventListener _allmcq_child_listener;
	private DatabaseReference results = _firebase.getReference("results");
	private ChildEventListener _results_child_listener;
	private AlertDialog.Builder dialog_result;
	private AlertDialog customdialog1;
	private Calendar calendar_1 = Calendar.getInstance();
	private AlertDialog cd2;
	private TimerTask t3;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.test);
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
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview1 = (TextView) findViewById(R.id.textview1);
		radiobutton_op1 = (RadioButton) findViewById(R.id.radiobutton_op1);
		radiobutton_op2 = (RadioButton) findViewById(R.id.radiobutton_op2);
		radiobutton_op3 = (RadioButton) findViewById(R.id.radiobutton_op3);
		radiobutton_op4 = (RadioButton) findViewById(R.id.radiobutton_op4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview_subjectname = (TextView) findViewById(R.id.textview_subjectname);
		questions_count_view = (TextView) findViewById(R.id.questions_count_view);
		time_view = (TextView) findViewById(R.id.time_view);
		question_view = (TextView) findViewById(R.id.question_view);
		submit = (Button) findViewById(R.id.submit);
		textview_added_by = (TextView) findViewById(R.id.textview_added_by);
		report_question = (ImageView) findViewById(R.id.report_question);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		dialog_result = new AlertDialog.Builder(this);
		
		radiobutton_op1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (radiobutton_op2.isChecked()) {
					radiobutton_op2.setChecked(false);
				}
				if (radiobutton_op3.isChecked()) {
					radiobutton_op3.setChecked(false);
				}
				if (radiobutton_op4.isChecked()) {
					radiobutton_op4.setChecked(false);
				}
				if (radiobutton_op1.isChecked()) {
					current_selected = "1";
				}
			}
		});
		
		radiobutton_op2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (radiobutton_op1.isChecked()) {
					radiobutton_op1.setChecked(false);
				}
				if (radiobutton_op3.isChecked()) {
					radiobutton_op3.setChecked(false);
				}
				if (radiobutton_op4.isChecked()) {
					radiobutton_op4.setChecked(false);
				}
				if (radiobutton_op2.isChecked()) {
					current_selected = "2";
				}
			}
		});
		
		radiobutton_op3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (radiobutton_op2.isChecked()) {
					radiobutton_op2.setChecked(false);
				}
				if (radiobutton_op1.isChecked()) {
					radiobutton_op1.setChecked(false);
				}
				if (radiobutton_op4.isChecked()) {
					radiobutton_op4.setChecked(false);
				}
				if (radiobutton_op3.isChecked()) {
					current_selected = "3";
				}
			}
		});
		
		radiobutton_op4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (radiobutton_op2.isChecked()) {
					radiobutton_op2.setChecked(false);
				}
				if (radiobutton_op3.isChecked()) {
					radiobutton_op3.setChecked(false);
				}
				if (radiobutton_op1.isChecked()) {
					radiobutton_op1.setChecked(false);
				}
				if (radiobutton_op4.isChecked()) {
					current_selected = "4";
				}
			}
		});
		
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((radiobutton_op1.isChecked() || radiobutton_op2.isChecked()) || (radiobutton_op3.isChecked() || radiobutton_op4.isChecked())) {
					if (!(total_question == current_question)) {
						if (temp_ans.equals(current_selected)) {
							total_points++;
						}
						current_selected = "";
						temp_ans = "";
						current_question++;
						if (total_question == current_question) {
							submit.setText("Submit");
						}
						questions_count_view.setText("Question No :".concat(String.valueOf((long)(current_question))).concat("/".concat(String.valueOf((long)(total_question)))));
						current_uqno = uqno_list.get((int)(SketchwareUtil.getRandom((int)(0), (int)(uqno_list.size() - 1))));
						n = uqno_list.indexOf(current_uqno);
						uqno_list.remove((int)(n));
						done_uqno.add(current_uqno);
						radiobutton_op1.setChecked(false);
						radiobutton_op2.setChecked(false);
						radiobutton_op3.setChecked(false);
						radiobutton_op4.setChecked(false);
						radiobutton_op3.setEnabled(true);
						radiobutton_op4.setEnabled(true);
						radiobutton_op3.setVisibility(View.VISIBLE);
						radiobutton_op4.setVisibility(View.VISIBLE);
						count3 = 0;
						for(int _repeat68 = 0; _repeat68 < (int)(allmcq_maplist.size()); _repeat68++) {
							if (current_uqno.equals(allmcq_maplist.get((int)count3).get("uqno").toString())) {
								question_view.setText(allmcq_maplist.get((int)count3).get("question").toString());
								radiobutton_op1.setText(allmcq_maplist.get((int)count3).get("op1").toString());
								radiobutton_op2.setText(allmcq_maplist.get((int)count3).get("op2").toString());
								if (!allmcq_maplist.get((int)count3).get("op1").toString().equals("True")) {
									radiobutton_op3.setText(allmcq_maplist.get((int)count3).get("op3").toString());
									radiobutton_op4.setText(allmcq_maplist.get((int)count3).get("op4").toString());
								}else {
									radiobutton_op3.setEnabled(false);
									radiobutton_op4.setEnabled(false);
									radiobutton_op3.setVisibility(View.INVISIBLE);
									radiobutton_op4.setVisibility(View.INVISIBLE);
								}
								textview_added_by.setText("MCQ Added by~ ".concat(allmcq_maplist.get((int)count3).get("added_by").toString()));
								temp_ans = allmcq_maplist.get((int)count3).get("ans").toString();
								temp_map = allmcq_maplist.get((int)count3);
								break;
							}
							count3++;
						}
					}else {
						if (temp_ans.equals(current_selected)) {
							total_points++;
						}
						r_msg = "";
						r_msg = "Subject Name (Code) : ".concat(getIntent().getStringExtra("subject name code")).concat("\n");
						r_msg = r_msg.concat("Test Mode : ").concat(getIntent().getStringExtra("test_type").concat("\n"));
						r_msg = r_msg.concat("Total Questions attempt : ").concat(String.valueOf((long)(current_question)).concat("\n"));
						r_msg = r_msg.concat("Obtained Marks : ").concat(String.valueOf((long)(total_points)).concat("/".concat(String.valueOf((long)(total_question)))).concat("\n"));
						map_r.clear();
						map_r = new HashMap<>();
						map_r.put("idno", getIntent().getStringExtra("idno"));
						map_r.put("name", getIntent().getStringExtra("name"));
						map_r.put("subject(code)", getIntent().getStringExtra("subject name code"));
						map_r.put("subject_code", getIntent().getStringExtra("subject code"));
						map_r.put("test_type", getIntent().getStringExtra("test_type"));
						map_r.put("total_marks", String.valueOf((long)(total_question)));
						map_r.put("obtain_marks", String.valueOf((long)(total_points)));
						map_r.put("time", new SimpleDateFormat("hh:mm:ss dd-MM-yy").format(calendar_1.getTime()));
						_password_generatorV1();
						count1 = 0;
						for(int _repeat181 = 0; _repeat181 < (int)(results_maplist.size()); _repeat181++) {
							if (results_maplist.get((int)count1).get("key").toString().equals(getIntent().getStringExtra("idno").concat("-".concat(password_genrated)))) {
								_password_generatorV1();
							}
							count1++;
						}
						map_r.put("key", getIntent().getStringExtra("idno").concat("-".concat(password_genrated)));
						results.child(getIntent().getStringExtra("idno").concat("-".concat(password_genrated))).updateChildren(map_r);
						map_r.clear();
						dialog_result.setCancelable(false);
						t2.cancel();
						dialog_result.setTitle("📃Result📃");
						dialog_result.setMessage(r_msg);
						dialog_result.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								finish();
							}
						});
						dialog_result.create().show();
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️Please Select Answer⚠️");
				}
			}
		});
		
		report_question.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog cd2 = new AlertDialog.Builder(TestActivity.this).create();
				LayoutInflater cd2LI = getLayoutInflater();
				View cd2CV = (View) cd2LI.inflate(R.layout.report_question_cd, null);
				cd2.setView(cd2CV);
				final LinearLayout l1 = (LinearLayout)
				cd2CV.findViewById(R.id.l1);
				final LinearLayout l2 = (LinearLayout)
				cd2CV.findViewById(R.id.l2);
				final LinearLayout l3 = (LinearLayout)
				cd2CV.findViewById(R.id.l3);
				final CheckBox cb_1 = (CheckBox)
				cd2CV.findViewById(R.id.cb_1);
				final CheckBox cb_2 = (CheckBox)
				cd2CV.findViewById(R.id.cb_2);
				final CheckBox cb_3 = (CheckBox)
				cd2CV.findViewById(R.id.cb_3);
				final TextView tv = (TextView)
				cd2CV.findViewById(R.id.tv);
				final EditText et_other = (EditText)
				cd2CV.findViewById(R.id.et_other);
				final Button cd_report = (Button)
				cd2CV.findViewById(R.id.cd_report);
				final Button cd_cancel = (Button)
				cd2CV.findViewById(R.id.cd_cancel);
				cd2.setCancelable(false);
				cd2.show();
				cd_report.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						if ((cb_1.isChecked() || cb_2.isChecked()) || (cb_3.isChecked() || !et_other.getText().toString().equals(""))) {
							report_remark = "";
							if (cb_1.isChecked()) {
								report_remark = "Question is wrong.\n";
							}
							if (cb_2.isChecked()) {
								report_remark = report_remark.concat("Answer is wrong/missing.\n");
							}
							if (cb_3.isChecked()) {
								report_remark = report_remark.concat("Spelling mistakes.\n");
							}
							if (!et_other.getText().toString().equals("")) {
								report_remark = report_remark.concat(et_other.getText().toString());
							}
							reported_uqno = current_uqno;
							count1 = done_uqno.size() - 1;
							done_uqno.remove((int)(count1));
							current_uqno = uqno_list.get((int)(SketchwareUtil.getRandom((int)(0), (int)(uqno_list.size() - 1))));
							uqno_list.remove((int)(uqno_list.indexOf(current_uqno)));
							done_uqno.add(current_uqno);
							radiobutton_op1.setChecked(false);
							radiobutton_op2.setChecked(false);
							radiobutton_op3.setChecked(false);
							radiobutton_op4.setChecked(false);
							radiobutton_op3.setEnabled(true);
							radiobutton_op4.setEnabled(true);
							radiobutton_op3.setVisibility(View.VISIBLE);
							radiobutton_op4.setVisibility(View.VISIBLE);
							count3 = 0;
							for(int _repeat106 = 0; _repeat106 < (int)(allmcq_maplist.size()); _repeat106++) {
								if (current_uqno.equals(allmcq_maplist.get((int)count3).get("uqno").toString())) {
									question_view.setText(allmcq_maplist.get((int)count3).get("question").toString());
									radiobutton_op1.setText(allmcq_maplist.get((int)count3).get("op1").toString());
									radiobutton_op2.setText(allmcq_maplist.get((int)count3).get("op2").toString());
									if (!allmcq_maplist.get((int)count3).get("op1").toString().equals("True")) {
										radiobutton_op3.setText(allmcq_maplist.get((int)count3).get("op3").toString());
										radiobutton_op4.setText(allmcq_maplist.get((int)count3).get("op4").toString());
									}else {
										radiobutton_op3.setEnabled(false);
										radiobutton_op4.setEnabled(false);
										radiobutton_op3.setVisibility(View.INVISIBLE);
										radiobutton_op4.setVisibility(View.INVISIBLE);
									}
									temp_ans = allmcq_maplist.get((int)count3).get("ans").toString();
									textview_added_by.setText("MCQ Added by~ ".concat(allmcq_maplist.get((int)count3).get("added_by").toString()));
									temp_map.put("wrong_flag", "on");
									temp_map.put("reported_by", getIntent().getStringExtra("name"));
									temp_map.put("remark", report_remark);
									tp_key = temp_map.get("uqno").toString();
									allmcq.child(tp_key).updateChildren(temp_map);
									SketchwareUtil.showMessage(getApplicationContext(), "MCQ Reported ✅");
									temp_map = allmcq_maplist.get((int)count3);
									break;
								}
								count3++;
							}
							cd2.dismiss();
						}else {
							SketchwareUtil.showMessage(getApplicationContext(), "Please select or enter any reason to report question");
						}
					}
				});
				cd_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						cd2.dismiss();
					}
				});
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
				if (_childValue.get("subject_code").toString().equals(getIntent().getStringExtra("subject code")) && _childValue.get("wrong_flag").toString().equals("off")) {
					allmcq_maplist.add(_childValue);
					if (!_childValue.get("uqno").toString().equals(getIntent().getStringExtra("random uqno start"))) {
						uqno_list.add(_childValue.get("uqno").toString());
					}
				}
				if (!(1 < current_question)) {
					if (_childValue.get("uqno").toString().equals(getIntent().getStringExtra("random uqno start"))) {
						question_view.setText(_childValue.get("question").toString());
						radiobutton_op1.setText(_childValue.get("op1").toString());
						radiobutton_op2.setText(_childValue.get("op2").toString());
						if (!_childValue.get("op1").toString().equals("True")) {
							radiobutton_op3.setText(_childValue.get("op3").toString());
							radiobutton_op4.setText(_childValue.get("op4").toString());
						}else {
							radiobutton_op3.setEnabled(false);
							radiobutton_op4.setEnabled(false);
							radiobutton_op3.setVisibility(View.INVISIBLE);
							radiobutton_op4.setVisibility(View.INVISIBLE);
						}
						temp_ans = _childValue.get("ans").toString();
						temp_maplist.clear();
						temp_maplist.add(_childValue);
						temp_map = temp_maplist.get((int)0);
						textview_added_by.setText("MCQ Added by~ ".concat(_childValue.get("added_by").toString()));
					}
				}
				count2 = 0;
				if (!(uqno_list.size() == 0)) {
					for(int _repeat172 = 0; _repeat172 < (int)(done_uqno.size()); _repeat172++) {
						if (done_uqno.get((int)(count2)).equals(uqno_list.get((int)(uqno_list.size() - 1)))) {
							uqno_list.remove((int)(uqno_list.size() - 1));
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
				if (!_childValue.get("uqno").toString().equals(getIntent().getStringExtra("random uqno start"))) {
					if (_childValue.get("subject_code").toString().equals(getIntent().getStringExtra("subject code")) && _childValue.get("wrong_flag").toString().equals("off")) {
						allmcq_maplist.add(_childValue);
						uqno_list.add(_childValue.get("uqno").toString());
					}
				}
				count2 = 0;
				if (!(uqno_list.size() == 0)) {
					for(int _repeat57 = 0; _repeat57 < (int)(done_uqno.size()); _repeat57++) {
						if (done_uqno.get((int)(count2)).equals(uqno_list.get((int)(uqno_list.size() - 1)))) {
							uqno_list.remove((int)(uqno_list.size() - 1));
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
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allmcq.addChildEventListener(_allmcq_child_listener);
		
		_results_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				results_maplist.clear();
				results.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						results_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								results_maplist.add(_map);
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
				results_maplist.clear();
				results.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						results_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								results_maplist.add(_map);
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
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		results.addChildEventListener(_results_child_listener);
	}
	
	private void initializeLogic() {
		report_dbFlag = 0;
		setTitle(getIntent().getStringExtra("test_type"));
		textview_subjectname.setText("Subject Name :\n".concat(getIntent().getStringExtra("subject name code").concat("\n(".concat(getIntent().getStringExtra("test_type").concat(")")))));
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
		final AlertDialog customdialog1 = new AlertDialog.Builder(TestActivity.this).create();
		LayoutInflater customdialog1LI = getLayoutInflater();
		View customdialog1CV = (View) customdialog1LI.inflate(R.layout.countdown_center, null);
		customdialog1.setView(customdialog1CV);
		final LinearLayout l1 = (LinearLayout)
		customdialog1CV.findViewById(R.id.l1);
		final TextView count_view = (TextView)
		customdialog1CV.findViewById(R.id.count_view);
		customdialog1.setCancelable(false);
		customdialog1.show();
		total_points = 0;
		countdown = 3;
		done_uqno.clear();
		current_uqno = getIntent().getStringExtra("random uqno start");
		done_uqno.add(getIntent().getStringExtra("random uqno start"));
		count_view.setText(String.valueOf((long)(countdown)));
		t1 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						count_view.setText(String.valueOf((long)(countdown)));
						if (countdown == 0) {
							count_view.setText("Start");
							t3 = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											customdialog1.dismiss();
											t1.cancel();
										}
									});
								}
							};
							_timer.schedule(t3, (int)(500));
						}
						countdown--;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t1, (int)(0), (int)(1000));
		if (getIntent().getStringExtra("test_type").equals("Practice Test")) {
			mm = 05;
			ss = 00;
			total_question = 10;
		}
		if (getIntent().getStringExtra("test_type").equals("Unit Test 1") || getIntent().getStringExtra("test_type").equals("Unit Test 2")) {
			mm = 30;
			ss = 00;
			total_question = 30;
		}
		if (getIntent().getStringExtra("test_type").equals("Semester Test")) {
			mm = 60;
			ss = 00;
			total_question = 60;
		}
		current_question = 1;
		questions_count_view.setText("Question No : ".concat(String.valueOf((long)(current_question)).concat("/".concat(String.valueOf((long)(total_question))))));
		mmt = String.valueOf((long)(mm));
		sst = String.valueOf((long)(ss));
		if (mm < 10) {
			mmt = "0".concat(String.valueOf((long)(mm)));
		}
		if (ss < 10) {
			sst = "0".concat(String.valueOf((long)(ss)));
		}
		time_view.setText("Time : ".concat(mmt.concat(" : ").concat(sst)));
		t2 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if ((mm == 0) && (ss == 0)) {
							SketchwareUtil.showMessage(getApplicationContext(), "❌TIME OUT❌");
							r_msg = "";
							r_msg = "Subject Name (Code) : ".concat(getIntent().getStringExtra("subject name code")).concat("\n");
							r_msg = r_msg.concat("Test Mode : ").concat(getIntent().getStringExtra("test_type").concat("\n"));
							r_msg = r_msg.concat("Total Questions attempt : ").concat(String.valueOf((long)(current_question)).concat("\n"));
							r_msg = r_msg.concat("Obtained Marks : ").concat(String.valueOf((long)(total_points)).concat("/".concat(String.valueOf((long)(total_question)))).concat("\n"));
							map_r.clear();
							map_r = new HashMap<>();
							map_r.put("idno", getIntent().getStringExtra("idno"));
							map_r.put("name", getIntent().getStringExtra("name"));
							map_r.put("subject(code)", getIntent().getStringExtra("subject name code"));
							map_r.put("subject_code", getIntent().getStringExtra("subject code"));
							map_r.put("test_type", getIntent().getStringExtra("test_type"));
							map_r.put("total_marks", String.valueOf((long)(total_question)));
							map_r.put("obtain_marks", String.valueOf((long)(total_points)));
							map_r.put("time", new SimpleDateFormat("hh:mm:ss dd-MM-yy").format(calendar_1.getTime()));
							_password_generatorV1();
							count1 = 0;
							for(int _repeat390 = 0; _repeat390 < (int)(results_maplist.size()); _repeat390++) {
								if (results_maplist.get((int)count1).get("key").toString().equals(getIntent().getStringExtra("idno").concat("-".concat(password_genrated)))) {
									_password_generatorV1();
								}
								count1++;
							}
							map_r.put("key", getIntent().getStringExtra("idno").concat("-".concat(password_genrated)));
							results.child(getIntent().getStringExtra("idno").concat("-".concat(password_genrated))).updateChildren(map_r);
							map_r.clear();
							dialog_result.setCancelable(false);
							t2.cancel();
							dialog_result.setTitle("📃Result📃");
							dialog_result.setMessage(r_msg);
							dialog_result.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									finish();
								}
							});
							dialog_result.create().show();
							t2.cancel();
						}else {
							if (ss == 00) {
								mm--;
								ss = 59;
							}else {
								ss--;
							}
							mmt = String.valueOf((long)(mm));
							sst = String.valueOf((long)(ss));
							if (mm < 10) {
								mmt = "0".concat(String.valueOf((long)(mm)));
							}
							if (ss < 10) {
								sst = "0".concat(String.valueOf((long)(ss)));
							}
							time_view.setText("Time : ".concat(mmt.concat(" : ").concat(sst)));
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t2, (int)(3500), (int)(1000));
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
		dialog.setCancelable(false);
		dialog.setTitle("⚠️ Alert ⚠️");
		dialog.setMessage("You can't exit while ongoing ⚠️");
		dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
	}
	public void _password_generatorV1 () {
		alphabet_low = "abcdefghijklnopqrstuvwxyz";
		alphabet_high = "ABCDEFGHIJKLNMOPQRSTUVWXYZ";
		all_numbers = "0123456789";
		symbol = " @_*?";
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