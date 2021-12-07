# UnitTestForAndroid
单元测试Jacoco配置

## 一、引入背景
片面追求单元测试的数量，导致编写出大量的重复测试，数量上去了，质量却依然原地踏步。如何来衡量单元测试的质量呢？相比单纯追求单元测试的数量，分析单元测试的代码覆盖率是一种更为可行的方式。
JaCoCo（Java Code Coverage）就是一种分析单元测试覆盖率的工具，使用它运行单元测试后，可以给出代码中哪些代码部分被单元测试测到，哪些部分没有没测到，并且给出整个项目的单元测试覆盖情况百分比，看上去一目了然。

## 二、Jacoco简介
Jacoco是一个开源的测试代码覆盖率的框架，所谓代码覆盖率及在执行手动或自动化用例时同时记录源代码中每一行/没一个分支是否都被执行到了，以此来从一个方面反映测试用例是否覆盖了足够多的逻辑分支。

## 三、Jacoco原理
1、覆盖率测试工具的设计本身是为了检验自动化脚本的覆盖率的，做自动化执行时可以通过选项设置将经过Jacoco插桩的Build包的执行记录记录下来，生成文件，和源码对比后生成覆盖率报告，因此我们先来简单讲讲如何验证自动化脚本的代码覆盖率
2、JaCoCo支持on-the-fly和offline的两种插桩模式：
1）On-the-fly插桩：
JVM中通过-javaagent参数指定特定的jar文件启动Instrumentation的代理程序，代理程序在通过Class Loader装载一个class前判断是否转换修改class文件，将统计代码插入class，测试覆盖率分析可以在JVM执行测试代码的过程中完成。
2）Offline模式：
在测试前先对文件进行插桩，然后生成插过桩的class或jar包，测试插过桩 的class和jar包后，会生成动态覆盖信息到文件，最后统一对覆盖信息进行处理，并生成报告。

## 四、基础配置
1、工程APP的build.gradle配置如下：
```
dependencies {
    ...
    classpath "org.jacoco:org.jacoco.core:0.8.6"
}
```
2、application目录的build.gradle文件修改如下：
```
android {
    defaultConfig {
        。。。
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    。。。
    testOptions {
        unitTests.all {
            jacoco {
                includeNoLocationClasses = true
                jacoco.excludes = ['jdk.internal.*']
            }
        }
//        execution 'ANDROID_TEST_ORCHESTRATOR'
//        animationsDisabled true
//        unitTests {
//            includeAndroidResources = true
//        }
    }
    。。。
    buildTypes {
        debug {
            //如果要在本地生成单元测试覆盖率报告，本参数更改为true ,正常开发则更改为false
            testCoverageEnabled true
        }
    }
}
apply plugin: 'jacoco'
jacoco {
    toolVersion = "0.8.6"   //版本号可用最新
}
 
task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest']  ) {
    //设置gradle命令分组
    group = "Reporting"
    //命令描述
    description = "Generate Jacoco coverage reports after running tests."
 
 
    //配置生成报告
    reports {
        xml.enabled = true
        html.enabled = true
    }
 
 
    //****** 根据项目需要修改 ******
    //定义需要检测覆盖率的目录======需要修改为你的module，并指定到源码======
    def coverageSourceDirs = [
            '../app/src'
    ]
    //设置需要检测覆盖率的目录
    sourceDirectories.from = files(coverageSourceDirs)
//    //额外挂载依赖Moudle
//    additionalSourceDirs.from = files(coverageSourceDirs)
 
 
    //****** 根据项目需要修改 ******
    //定义不需要检测的文件列表
    def unitTestCoverageExclusions = [
            '**/R.class',
            '**/R$*.class',
            '**/*$ViewInjector*.*',
            '**/*$ViewBinder*.*',
            '**/BuildConfig.*',
            '**/Manifest*.*'
//            '**/*Activity.*',
//            '**/*Fragment.*',
//            '**/*Adapter.*',
//            '**/*Dialog.*',
//            '**/*View.*',
//            '**/*Application.*'
    ]
    //定义检测覆盖率的class所在目录(以项目class所在目录为准)；gradle3.2 class所在目录 dir: './build/intermediates/javac/debug/compileDebugJavaWithJavac/classes',
    // 下面dir需要指定到，编译生成的*.class文件
    classDirectories.from = fileTree(dir: '../app/build/intermediates/app_classes/debug/com/example/demo', excludes: unitTestCoverageExclusions)
 
 
    //存储APP运行时产生exec报告的路径
    executionData.from = files("$buildDir/jacoco/testDebugUnitTest.exec")
}
```
3、命令运行
createDebugUnitTestCoverageReport：生成本地单元测试UnitTest测试覆盖率报告
createDebugAndroidTestCoverageReport：生成Android Instrumentation测试覆盖率报告
jacocoTestReport:执行单元测试，生成测试报告；
其他命令略；

4、报告示例：
1）报告位置：

2）报告示例：

## 五、覆盖率
Jacoco从多种角度对代码进行了分析，包括指令（Instructions，C0 Coverage），分支（Branches，C1 Coverage），圈复杂度（Cyclomatic Complexity），行（Lines），方法（Methods），类（Classes）。
1. Instructions
Jacoco计算的最小单位就是字节码指令。指令覆盖率表明了在所有的指令中，哪些被指令过以及哪些没有被执行。这项指数完全独立于源码格式并且在任何情况下有效，不需要类文件的调试信息。
2. Branches
Jacoco对所有的if和switch指令计算了分支覆盖率。这项指标会统计所有的分支数量，并同时支出哪些分支被执行，哪些分支没有被执行。这项指标也在任何情况都有效。异常处理不考虑在分支范围内。
在有调试信息的情况下，分支点可以被映射到源码中的每一行，并且被高亮表示。
3. Cyclomatic Complexity
Jacoco为每个非抽象方法计算圈复杂度，并也会计算每个类，包，组的复杂度。根据McCabe1996的定义，圈复杂度可以理解为覆盖所有的可能情况最少使用的测试用例数。这项参数也在任何情况下有效。
4. Lines
该项指数在有调试信息的情况下计算。因为每一行代码可能会产生若干条字节码指令，所以我们用三种不同状态表示行覆盖率
红色背景：无覆盖，该行的所有指令均无执行；黄色背景：部分覆盖，该行部分指令被执行；绿色背景：全覆盖，该行所有指令被执行。
5. Methods
每一个非抽象方法都至少有一条指令。若一个方法至少被执行了一条指令，就认为它被执行过。因为JaCoco直接对字节码进行操作，所以有些方法没有在源码显示（比如某些构造方法和由编译器自动生成的方法）也会被计入在内
6. Classes
每个类中只要有一个方法被执行，这个类就被认定为被执行。同5一样，有些没有在源码声明的方法被执行，也认定该类被执行
