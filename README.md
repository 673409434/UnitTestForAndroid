# 测试基础
## 一、组织整理代码便于测试
1、测试驱动的迭代开发——迭代创建和测试代码：
迭代开发某项功能时，您可以先编写一个新测试，也可以将用例和断言添加到现有单元测试。测试最初会失败，因为该功能尚未实现。

务必考虑随着设计新功能而出现的责任单元。对于每个单元，您需要编写相应的单元测试。您的单元测试应几乎囊括与单元的所有可能的互动，包括标准互动、无效输入以及资源不可用的情况。
下图：由测试驱动的迭代开发关联的两个周期。

2、将应用看作一系列模块
为了使您的代码更易于测试，应从模块的角度进行开发，其中每个模块代表用户在您的应用中完成的一项特定任务。这种开发角度与基于堆栈的应用视图（通常包含代表界面、业务逻辑和数据的层）形成对比。

例如，“任务列表”应用可能包含用于创建任务的模块、查看有关已完成任务的统计信息的模块，以及拍摄要与特定任务相关联的照片的模块。这种模块化架构还可以帮助您使不相关的类保持分离，并为在开发团队内分配所有权提供了一个自然的结构。

务必在每个模块周围设置明确定义的界限，并随着应用规模和复杂性的增长而创建新模块。每个模块应只重点关注一个领域，并且用于模块间通信的 API 应保持一致。为更轻松、快捷地测试这些模块间的交互，不妨考虑创建模块的虚假实现。在测试中，一个模块的真实实现可以调用另一个模块的虚假实现。

## 二、AndroidStudio中测试环境
1、测试目录
Android Studio 中的典型项目包含两个用于放置测试的目录。请按以下方式组织整理您的测试：
1. androidTest 目录：包含在真实或虚拟设备上运行的测试。此类测试包括仪器（插桩）单元测试、集成测试、端到端测试，以及仅靠 JVM 无法完成应用功能验证的其他测试。
2. test 目录应包含在本地计算机上运行的测试，如单元测试（本地）。

2、测试环境&利弊
在设备上运行测试时，您可以从以下类型中进行选择：
1. 真实设备
2. 虚拟设备（如 Android Studio 中的模拟器）
3. 模拟设备（如 Robolectric）

真实设备可提供最高的保真度，但运行测试所花费的时间也最多。另一方面，模拟设备可提供较高的测试速度，但代价是保真度较低。不过，平台在二进制资源和逼真的循环程序上的改进使得模拟设备能够产生更逼真的结果。

3、测试case是否使用替身
创建测试时，您可以选择创建真实对象或测试替身，如虚假对象或模拟对象。通常，在测试中使用真实对象比使用测试替身要好，尤其是当被测对象满足以下某个条件时：
1. 该对象是数据对象。
2. 除非与依赖项的真实对象版本通信，否则该对象无法运行。事件回调处理程序就是一个很好的例子。
3. 很难复制该对象与依赖项的通信。SQL 数据库处理程序就是一个很好的例子，其中内存中数据库提供的测试比数据库结果的虚假对象更可靠。

特别是，模拟您并不拥有的类型的实例通常会导致测试很脆弱，只有在您已经了解其他人实现该类型的复杂性时，测试才有效。只在万不得已时才使用此类模拟。您可以模拟自己的对象，但请注意，使用 @Spy 注释的模拟比对类中的所有功能打桩的模拟提供的保真度要高。

不过，如果您的测试尝试对真实对象执行以下类型的操作，最好创建虚假对象甚至是模拟对象：
1. 长时间的操作，如处理大文件。
2. 非封闭型操作，如连接到任意开放端口。
3. 难以创建的配置。

## 三、测试级别分类
1、测试金字塔的级别
测试金字塔（如图）说明了应用应如何包含三类测试（即小型、中型和大型测试）：
1. 小型测试是指单元测试，用于验证应用的行为，一次验证一个类。
2. 中型测试是指集成测试，用于验证模块内堆栈级别之间的互动或相关模块之间的互动。
3. 大型测试是指端到端测试，用于验证跨越了应用的多个模块的用户操作流程。

