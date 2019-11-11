# milight
scala milight / limitless led library


## Usage from SBT

Add the following to your dependencies
```
"com.larroy.milight" %% "milight" % "0.3-SNAPSHOT"
```


## Example usage
```
import com.larroy.milight._
// Provide the IP address of the WIFI bridge
val w = WifiBridge("192.168.178.20")
// Set the color of group 4 to red
w.color(4,Color(255,0,0))
// You can also set the hue value
w.hue(4,128)
// turn off group 4
w.off(4)
// turn off all groups
w.off(0)
// turn on all groups
w.on(0)
// turn on 4 and set to red
w.color(4,Color(255,0,0))
// You can also queue raw commands
w.queue(Command(64, 127))
w.queue(Command(64, 1))

// Close the socket and the worker thread when done
w.close()
```

