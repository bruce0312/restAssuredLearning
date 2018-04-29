
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

public class test1 {

    @Test
    public void gettest(){

//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("limit",2);
//        map.put("offset","0");
//        map.put("type","last_actived");
//        get("https://testerhome.com/api/v3/topics.json?{limit}&{offset}&{type}",map).prettyPeek();

//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("topics","topics");
//        map.put("topicid",12912);
//        get("https://testerhome.com/{topics}/{topicid}",map).prettyPeek();

//        given().param("user[login]","china-sjm@qq.com").param("user[password]","Sjm19950312").param("user[remember_me]",1)
//        .post("https://testerhome.com/account/sign_in").prettyPeek();
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("user[login]","china-sjm@qq.com");
//        map.put("user[password]","Sjm19950312");
//        map.put("user[remember_me]","1");
//        given().param(String.valueOf(map)).post("https://testerhome.com/account/sign_in").prettyPeek();

//        given().urlEncodingEnabled(true).param("username","社区").param("userpassword","xxxxx")
//                .post("https://testerhome.com/account/sign_in").prettyPeek();

        //multiPart()  向接口上传文件的方法
//        File file = new File("/Users/bruce/Desktop/burp_cert.cer");
//        given().multiPart(file).post("https://testerhome.com/account/sign_in");

    }

    @Test
    public void jsonpathTest(){
        Response response = get("https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived");
        List<Object> list = response.jsonPath().getList("topics");
        System.out.println(list.size());
        System.out.println(response.jsonPath().getString("topics[0].user.id"));

    }

    public static void main(String[] args){

    }
}
