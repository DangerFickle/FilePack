# FIlePack

# 批量复制打包文件，使用场景：在所有同学作业都是相同的情况下，根据配置文件批量打包，免去学习委员的苦逼CV操作
# 并重命名为指定application.yml中student属性的各项值，会自动下载打包好的文件，名为success.zip
![image](https://user-images.githubusercontent.com/59493932/202903760-bd7cfd39-88b5-4f23-9acf-2f9d6c68e544.png)

## 使用方式
### 基于SpringBoot
打包为jar包，上传到网站根目录
![image](https://user-images.githubusercontent.com/59493932/203309545-bea883c2-6270-401f-ae46-83b4d5cce78c.png)

application.yml配置文件中的students属性为list,其中的每一项值都会做为生成时的文件名
![image](https://user-images.githubusercontent.com/59493932/203309729-95587b45-6a2b-4bc5-8a5a-2a6270b294bb.png)

程序运行效果，如下：
![image](https://user-images.githubusercontent.com/59493932/203310923-5c08f033-f5be-4ad9-9ff6-9f25a00aab38.png)
