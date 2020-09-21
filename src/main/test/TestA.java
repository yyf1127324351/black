import com.alibaba.fastjson.JSONObject;
import com.back.model.Clock;
import com.back.service.HolidayService;
import com.constant.Constants;
import com.utils.DateUtils;
import com.utils.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
public class TestA {
    private static Logger logger = LoggerFactory.getLogger(TestA.class);

    @Autowired
    HolidayService holidayService;

    @Test
    public void test(){

        showSimpleDeal();



//        Clock clock = new Clock();
//        clock.setAttendanceNumber(Long.valueOf(111));
//        clock.setClockInTime("2020-05-01 12:12:00");
//        //打卡时间
//        clock.setClockDateTime("2020-05-01 12:12:00");
//
//
//        clock.setSerialNumber("ssss");
//        clock.setSource(1);
//        clock.setCreateTime(new Date());
//        clock.setUpdateTime(new Date());
//        clock.setSourceDescribe("考勤机序列号-");
//
//        Map<String,Object> map = new HashMap();
//        map.put("clock", JSONObject.toJSONString(clock));
//
//        logger.info("paramJson: {}", JSONObject.toJSONString(clock));
//
//        String result = HttpUtils.post("http://192.168.4.6:8089/api/v1/attendanceClock/handleClock",map);



//        String calculateDate = "2020-05-01";
//        String year = calculateDate.substring(0, 4);
//        String month = Integer.valueOf(calculateDate.substring(5, 7)).toString();
//        String aa = "09:00:00";
//        aa = aa.substring(0, 5);
//        List<Integer> list2 = new ArrayList<>();
//
//        List<Integer> list = new ArrayList<>();
//        list2.addAll(list);
//
//        System.out.println("aaa");


//        BaseResponse baseResponse = holidayService.getHolidayPageList(new Holiday());
//        String redirectUrl = "http://192.168.4.6:8088/hr/goHr?lb_sso_token";
//        String redirectUrl = "http://192.168.4.6:8088/hr/goHr";
//        if (redirectUrl.contains("?")) {
//            redirectUrl = redirectUrl.substring(0, redirectUrl.indexOf("?"));
//        }
//        redirectUrl += ("?" + Constants.TOKEN + "=" + "11111");
//        redirectUrl += ("&" + Constants.TMP_TOKEN + "=" + "22222");
//
//        System.out.println(redirectUrl);

//       String domain = getCookieDomain("https://www.2345.com/");
//        System.out.println(domain);
//        String domain2 = get("https://www.2345.com/");
//        System.out.println(domain2);

    }

    public static void showSimpleDeal() {
        List<String> list1 = new ArrayList<>();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List<String> list2 = new ArrayList<>();
        list2.add("3333");
        list2.add("4444");

        Set<String> list1Set = new HashSet<>(list1);

        Set<String> list2Set = new HashSet<>(list2);

        // 交集
        List<String> intersection = list1.stream().filter(list2Set::contains).collect(toList());
        System.out.println("---得到交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2Set.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1Set.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out::println);

        // 去重并集
        list1Set.addAll(list2Set);
        List<String> listDistinctAll = new ArrayList<>(list1Set);
        System.out.println("---得到去重并集 listDistinctAll---");
        listDistinctAll.parallelStream().forEach(System.out::println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEach(System.out::println);
    }




    public static String getCookieDomain(String url) {
        String PATTERN_L2DOMAIN = "\\w*\\.\\w*:";
        String PATTERN_IP = "(\\d*\\.){3}\\d*";
        /* 以IP形式访问时，返回IP */
        Pattern ipPattern = Pattern.compile(PATTERN_IP);
        Matcher matcher = ipPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }

        /* 以域名访问时，返回二级域名 */
        Pattern pattern = Pattern.compile(PATTERN_L2DOMAIN);
        matcher = pattern.matcher(url);
        if (matcher.find()) {
            System.out.println("[HttpUtil][getCookieDomain] match domain.");
            String domain =  matcher.group();
            /* 裁剪一下是因为连着冒号也匹配进去了，唉~ */
            return domain.substring(0, domain.length() - 1);
        }

        return null;
    }


    public static String get(String domain){
        Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        if (pattern.matcher(domain).matches()) {
            return domain;
        } else {
            return domain.replaceAll(".*\\.(?=.*\\.)", "");
        }
    }
}
