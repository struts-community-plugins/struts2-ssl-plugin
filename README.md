#labels Featured
= Overview =

This struts2 plugin is designed to redirect request in http or https mode based on a annotation available in class or method level.


= Features =

  * Annotation and Non-annotation based usage
  * Class level or method level annotation usage
  * Port configuration
  * configuration using constants (In version 1.1) 


= Usage =

The plug-in extends struts-default so you can extend the "ssl-default" package to get these additional features.

= Examples =

   * How to configure all actions in a package to go in https mode. 
 Note: Setting the useAnnotations property to false will override the default behavior of the plugin to ignore the annotation and make every action in the package to redirect in https mode.

{{{ 
	<package name="myPackage1" extends="ssl-default" namespace="/secured">

        <!-- How to Configureing the Interceptor to use user sepecified properties.-->
        <interceptors>
        <interceptor-stack name="secureStack">
        <interceptor-ref name="secure">
            <param name="useAnnotations">false</param>
         </interceptor-ref>
         <interceptor-ref name="defaultStack"/>
        </interceptor-stack>
        </interceptors>

        <action name="index" class="com.googlecode.sslplugin.example.IndexAction">
		<result>/jsp/index.jsp</result>
	</action>

        <action name="index1" class="com.googlecode.sslplugin.example.IndexAction" method="execute1">
		<result>/jsp/index1.jsp</result>
	</action>


        <action name="helloWorld" class="com.googlecode.sslplugin.example.HelloWorldAction">
		<result name="input">/jsp/index.jsp</result>
		<result>/jsp/helloWorld.jsp</result>
	</action>

        <action name="helloWorld1" class="com.googlecode.sslplugin.example.HelloWorldAction" method="execute1">
		<result name="input">/jsp/index1.jsp</result>
		<result>/jsp/helloWorld.jsp</result>
	</action>

    </package>
}}}

 * How to configure the plugin to use user specified ports
 Note: setting the httpPort or httpsPort will override the default ports used by the plugin (8080 and 8443) , based on the annotation (@Secured) available in the class level or method level redirection will happen.

{{{
    <package name="myPackage2" extends="ssl-default" namespace="/annotaed">

        <!-- How to Configureing the Interceptor to use user sepecified ports.-->
        <interceptors>
        <interceptor-stack name="secureStack">
        <interceptor-ref name="secure">
            <param name="httpsPort">443</param>
         </interceptor-ref>
         <interceptor-ref name="defaultStack"/>
        </interceptor-stack>
        </interceptors>

        <action name="index" class="com.googlecode.sslplugin.example.IndexAction">
		<result>/jsp/index.jsp</result>
	</action>

        <action name="index1" class="com.googlecode.sslplugin.example.IndexAction" method="execute1">
		<result>/jsp/index1.jsp</result>
	</action>


        <action name="helloWorld" class="com.googlecode.sslplugin.example.HelloWorldAction">
		<result name="input">/jsp/index.jsp</result>
		<result>/jsp/helloWorld.jsp</result>
	</action>

        <action name="helloWorld1" class="com.googlecode.sslplugin.example.HelloWorldAction" method="execute1">
		<result name="input">/jsp/index1.jsp</result>
		<result>/jsp/helloWorld.jsp</result>
	</action>

    </package>
}}}

 * How to configure all the methods in the class to redirect in https mode.
 Note: if the @Secured annotation is present in the class level all the action methods will redirect in https mode.
 
{{{
@Secured
public class IndexAction extends ActionSupport {

        // configure the logger for this class
    private static Log log = LogFactory.getLog(IndexAction.class);

    /**
     * This method is called in https mode, as it has the @Secured annotation in class level.
     *
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        log.info("Inside execute() method");
        return SUCCESS;
    }


    /**
     * This method is called in https mode, as it has the @Secured annotation in class level.
     *
     * @return
     * @throws Exception
     */
    public String execute1() throws Exception {
        log.info("Inside execute1() method");
        return SUCCESS;
    }


}
}}} 

 * How to configure specific methods in a class to redirect in https mode.
Note : only the action method execute1() method will redirect in https mode as @Secured annotation is only present in that method.

{{{
public class IndexAction extends ActionSupport {

        // configure the logger for this class
    private static Log log = LogFactory.getLog(IndexAction.class);

    /**
     * This method will not be called in https mode.
     *
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        log.info("Inside execute() method");
        return SUCCESS;
    }


    /**
     * This method is called in https mode, as it has the @Secured annotation
     *
     * @return
     * @throws Exception
     */
    @Secured
    public String execute1() throws Exception {
        log.info("Inside execute1() method");
        return SUCCESS;
    }


}
}}}

= Settings =

The following settings can be customized.

 * httpsPort – https port number to use
 * httpPort – http port number to use
 * useAnnotations – true/false to use annotations or not.

= Configuration reference =

* Only support in version 1.1 or higher *

Add a constant element to your struts config file to change the value of a configuration setting, like:

{{{
<constant name="struts2.sslplugin.httpPort" value="80"/>
}}}

|| *Name* || *Default Value* || *Description* ||
||struts2.sslplugin.httpPort||8080||http port use for redirection||
||struts2.sslplugin.httpsPort||8443||https port use for redirection||
||struts2.sslplugin.annotations||true||use annotations||



= Installation = 

This plug-in can be installed by copying the plug-in jar into your application's /WEB-INF/lib directory. No other files need to be copied or created