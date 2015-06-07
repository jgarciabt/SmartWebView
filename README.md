# SmartWebView

Android skeleton for a web view based app. 

## Back Navigation Flow

The application implements a system to emulate the native back navigation (hierachical), that means the app will always go to the father directory. If the app is on the root, when the user press back the app will be closed.

Because of that, this application only works with hierachical web pages.

## Offline handling control

If the app looses internet connection (Wifi or mobile) or an error loading the page happend, an offline page will be loaded. This page is stored on the assert folder so is easily to change it. 

To notify the user the app is running in offline mode, a [Snackbar](https://github.com/nispok/snackbar) is displayed. You can perform any action when the user dissmis the Snackbar if you need it.






