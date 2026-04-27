# 一期 K8s 部署基线

## 目标
- 管理端核心服务基于 K8s 运行
- `kg-gateway`、`kg-auth` 支持多副本
- `kg-system` 先以单副本稳定运行
- `kg-business-kinder` 预留水平扩缩容能力
- 所有服务逐步统一健康探针与优雅停机
- 敏感配置通过 `Secret` 注入

## 资源清单
- `namespace.yaml`
- `configmap.yaml`
- `secret.yaml`
- `gateway-deployment.yaml`
- `gateway-service.yaml`
- `auth-deployment.yaml`
- `auth-service.yaml`
- `system-deployment.yaml`
- `system-service.yaml`
- `business-kinder-deployment.yaml`
- `business-kinder-service.yaml`
- `business-kinder-hpa.yaml`

## 使用步骤
1. 修改 `secret.yaml` 中的密钥和数据库密码
2. 修改各 `Deployment` 中的镜像地址
3. 确认命名空间、配置项和服务暴露方式符合目标环境
4. 执行部署

```bash
kubectl apply -f deploy/k8s/phase1/
```

## 一期副本规划
- `kg-gateway`：2 副本
- `kg-auth`：2 副本
- `kg-system`：1 副本
- `kg-business-kinder`：3 副本，并配置 HPA
