/**
 * <br>
 * Package: com.jianq.manager.spm.sm.dao <br>
 * FileName: SupermarketDaoTest.java <br>
 * <br>
 *
 * @version
 * @author hp
 * @created 2017-5-16
 */

package com.jianq.manager.spm.sm.dao;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.SendingContext.RunTime;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Array;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SupermarketDaoTest {

    private static Log log = LogFactory.getLog(SupermarketDaoTest.class);


    @Test
    public void testJfinal() {
        int[] a = {1, 3, 87, 55};
        System.out.println("===================================================");
        try {
            System.out.println(a);
            System.out.println(Arrays.toString(a));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("===================================================");
    }

}
