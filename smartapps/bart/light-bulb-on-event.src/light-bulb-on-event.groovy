/**
 *  Light Dimmer on Event
 *
 *  Copyright 2015 Bart
 */
definition(
    name: "Light Bulb on Event",
    namespace: "Bart",
    author: "Bart",
    description: "Increase light's brightness based on event like motion, door, etc. and back to normal state",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

    section("Bulb Options"){
        input(name: "dimmers", type: "capability.colorControl", title: "Bulbs", required: true, multiple: true)
        input(name: "onEventDimLevel", type: "enum", options: [[0:"Off"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]], title: "On Event Dimmer Level", required: true, multiple: false)
        input(name: "onEventColor", type: "enum", options: [[0:"White"],[3:"Warm White"],[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:"Red"]], title: "On Event Dimmer Color", required: false, multiple: false)
        input(name: "afterEventDimLevel", type: "enum", options: [[0:"Off"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]], title: "After Event Dimmer Level", required: true, multiple: false)
    	input(name: "afterEventColor", type: "enum", options: [[0:"White"],[3:"Warm White"],[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:"Red"]], title: "After Event Color", required: false, multiple: false)
    }
    section("Event Triger Sensors"){
        input(name: "motion", type: "capability.motionSensor", title: "Motion sensors", required: false, multiple: true)
        input(name: "acceleration", type: "capability.accelerationSensor", title: "Acceleration sensors", required: false, multiple: true)
        input(name: "contact", type: "capability.contactSensor", title: "Contact sensors", required: false, multiple: true)
        input(name: "lock", type: "capability.lock", title: "Door locks", required: false, multiple: true)
    }
    section("Light Sensor"){
        input(name: "luxSensors", type: "capability.illuminanceMeasurement", title: "Lux sensor", required: true, multiple: false)
    }
    section("Event Options"){
        input(name: "onEventLux", type: "number", title: "ON & Triger if lux is below", required: true, multiple: false)
        input(name: "turnOffAfterTime", type: "number", title: "Turn off after (seconds)", required: false, multiple: false)
    }
}

def installed(){ 
    initialize()
}

def updated() {
    unschedule()
    unsubscribe()
    initialize()
}

