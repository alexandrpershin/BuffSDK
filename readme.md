
# Solution summary


## About the app 

The app provides an SDK with a custom view named BuffView.
BuffView is custom view which provides to client dialog with questions and answers.
Client can use BuffView in XML layout.

Important things to keep in mind before use:

Use BuffView.initialize() method to passs the error callback and optional success callback.
This method will start work of SDK.

Use BuffView.onPause(), BuffView.stop() and BuffView.resume() to stop/pause and resume work
of SDK. These methods should be called from client side in case when Activity/Fragment was stopped/paused or resumed.
 

## Tech stack
 
 1. Kotlin Coroutines for network requests and background operations.
 2. Koin library for dependency injection.
 3. Chuck interceptor library to visualize network requests (is disabled for release version).
 4. Ktx library for some cool extensions
 5. Retrofit library for network communications
 6. MockK library for unit testing


## How app works?

1. The client uses BuffView on its side and calls BuffView.initialize ()
2. The SDK waits for an initial delay and makes a request to the backend to get the data.
3. Once the data is received, it is parsed and displayed to the client using BuffView.
4. This happens every 30 seconds while BuffView.stop () is called.
5. After BuffView is displayed, the user is given n seconds to read the question and select one of the answer options.
6. After the user has selected the option, the timer freezes and the BuffView is hidden with a delay of 2 seconds.
7. If the user pressed the close button, BuffView will hide immediately.
8. If the user hasn't clicked anything, BuffView will disappear when the timer expires.  
9. Sdk allows to pas clients errorCallback to handle SDK errors on client side.

