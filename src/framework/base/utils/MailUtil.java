package framework.base.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * @author hjin
 * @cratedate 2013-9-20 下午5:13:29
 * 
 */
public class MailUtil
{
	/**
	 * 发送简单邮件
	 * 
	 * @author hjin
	 * @param displayName
	 *            发件人名字,用于显示
	 * @param mailBoxTo
	 *            收件人邮箱地址
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @param mailServer
	 *            邮件服务器(smtp)
	 * @param mailAccount
	 *            发件人邮件地址
	 * @param mailPassword
	 *            发件人邮件密码
	 * @throws Exception
	 * 
	 */
	public static void send(String displayName, String mailBoxTo, String title,
	        String content, String mailServer, String mailAccount,
	        String mailPassword) throws Exception
	{
		// str_content="<a href='www.163.com'>html元素</a>"; //for testing send
		// html mail!
		// 建立邮件会话
		Properties props = new Properties(); // 用来在一个文件中存储键-值对的，其中键和值是用等号分隔的，
		// 存储发送邮件服务器的信息
		props.put("mail.smtp.host", mailServer);
		// 同时通过验证
		props.put("mail.smtp.auth", "true");
		// 根据属性新建一个邮件会话
		Session s = Session.getInstance(props);
		// s.setDebug(true); // 打印调试信息。
		s.setDebug(false);

		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(s);

		// 设置邮件
		displayName = MimeUtility.encodeText(displayName);
		InternetAddress from = new InternetAddress(displayName + " <"
		        + mailAccount + ">"); // xxx@163.com
		message.setFrom(from); // 设置发件人的地址

		// 设置收件人,并设置其接收类型为TO
		InternetAddress to = new InternetAddress(mailBoxTo); // xxx@163.com
		message.setRecipient(Message.RecipientType.TO, to);

		// 设置标题
		message.setSubject(title); // java学习

		// 设置信件内容
		// message.setText(str_content); //发送文本邮件 //你好吗？
		message.setContent(content, "text/html;charset=gbk"); // 发送HTML邮件
		// //<b>你好</b><br><p>大家好</p>

		// 存储邮件信息
		message.saveChanges();

		// 发送邮件
		Transport transport = s.getTransport("smtp");
		// 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
		transport.connect(mailServer, mailAccount, mailPassword);
		// 发送邮件,其中第二个参数是所有已设好的收件人地址
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		// System.out.println(mailAccount + " send mail to " + mailBoxTo
		// + " successsfully");
	}

	public static void main(String[] args) throws Exception
	{
		String nickName = "黄琎";
		String mailBoxTo = "34489659@qq.com";
		String title = "测试邮件";
		String content = "发送内容:找回密码";
		String mailServer = "smtp.163.com";
		String mailAccount = "hjshiwbd@163.com";
		String mailPassword = "87859318wbd";

		send(nickName, mailBoxTo, title, content, mailServer, mailAccount,
		        mailPassword);
	}
}
