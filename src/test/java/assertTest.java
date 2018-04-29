import com.sun.javaws.exceptions.ErrorCodeResponseException;
import groovy.transform.TailRecursive;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.tools.tree.LessExpression;


import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class assertTest {

    @BeforeClass
    public static void setup(){
        useRelaxedHTTPSValidation();
        RestAssured.baseURI="https://testerhome.com";     //全局baseURI
        RestAssured.proxy("127.0.0.1",8080);   //全局代理
    }

    @Test
    public void testHtml(){


        given().queryParam("q","appium")
                .when()
                    .get("https://testerhome.com/search").prettyPeek()
                .then()
                    .statusCode(200)
                    .body("html.head.title",equalTo("appium · 搜索结果 · TesterHome"))
                ;
    }

    @Test
    public void testTesterHomeJson(){
        given()
            .when()
                .get("https://testerhome.com/api/v3/topics.json").prettyPeek()
            .then()
                .statusCode(200)
                .body("topics.title",hasItems("优质招聘汇总"))
                .body("topics.title[1]",equalTo("优质招聘汇总"))
                .body("topics.id[-1]",equalTo(12241))
                .body("topics.findAll{ topic->topic.id == 10254 }.title",hasItems("优质招聘汇总"))
                .body("topics.find{ topic->topic.id == 10254 }.title",equalTo("优质招聘汇总"))
                .body("topics.title.size()",equalTo(22))
                ;
    }


    @Test
    public void testTesterHomeJsonSingle(){
        given()
            .when()
                .get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
            .then()
                .statusCode(200)
                .body("topic.title",equalTo("优质招聘汇总"))
                ;
    }

    @Test
    public void testTestHomeSearch(){
        given()
            .when()
                .get("https://testerhome.com/")
            .then()
                .statusCode(200)
                ;
    }


    @Test
    public void testXml(){
        given()
                .when()
                .get("http://0.0.0.0:8000/hogwarts.xml").prettyPeek()
                .then()
                .statusCode(200)
                .body("shopping.category.item.name[2]",equalTo("Paper"))
                .body("shopping.category[1].item[1].name[0]",equalTo("Pens"))
                .body("shopping.category.size()",equalTo(3))
                .body("**.find {it.name == 'Chocolate' }.price ", equalTo("10"))
                .body("**.find {it.@type == 'present'}.item.price",equalTo("200"))
                ;
    }

    @Test
    public void testTesterHomeJsonGlobal(){
        given()
                .proxy("127.0.0.1",8080)
                .when()
                .get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
                .then()
                .statusCode(200)
                ;
    }

    @Test
    public void jsonPostTest(){
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("id",6040);
        data.put("title","通过代理安装appium");
        data.put("name","bruce");

        HashMap<String,Object> root = new HashMap<String, Object>();
        root.put("topic",data);

        given()
                .contentType(ContentType.JSON)
                .body(root)
            .when()
                .post("https://www.baidu.com").prettyPeek()
            .then()
                .time(lessThan(1000L))    //毫秒  milliseconds
                ;

    }


    @Test
    public void multiApiMultiData(){


        Response rs= given()
                .when()
                .get("/api/v3/topics/6040.json")
                .then()
                .statusCode(200)
                .extract()
                .response()
                ;

        String name = rs.path("topic.user.name");
        Integer uid = rs.path("topic.user.id");

        System.out.println(name);
        System.out.println(uid);

        given().queryParam("q",name)
                .cookie("name",name)
                .cookie("uid",uid)
                .when()
                .get("/search")
                .then()
                .statusCode(200)
                .body(containsString(name))
                ;


    }

    @Test
    public void testSpec(){
        //创建通用可复用的判断逻辑    一般在该通用方法中判断 1.status code 2.time  3.业务所需要的判断逻辑（比如说验证返回的response中是否包含某些字段）
        ResponseSpecification responseSpecification= new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
        responseSpecification.time(lessThan(1500L));
        responseSpecification.body(not(containsString("error")));

        given().get("/api/v3/topics/6040.json")
                .then().spec(responseSpecification)
                ;
    }


    @Test
    public void testFilter(){
        filters();
    }










}
