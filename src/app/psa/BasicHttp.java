package app.psa;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * �����˻�����http����
 * @author L.Z.W
 *
 */
public class BasicHttp {

	/**
	 * Get ����
	 * @param url ��ַ
	 * @return
	 */
	public static String getResponseForGet(String url) throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet(url);
		return getResponse(httpGet);
	}

	/**
	 * Put ���� 1
	 * @param url ��ַ
	 * @param pairs ��ֵ�� List
	 * @return
	 */
	public static String getResponseforPut(String url,List<BasicNameValuePair> pairs) throws UnsupportedEncodingException{
		HttpPut httpPut = new HttpPut(url);
		if (pairs != null) {
			httpPut.setEntity(new UrlEncodedFormEntity(pairs));
		}
		try {
			return getResponse(httpPut);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Put ���� 2
	 * @param url ��ַ
	 * @param pairs ��ֵ�� List
	 * @param cookie
	 * @return
	 */
	public static String getResponseforPut(String url,List<BasicNameValuePair> pairs,String cookie) throws ClientProtocolException, IOException{
		
		if (url.equals("")||url == null) {
			return null;
		}
		HttpPut httpPut = new HttpPut(url);
		if (pairs != null) {
			httpPut.setEntity(new UrlEncodedFormEntity(pairs));	
		}
		if (cookie != null) {
			httpPut.addHeader("USER_TOKEN",cookie);
		}
		return getResponse(httpPut);
	}
	
	/**
	 * Put ���� 3
	 * @param url ��ַ
	 * @param object JSON object
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforPut(String url,JSONObject object) throws ClientProtocolException, IOException{
		if (url.equals("")||url == null) {
			return null;
		}
		HttpPut httpPut = new HttpPut(url);
		if (object != null) {
			StringEntity entity = new StringEntity(object.toString());
			httpPut.setEntity(entity);
		}
		return getResponse(httpPut);
	}

	/**
	 * Post ���� �ϴ���ֵ��
	 * @param url
	 * @param pairs
	 * @return
	 */
	public static String getResponseforPost(String url,List<BasicNameValuePair> pairs) throws ClientProtocolException, IOException{

		if (url.equals("")||url == null) {
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		if (pairs != null) {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));	
		}
		return getResponse(httpPost);
	}
	
	/**
	 * Post ���� �ϴ���ֵ��
	 * @param url ��ַ
	 * @param pairs ��ֵ�� List
	 * @param cookie 
	 * @return
	 */
	public static String getResponseforPost(String url,List<BasicNameValuePair> pairs,String cookie) throws ClientProtocolException, IOException{
		if (url == null||url.equals("")) {
			return null;
		}
		if (cookie == null||cookie.equals("")) {
			return getResponseforPost(url, pairs);
		}
		HttpPost httpPost = new HttpPost(url);
		if (pairs != null) {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));	
		}
		httpPost.setHeader("USER_TOKEN",cookie);
		return getResponse(httpPost);
	}
	
	/**
	 * Post ���� �ϴ�JSONObject
	 * @param url ��ַ
	 * @param object json object
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforPost(String url,JSONObject object) throws ClientProtocolException, IOException{
		if (url == null||url.equals("")) {
			return null;
		}
		if (object == null) {
			return null;
		}
		StringEntity entity = new StringEntity(object.toString());
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		return getResponse(httpPost);
	}
	
	/**
	 * Post ���� �ϴ�JSONObject
	 * @param url ��ַ
	 * @param object json object
	 * @param cookie �û���Ϣ
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforPost(String url,JSONObject object,String cookie) throws ClientProtocolException, IOException{
		if (url == null||url.equals("")) {
			return null;
		}
		if (object == null) {
			return null;
		}if (cookie == null) {
			return getResponseforPost(url, object);
		}
		
		StringEntity entity = new StringEntity(object.toString());
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		httpPost.setHeader("USER_TOKEN", cookie);
		return getResponse(httpPost);
	}
	
	/**
	 * Post ���� �ϴ�ͼƬ
	 * @param url
	 * @param fileName
	 * @param file
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforPost(String url,String fileName,File file) throws ClientProtocolException, IOException{
		if (url == null|| url.equals("")) {
			return null;
		}
		if (file == null || fileName == null) {
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		FileBody fileBody = new FileBody(file,"image/jpeg");
		MultipartEntity entity = new MultipartEntity();
		entity.addPart(fileName, fileBody);
		httpPost.setEntity(entity);
		return getResponse(httpPost);
	}
	
	/**
	 * Post ���� �ϴ�ͼƬ
	 * @param url
	 * @param fileName
	 * @param file
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforPost(String url,String fileName,File file,String cookie) throws ClientProtocolException, IOException{
		if (url == null|| url.equals("")) {
			return null;
		}
		if (file == null || fileName == null) {
			return null;
		}
		if (cookie == null) {
			return getResponseforPost(url, fileName, file);
		}
		HttpPost httpPost = new HttpPost(url);
		FileBody fileBody = new FileBody(file,"image/jpeg");
		MultipartEntity entity = new MultipartEntity();
		entity.addPart(fileName, fileBody);
		httpPost.setEntity(entity);
		httpPost.setHeader("USER_TOKEN",cookie);
		return getResponse(httpPost);
	}
	
	/**
	 * delete ����
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getResponseforDelete(String url) throws ClientProtocolException, IOException{
		if (url == null || url.equals("")) {
			return null;
		}
		HttpDelete httpDelete = new HttpDelete(url);
		return getResponse(httpDelete);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getResponse(HttpUriRequest request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		int statusCode = response.getStatusLine().getStatusCode(); 
		if (HttpStatus.SC_OK == statusCode) {
			String result = EntityUtils.toString(response.getEntity(),"utf-8");
			Log.e("getResponse Error! statusCode == ", String.valueOf(statusCode));
			return result;
		}
		else {
			Log.e("getResponse Error! statusCode == ", String.valueOf(statusCode));
			return null;
		}
	}
}
