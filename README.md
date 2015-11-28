# SmartWebView

Android skeleton for a web view based app. 

## Back Navigation Flow

The application implements a system to emulate the native back navigation (hierachical), that means the app will always go to the father directory. If the app is on the root, when the user press back the app will be closed. Because of this, this application only works with hierachical web pages.

The application also implements a non-host policy to open links and resources from other sites (outside your host) in a default browser. This is 100% configurable and you can implement a white/black list for this functionality.

## Offline handling control

If the app looses internet connection (Wifi or mobile) or an error loading the page happend, an offline page will be loaded. This page is stored on the assert folder so is easily to change it. 


