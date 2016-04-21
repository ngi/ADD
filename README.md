Eclipse SWT with Maven on Leopard
(Original URL: http://alistairisrael.wordpress.com/2009/07/15/writing-eclipse-swt-apps-using-maven/ )

mvn install:install-file -DgroupId=org.eclipse.swt.cocoa -DartifactId=macosx -Dversion=3.6.1 -Dpackaging=jar -Dfile=swt/swt-3.6.2-cocoa-macosx-x86_64/swt.jar

and run the app with -XstartOnFirstThread option to the Java runtime.