沿着金字塔逐级向上，从小型测试到大型测试，各类测试的保真度逐级提高，但维护和调试工作所需的执行时间和工作量也逐级增加。因此，您编写的单元测试应多于集成测试，集成测试应多于端到端测试。虽然各类测试的比例可能会因应用的用例不同而异，但我们通常建议各类测试所占比例如下：小型测试占 70%，中型测试占 20%，大型测试占 10%。

2、测试金字塔之“小型测试”
编写的小型测试应该是高度集中的单元测试，能够详尽地验证应用中每个类的功能和约定；

======本地单元测试：尽可能使用 AndroidX Test API，以便您的单元测试可以在设备或模拟器上运行。对于始终在由 JVM 驱动的开发计算机上运行的测试，如果依赖于 Android 框架中的对象，您可以使用 Robolectric。

Robolectric 会模拟 Android 4.1（API 级别 16）或更高版本的运行时环境，并提供由社区维护的虚假对象（称为“影子”）。通过此功能，您可以测试依赖于框架的代码，而无需使用模拟器或模拟对象。Robolectric 支持 Android 平台的以下几个方面：组件生命周期；事件循环；所有资源

======插桩（仪器）单元测试：您可以在物理设备或模拟器上运行插桩单元测试。不过，这种形式的测试所用的执行时间明显多于本地单元测试，因此，最好只有在必须根据实际设备硬件评估应用的行为时才依靠此方法。

运行插桩测试时，AndroidX Test 会使用以下线程：
1. 主线程，也称为“界面线程”或“Activity 线程”，界面交互和 Activity 生命周期事件发生在此线程上。
2. 插桩线程，大多数测试都在此线程上运行。当您的测试套件开始时，AndroidJUnitTest 类将启动此线程。

如果您需要在主线程上执行某个测试，请使用 @UiThreadTest 注释该测试。

3、测试金字塔之“中型测试”
除了通过运行小型测试来测试应用的每个单元之外，您还应从模块级别验证应用的行为。为此，请编写中型测试，即用于验证一组单元的协作和交互的集成测试。

您可以根据应用的结构和以下中型测试示例（按范围递增的顺序）来定义表示应用中的单元组的最佳方式：
1. 视图和视图模型之间的互动，如测试 Fragment 对象、验证布局 XML 或评估 ViewModel 对象的数据绑定逻辑。
2. 应用的代码库层中的测试，验证不同数据源和数据访问对象 (DAO) 是否按预期进行互动。
3. 应用的垂直切片，测试特定屏幕上的互动。此类测试目的在于验证应用堆栈的所有各层的互动。
4. 多 Fragment 测试，评估应用的特定区域。与本列表中提到的其他类型的中型测试不同，这种类型的测试通常需要真实设备，因为被测互动涉及多个界面元素。

4、测试金字塔之“大型测试”
尽管单独测试应用中的每个类和模块很重要，但验证可引导用户使用多个模块和功能的端到端工作流也同样重要。这些类型的测试会在您的代码中形成不可避免的瓶颈，但您可以通过验证尽可能接近实际成品的应用来最大限度地减轻这种影响。

如果您的应用足够小，您可能只需要一套大型测试来评估应用的整体功能。否则，您应按团队所有权、功能垂直领域或用户目标来划分大型测试套件。

## 四、一些基础解释
1、Robolectric：实现了一套JVM能运行的Android代码，单元测试的时候去截取android相关的调用代码，然后转到实现的代码去执行

2、Instrumentation：通过Android系统的Instrumentation测试框架，我们可以编写测试代码，并且打包成APK，运行在Android手机上，逼真但是很慢

3、断言库Truth：https://truth.dev

4、Espresso库：可让您以编程方式且以线程安全的方式找到应用中的界面元素并与之互动。https://developer.android.com/training/testing/espresso

5、AndroidJUnitRunner 类：定义了一个基于插桩的 JUnit 测试运行程序，可让您在 Android 设备上运行 JUnit 3 或 JUnit 4 型测试类。该测试运行程序可帮助您将测试软件包和被测应用加载到设备或模拟器上、运行测试并报告结果。https://developer.android.com/reference/androidx/test/runner/AndroidJUnitRunner

