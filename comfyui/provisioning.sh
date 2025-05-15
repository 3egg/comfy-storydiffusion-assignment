printf "=========================================================\n"

cd /opt/ComfyUI/custom_nodes
git clone https://github.com/smthemex/ComfyUI_StoryDiffusion.git
pip config set global.index-url https://mirrors.tuna.tsinghua.edu.cn/pypi/web/simple

cd /opt/ComfyUI/custom_nodes/ComfyUI_StoryDiffusion
pip install -r requirements.txt

cp checkpoints/juggernautXL_v8Rundiffusion.safetensors opt/ComfyUI/models/checkpoints
printf "=========================================================\n"