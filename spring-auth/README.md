# 旨在根据changgou商城 分离出 基于spring security + auth2.0 实现微服务的权限认证服务系统
## 模拟 OAuth2.0 认证环境搭建

- 前提知识
1. JWT
2. auth  
 流程 
1、用户请求认证服务完成认证。

2、认证服务下发用户身份令牌，拥有身份令牌表示身份合法。

3、用户携带令牌请求资源服务，请求资源服务必先经过网关。

4、网关校验用户身份令牌的合法，不合法表示用户没有登录，如果合法则放行继续访问。

5、资源服务获取令牌，根据令牌完成授权。

6、资源服务完成授权则响应资源信息。

Oauth2有以下授权模式：

1.授权码模式（Authorization Code）
2.隐式授权模式（Implicit）
3.密码模式（Resource Owner Password Credentials）
4.客户端模式（Client Credentials）

步骤 
1.pom文件 jar包引入


2.
