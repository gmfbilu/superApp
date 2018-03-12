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
2. 添加SplashActivity;
3. 添加LoginActivity；
4. 添加MainActivity；

### girls/news组件功能（业务组件）：
1. 这两个组件都是业务组件，根据产品的业务逻辑独立成一个组件；

### common组件功能（功能组件）：
1. common组件是基础库，添加一些公用的类；
2. 例如：网络请求、图片加载、工具类、base类等等；
3. 声明APP需要的uses-permission；
4. 定义全局通用的主题（Theme）；

