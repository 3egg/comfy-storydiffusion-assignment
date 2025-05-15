# 我们将使用这个经过验证的 comfyui 镜像作为基础
FROM ghcr.io/ai-dock/comfyui:v2-cuda-12.1.1-base-22.04-v0.2.7

# 禁用基础镜像提供的身份验证
ENV WEB_ENABLE_AUTH=false

# 现在我们将模型复制到镜像中
ENV MODEL_DIR=/opt/ComfyUI/models
COPY checkpoints/juggernautXL_v8Rundiffusion.safetensors ${MODEL_DIR}/checkpoints/juggernautXL_v8Rundiffusion.safetensors

# 启动前脚本
COPY provisioning.sh /opt/ai-dock/bin/provisioning.sh

# 我们还需要将 comfyui-api 二进制文件复制到镜像中，因为 ComfyUI 默认完全异步，没有方便的方式来获取生成的图像
ADD https://github.com/SaladTechnologies/comfyui-api/releases/download/1.6.1/comfyui-api .
RUN chmod +x comfyui-api

# 可选：通过在启动服务器之前运行工作流来预热服务器
# comfyui-api 支持预热模式，它将在启动服务器之前运行提供的工作流
# 此示例假设你在 Dockerfile 所在的目录中有一个工作流 json 文件
COPY api.json .
ENV WARMUP_PROMPT_FILE=api.json

CMD ["./comfyui-api"]