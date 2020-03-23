# ImageBlur Library
This library can be used to add a blur effect to pictures. Asynchronous effect makes you assign the most stable and fastest effect. Blur your pictures with ImageBlur!

Sample Pictures
https://imgur.com/49z1xdA


# How does it work? (Sample codes)
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.birdeveloper:ImageBlur:1.0'
	}
	
Step 3. Add the activity or fragment


	ImageBlur.withContext(context)
                .blur(25f)
                .asyncTask(true)
                .load(R.drawable.profile)
                .into(imageView)
		
everything is ok! I hope you will be satisfied using it. Call the police if you encounter any problems: D
