package com.tian.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * 自己定义插件的主要逻辑入口
 * @Mojo 标识这是一个插件, name表示指定插件的名字, 一般习惯是第一部分自己定义,后面的-maven-plugin为固定写法
 *        defaultPhase 表示当使用方在没有显示指定插件的执行周期时,默认的执行时机
 *        extends AbstractMojo 固定写法, 要继承这个类, 然后实现execute方法
 *        命令执行过程中的输出直接用System.out.println
 *        @Parameter 为插件执行时可以传的参数, property = "path" 指定了参数名
 *
 *
 * 插件的使用方如下配置:
<plugin>
<groupId>com.tian</groupId>
<artifactId>count-maven-plugin</artifactId>
<version>1.0-SNAPSHOT</version>
<configuration>
<path>E:\project\springmvcmybatis</path>
</configuration>
<executions>
<execution>
<phase>package</phase>
<goals>
<goal>count-maven-plugin</goal>
</goals>
</execution>
</executions>
</plugin>
 *
 *
 * Created by tianxiong on 2019/11/1.
 */
@Mojo(name = "count-maven-plugin", defaultPhase = LifecyclePhase.PACKAGE)
public class CountFileMojo extends AbstractMojo {
    @Parameter(property = "path")
    private String path;
    int num1 = 0;   //总文件数量
    int num2 = 0;   //目录数量
    int num3 = 0;   //java文件数量

    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println(path);
        System.out.println(countFile(path));
    }

    public String countFile(String dir) {
   File f=new File(dir);
   File fs[]=f.listFiles();


   if (fs != null) {
     for (int i = 0;
            i<fs.length ;
            i++){
       File currenFile=fs[i];
       if (currenFile.isFile()) {
         num1 += 1; //如果是文件就加1
         String fileName=currenFile.getName();
         String suffix=fileName.substring(fileName.lastIndexOf(".") +1);
         if (suffix.equals("java")) {
           num3 += 1;
         }
       } else {
          //否则就是目录
         num2 += 1;
         countFile(currenFile.getAbsolutePath());
       }
     }
   }
   return "TotalnumberofFile:" + num1 + "||||Thenumberofdiris:" + num2 + "||||TotalnumberofJavaFile:" + num3;
 }
}
