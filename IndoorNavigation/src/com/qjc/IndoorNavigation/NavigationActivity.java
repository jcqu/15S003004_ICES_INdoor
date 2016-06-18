package com.qjc.IndoorNavigation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.qjc.IndoorNavigation.R;
import com.qjc.IndoorNavigation.ImageMap.ImageMap;
import com.qjc.IndoorNavigation.ImageMap.Bubble;
import com.qjc.IndoorNavigation.ImageMap.CircleShape;
import com.qjc.IndoorNavigation.ImageMap.Shape;
import com.qjc.IndoorNavigation.utils.PopMenu;
import com.qjc.IndoorNavigation.utils.PopMenu.OnItemClickListener;
import com.zbar.lib.CaptureActivity;

/**
 * @ClassName: NavigationActivity
 * @Description: TODO ��λҳ��Activity
 * @author ����
 * @date 2015-3-23 ����4:00:35
 */
public class NavigationActivity extends Activity implements OnItemClickListener {
	private ImageMap map; // �Զ�����ͼ����
	private String apmac;
	private String aplev;
	private Handler mHandler2;
	private SharedPreferences Qres;
	private int Qrstate;
	private String Qrstr = null;
	private Button nbtn1;
	private Button nbtn2;
	private Button nbtn3;
	private int resx = -1;// res
	private int resy = -1;
	private EditText search;
	private PopMenu popMenu;// �����˵�
	///////////////////Ӧ�ӷ��������أ����ؽ���////////////////////
	private String[] popstr = { "F1", "F2", "F3", "F4", "F5" };
	private int[] drawarray = { R.drawable.f1, R.drawable.f2, R.drawable.f3,
			R.drawable.f4, R.drawable.f5 };
	private int Floor=4;//Ĭ��¥��
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.navigation);
		nbtn1 = (Button) findViewById(R.id.nbtn1);
		search=(EditText) findViewById(R.id.home_search_edit);
		nbtn1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				popMenu.showAsDropDown(v);
			}
		});
		nbtn2 = (Button) findViewById(R.id.nbtn2);
		nbtn2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NavigationActivity.this,
						CaptureActivity.class);
				startActivity(intent);
			}
		});
		nbtn3 = (Button) findViewById(R.id.nbtn3);
		nbtn3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NavigationActivity.this,
						NaviPath.class);
				String g=search.getText().toString();
				//��BundleЯ������
			    Bundle bundle=new Bundle();
			    //���ݲ���
				if ((resx == -1) && (resy == -1)) {
					resx = 0;resy = 6;
				}else {
				///////////////////����任��Ӧ�ӷ��������أ����ؽ���////////////////////
					resx = 12*resx-20;resy = 6*resy;
				}
				bundle.putInt("floor", Floor);
			    bundle.putInt("startx", resx);
			    bundle.putInt("starty", resy);
			    bundle.putString("goal", g);
			    intent.putExtras(bundle);
				startActivity(intent);
				resx = -1;resy = -1;
				// NavigationActivity.this.finish();
			}
		});
		// ��ʼ�������˵�
		popMenu = new PopMenu(this);
		popMenu.addItems(popstr);
		popMenu.setOnItemClickListener(this);
		map = (ImageMap) findViewById(R.id.imagemap);
		// ����Դ�ļ�����һ��bitmap����ͼ
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.f4, new BitmapFactory.Options());
		// ��ͼ���ص�ImageMap����ȥ
		map.setMapBitmap(bitmap);
		// ����һ��������עλ�õ���ͼview�����view�Լ����Զ����
		View bubble = getLayoutInflater().inflate(R.layout.popup, null);

		// ����ͼ�ӽ�ImageMap
		map.setBubbleView(bubble, new Bubble.RenderDelegate() {
			@Override
			public void onDisplay(Shape shape, View bubbleView) {
				ImageView logo = (ImageView) bubbleView.findViewById(R.id.logo); // ͨ��bubbleView�õ���Ӧ�Ŀؼ�
				logo.setImageResource(R.drawable.loc_logo); // ͼƬ
			}
		});

		Qres = getSharedPreferences("Qres", MODE_PRIVATE);
		Qrstate = Qres.getInt("Qrstate", 0);
		Qrstr = Qres.getString("QrRes", "");
		if (Qrstate == 0) {
			mHandler2 = new MyHandler2();
			apmac = "";
			aplev = "";
			String[] macarray=GetapinfoActivity.macarray5;
			if (Floor==5) {
				macarray=GetapinfoActivity.macarray5;
			}
			else if(Floor==4){
				macarray=GetapinfoActivity.macarray4;
			}
			// ��ȡ��ǰλ��AP��Ϣ
			WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			wm.startScan();
			ComparatorUser c = new ComparatorUser();
			List<ScanResult> results = wm.getScanResults();
			try {
				Collections.sort(results, c);
				int max = Math.min(30, results.size());
				for (int i = 0; i < max; i++) {// ȥ�����Է�����
					ScanResult one = results.get(i);
					for (int j = 0; j < macarray.length; j++) {
						if (one.BSSID.equals(macarray[j])) {
							apmac += (j + ",");
							aplev += one.level;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// �������߳�
			Thread t = new NetWorkThread2();
			t.start();
			SharedPreferences.Editor editor = Qres.edit();
			editor.putInt("Qrstate", 0);
			editor.commit();
		} else if (Qrstate == 1) {
			// ��ʽ��2#501#2#1#2015-4-8
			String[] resarray;// QR���������
			resarray = Qrstr.split("#");
			resx = Integer.valueOf(resarray[2]).intValue();
			resy = Integer.valueOf(resarray[3]).intValue();
			showPostion();
			SharedPreferences.Editor editor = Qres.edit();
			editor.putInt("Qrstate", 0);
			editor.commit();
		}

	}
	
	class NetWorkThread2 extends Thread// ���̷߳��ʷ�����
	{
		String strResult;
		int state;
		JSONObject mJson;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("thread--->" + Thread.currentThread().getName());
			String httpUrl = getString(R.string.httpurl)
					+ "/IndoorNavigation/navigation.php";
			String parms = "apmac=" + apmac + "&aplev=" + aplev + "&floorid=" + Floor;
			System.out.println(httpUrl + "?" + parms);
			try {
				strResult = GetapinfoActivity.mhttpGet1(httpUrl, parms);
				System.out.println(strResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (strResult != null && strResult.startsWith("\ufeff")) {
				strResult = strResult.substring(1);
			}
			try {
				mJson = new JSONObject(strResult);
				state = mJson.getInt("state");
				if (state == 1) {
					resx = mJson.getInt("datax");
					resy = mJson.getInt("datay");
				}
				showPostion();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg1 = mHandler2.obtainMessage();
			msg1.arg1 = state;
			mHandler2.sendMessage(msg1);

		}
	}

	@SuppressLint("HandlerLeak")
	class MyHandler2 extends Handler// handler������
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == 1) {
				Toast.makeText(NavigationActivity.this, "ԭ�������Ⱑ��",
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(NavigationActivity.this, "û�ҵ�������,�Ҹ���־�ԵĽ������ԣ�",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	class ComparatorUser implements Comparator { // �Ƚ���

		@Override
		public int compare(Object arg0, Object arg1) {
			ScanResult temp1 = (ScanResult) arg0;
			ScanResult temp2 = (ScanResult) arg1;
			if (temp1.level > temp2.level)
				return -1;
			else if (temp1.level < temp2.level)
				return 1;
			else if (temp1.level == temp2.level)
				return 0;
			return 0;
		}

	};

	public void showPostion() { // �÷�������ʵ��һ��Բ�㣬���ں�bubble���а󶨣�����������ʾ�ڵ�ͼ��
		CircleShape black = new CircleShape("NO", Color.RED); // Color.BLUE��Բ�����ɫ
		if ((resx != -1) && (resy != -1)) {
			double x = resx * dip2px(this, 93); // x���꣬res�ӷ�������ȡ
			double y = resy * dip2px(this, 57); // y����
			black.setValues(String.format("%.5f,%.5f,15", x, y)); // ����Բ���λ�úʹ�С
			map.addShapeAndRefToBubble(black); // �ӵ���ͼ��
		} else {
			double x = dip2px(this, 93); // x���꣬res�ӷ�������ȡ
			double y = dip2px(this, 57); // y����
			black.setValues(String.format("%.5f,%.5f,15", x, y)); // ����Բ���λ�úʹ�С
			map.addShapeAndRefToBubble(black); // �ӵ���ͼ��
		}
	}

	public static int dip2px(Context context, double dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp */
	public static int px2dip(Context context, double pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public void onItemClick(int index) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				drawarray[index], new BitmapFactory.Options());
		// ��ͼ���ص�ImageMap����ȥ
		map.setMapBitmap(bitmap);
		nbtn1.setText(popstr[index]);
		Floor=index+1;
		//map.removeShape(null);
		//navigate();
	}

}
