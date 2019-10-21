[![author](https://img.shields.io/badge/author-hglf-blue.svg)](https://github.com/hotstu)
[![Download](https://api.bintray.com/packages/hglf/maven/Lame4Droid/images/download.svg) ](https://bintray.com/hglf/maven/Lame4Droid/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

 Lame4Droid
=====================

mp3转码库，用于将录音转换为mp3格式
支持：一边录音一边转换直接输出mp3

Android自带的录音功能只能输出amr格式，网上有很多库转mp3的库，转了一圈发现存在各种不足

1. 没有使用cmake

2. 编码质量不行

3. 没有上传jcenter，不方便引用

4. 年久失修


这个库的特色

1. androidx

2. 采用生产者/消费者 + 装饰器设计模式重构代码，思路清晰

3. 上传jcenter库，直接引用，不需要多余配置