6、Mockito说明
创建一个虚假的类用来替换真实的对象；
1. 验证这个对象的某些方法的调用情况，调用了多少次，参数是什么等等
2. 指定这个对象的某些方法的行为，返回特定的值，或者是执行特定的动作：
构造对象： Mockito.mock(Class<?> class)

Mockito.verify (mock对象).method(arg1,arg2)：验证该方法是否被调用了，同时参数是arg1,arg2，有些时候不需要关注参数，只想知道方法是否给调用了，参数可以设成anyXXX(); 表示任何该XX类型都可以；

Mockito.when(mockObject.targetMethod(args)).thenXXX：注意：void方法不能使用when/thenReturn语法

Mockito.doAnswer(desiredAnswer).when(mockObject).targetMethod(args);：多用于没有返回值方法，desiredAnswe是一个Answer对象，我们要执行什么动作就在这里面实现


## 参考：https://developer.android.com/training/testing/fundamentals





# 构建单元测试
## 一、前言
单元测试通常以可重复的方式运用尽可能小的代码单元（可能是方法、类或组件）的功能。通常会创建下面这些类型的自动化单元测试：

1、本地测试：仅在本地计算机上运行的单元测试。这些测试编译为在 Java 虚拟机 (JVM) 本地运行，以最大限度地缩短执行时间。如果您的测试依赖于 Android 框架中的对象，我们建议您使用 Robolectric。对于依赖于您自己的依赖项的测试，请使用模拟对象来模拟您的依赖项的行为。

2、插桩测试：在 Android 设备或模拟器上运行的单元测试。这些测试可以访问插桩测试信息，如被测应用的 Context。您可以使用此方法来运行具有复杂 Android 依赖项（需要更强大的环境，如 Robolectric）的单元测试。


## 二、构建本地单元测试
当您需要更快地运行测试而不需要与在真实设备上运行测试关联的保真度和置信度时，可以使用本地单元测试来评估应用的逻辑。
对于这种方法，您通常使用 Robolectric 或模拟框架（如 Mockito）来实现依赖项。通常，与测试关联的依赖项类型决定了您使用的工具：
1. 如果您的测试对 Android 框架有依赖性（特别是与框架建立复杂互动的测试），最好使用 Robolectric 添加框架依赖项。
2. 如果您的测试对 Android 框架的依赖性极小，或者如果测试仅取决于您自己的对象，可以使用诸如 Mockito 之类的模拟框架添加模拟依赖项。

1、测试环境
在 Android Studio 项目中，本地单元测试的源文件存储在 module-name/src/test/java/ 中；
如果您的测试需要与 Android 依赖项互动，请添加 Robolectric 或 Mockito 库以简化您的本地单元测试。gradle基础配置:
```
  android {
        // ...
 
 
        //如果您的测试依赖于资源，请在 应用的 build.gradle 文件中启用 includeAndroidResources 选项。然后，您的单元测试可以访问编译版本的资源，从而使测试更快速且更准确地运行。
        testOptions {
            unitTests.includeAndroidResources = true
        }
    }   
 
dependencies {
        // Required（必须） -- JUnit 4 framework
        testImplementation 'junit:junit:4.12'
        // Optional(可选) -- Robolectric environment
        testImplementation 'androidx.test:core:1.0.0'
        // Optional（可选） -- Mockito framework
        testImplementation 'org.mockito:mockito-core:1.10.19'
 
        //其他一些库（选用）
        testImplementation 'org.robolectric:robolectric:' + rootProject.robolectricVersion
        testImplementation 'androidx.test.espresso:espresso-core:' + rootProject.espressoVersion
        testImplementation 'androidx.test.espresso:espresso-intents:' + rootProject.espressoVersion
        //断言库
        testImplementation 'androidx.test.ext:truth:' + rootProject.extTruthVersion
    }
```

## 三、构建插桩（仪器）单元测试
插桩单元测试是在实体设备和模拟器上运行的测试，此类测试可以利用 Android 框架 API 和辅助性 API，如 AndroidX Test。插桩测试提供的保真度比本地单元测试要高，但运行速度要慢得多。因此，我们建议只有在必须针对真实设备的行为进行测试时才使用插桩单元测试。

