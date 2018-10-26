导入安全证书到jdk
一：.导入证书

1.打开doc窗口，打开cmd，执行命令：

keytool -import -file d:\client1.crt -keystore "C:\Program Files\Java\jre1.8.0_191\lib\security\cacerts" -alias client1.com
-file 指定证书文件的位置

-alias  指定证书的别名

2.输入密钥库口令：

changeit

3.是否信任此证书? [否]:  
y

二：验证证书是否导入
keytool -list -keystore "C:\Program Files\Java\jre1.8.0_191\lib\security\cacerts"| findstr /i client1.com