#Android Verify Error

Simple example to illustrate when VerifyError is happening.

##Problem

To stump on the problem one need to mess with generics:

First we'll need a parent class with generic parameter:
 
```java
 public class BaseGeneric<T> {

  T data;

}
```

Note the `data` field of generic type. 
Next, we need a class with a generic method:

```java
public class Convertor {

  public <T> T convert(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      return null;
    }
  };

}
```

And to actually cause a VerifyError, let's have a descendant for `BaseGeneric`:

```java
public class Generic extends BaseGeneric<String> {

  public String assign() {
    return data = new Convertor().convert(String.class);
  }

}
```

Calling `Generic.assign()` anywhere in an Android application will cause a runtime VerifyError.
Tested on both Dalvik and ART.

Dalvik stacktrace:

      java.lang.VerifyError: io.github.aectann.verifyerror.Generic
                  at io.github.aectann.verifyerror.MainActivity.onCreate(MainActivity.java:15)
                  at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1047)
                  at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1611)
                  at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1663)
                  at android.app.ActivityThread.access$1500(ActivityThread.java:117)
                  at android.app.ActivityThread$H.handleMessage(ActivityThread.java:931)
                  at android.os.Handler.dispatchMessage(Handler.java:99)
                  at android.os.Looper.loop(Looper.java:130)
                  at android.app.ActivityThread.main(ActivityThread.java:3683)
                  at java.lang.reflect.Method.invokeNative(Native Method)
                  at java.lang.reflect.Method.invoke(Method.java:507)
                  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
                  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
                  at dalvik.system.NativeStart.main(Native Method)
                  
ART stacktrace:

      java.lang.VerifyError: Rejecting class io.github.aectann.verifyerror.Generic because it failed compile-time verification (declaration of 'io.github.aectann.verifyerror.Generic' appears in /data/app/io.github.aectann.verifyerror-1.apk)
                  at io.github.aectann.verifyerror.MainActivity.onCreate(MainActivity.java:15)
                  at android.app.Activity.performCreate(Activity.java:5231)
                  at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1087)
                  at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2159)
                  at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2245)
                  at android.app.ActivityThread.access$800(ActivityThread.java:135)
                  at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1196)
                  at android.os.Handler.dispatchMessage(Handler.java:102)
                  at android.os.Looper.loop(Looper.java:136)
                  at android.app.ActivityThread.main(ActivityThread.java:5017)
                  at java.lang.reflect.Method.invoke(Native Method)
                  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:779)
                  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:595)

##Workaround
To avoid the problem assign return value of the generic method to a local variable first:

```java
public class GenericFixed extends BaseGeneric<String> {

  public String assign() {
    String convert = new Convertor().convert(String.class);
    return data = convert;
  }

}
```