1、测试环境
在 Android Studio 项目中，您必须将插桩测试的源文件存储在 module-name/src/androidTest/java/ 中

在开始之前，您应先添加 AndroidX Test API，以便为您的应用快速构建和运行插桩测试代码。AndroidX Test 包含 JUnit 4 测试运行程序 (AndroidJUnitRunner) 和用于功能界面测试的 API（Espresso 和 UI Automator）。

您还需要为项目配置 Android 测试依赖项，以使用 AndroidX Test 提供的测试运行程序和规则 API。为了简化测试开发，您还应添加 Hamcrest 库，该库可让您使用 Hamcrest 匹配器 API 创建更灵活的断言。

build.gradle配置依赖：

```
// 如需使用 JUnit 4 测试类，请务必在您的项目中将 AndroidJUnitRunner 指定为默认插桩测试运行程序，方法是在应用的模块级 build.gradle 文件中添加以下设置：
android {
        defaultConfig {
            testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        }
    }
     
 
 
 dependencies {
        androidTestImplementation 'androidx.test.ext:junit:1.1.3'
        androidTestImplementation 'androidx.test.ext:junit-ktx:1.1.3'
 
        androidTestImplementation 'androidx.test:runner:1.1.0'
        androidTestImplementation 'androidx.test:rules:1.1.0'
        androidTestImplementation 'androidx.test:core:1.4.1-alpha03'
        androidTestImplementation 'androidx.test:core-ktx:1.4.1-alpha03'
 
        // Optional(可选) -- UI testing with Espresso
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
 
        // Optional(可选) -- UI testing with UI Automator
        androidTestImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
 
        // Optional(可选) -- Hamcrest library
        androidTestImplementation 'org.hamcrest:hamcrest-library:1.3'
    }
```

## 四、单元测试示例
简单示例：https://developer.android.com/training/testing/unit-testing

官方github示例：https://github.com/android/testing-samples.git

美团实践（版本较老，可做参考）：https://tech.meituan.com/2015/12/24/android-unit-test.html








# UnitTestForAndroid——单元测试Jacoco配置

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
1、根目录build.gradle中添加jacoco插架依赖；
```
dependencies {
...
classpath "org.jacoco:org.jacoco.core:0.8.6"
}
```
2、编写jacoco.gralde脚本。
```
如下：
apply plugin: 'jacoco'
jacoco {
toolVersion = "0.8.6"   //版本号可用最新
}

//定义jacoco命令，命令依赖单侧"testDebugUnitTest"
task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest']  ) {
//step1：定义命令分组等信息
//设置gradle命令分组
group = "Reporting"
//命令描述
description = "Generate Jacoco coverage reports after running tests."


    //step2：定义需要检测覆盖率的目录======根据项目需要修改，需要修改为你的module，并指定到源码======
    def coverageSourceDirs = [
            "${projectDir}/src/main/java"
    ]
    //设置需要检测覆盖率的目录
    sourceDirectories.from = files(coverageSourceDirs)
    //额外挂载依赖Moudle
    additionalSourceDirs.from = files(coverageSourceDirs)
 
 
    //step3：定义检测覆盖率的class所在目录(以项目class所在目录为准)；***** 根据项目需要修改 ******
    //定义不需要检测的文件列表
    def unitTestCoverageExclusions = [
            '**/R.class',
            '**/R$*.class',
            '**/*$ViewInjector*.*',
            '**/*$ViewBinder*.*',
            '**/BuildConfig.*',
            '**/Manifest*.*',
            '**/*Activity.*',
            '**/*Fragment.*',
            '**/*Adapter.*',
            '**/*Dialog.*',
            '**/*View.*',
            '**/*Application.*'
    ]
    // 下面dir需要指定到，编译生成的*.class文件
    def javaClasses = fileTree(dir: "$buildDir/intermediates/javac/debug/classes", excludes: unitTestCoverageExclusions)
    def kotlinClasses = fileTree(dir: "$buildDir/tmp/kotlin-classes/debug", excludes: unitTestCoverageExclusions)
    classDirectories.from = files([javaClasses, kotlinClasses])
 
 
    //step4：APP运行时产生exec报告的路径，报告需要从这里文件解析生成
    executionData.from = fileTree(dir: "$buildDir", includes: ['**/*.exec', '**/*.ec'])
 
 
    //step5：配置生成报告
    reports {
        csv.enabled = false
        xml.enabled = false
        html.enabled = true
    }
}
```
3、要进行单元测试的lib module的build.gradle中添加
```
apply from: "../jacoco.gradle"
```
4、build.gradle文件如下配置可选（根据使用可选）：
```
android {
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
}
因为依赖“testDebugUnitTest”，所以会先执行单侧，在build/reports/tests/testDebugUnitTest/目录下面生成测试报告，同时build/reports/jacoco/jacocoTestReports/目录下面生成覆盖率报告；
```
5、命令运行
执行各个模块中的jacocoTestReport命令，生成本地单元测试报告 和 对应模块的覆盖率报告；
因为jacocoTestReport命令依赖“testDebugUnitTest”，所以会先执行单侧，在build/reports/tests/testDebugUnitTest/目录下面生成测试报告，同时build/reports/jacoco/jacocoTestReports/目录下面生成覆盖率报告；

