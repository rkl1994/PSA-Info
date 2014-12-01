package app.psa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.widget.BaseAdapter;

public class Http {

	public static String base_url = "http://10.60.37.183/pas/api";
	
	public static String Register_url = base_url+"/config/register";
	
	
	public static String AirConStatus_url = base_url+"/control/aircon/status";
	public static String AirConTemperature_url = base_url+"/control/aircon/temperature";
	public static String AirConFlow_url = base_url+"/control/aircon/flow";
	
	public static String AudioVolume_url = base_url+"/control/audio/volume";
	public static String AudioPlaying_url = base_url+"/control/audio/playing";
	public static String MusicControl_url = base_url+"/control/music/control";
	public static String MusicNow_url = base_url+"/control/music/now";
	
	public static String radioControl_url = base_url+"/control/radio/control";
	public static String radioNow_url = base_url+"/control/radio/now";
	
	public static String contactsList_url = base_url+"/control/contacts/list";
	public static String teleCallin_url = base_url+"/control/telephone/callin";
	public static String teleCallout_url = base_url+"/control/telephone/callout";
	public static String teleNow_url = base_url+"/control/telephone/now";
	
	public static String addressSearch_url = base_url+"/control/address/search";
	public static String postNavigationDest_url = base_url+"/control/navigation/dest";
	public static String getNavigationDest_url = base_url+"/control/navigation/dest";
	public static String postNavigationSync_url = base_url+"/control/navigation/sync";
	public static String getNavigationSync_url = base_url+"/control/navigation/sync";
	public static String actionToMapServer_url = base_url+"/control/navigation/actionToMapServer";
	/**1. Config APIs**/
	
	/**
	 * ע��ͻ���
	 * @param ip ����ip
	 * @param port �����˿ں�
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void Register(String ip,String port) throws ClientProtocolException, IOException{
		List<BasicNameValuePair> mList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("ip",ip);
		BasicNameValuePair pair2 = new BasicNameValuePair("port",port);
		BasicNameValuePair pair3 = new BasicNameValuePair("clientType", "control_center");
		mList.add(pair1);
		mList.add(pair2);
		mList.add(pair3);
		BasicHttp.getResponseforPost(Register_url, mList);
	}
	
	
	/**2. Control APIs**/
	
	/**
	 * 2.1.1����/�رտյ�
	 * @param status
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String AirConStatusPost(String status) throws ClientProtocolException, IOException{
		String result;
		List<BasicNameValuePair> mList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("status",status);
		mList.add(pair1);
		result = BasicHttp.getResponseforPost(AirConStatus_url, mList);
		return result;
	}
	
	/**
	 * 2.1.2��ȡ�յ�����״̬
	 * @return status
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String AirConStatusGet() throws ClientProtocolException, IOException{
		String result;
		result = BasicHttp.getResponseForGet(AirConStatus_url);
		return result;
	}
	
	/*2.1.3����Ŀ���¶�*/
	public static String AirConTemperaturePost(String temperature) throws ClientProtocolException, IOException{
		String temp;
		List<BasicNameValuePair> postTempList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postTempPair = new BasicNameValuePair("temperature",temperature);
		postTempList.add(postTempPair);
		temp = BasicHttp.getResponseforPost(AirConTemperature_url, postTempList);
		return temp;
		
	}
	
	/*2.1.4��ȡĿ���¶�*/
	
	public static String AirConTemperatureGet() throws ClientProtocolException, IOException{
		String temp;
		temp = BasicHttp.getResponseForGet(AirConTemperature_url);
		return temp;

	}
	
	/*2.1.5�յ��ͷ�*/
	
	public static String AirConFlowPost(String status)throws ClientProtocolException, IOException{
		String result;
		List<BasicNameValuePair> flowPostList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair flowPost = new BasicNameValuePair("status",status);
		flowPostList.add(flowPost);
		result = BasicHttp.getResponseforPost(AirConFlow_url, flowPostList);
		return result;
	}
	
	
	/*2.1.6��ȡ�ͷ�״̬*/
	public static String AirConFlowGet() throws ClientProtocolException, IOException{
		String result;
		result = BasicHttp.getResponseForGet(AirConFlow_url);
		return result;
	}
	
	
	/*2.2.1��������*/
	public static String AudioVolumePost(String volume)throws ClientProtocolException, IOException{
		String vol;
		List<BasicNameValuePair> postAudioVolumeList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postAudioVolumePair = new BasicNameValuePair("volume",volume);
		postAudioVolumeList.add(postAudioVolumePair);
		vol = BasicHttp.getResponseforPost(AudioVolume_url, postAudioVolumeList);
		return vol;
	}
	
	/*2.2.2��ȡ����*/
	public static String AudioVolumeGet() throws ClientProtocolException,IOException{
		String vol;
		vol = BasicHttp.getResponseForGet(AudioVolume_url);
		return vol;
	}
	
	
	/*2.2.2��ȡ����״̬*/
	
