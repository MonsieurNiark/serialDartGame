# serialDartGame
A Java 8 DartGame Project for Analytics for Raspberry Pi, with an Arduino in Serial Input for reading electronic dartboard inputs

I am using an Arduino Mega for reading inputs pulse from the dartboard.
I am using Vaadin 21 for the web interface (avoiding me to have a LCD screen plugged into my Raspberry Pi, i can directly access to my scoreboard with tablet or smartphone)
Raspberry Pi as a server, with Wifi. I can also use a regular computer, just need to be able to read the COM port for Serial input.

For Analytics, I am using Grafana, with an extra plugin for create my custom heatmap of the dartboard.
