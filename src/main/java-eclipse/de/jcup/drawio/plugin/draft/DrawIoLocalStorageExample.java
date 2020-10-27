package de.jcup.drawio.plugin.draft;

import java.io.File;
import java.net.MalformedURLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

public class DrawIoLocalStorageExample {
    public static void main(String[] args) {
        new DrawIoLocalStorageExample().start(args);
    }

    public void start(String[] args) {

        Display display = new Display();
        final Shell shell = new Shell(display);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        shell.setLayout(gridLayout);
        ToolBar toolbar = new ToolBar(shell, SWT.NONE);

        GridData data = new GridData();
        data.horizontalSpan = 3;
        toolbar.setLayoutData(data);

        Label labelAddress = new Label(shell, SWT.NONE);
        labelAddress.setText("Address");

        final Text location = new Text(shell, SWT.BORDER);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        data.grabExcessHorizontalSpace = true;
        location.setLayoutData(data);

        final Browser browser = new Browser(shell, SWT.NONE);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.horizontalSpan = 3;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        browser.setLayoutData(data);

        final Label status = new Label(shell, SWT.NONE);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        status.setLayoutData(data);

        final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
        data = new GridData();
        data.horizontalAlignment = GridData.END;
        progressBar.setLayoutData(data);

        browser.addStatusTextListener(new StatusTextListener() {
            public void changed(StatusTextEvent event) {
                status.setText(event.text);
            }
        });
        browser.addLocationListener(new LocationListener() {
            public void changed(LocationEvent event) {
                if (event.top) {
                    location.setText(event.location);
                }
            }

            public void changing(LocationEvent event) {
            }
        });
        new JavaFunction(browser, "myJavaFunction");
        new JavaFunctionExport(browser, "myJavaFunctionExport");

        try {
            browser.setUrl(new File("./src/main/java-eclipse/de/jcup/drawio/plugin/draft/localstorage-svg.html").toURI().toURL().toString());
            

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        shell.open();
        
        // bug workaround: url is not rendered until resize of windows is done?!!? So just set a size...
        shell.setSize(1280,1024);

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    // https://stackoverflow.com/questions/53556280/swt-browser-widget-how-to-listen-to-javascript-events
    private static class JavaFunction extends BrowserFunction {

        JavaFunction(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] arguments) {
            System.out.println("Button pressed!");
            return null;
        }
    }

    private static class JavaFunctionExport extends BrowserFunction {

        JavaFunctionExport(Browser browser, String name) {
            super(browser, name);
        }

        @Override
        public Object function(Object[] arguments) {
            System.out.println("Export pressed!");
            System.out.println("arguments:");
            System.out.println("------------------------------");
            for (Object obj : arguments) {
                System.out.println(obj);
            }
            System.out.println("------------------------------");
            return null;
        }
    }
}
