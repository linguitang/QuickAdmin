
#QuickAdmin

QuickAdmin是基于Spring Boot和React.js实现的管理系统开发框架。用于开发网站的后台管理系统。

本框架提供了如下功能：

* 完整的基于Bootstrap的响应式UI界面实现。
* 基于React的常用的管理系统显示组件。
* 前后台间的通信封装。
* 常用的数据库基础操作封装。
* 最小化的用户管理功能
* 常用的简单工具类

本框架中富文本编辑器采用[WangEditor](http://www.wangeditor.com/)实现。是一个国人开源的优秀的轻量级富文本编辑器，希望多多支持。


##框架概述

本框架主要实现前端页面的组件化，通过组合组件实现常见管理系统的大部分功能。同时也充分利用React.js的丰富资源和强大能力。用户可以充分扩展自己的功能。

本框架依赖于以下环境：

- JDK 
- Gradle 
- Node.js
- Webpack

开发环境以Intellij Idea为例，

1. 以Gradle项目引入项目，并执行sync以解决依赖
2. 配置Application及application.properties，配置数据源及监听端口等参数。application.properties是Spring boot的配置文件，具体配置可以参考Spring Boot文档。
3. 使用本项目提供的demo.sql文件初始化数据库。
4. 通过`Application.main()`方法或执行run任务启动服务即可访问。默认用户为admin，密码为admin
5. 不建议通过其它方式为本管理系统添加页面。

开发时推荐按照以下方式进行：

* 在`com.xinou.quickadmin.controller.api`包下为前端提供json接口。应当继承`BaseController`类，并调用响应render方法提供返回。
* 通过修改`com.xinou.quickadmin.controller.AuthIntercepto`类构造方法中传入接口实现不同的用户校验逻辑。
* 通过添加@IngeroCheck注解可以简单地跳过特定请求的登录验证。
* 在`resource/comp`下创建React组件，在`application.js`中配置路由。并使用webpack进行打包，生成的文件为`resources//jsx/main.js``
* 页面中导航组件位于`resources/comp/framework/navbar.js`，通过在application.js中配置json，最多提供两层导航支持。
* 开发组件建议参考现有demo，在`resources/comp/framework/adminUIComponents.js`中提供了常用的数据展示组件。

##开发指南

###后端开发


####Controller

本项目中Controller主要任务是为前端提供json接口。应当继承`com.xinou.quickadmin.controller.BaseController`方法，并调用响应render方法提供返回。


####Service

本项目中service主要为controller提供业务支持。在`com.xinou.quickadmin.service.BaseService`中提供了常见的数据库操作。

####登录验证及跳过

本项目通过拦截器实现登录验证，拦截器为`com.xinou.quickadmin.auth.AuthInterceptor`。

通过`Application.addInterceptors()`方法添加AuthInterceptor的实例，即可实现登录验证。

默认用户校验很简单，可以通过实现`com.xinou.quickadmin.auth.UserAuth`接口来实现更丰富的校验功能。

在Controller中提供的接口上添加`@IngoreCheck`可以跳过此接口的登录验证。

####工具类

IDUtil：用于生成ID，验证码，邀请码等字符串

MD5Util：用于计算MD5值

TextUtil：用于将文本输入中的回车换为`<br/>`换行。

###前端开发

####导航设置

导航配置位于`/resources/comp/application.js`中IndexWrapper.state.navtree参数

主页面左侧导航栏通过一个json数组配置，每个导航包括一个name属性和component属性，分别为导航名称和对应组件的路由。

导航通过React-router的Link组件实现路由跳转。

通过配置`subnav`可以支持最多双层导航。

案例json如下所示：

```
[
    {
        name:"用户管理",//导航名称
        component:"/user/admin"//对应组件路由
    },{
        name:"新闻管理",
        subnav:[//子导航列表
            {
                name:"新闻列表",
                component:"/news/list"
            }

        ]
    }
]
```

在组件中需要跳转，可以使用Link组件，或者通过hashHistory实现跳转。

```hashHistory.push("/news/update/"+item.id);```



####路由设置

组件路由通过React-route实现。配置位于`application.js`中。是标准的React-router用法。

添加一个路由仅需要一行代码：

```
<Route path="user/admin" component={AdminUserList}/>
```

path指定访问路径，component指定加载的组件。

####组件应用

在项目中包含了`user.comp.js`和`news.comp.js`两个案例。用户可以自行仿照添加组件。

`user.comp.js`中的 `AdminUserList` 组件是一个标准的列表视图组件。已经添加了详尽的注释，代码就不再重复粘贴。

####网络请求

在`/resources/comp/framework/commonUtil.js`中，提供了两个基本的网络请求：xget和xpost方法。

在xget和xpost方法中封装了对标准返回数据的基本处理，对于报错会直接处理。所以回调方法的参数仅为data对象。


##组件列表

所有预设的组件源码均位于/resources/comp/framework/adminUIComponents.js文件中。部分组件可能依赖于commonUtils.js中的工具类。


###SmartBox

基本的外包裹框，包括标题栏，面包屑导航和功能按钮。

用法如下：

```
<SmartBox title="标题" index={["导航1级","导航2级"...]} option={{reload:function(){},add:function(){}}}
```

Title参数为一个字符串，显示为标题。

Index参数为面包屑导航，为一个字符串数组，可以有任意多级。

Option为限选项，根据需要自行选择添加。未指定的则不会显示在右上角。reload为刷新按钮，add为加号按钮。

###DataTable

基本的数据表格。

用法如下：

```
<DataTable index={[{name:"列名",col:"字段名"}]} data={[{k:v,k2:v2},{k:v,k2:v3}]} buttons={{name:"按钮文字",onclick:function(item){点击事件}}}/>
```

Index：列名与数据字段的映射。表头由列名组成，根据对应的字段名在数据中查找数据填充表格。col参数可以对应字段名，也可以对应一个方法，将方法的返回值用来填充表格。

比如用于显示一个图片：

```
{name:"image",col:function(item){return <img src={item.image} />}
```

Data：数据列表。一般是通过Ajax从服务端请求得来的列表。

Buttons：最后一列的功能按钮，传入参数item为对应此列的数据对象。可以做任何操作。

###Pager

分页按钮

用法如下：

```
<Pager pagecount={12} last={1} callback={function(page){加载数据}} />
```

Pagecount：总计页数。

Last：当前页数。

以上两个参数在标准的页面接口返回信息中可以找到。一般存储在state中以供使用。

callback：点击选择页数之后加载数据的接口。page参数为用户选中的页数。可以在此通过Ajax请求数据。

###ImageUpdate

一个可以预览的图片上传组件。通过formData实现异步上传，需要提供文件上传接口。也可自行改写此组件，使用OSS等第三方文件上传组件。

用法如下：

```
<ImageUpdate title="标题" action = "/common/imgupload" url={"http://img.url"} onChange={function(url){this.setState({url:url}}}/>
```

Title：此行输入的标题

Action：上传图片的接口URL,此接口上传文件字段为file，返回值应为图片URL，可以根据自己的参数自行改写。

Url：显示图片的URL。

onChange：当选中图片后的回调方法，在回调方法中必须重新指定URL参数以实现显示图片的效果。

###SingleButtonImageUploader

类似于ImageUpdate的组件，不提供预览，仅有一个按钮。

用法如下：


```
<SingleButtonImageUploader title="标题" action = "/common/imgupload" 
    url={"http://img.url"} onChange={function(url){this.setState({url:url}}} auto=false/>
```

Title：此行输入的标题

Action：上传图片的接口URL,此接口上传文件字段为file，返回值应为图片URL，可以根据自己的参数自行改写。

Url：显示图片的URL。

onChange：当选中图片后的回调方法。

auto：是否支持重复上传，设为auto后，当上传成功后文字仍为title，否则为上传成功。

###Modal

基于Bootstrap模态框的弹出框。可以作为容器与其它组件配合使用。

用法如下：

```
<Modal id="id" title="标题" btns={[{title:"按钮",onclick:function(){...}}]}><div>里面随便写</div></Modal>
```

弹出框带有一个标题和底部按钮栏。自带有关闭按钮。

可以通过Bootstrap的调用方式：$("#id").modal("show")和$("#id").modal("hidden")来控制模态框的开关。

title： 标题

id：唯一的id，用于控制此模态框

btns：在按钮列表中可以添加其余按钮。

###SelectableInput

基于html5的datalist实现的输入搜索选框，类似于12306的地址选择功能。

用法如下：

```
<SelectableInput pkey="pkey" title="标题"  data={[{k:v},{kv:vk}]} onChange={function(value){...}} inputable=true/>
```

pkey：唯一key，用于对应datalist于input

title：标题

data：数据数据，也可以使用另一种方式：dataurl，输入一个url，然后会向此url请求参数。此url也应当使用标准接口。

onChange: 输入数据后的回调方法

inputable：是否接受用户自定义输入。为true时接受。否则不接受在data之外的输入。

###SearchLabel

支持选择条件的搜索框，仅允许同时一个条件进行搜索。

用法如下：

```
<SearchLabel searchKeys={[{key:"name",name:"姓名"},{key:"age",name:"年龄"]} loadPage={function(queryString){...}}/>
```

searchKeys：用于搜索的条件列表,key表示实际上传的字段，name是用于显示的名称

loadPage:用于加载数据的回调函数。queryString是可以直接拼接在url后的参数列表。包括searchType和searchData两个参数。分别为搜索的key和数据。