	public static String AudioPlayingGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(AudioPlaying_url);
		return result;
	}
	
	
	/*2.2.3���ֿ���*/
	public static String musicControlPost(String action) throws ClientProtocolException,IOException {
		String act;
		List<BasicNameValuePair> postMusicControlList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postMusicControlPair = new BasicNameValuePair("action",action);
		postMusicControlList.add(postMusicControlPair);
		act = BasicHttp.getResponseforPost(MusicControl_url, postMusicControlList);
		return act;
	}
	
	
	/*2.2.4��ȡ�����б�&��������  ����û����*/
	
	
	/*2.2.5��ȡ��ǰ���Ÿ���*/
	public static String musicNowGet() throws ClientProtocolException,IOException {
		String songNow;
		songNow = BasicHttp.getResponseForGet(MusicNow_url);
		return songNow;
		
	}
	
	/*2.2.6����������*/
	
	public static String radioControlPost(String action) throws ClientProtocolException,IOException {
		String act;
		List<BasicNameValuePair> postradioControlList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postRadioControlPair = new BasicNameValuePair("action",action);
		postradioControlList.add(postRadioControlPair);
		act = BasicHttp.getResponseforPost(radioControl_url, postradioControlList);
		return act;
	}
	
	
	/*2.2.7  2.2.8 �����������*/
	
	/*2.2.9��ȡ��ǰ����Ƶ��*/
	public static String radioNowGet(String Radio) throws ClientProtocolException,IOException {
		String radioNow;
		radioNow = BasicHttp.getResponseForGet(radioNow_url);
		return radioNow;
		
	}
	
	
	
	/*2.3.1��ȡͨѶ¼�б�*/
	public static String contactsListGet() throws ClientProtocolException, IOException{
		String contact;
		contact = BasicHttp.getResponseForGet(contactsList_url);
		return contact;
	}
	
	/*2.3.2����ͨѶ¼�б�*/
	public static String contactsSearchGet() throws ClientProtocolException, IOException{
		String contact;
		contact = BasicHttp.getResponseForGet(contactsList_url);
		return contact;
	}
	
	/*2.4.1ģ���������*/
	public static void telephonecallinPost(String name,String number) throws ClientProtocolException,IOException{
		//String teleName;
		//String teleNumber;
		List<BasicNameValuePair> telecallinList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleCallinPair1 = new BasicNameValuePair("name",name);
		BasicNameValuePair teleCallinPair2 = new BasicNameValuePair("number",number);
		telecallinList.add(teleCallinPair1);
		telecallinList.add(teleCallinPair2);
		
		BasicHttp.getResponseforPost(teleCallin_url, telecallinList);
	
	}
	
	/*2.4.2�绰����*/
	public static void telephonecalloutPost(String name,String number) throws ClientProtocolException,IOException{
		//String teleName;
		//String teleNumber;
		List<BasicNameValuePair> telecalloutList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleCalloutPair1 = new BasicNameValuePair("name",name);
		BasicNameValuePair teleCalloutPair2 = new BasicNameValuePair("number",number);
		telecalloutList.add(teleCalloutPair1);
		telecalloutList.add(teleCalloutPair2);
		
		BasicHttp.getResponseforPost(teleCallout_url, telecalloutList);
	
	}
	
	/*2.4.3�绰 �����һ�*/
	public static String telephoneAnswerPost(String answer) throws ClientProtocolException,IOException{
		String result;
		List<BasicNameValuePair> teleAnswerList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleAnswerPair = new BasicNameValuePair("answer",answer);
		teleAnswerList.add(teleAnswerPair);
		result = BasicHttp.getResponseforPost(teleCallout_url, teleAnswerList);
		return result;
	
	}
	
	/*2.4.4��ǰͨ��״̬*/
	public static String telephoneNowGet()throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(teleNow_url);
		return result;
	}
	
	/*2.6.1��ȡ��ַ���б���ʲô��˼*/
	
	/*2.6.2������ַ���б�*/
	public String addressSearchGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(addressSearch_url);
		return result;
	}
	
	
	
	/*2.6.3 ����������Ŀ�ĵ�*/
	public static String navigationDestPost(String addressIndex) throws ClientProtocolException,IOException{
		String result;
		List<BasicNameValuePair> navigationDestList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair navigationDestPair = new BasicNameValuePair("addressIndex",addressIndex);
		navigationDestList.add(navigationDestPair);
		result = BasicHttp.getResponseforPost(teleCallout_url, navigationDestList);
		return result;
	
	}
	

	/*2.6.4 ��������ȡĿ�ĵ�*/
	public String navigationDestGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(getNavigationDest_url);
		return result;
	
	}
	
	
	/*2.6.5 ������ͬ��������Ϣ*/
	public static void navigationSync(String speed,String message,String isLast,String x,String y,String distanceToNext) 
			throws ClientProtocolException, IOException{
		List<BasicNameValuePair> navigationSyncList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("speed",speed);
		BasicNameValuePair pair2 = new BasicNameValuePair("message",message);
		BasicNameValuePair pair3 = new BasicNameValuePair("isLast", isLast);
		BasicNameValuePair pair4 = new BasicNameValuePair("x", x);
		BasicNameValuePair pair5 = new BasicNameValuePair("y", y);
		BasicNameValuePair pair6 = new BasicNameValuePair("distanceToNext", distanceToNext);
		
		navigationSyncList.add(pair1);
		navigationSyncList.add(pair2);
		navigationSyncList.add(pair3);
		navigationSyncList.add(pair4);
		navigationSyncList.add(pair5);
		navigationSyncList.add(pair6);
		
		BasicHttp.getResponseforPost(postNavigationSync_url, navigationSyncList);
	}
	
	
	
	
	/*2.6.6 ��������ȡͬ��������Ϣ*/
	public String navigationSyncGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(getNavigationSync_url);
		return result;

	}
	
	
	/*2.6.7 ���������Ͷ�������ͼ������*/
	public String navigationActionToMapServerPost(String actionId) throws ClientProtocolException,IOException{
		String action;
		List<BasicNameValuePair> navigationActionToMapServerList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair navigationActionToMapServerPair = new BasicNameValuePair("actionId",actionId);
		navigationActionToMapServerList.add(navigationActionToMapServerPair);
		action = BasicHttp.getResponseforPost(actionToMapServer_url, navigationActionToMapServerList);
		return action;
		
	}
	
	
	
}
