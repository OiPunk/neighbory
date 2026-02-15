# Contributing to Neighbory

感谢你愿意参与 `Neighbory` 的建设。

## 目标

本项目是 Spring Boot 新手练手工程，同时按开源项目标准维护。请确保你的提交满足：

- 对新手友好（可读、可运行、可解释）
- 不破坏现有 API 行为（除非明确说明）
- `./mvnw clean verify` 必须通过
- 覆盖率门禁（JaCoCo 100% line coverage）不被破坏

## 开发环境

- JDK 21+
- Git
- Maven Wrapper（本仓库内置）

## 提交流程

1. Fork 并新建分支（建议：`feat/*`、`fix/*`、`docs/*`）
2. 编写代码与测试
3. 本地执行：
   - macOS/Linux: `./mvnw clean verify`
   - Windows: `.\\mvnw.cmd clean verify`
4. 更新相关文档（README / docs）
5. 发起 Pull Request

## Commit 建议

建议使用 Conventional Commits：

- `feat:` 新功能
- `fix:` 缺陷修复
- `docs:` 文档更新
- `refactor:` 重构
- `test:` 测试改动
- `chore:` 构建、依赖、脚手架变更

示例：

- `feat(estate): add estate query by manager id`
- `fix(i18n): fallback to default locale when header invalid`

## Pull Request 要求

请在 PR 描述中至少包含：

- 变更目的
- 主要改动点
- 测试结果（命令和结果摘要）
- 是否包含 breaking change
- 文档是否已同步更新

## 文档与代码风格

- 保持分层清晰：`controller -> service -> repository`
- 避免把业务逻辑写在 Controller
- 公共行为变更必须补充测试
- 关键非直观逻辑请加简洁注释

## 问题反馈

- 功能建议：使用 Feature Request 模板
- 缺陷反馈：使用 Bug Report 模板
- 安全问题：请不要公开提交 Issue，参见 `SECURITY.md`
