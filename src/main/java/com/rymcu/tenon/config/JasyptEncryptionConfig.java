package com.rymcu.tenon.config;

import com.rymcu.tenon.core.constant.ProjectConstant;
import com.rymcu.tenon.util.Utils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2024/4/15 21:18.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.config
 */
@Configuration
public class JasyptEncryptionConfig {

    @Bean(name ="jasyptStringEncryptor")
    public StringEncryptor passwordEncryptor(){
        return Utils.initPasswordEncryptor(System.getenv(ProjectConstant.ENCRYPTION_KEY));
    }

}
