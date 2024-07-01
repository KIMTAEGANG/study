package com.study;

import com.study.common.config.JasyptConfig;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(classes = JasyptConfig.class)
public class JasyptTest {

    final BeanFactory beanFactory = new AnnotationConfigApplicationContext(JasyptConfig.class);
    final StringEncryptor stringEncryptor = beanFactory.getBean("jasyptStringEncryptor", StringEncryptor.class);
    @Test
    public void jasypt_decrypt_test() {
        String keyword = "";
        String enc = stringEncryptor.encrypt(keyword);
        String des = stringEncryptor.decrypt(enc);
        assertThat(keyword).isEqualTo(des);

        System.out.println("enc>>>>>>>>>>>>>>>>>>>>>" + enc);

    }
}
