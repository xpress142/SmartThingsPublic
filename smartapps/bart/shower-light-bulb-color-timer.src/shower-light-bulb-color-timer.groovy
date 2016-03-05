/**
 *  Light Dimmer on Event
 *
 *  Copyright 2015 Bart
 */
definition(
    name: "Shower Light Bulb Color Timer",
    namespace: "Bart",
    author: "Bart",
    description: "Change light bulb color after time in order to measure time in the shower",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

    section("Shower Bulb"){
        input(name: "showerBulb", type: "capability.colorControl", title: "Shower Bulb", required: true, multiple: false)
        }
    section("First Time Period"){    
        input(name: "firstPeriodTime", type: "enum", options: [[30:"30 Seconds"],[60:"1 Minute"],[120:"2 Minutes"],[180:"3 Minutes"],[240:"4 Minutes"],[300:"5 Minutes"]], title: "Time", required: false, multiple: false)
        input(name: "firstPeriodColor", type: "enum", options: [[0:"White"],[3:"Warm White"],[5:"Orange"],[10:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:"Red"]], title: "Color", required: false, multiple: false)
    	    	
		}
    section("Second Time Period"){
    	input(name: "secondPeriodTime", type: "enum", options: [[30:"30 Seconds"],[60:"1 Minute"],[120:"2 Minutes"],[180:"3 Minutes"],[240:"4 Minutes"],[300:"5 Minutes"]], title: "Time", required: false, multiple: false)
    	input(name: "secondPeriodColor", type: "enum", options: [[0:"White"],[3:"Warm White"],[5:"Orange"],[10:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:"Red"]], title: "Color", required: false, multiple: false)
     	}
    section("Third Time Period"){
        input(name: "thirdPeriodTime", type: "enum", options: [[30:"30 Seconds"],[60:"1 Minute"],[120:"2 Minutes"],[180:"3 Minutes"],[240:"4 Minutes"],[300:"5 Minutes"]], title: "Time", required: false, multiple: false)
        input(name: "thirdPeriodColor", type: "enum", options: [[0:"White"],[3:"Warm White"],[5:"Orange"],[10:"Yellow"],[30:"Green"],[60:"Blue"],[80:"Purple"],[100:"Red"]], title: "Color", required: false, multiple: false)
    
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
		        
        subscribe(showerBulb, "switch", bulbEvent)


        
}
def bulbEvent(evt) {
	
     if (showerBulb.currentValue('switch').contains('on')) {
		 log.debug "Bulb On"
         
         def firstDelay = firstPeriodTime as Integer
         runIn(firstDelay, firstColor)
         
         def secondDelay = secondPeriodTime as Integer
         runIn(secondDelay, secondColor)
         
         def thirdDelay = thirdPeriodTime as Integer
         runIn(thirdDelay, thirdColor)
	}
    if (showerBulb.currentValue('switch').contains('off')) {
		 
         log.debug "Bulb Off"
        unschedule(firstColor)
        unschedule(secondColor)
        unschedule(thirdColor)
        unschedule(resetColor)
         showerBulb.reset()
	}
}
    
def firstColor() { 
	
    def firstColor = firstPeriodColor as Integer
    showerBulb.setColor([hue: firstColor, saturation: 100]) 
   
	//log.debug "First color set to: ${firstPeriodColor}"
    
    }
    
def secondColor() { 
	
    def secondColor = secondPeriodColor as Integer
    showerBulb.setColor([hue: secondColor, saturation: 100]) 
    
	//log.debug "Second color set to: ${secondPeriodColor}"
   
    
    }
    
def thirdColor() { 
	
    def thirdColor = thirdPeriodColor as Integer
    showerBulb.setColor([hue: thirdColor, saturation: 100]) 
    
	//log.debug "Third color set to: ${thirdPeriodColor}"
    

    runIn(60, resetColor)
    }
    
def resetColor() { 

	 showerBulb.reset()
     
     }






    /*
    def reset() {
		log.debug "reset()"
		sendEvent(name: "color", value: "#ffffff")
		setColorTemperature(99)
    }
	*/