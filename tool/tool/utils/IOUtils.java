package tool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class IOUtils
{
	/**
	 * 读取文本文件,每一行为一个元素,组成list<String>
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> readFile(String path)
	{
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while (br.ready())
			{
				String ss = br.readLine();
				ss = ss.trim();
				list.add(ss);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
				if (fr != null)
				{
					fr.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 文件写入
	 * 
	 * @param path
	 * @param fileName
	 * @param content
	 * @param isAppend
	 *            若true则续写,否则重新写文件
	 * @throws IOException
	 */
	public static void writeFile(String path, String fileName, String content,
	        boolean isAppend)
	{
		File file = new File(path + "/" + fileName);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(file, isAppend);
			bw = new BufferedWriter(fw);
			bw.write(content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bw != null)
				{
					bw.close();
				}
				if (fw != null)
				{
					fw.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void writeObjFile(String path, String fileName,
	        Object content, boolean isAppend)
	{
		File file = new File(path + "/" + fileName);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(file, isAppend);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (oos != null)
				{
					oos.close();
				}
				if (fos != null)
				{
					fos.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
