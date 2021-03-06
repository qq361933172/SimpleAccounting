# 拾记 SimpleAccounting

[![Build Status](https://travis-ci.com/SkywalkerDarren/SimpleAccounting.svg?branch=master)](https://travis-ci.com/SkywalkerDarren/SimpleAccounting)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/github/license/SkywalkerDarren/SimpleAccounting)](https://img.shields.io/github/license/SkywalkerDarren/SimpleAccounting)

一个能养成记账习惯的记账本。清爽无广告，不搞花里胡哨的纯粹记账本

## 上手指南

直接安装地址：[下载地址](https://github.com/SkywalkerDarren/SimpleAccounting/releases)

截图如图：
![](example/main.gif)
![](example/bill.gif)
![](example/billdetail.gif)
![](example/detail.gif)
![](example/discovery.png)
![](example/edit.png)
![](example/classify.png)
![](example/journal.png)

如需自己编译请继续往下看

### 准备工作

编译需要的版本如下：

* jdk 1.8
* kotlin 1.3.61
* platforms android-29
* buildTools 29.0.2

### 安装

开始编译：

```bash
./gradlew assembleDebug
```

编译后的文件在 `app/build/outputs/apk/release/app-debug.apk`

## 使用框架

项目架构：

- ~~MVC~~ 已重构
- MVVM

使用算法：

- **双栈算法** 计算表达式求值
- **FSM有限状态机** 计算表达式合法性检测

基础组件：

- ~~android.support~~ 已迁移
- androidx
- kotlin协程

jetpack组件：

* Preference
* ~~SQLiteDatabase~~ 改用框架
* Room 持久化框架
* DataBinding 数据绑定
* ViewModel 具有生命周期感知的数据绑定库
* LiveData 具有生命周期感知的数据存储器
* LifeCycle 生命周期管理框架
* WorkManager 任务调度组件

视图组件：

* Material MD设计组件
* RecyclerView
* ConstraintLayout
* CardView
* VectorDrawable

第三方组件：

* [Apache Commons CSV](http://commons.apache.org/proper/commons-csv/) csv格式化工具
* [Gson](https://github.com/google/gson) json转换工具
* [OkHttp](https://square.github.io/okhttp/) 网络请求库
* [Retrofit2](https://square.github.io/retrofit/) RESTful网络请求框架
* [Glide](https://github.com/bumptech/glide) 异步图片加载库
* [Joda Time](https://www.joda.org/joda-time/) 时间库
* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) 图表库
* [Segmented Button](https://github.com/addisonElliott/SegmentedButton) 分段按钮库
* [BRVAH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) adapter辅助库
* [Pinned Section Item Decoration](https://github.com/oubowu/PinnedSectionItemDecoration) recyclerview吸顶装饰器

测试框架：

* Junit4 一般单元测试库
* Espresso 视图单元测试库
* AndroidJunit 安卓单元测试库
* [Mocktio](https://site.mockito.org/) Mock库

## 版本管理

使用 [SemVer](https://semver.org/lang/zh-CN/) 作语义化版本。 所有的版本可在这里获得，[仓库TAG](https://github.com/SkywalkerDarren/SimpleAccounting/tags). 

## License

该项目使用MIT协议发布 - 见[LICENSE.md](LICENSE.md)获取更多细节

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## 已知问题

有些按钮没有反应，是因为功能还没做

若有其他bug请提Issue