方案三：UnitTest 本地单元测试——统计APP覆盖率
1、根目录build.gradle中添加jacoco插架依赖；
```
dependencies {
...
classpath "org.jacoco:org.jacoco.core:0.8.6"
}
```
2、编写jacoco.gralde脚本。
```
apply plugin: 'jacoco'

jacoco {
toolVersion = "0.8.6"   //版本号可用最新
}

//定义jacoco命令，命令依赖单侧"testDebugUnitTest"
task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest']  ) {
//step1：定义命令分组等信息
//设置gradle命令分组
group = "Reporting"
//命令描述
description = "Generate Jacoco coverage reports after running tests."


    //定义不需要检测的文件列表
    def unitTestCoverageExclusions = [
            '**/R.class',
            '**/R$*.class',
            '**/*$ViewInjector*.*',
            '**/*$ViewBinder*.*',
            '**/BuildConfig.*',
            '**/Manifest*.*',
            '**/*Activity.*',
            '**/*Fragment.*',
            '**/*Adapter.*',
            '**/*Dialog.*',
            '**/*View.*',
            '**/*Application.*'
    ]
    // 下面dir需要指定到，编译生成的*.class文件
    project.rootProject.allprojects.forEach {
        println("======cur project is ${it.name},   buildDir is $it.buildDir" )
 
        if (it.name.contains("LibTest" ) ) {
            println("======test projects is  ${it.name},   buildDir is $it.buildDir" )
            //step2：定义需要检测覆盖率的目录======根据项目需要修改，需要修改为你的module，并指定到源码======
            sourceDirectories.from.add(files("${projectDir}" + "/src/main/java"))
 
            //step3：定义检测覆盖率的class所在目录(以项目class所在目录为准)；***** 根据项目需要修改 ******
            //kotlinClasses
            classDirectories.from.add(fileTree(dir:"${it.buildDir}" + "/tmp/kotlin-classes/debug", excludes: unitTestCoverageExclusions))
            //javaClasses
            classDirectories.from.add(fileTree(dir:"${it.buildDir}" + "/intermediates/javac/debug/classes", excludes: unitTestCoverageExclusions))
 
        }
    }
 
    //step4：APP运行时产生exec报告的路径，报告需要从这里文件解析生成
    executionData.from = fileTree(dir: "$buildDir", includes: ['**/*.exec', '**/*.ec'])
 
 
    //step5：配置生成报告
    reports {
        csv.enabled = false
        xml.enabled = false
        html.enabled = true
    }
}
```
3、App module的build.gradle中添加
```
apply from: "../jacoco.gradle"
```
4、更改脚本中的if (it.name.contains("LibTest" ) )代码，以指定对应要单侧的moudle。执行jacocoTestReport命令，生成本地单元测试报告 和 对应模块的覆盖率报告；
5、统计整个APP的单侧覆盖率。
1）修改上述if (it.name.contains("LibTest" ) )代码，已经添加所有的module。
2）在工程根目录运行命令
./gradlew createDebugCoverageReport
3）再执行./gradlew jacocoTestReport，就会在build/reports/allReports目录下生成所有工程的单元测试覆盖率报告。


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
