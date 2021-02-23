package com.stevenwan.svlas;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.stevenwan.svlas.common.BaseEntity;

/**
 * @author steven-wan
 * @desc
 * @date 2021-01-28 17:24
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器 基本配置 6 个参数就可以了：dataSource，strategy，packageInfo，template，globalConfig，injectionConfig
        AutoGenerator autoGenerator = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://61.164.39.69:41523/stock_steven_wan_dev?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("jbF2019_13006");
        autoGenerator.setDataSource(dsc);

        //数据库表配置策略
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //父类的相关信息
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setSuperEntityColumns("id");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        //具体的表
        strategy.setInclude(new String[]{"quartz_scheduler_jobs"});
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.stevenwan.svlas");
        autoGenerator.setPackageInfo(pc);


        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("steven.wan");
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setOpen(false);
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.ONLY_DATE);
        autoGenerator.setGlobalConfig(gc);

        // template 沿用默认的模板以及引擎

        //injectionConfig
        //fileOutConfigList 配置 FileOutConfig 指定模板文件、输出文件达到自定义文件生成目的
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        autoGenerator.setCfg(cfg);

        autoGenerator.execute();

    }
}
