definition(
    name: "Water Sensor Toggle Switch ON / OFF",
    namespace: "Bart",
    author: "Bart",
    description: "ON /OFF light based on Water Sensor state",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

section("Arduino Device") {
		input(name: "arduinoDevice", type: "capability.waterSensor", title: "Arduino Device", multiple: false, required: true)		
		}
section("First Water Sensor") {
        input(name: "waterSensorLocation1", type: "enum", options: [[UpstairsShower:"Upstairs Shower"],[DownstairsShower:"Downstairs Shower"]], title: "Water Sensor Location", required: true, multiple: false)
		input(name: "shower1Switches", type: "capability.switch", title: "Switch", multiple: true, required: true)
        input(name: "switch1Action", type: "enum", options: ["Turn On","Turn Off", "Turn On & Off"], title: "Action", required: true, multiple: false)
    	}
section("Second Water Sensor") {
        input(name: "waterSensorLocation2", type: "enum", options: [[UpstairsShower:"Upstairs Shower"],[DownstairsShower:"Downstairs Shower"]], title: "Water Sensor Location", required: false, multiple: false)
		input(name: "shower2Switches", type: "capability.switch", title: "Switch", multiple: true, required: false)
        input(name: "switch2Action", type: "enum", options: ["Turn On","Turn Off", "Turn On & Off"], title: "Action", required: false, multiple: false)
    	}

      
      
}


def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(arduinoDevice, waterSensorLocation1, waterSensor1Event)
    subscribe(arduinoDevice, waterSensorLocation2, waterSensor2Event)
}     

def waterSensor1Event(evt){
	log.debug "$arduinoDevice reports $waterSensorLocation1 is $evt.value"
    	
       
    if (switch1Action == "Turn On"){
        if (evt.value.contains("wet")) { 
            shower1Switches.on()
        }
    }   
    if (switch1Action == "Turn Off"){
        if (evt.value.contains("dry")) { 
            shower1Switches.off()   
        }
    }
    if (switch1Action == "Turn On & Off"){    
        if (evt.value.contains("wet")) { 
            shower1Switches.on()
        }
        if (evt.value.contains("dry")) { 
            shower1Switches.off()   
        }	
    }
}
def waterSensor2Event(evt){
	
    log.debug "$arduinoDevice reports $waterSensorLocation2 is $evt.value"	
       
    if (switch2Action == "Turn On"){
        if (evt.value.contains("wet")) { 
            shower2Switches.on()
        }
    }   
    if (switch2Action == "Turn Off"){
        if (evt.value.contains("dry")) { 
            shower2Switches.off()   
        }
    }
    if (switch2Action == "Turn On & Off"){    
        if (evt.value.contains("wet")) { 
            shower2Switches.on()
        }
        if (evt.value.contains("dry")) { 
            shower2Switches.off()   
        }	
    }
}        
