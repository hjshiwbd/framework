::项目路径
set path1="..\主项目\iispace\WebRoot\WEB-INF\lib\"

call ant -buildfile build.xml
pause
::echo 复制framework.jar到项目路径:%path1%
::copy framework.jar %path1%
::pause
