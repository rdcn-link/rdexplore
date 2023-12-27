# rdexplore-ui

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```
## 打包成软件包
安装依赖
```sh
$ npm install electron-builder
$ npm install electron-package
```
执行打包命令：
```sh
electron-bulider
```

常用打包命令
```sh
npm run packager
```
配置package.json文件
```json
"scripts": {
   "package-mac": "electron-packager . Path --platform=darwin --electron-version=25.3.1 --download.mirrorOptions.mirror=https://npm.taobao.org/mirrors/electron/ --out=dist --icon=icons/icon.icns",
   "package-win": "electron-packager . helloworld --out dist --arch=x64 --electron-version=25.4.0 --download.mirrorOptions.mirror=https://npm.taobao.org/mirrors/electron/ --overwrite --ignore=node_modules --icon=icons/icon.icns",
}
```

- platform：设置平台，window，linux还是Mac,darwin:代表的是mac环境，win32:代表win平台
- arch：x84还是x64，
- Path的位置可以替换成你打包后的exe名字,这里就会生成Path.exe文件。
- electron-version：electron的版本，必须要指定，这里设置为25.3.1，可查看package.json中electron安装的版本号
- out 输出文件
- icon 图标地址