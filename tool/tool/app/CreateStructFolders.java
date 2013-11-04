package tool.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import tool.utils.IOUtils;
import framework.base.utils.DateUtil;
import framework.base.utils.ReflectUtil;

/**
 * 构造三层框架文件夹及文件
 *
 * @author hjin
 * @cratedate 2013-9-14 下午7:02:33
 */
public class CreateStructFolders
{
    private final String newline = "\r\n";

    /**
     * 模块包."."分隔
     */
    private String module_pkg;
    /**
     * 模块包."/"分隔
     */
    private String pkgModuleFolder;
    /**
     * mybatis mapper包名."."分隔
     */
    private String module_mapper_pkg;
    /**
     * mybatis mapper包名."/"分隔
     */
    private String pkgMapperFolder;
    /**
     * 模块名
     */
    private String module;
    /**
     * 模块名,首字母大写
     */
    private String module_cap;
    /**
     * 模块名,首字母小写
     */
    private String module_uncap;
    /**
     * 模块名,全小写
     */
    private String module_lower;
    /**
     * 作者
     */
    private String author;
    /**
     * 生成日期
     */
    private String createdate;

    public static void main(String[] args) throws Exception
    {
    	
    }

    public CreateStructFolders()
    {

    }

    /**
     * 构造器
     *
     * @param pkgModule
     * @param pkgMapper
     * @param module
     * @param author
     */
    public CreateStructFolders(String pkgModule, String pkgMapper,
                               String module, String author)
    {
        this.module_pkg = pkgModule;
        pkgModuleFolder = pkgModule.replace(".", "/");

        this.module_mapper_pkg = pkgMapper;
        pkgMapperFolder = pkgMapper.replace(".", "/");

        this.module = module;
        module_cap = StringUtils.capitalize(module);
        module_uncap = StringUtils.uncapitalize(module);
        module_lower = module.toLowerCase();

        this.author = author;

        this.createdate = DateUtil.formatDate(new Date());
    }

    public void execute() throws Exception
    {
        // 当前项目目录
        // F:\!job\svn\files\iispace\iiSpaceTeam\iiSpace0.1_code\framework
        //F:\svn\iispace\iiSpaceTeam\iiSpace0.1_code\framework
        String dir = "F:\\svn\\iispace\\iiSpaceTeam\\iiSpace0.1_code\\framework";//System.getProperty("user.dir");
        // 模板路径
        String tplDir = dir + "/templet/[module_lower]";
        // 模板文件夹
        File src = new File(tplDir);
        // 复制到此路径
        File dest = new File(dir + "/templet/temp/" + module_lower);
        // 父路径存在,则删除
        if (dest.exists())
        {
            dest.delete();
        }
        // 复制模板文件夹
        FileUtils.copyDirectory(src, dest);

        // 处理复制好的文件夹
        invokeFolder(dest);
    }

    /**
     * 遍历文件夹
     *
     * @author hjin
     * @cratedate 2013-9-14 下午7:56:20
     */
    public void invokeFolder(File parent) throws Exception
    {
        if (parent.isDirectory())
        {
            File[] children = parent.listFiles();

            for (File child : children)
            {
                if (child.isFile())
                {
                    // 正则解析替换所有表达式
                    createJavaFile(child);
                }
                else
                {
                    invokeFolder(child);
                }
            }
        }
    }

    /**
     * 根据模板tpl创建java文件
     *
     * @param file
     * @author hjin
     * @cratedate 2013-9-14 下午8:00:05
     */
    public void createJavaFile(File file) throws Exception
    {
        String fullPath = file.getAbsolutePath();

        // 创建java
        if (fullPath.endsWith(".tpl"))
        {
            // 换掉占位表达式
            String javaFullPath = doRegex(fullPath);
            // 改后缀名,.java
            String newFilePath = javaFullPath.substring(0,
                    javaFullPath.lastIndexOf("."))
                    + ".java";
            // java文件
            File javaFile = new File(newFilePath);
            if (!javaFile.exists())
            {
                javaFile.createNewFile();
            }

            List<String> contents = IOUtils.readFile(fullPath, false);
            StringBuffer content = new StringBuffer();
            for (String string : contents)
            {
                content.append(string).append(newline);
            }
            // System.out.println(content);

            String javaContent = doRegex(content.toString());
            System.out.println(javaContent);

            // 写到java文件
            FileWriter fw = new FileWriter(javaFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(javaContent);
            bw.close();
            fw.close();

            // 删除模板文件
            file.delete();
        }
    }

    /**
     * 正则替换
     *
     * @return
     * @author hjin
     * @cratedate 2013-9-14 下午8:09:07
     */
    public String doRegex(String src)
    {
        StringBuffer buffer = new StringBuffer();

        // 匹配[*]的格式
        String reg = "\\[[\\S&&[^\\[]&&[^\\]]]*\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(src);
        while (matcher.find())
        {
            // 匹配的[*]
            String s1 = matcher.group();
            // 去掉[]
            s1 = s1.substring(1, s1.length() - 1);
            // 反射
            String s2 = (String) ReflectUtil.invokeGetMethod(this, s1);
            // 替换
            matcher.appendReplacement(buffer, s2);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    // ###################################################################

    public String getModule_pkg()
    {
        return module_pkg;
    }

    public void setModule_pkg(String module_pkg)
    {
        this.module_pkg = module_pkg;
    }

    public String getPkgModuleFolder()
    {
        return pkgModuleFolder;
    }

    public void setPkgModuleFolder(String pkgModuleFolder)
    {
        this.pkgModuleFolder = pkgModuleFolder;
    }

    public String getModule_mapper_pkg()
    {
        return module_mapper_pkg;
    }

    public void setModule_mapper_pkg(String module_mapper_pkg)
    {
        this.module_mapper_pkg = module_mapper_pkg;
    }

    public String getPkgMapperFolder()
    {
        return pkgMapperFolder;
    }

    public void setPkgMapperFolder(String pkgMapperFolder)
    {
        this.pkgMapperFolder = pkgMapperFolder;
    }

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getModule_cap()
    {
        return module_cap;
    }

    public void setModule_cap(String module_cap)
    {
        this.module_cap = module_cap;
    }

    public String getModule_uncap()
    {
        return module_uncap;
    }

    public void setModule_uncap(String module_uncap)
    {
        this.module_uncap = module_uncap;
    }

    public String getModule_lower()
    {
        return module_lower;
    }

    public void setModule_lower(String module_lower)
    {
        this.module_lower = module_lower;
    }

    public String getNewline()
    {
        return newline;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getCreatedate()
    {
        return createdate;
    }

    public void setCreatedate(String createdate)
    {
        this.createdate = createdate;
    }

}
