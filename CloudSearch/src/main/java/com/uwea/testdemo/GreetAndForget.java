package com.uwea.testdemo;

public class GreetAndForget {
	   static public void main(String[] args) {

	        if (args.length <= 1) {
	            System.out.println(
	                "Enter base path and greeter class names as args.");
	            return;
	        }

	        for (int i = 1; i < args.length; ++i) {
	            try {

	                GreeterClassLoader gcl =
	                    new GreeterClassLoader(args[0]);

	                // Load the greeter specified on the command line
	                Class c = gcl.loadClass(args[i]);

	                // Instantiate it into a greeter object
	                Object o = c.newInstance();

	                // Cast the Object ref to the Greeter interface type
	                // so greet() can be invoked on it
	                Greeter greeter = (Greeter) o;

	                // Greet the world in this greeter's special way
	                greeter.greet();

	                // Forget the class loader object, Class
	                // instance, and greeter object
	                gcl = null;
	                c = null;
	                o = null;
	                greeter = null;

	                // At this point, the types loaded through the
	                // GreeterClassLoader object created at the top of
	                // this for loop are unreferenced and can be unloaded
	                // by the virtual machine.
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}