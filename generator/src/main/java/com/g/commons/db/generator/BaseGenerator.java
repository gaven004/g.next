package com.g.commons.db.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author zhongsh
 * @version 2017/7/13
 * @since 1.0
 */
public abstract class BaseGenerator {

	// 公共设置
	private static final String parent = "com.g"; // 自定义包路径
	private static final String dbUrl = "jdbc:mysql://localhost:3306/next?characterEncoding=utf8"; // 数据库
	private static final String dbUsername = "root"; // 数据库用户名
	private static final String dbPassword = "abcde12345"; // 数据库密码

	public void generate() {
		// 代码生成器
		final AutoGenerator mpg = new AutoGenerator();
		mpg.setGlobalConfig(
				// 全局配置
				new GlobalConfig()
						.setOutputDir(getBaseDir() + "\\src\\main\\java")//输出目录
						.setFileOverride(true)// 是否覆盖文件
						.setActiveRecord(false)// 开启 activeRecord 模式
						.setEnableCache(isEnableCache())// XML 二级缓存
						.setBaseResultMap(true)// XML ResultMap
						.setBaseColumnList(true)// XML columList
						.setAuthor(getAuthor())
						// 自定义文件命名，注意 %s 会自动填充表实体属性！
						.setMapperName("%sMapper")
						.setXmlName("%sMapper")
						// .setServiceName("%sService")
				        // .setServiceImplName("%sServiceImpl")
						.setServiceImplName("%sService") // 不使用service接口
						.setControllerName("%sController")
		).setDataSource(
				// 数据源配置
				new DataSourceConfig()
						.setDbType(DbType.MYSQL)// 数据库类型
						.setTypeConvert(new SqlTypeConvert() {
							// 自定义数据库表字段类型转换【可选】
							@Override
							public DbColumnType processTypeConvert(String fieldType) {
								return super.processTypeConvert(fieldType);
							}
						})
						.setDriverName("com.mysql.jdbc.Driver")
						.setUsername(getDbUsername())
						.setPassword(getDbPassword())
						.setUrl(getDbUrl())
		).setStrategy(
				// 策略配置
				new StrategyConfig()
						// .setCapitalMode(true)// 全局大写命名
						// .setDbColumnUnderline(true)//全局下划线命名
						// .setTablePrefix(new String[]{"bmd_", "mp_"})// 此处可以修改为您的表前缀
						.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
						.setInclude(getIncludeTable()) // 需要生成的表
						// .setExclude(new String[]{"test"}) // 排除生成的表
						// 自定义实体父类
						// .setSuperEntityClass("com.baomidou.demo.TestEntity")
						// 自定义实体，公共字段
						// .setSuperEntityColumns(new String[]{"test_id"})
						.setTableFillList(tableFillList())
						// 自定义 mapper 父类
						// .setSuperMapperClass("com.baomidou.demo.TestMapper")
						// 自定义 service 父类
						// .setSuperServiceClass("com.baomidou.demo.TestService")
						// 自定义 service 实现类父类
						// .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
						// 自定义 controller 父类
						.setSuperControllerClass("com.g.commons.controller.GeneralController")
						// 【实体】是否生成字段常量（默认 false）
						// public static final String ID = "test_id";
						.setEntityColumnConstant(true)
						// 【实体】是否为构建者模型（默认 false）
						// public User setName(String name) {this.name = name; return this;}
						.setEntityBuilderModel(true)
						// 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
						// .setEntityLombokModel(true)
						// Boolean类型字段是否移除is前缀处理
						// .setEntityBooleanColumnRemoveIsPrefix(true)
						// .setRestControllerStyle(true)
						.setControllerMappingHyphenStyle(true)
		).setPackageInfo(
				// 包配置
				new PackageConfig()
						.setModuleName(getModuleName())
						.setParent(getParent())// 自定义包路径
						.setController("controller")// 这里是控制器包名，默认 web
						.setEntity("model")
						.setServiceImpl("service") // 不使用service接口
		).setTemplate(
				// 关闭默认 xml 生成，调整生成 至 根目录
				new TemplateConfig().setXml(null)
						// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
						// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
						.setEntity(isGenerateModel() ? ConstVal.TEMPLATE_ENTITY_JAVA : null)
						.setMapper(isGenerateMapper() ? ConstVal.TEMPLATE_MAPPER : null)
						.setController(isGenerateController() ? ConstVal.TEMPLATE_CONTROLLER : null)
						// .setService(isGenerateService() ? ConstVal.TEMPLATE_SERVICE : null)
						.setService(null) // 不使用service接口
						.setServiceImpl(isGenerateService() ? ConstVal.TEMPLATE_SERVICEIMPL : null)
		);
		if (isGenerateXml()) {
			mpg.setCfg(
					// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
					new InjectionConfig() {
						@Override
						public void initMap() {
							Map<String, Object> map = new HashMap<>();
//							map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
							map.put("isGenerateXml", true);
							this.setMap(map);
						}
					}.setFileOutConfigList(Arrays.<FileOutConfig>asList(new FileOutConfig("/templates/mapper.xml.vm") {
						// 自定义输出文件目录
						@Override
						public String outputFile(TableInfo tableInfo) {
							PackageConfig packageInfo = mpg.getPackageInfo();
							StringBuilder sb = new StringBuilder();
							sb.append(getBaseDir()).append("\\src\\main\\resources\\");
							if (packageInfo.getParent() != null) {
								sb.append(packageInfo.getParent().replace(".", "\\"));
							}
							sb.append("\\mapper\\").append(tableInfo.getEntityName()).append("Mapper.xml");
							return sb.toString();
						}
					}))
			);
		}

		// 执行生成
		mpg.execute();

		// 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
//		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

	/**
	 * 是否使用二级缓存
	 */
	public boolean isEnableCache() {
		return true;
	}

	/**
	 * 自定义包路径
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * 数据库
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * 数据库用户名
	 */
	public String getDbUsername() {
		return dbUsername;
	}

	/**
	 * 数据库密码
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * 作者，@author xxx
	 */
	public abstract String getAuthor();

	/**
	 * 模块根目录
	 */
	public abstract String getBaseDir();

	/**
	 * 模块名
	 */
	public abstract String getModuleName();

	/**
	 * 需要生成的表，null表示全部表
	 */
	public abstract String[] getIncludeTable();

	/**
	 * 是否生成model
	 */
	public abstract boolean isGenerateModel();

	/**
	 * 是否生成Mapper
	 */
	public abstract boolean isGenerateMapper();

	/**
	 * 是否生成Xml
	 */
	public abstract boolean isGenerateXml();

	/**
	 * 是否生成Service
	 */
	public abstract boolean isGenerateService();

	/**
	 * 是否生成Controller
	 */
	public abstract boolean isGenerateController();

	/**
	 * 自定义需要填充的字段
	 */
	public List<TableFill> tableFillList() {
		return null;
	}
}
