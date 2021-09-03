package com.nango.skeletonweb.infrastructure.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wyn
 * @package com.nango.skeletonweb.infrastructure.config
 * @class DruidConfig
 * @date 2021/9/3 14:24
 * @description druid 配置
 *
 */
@Configuration
public class DruidConfig {
    /**
     * 配置Druid的监控
     * 配置管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        //druid后台登录的用户名
        initParams.put("loginUsername","admin");
        //druid后台登录的密码
        initParams.put("loginPassword","nango");
        //默认就是允许所有访问
        initParams.put("allow","");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置监控的filter
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }
}
