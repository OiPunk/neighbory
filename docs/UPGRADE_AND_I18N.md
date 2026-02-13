# 版本升级与国际化改造说明

## 1. 版本升级点

- Spring Boot: `3.5.10`
- Java: `21`
- Springdoc OpenAPI: `2.8.6`
- Flyway: `11.7.2`
- 测试与覆盖率治理：JaCoCo + JUnit5 + Mockito + AssertJ（100% Line Coverage）

## 2. 升级收益

- 使用当前稳定版本，避免旧教程 API 差异导致学习混乱
- 统一依赖版本，减少冲突
- 开箱支持现代特性（虚拟线程、ProblemDetail）

## 3. 国际化改造方案

- `AcceptHeaderLocaleResolver` 处理语言协商
- `messages*.properties` 管理中英文资源
- 统一业务提示 + 异常提示国际化

## 4. 关键文件

- `src/main/java/io/oipunk/neighbory/config/I18nConfig.java`
- `src/main/java/io/oipunk/neighbory/common/LocaleMessageService.java`
- `src/main/resources/messages_zh_CN.properties`
- `src/main/resources/messages_en_US.properties`

## 5. 请求示例

- `Accept-Language: zh-CN`
- `Accept-Language: en-US`
