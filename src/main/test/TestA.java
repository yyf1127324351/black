import com.back.service.HolidayService;
import com.constant.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
public class TestA {
    private static Logger logger = LoggerFactory.getLogger(TestA.class);

    @Autowired
    HolidayService holidayService;

    @Test
    public void test(){
//        String calculateDate = "2020-05-01";
//        String year = calculateDate.substring(0, 4);
//        String month = Integer.valueOf(calculateDate.substring(5, 7)).toString();
        String aa = "09:00:00";
        aa = aa.substring(0, 5);

        System.out.println("aaa");


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
