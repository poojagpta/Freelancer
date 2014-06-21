package com.xyz.cl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * See article http://www.drdobbs.com/jvm/java-custom-class-loaders/184404138
 * @author pooja
 *
 */
public class CustomClassLoader extends ClassLoader {

	/*found in the cache, and couldn't be loaded by the system loader.  Notice how 
	findClass is not responsible for loading any system classes like class loaders 
	for Java 1.1.
	*/
    public Class findClass(String name) throws ClassNotFoundException
    {
        byte[]				classData;
        Class				newClass;
        
        /* Use CustomClassLoader to read the class data */
        classData = this.readClassFile(name);
        if (classData == null) {
        	throw new ClassNotFoundException();
        }
        newClass = defineClass(name, classData, 0, classData.length);
        System.out.println("Class " + newClass.getName() + " loaded by CustomLoader2");
        return newClass;
    }
    
	/** Not required.  Just put this here to watch the class loading process.
	*/
	public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		System.out.println("Loading class " + name);
		return super.loadClass(name, resolve);
	}

	public byte[] readClassFile(String className)
    {
		byte[]				inputBytes = null;
		int					bytesRead = 0;
		File				classFile = null;
		FileInputStream		fileStream = null;
		String				fileName;
		String				pathName;
		
		/* Support package names by converting the class name to 
		a file name by replacing the "." in the package name with a 
		path separator. This only applies because the class file is being 
		loaded from a file.
		*/
		fileName = className.replace('.', File.separatorChar);
		pathName = ".//TestClass//" + fileName + ".class";  //C:\\dev\\freelancer\\SampleMavenProj\\
		
		/* Read the class file into a buffer in one shot */
		try {
			classFile = new File(pathName);
			fileStream = new FileInputStream(classFile);
			
			/* Create a buffer big enough to hold the entire class file. */
			bytesRead = (int)classFile.length();
			inputBytes = new byte[bytesRead];
			
			/* Read the class file into the buffer */
			try {
				bytesRead = fileStream.read(inputBytes);
			}
			catch (IOException e) {
				System.out.println("Exception trying to read the class file");
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + classFile.toString() + " doesn't exist");	
		}
		return inputBytes;
	}
}