def initialize() {
		        
        if(luxSensors) {
            subscribe(settings.luxSensors, "illuminance", dayTimeOnOff)
        }        
		if(motion) {
            subscribe(settings.motion, "motion", motionAction)
        }
        if(acceleration) {
            subscribe(settings.acceleration, "acceleration", accelerationAction)
        }
        if(contact) {
            subscribe(settings.contact, "contact", contactAction)
        }
        if(lock) {
            subscribe(settings.lock, "lock", lockAction)
        }     
}
def dayTimeOnOff(evt) {
	def AfterEventLevel = afterEventDimLevel as Integer
    
    if (luxSensors.latestValue("illuminance") >= onEventLux){
    settings.dimmers?.setLevel(0)
    }
    else {
        //def dimmersCurrentState = dimmers.currentState("switchLevel")
        //if(dimmersCurrentState == AfterEventLevel)
    	
    settings.dimmers?.setLevel(AfterEventLevel)
	}
}
def motionAction(evt) {
	def onEventHue = onEventColor as Integer
	def Eventlevel = onEventDimLevel as Integer

	if (luxSensors.latestValue("illuminance") <= onEventLux){
		if (motion.latestValue("motion").contains("inactive")) {
        def Delay = turnOffAfterTime as Integer
        runIn(Delay, normalDimLevel)
        }
        else if (onEventHue == 0){ // Cold White        
		settings.dimmers?.setColorTemperature(0)  // Cold White 0      
        settings.dimmers?.setLevel(Eventlevel)
       	}
        else if (onEventHue == 3){ // Warm White 
		settings.dimmers?.setColorTemperature(99) // Warm White 99        
        settings.dimmers?.setLevel(Eventlevel)         
       	}
       	else { // other collors
        
        settings.dimmers?.setColor([hue: onEventHue, saturation: 100]) // [[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:Red"]],
		settings.dimmers?.setLevel(Eventlevel)  
        }   
	}
}
def accelerationAction(evt) {
	def onEventHue = onEventColor as Integer
	def Eventlevel = onEventDimLevel as Integer
    
	if (luxSensors.latestValue("illuminance") <= onEventLux){
		if (acceleration.latestValue("acceleration").contains("inactive")) {
        def Delay = turnOffAfterTime as Integer
        runIn(Delay, normalDimLevel)
        }
        else if (onEventHue == 0){ // Cold White        
		settings.dimmers?.setColorTemperature(0)  // Cold White 0      
        settings.dimmers?.setLevel(Eventlevel)
       	}
        else if (onEventHue == 3){ // Warm White 
		settings.dimmers?.setColorTemperature(99) // Warm White 99        
        settings.dimmers?.setLevel(Eventlevel)         
       	}
       	else { // other collors
        settings.dimmers?.setColor([hue: onEventHue, saturation: 100]) // [[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:Red"]],
		settings.dimmers?.setLevel(Eventlevel)  
        }     
	}
}
def contactAction(evt) {
	def onEventHue = onEventColor as Integer
	def Eventlevel = onEventDimLevel as Integer
    
	if (luxSensors.latestValue("illuminance") <= onEventLux){
		if (contact.latestValue("contact").contains("closed")) {
        def Delay = turnOffAfterTime as Integer
        runIn(Delay, normalDimLevel)
        }
        else if (onEventHue == 0){ // Cold White        
		settings.dimmers?.setColorTemperature(0)  // Cold White 0      
        settings.dimmers?.setLevel(Eventlevel)
       	}
        else if (onEventHue == 3){ // Warm White 
		settings.dimmers?.setColorTemperature(99) // Warm White 99        
        settings.dimmers?.setLevel(Eventlevel)         
       	}
       	else { // other collors
        settings.dimmers?.setColor([hue: onEventHue, saturation: 100]) // [[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:Red"]],
		settings.dimmers?.setLevel(Eventlevel)  
        }     
	}
}

def lockAction(evt) {
	def onEventHue = onEventColor as Integer
	def Eventlevel = onEventDimLevel as Integer
    
	if (luxSensors.latestValue("illuminance") <= onEventLux){
		if (lock.latestValue("lock").contains("locked")) {
        def Delay = turnOffAfterTime as Integer
        runIn(Delay, normalDimLevel)
        }
        else if (onEventHue == 0){ // Cold White        
		settings.dimmers?.setColorTemperature(0)  // Cold White 0      
        settings.dimmers?.setLevel(Eventlevel)
       	}
        else if (onEventHue == 3){ // Warm White 
		settings.dimmers?.setColorTemperature(99) // Warm White 99        
        settings.dimmers?.setLevel(Eventlevel)         
       	}
       	else { // other collors
        settings.dimmers?.setColor([hue: onEventHue, saturation: 100]) // [[5:"Orange"],[12:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:Red"]],
		settings.dimmers?.setLevel(Eventlevel)  
        }    
	}
}

def normalDimLevel() {        
        def AfterEventLevel = afterEventDimLevel as Integer
        settings.dimmers?.setLevel(AfterEventLevel)
        def DelayOff = 1 as Integer
        runIn(DelayOff, normalColor)
		}

def normalColor() {      
		
        def afterEventHue = afterEventColor as Integer

    	if (afterEventHue == 0){ // White
		settings.dimmers?.setColorTemperature(0) // Cold White 0
       	}
        else if (afterEventHue == 3){ // Cold White  
        settings.dimmers?.setColorTemperature(99) // Warm White 99      
       	}
        else{        
    	settings.dimmers?.setColor([hue: afterEventHue, saturation: 100])
        }
}    
