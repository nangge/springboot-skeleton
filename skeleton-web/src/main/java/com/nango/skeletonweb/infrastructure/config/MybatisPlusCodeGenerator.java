package com.nango.skeletonweb.infrastructure.config;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author nango
 * @Date 2021-09-03 14:38
 * @Description mybatis-plus 代码自动生成
 */
public class MybatisPlusCodeGenerator {

    private final static String MODULE_NAME = "skeleton-web";
    private final static String TEMPLATE_PATH = "/templates/mapper.xml.vm";
    private final static String HOST_IP_ADDRESS = "127.0.0.1";
    private final static String DATABASE = "test";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456";
    private final static String TABLE_PREFIX = "t_";
    private final static String COMMON_SUPER_MODEL = "com.nango.skeletonweb.infrastructure.persistence.BaseModel";
    private final static String[] COMMON_COLUMNS = {"id", "create_time", "modify_time"};

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/" + MODULE_NAME + "/src/main/java");
        globalConfig.setAuthor("nango");
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sDao");
        globalConfig.setServiceImplName("%sDaoImpl");
        globalConfig.setFileOverride(true);  //文件覆盖
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        generator.setGlobalConfig(globalConfig);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + HOST_IP_ADDRESS + ":3306/" + DATABASE + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("模块名"));
        packageConfig.setParent("com.nango.skeletonweb.infrastructure.persistence");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("dao");
        packageConfig.setServiceImpl("dao.impl");
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出-
        focList.add(new FileOutConfig(TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/" + MODULE_NAME + "/src/main/resources/mapper/" + packageConfig.getModuleName() +
                        "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        templateConfig.setController(null);
        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(TABLE_PREFIX);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(COMMON_SUPER_MODEL);
        strategy.setEntityLombokModel(true);
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns(COMMON_COLUMNS);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // strategy.setControllerMappingHyphenStyle(true);
        generator.setStrategy(strategy);
        generator.execute();
    }

}
