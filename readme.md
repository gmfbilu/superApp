## 集成开发模式和组件开发模式转换

**1、首先打开Android项目的 gradle.properties 文件，然后将 isModule 改为你需要的开发模式（true/false），
然后点击 "Sync Project" 按钮同步项目；**


## 组件功能介绍

### app组件功能（空壳工程）：
1. 配置整个项目的Gradle脚本，例如 混淆、签名等；
2. app组件中可以初始化全局的库，例如Lib.init(this);
3. 添加 multiDex 功能
4. 业务组件管理（组装）；

### main组件功能（业务组件）：
1. 声明应用的launcherActivity----->android.intent.category.LAUNCHER；

### common组件功能（功能组件）：
1. common组件是基础库，添加一些公用的类；
2. 例如：网络请求、图片加载、工具类、base类等等；
3. 声明APP需要的uses-permission；
4. 定义全局通用的主题（Theme）；

## 打包
1. 打包前先建一个当前版本的分支，热更新修复bug就在该分支上修改
2. 打开Gradle projects 下:app下Tasks下build，打assembleRelease包
3. 经过上述步骤在build文件夹下生成TinkerbakApk文件夹，TinkerbakApk是基准包，此文件夹要上传或者保存，以备出现bug热更新。将此包中的apk用加固重签名工具加固重签名，然后将此包重命名后放在app/baseApk下
4. 然后执行Tasks下channel下的reBuildRelease，在build/reBuildChannel下生成渠道包
4. 需要打patch包的时候，新建分支修改bug，然后把TinkerbakApk复制到build目录下，修改baseApkDir，然后进入到:app下tinker-support下打buildTinkerPatchRelease包，然后在build/outputs/patch/release下生成patch_signed_7zip包，将此包上传到平台上发布

